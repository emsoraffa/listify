import React, { useMemo, useCallback } from 'react';
import {
  Slate,
  Editable,
  withReact,
  useSlateStatic,
  useReadOnly,
  ReactEditor,
} from 'slate-react';
import {
  Editor,
  Transforms,
  Range,
  Point,
  createEditor,
  Descendant,
  BaseEditor,
  Element as SlateElement,
} from 'slate';
import { withHistory, HistoryEditor } from 'slate-history';
import './styles.css'; // Import the CSS file
import { TextElement, TextElementProps } from '../TextElement';

const initialValue: Descendant[] = [
  {
    type: 'check-list-item',
    checked: false,
    children: [{ text: 'Milk' }],
  },
  {
    type: 'check-list-item',
    checked: false,
    children: [{ text: 'Eggs' }],
  },
  {
    type: 'check-list-item',
    checked: true,
    children: [{ text: 'Bread' }],
  },
  {
    type: 'check-list-item',
    checked: false,
    children: [{ text: 'Butter' }],
  },
];

//Overrides the deleteBackward method to transform a checklist item into a paragraph 
//if the cursor is at the start of a checklist item and the delete or backspace key is pressed.
const withChecklists = (editor: BaseEditor & ReactEditor & HistoryEditor) => {
  const { deleteBackward } = editor;

  editor.deleteBackward = (...args) => {
    const { selection } = editor;

    if (selection && Range.isCollapsed(selection)) {
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


export function ListEditor() {
  const renderElement = useCallback((props: TextElementProps) => <TextElement {...props} />, []);
  const editor = useMemo(
    () => withChecklists(withHistory(withReact(createEditor() as BaseEditor & ReactEditor & HistoryEditor))),
    []
  );

  return (
    <Slate editor={editor} initialValue={initialValue}>
      <Editable
        renderElement={renderElement}
        placeholder="Get to work…"
        spellCheck
        autoFocus
      />
    </Slate>);
};


