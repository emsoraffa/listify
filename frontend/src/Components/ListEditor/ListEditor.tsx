import React, { useMemo, useCallback, useState, useContext, useRef, useImperativeHandle, forwardRef } from 'react';
import { Slate, Editable, withReact, ReactEditor } from 'slate-react';
import { BaseEditor, createEditor, Descendant, Editor, Element as SlateElement, Path, Point, Range as SlateRange, Transforms } from 'slate';
import { withHistory, HistoryEditor } from 'slate-history';
import { TextElement, TextElementProps } from '../TextElement';
import { CheckListItemElement } from '../CheckListItemElement';
import { Leaf } from '../Leaf/Leaf';

interface ListEditorProps {
  listItems: Descendant[];
  setListItems: (value: Descendant[]) => void;
  debouncedSave: () => void;
}

interface ListEditorRef {
  focus: () => void;
}

export const ListEditor = forwardRef<ListEditorRef, ListEditorProps>(({ listItems, setListItems, debouncedSave }, ref) => {
  const editor = useMemo(
    () => withChecklists(withHistory(withReact(createEditor() as BaseEditor & ReactEditor & HistoryEditor))),
    []
  );

  const renderElement = useCallback((props: any) => {
    switch (props.element.type) {
      case 'check-list-item':
        return <CheckListItemElement {...props} />;
      default:
        return <p {...props.attributes}>{props.children}</p>;
    }
  }, []);

  const renderLeaf = useCallback((props: any) => <Leaf {...props} />, []);

  useImperativeHandle(ref, () => ({
    focus: () => {
      ReactEditor.focus(editor);
    }
  }));

  const onChange = (newValue: Descendant[]) => {
    setListItems(newValue);
    //TODO: fix autosave 
    //debouncedSave();
  };

  const initialValue = listItems.length === 0 ? [
    {
      type: 'check-list-item',
      id: null,
      checked: false,
      children: [{ text: 'Add your items here...', fontSize: '24px' }],
    }
  ] : listItems;


  return (
    <Slate editor={editor} initialValue={listItems} onChange={onChange}>
      <Editable
        renderElement={renderElement}
        renderLeaf={renderLeaf}
        placeholder="      Add your items here.."
        spellCheck
        autoFocus
      />
    </Slate>
  );
});

//Overrides the deleteBackward method to transform a checklist item into a paragraph 
//if the cursor is at the start of a checklist item and the delete or backspace key is pressed.
const withChecklists = (editor: BaseEditor & ReactEditor & HistoryEditor) => {
  const { deleteBackward, insertBreak } = editor;

  editor.deleteBackward = (...args) => {
    const { selection } = editor;
    if (selection && SlateRange.isCollapsed(selection)) {
      const [match] = Editor.nodes(editor, {
        match: n =>
          !Editor.isEditor(n) &&
          SlateElement.isElement(n) &&
          n.type === 'check-list-item',
      });

      if (match) {
        const [, path] = match;
        const start = Editor.start(editor, path);

        if (Point.equals(selection.anchor, start)) {
          const newProperties: Partial<SlateElement> = {
            type: 'paragraph',
          };
          Transforms.setNodes<SlateElement>(editor, newProperties, {
            match: n =>
              !Editor.isEditor(n) &&
              SlateElement.isElement(n) &&
              n.type === 'check-list-item',
          });
          return;
        }
      }
    }

    deleteBackward(...args);
  };

  editor.insertBreak = () => {
    const { selection } = editor;
    if (selection) {
      const [match] = Editor.nodes(editor, {
        match: n => SlateElement.isElement(n) && n.type === 'check-list-item',
        at: selection,
      });

      if (match) {
        const [node, path] = match as [SlateElement, Path];
        if (Editor.isEnd(editor, selection.anchor, path)) {
          // Insert a new checklist item with id: null when "Enter" is pressed at the end of a checklist item
          const newChecklistItem: SlateElement = {
            type: 'check-list-item',
            id: null,
            checked: false,
            children: [{ text: '', fontSize: "24px" }],
          };
          Transforms.insertNodes(editor, newChecklistItem, { at: Path.next(path) });
          // Move the cursor to the start of the new checklist item
          const newPath = Path.next(path);
          Transforms.select(editor, Editor.start(editor, newPath));
          return;
        }
      }
    }

    insertBreak();
  };

  return editor;
};

export default withChecklists;
