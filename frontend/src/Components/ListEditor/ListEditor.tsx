import React, { useMemo, useCallback, useState, useContext, useRef, useImperativeHandle, forwardRef } from 'react';
import { Slate, Editable, withReact, ReactEditor } from 'slate-react';
import { BaseEditor, createEditor, Descendant, Editor, Element as SlateElement, Point, Range as SlateRange, Transforms } from 'slate';
import { withHistory, HistoryEditor } from 'slate-history';
import { TextElement, TextElementProps } from '../TextElement';

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

  const renderElement = useCallback((props: TextElementProps) => <TextElement {...props} />, []);

  useImperativeHandle(ref, () => ({
    focus: () => {
      ReactEditor.focus(editor);
    }
  }));

  const onChange = (newValue: Descendant[]) => {
    setListItems(newValue);
    debouncedSave();
  };

  return (
    <Slate editor={editor} initialValue={listItems} onChange={onChange}>
      <Editable
        renderElement={renderElement}
        placeholder="Get to work…"
        spellCheck
        autoFocus
      />
    </Slate>
  );
});

//Overrides the deleteBackward method to transform a checklist item into a paragraph 
//if the cursor is at the start of a checklist item and the delete or backspace key is pressed.
const withChecklists = (editor: BaseEditor & ReactEditor & HistoryEditor) => {
  const { deleteBackward } = editor;

  editor.deleteBackward = (...args) => {
    const { selection } = editor;

    if (selection && SlateRange.isCollapsed(selection)) {
      const [match] = Editor.nodes(editor, {
        match: (n) =>
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
          Transforms.setNodes(editor, newProperties, {
            match: (n) =>
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

  return editor;
};
