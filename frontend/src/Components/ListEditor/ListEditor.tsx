
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

//TODO: Fix this shit.

// Define custom types
type CustomElement =
  | { type: 'paragraph'; children: CustomText[] }
  | { type: 'check-list-item'; checked: boolean; children: CustomText[] };
type CustomText = { text: string; bold?: true };

// Extend Slate types
declare module 'slate' {
  interface CustomTypes {
    Editor: BaseEditor & ReactEditor & HistoryEditor;
    Element: CustomElement;
    Text: CustomText;
  }
}

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
  {
    type: 'check-list-item',
    checked: false,
    children: [{ text: 'Cheese' }],
  },
  {
    type: 'check-list-item',
    checked: true,
    children: [{ text: 'Apples' }],
  },
  {
    type: 'check-list-item',
    checked: false,
    children: [{ text: 'Oranges' }],
  }
];// Extend the editor with checklist behavior
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

// Type guard for check-list-item
function isCheckListItemElement(element: CustomElement): element is { type: 'check-list-item'; checked: boolean; children: CustomText[] } {
  return element.type === 'check-list-item';
}

// Define the element rendering function
interface ElementProps {
  attributes: any;
  children: any;
  element: CustomElement;
}

const Element = (props: ElementProps) => {
  const { attributes, children, element } = props;

  if (isCheckListItemElement(element)) {
    // Directly pass down the element with the correct type
    return <CheckListItemElement attributes={attributes} children={children} element={element} />;
  } else {
    return <p {...attributes}>{children}</p>;
  }
};

// Define the CheckListItemElement component
interface CheckListItemElementProps {
  attributes: any;
  children: any;
  element: { type: 'check-list-item'; checked: boolean; children: CustomText[] };
}

export function CheckListItemElement({
  attributes,
  children,
  element,
}: CheckListItemElementProps) {
  const editor = useSlateStatic();
  const readOnly = useReadOnly();
  const { checked } = element;

  return (
    <div {...attributes} className="checklist-item">
      <span contentEditable={false} className="checkbox-container">
        <input
          type="checkbox"
          checked={checked}
          onChange={(event) => {
            const path = ReactEditor.findPath(editor, element);
            const newProperties: Partial<SlateElement> = {
              checked: event.target.checked,
            };
            Transforms.setNodes(editor, newProperties, { at: path });
          }}
        />
      </span>
      <span
        contentEditable={!readOnly}
        suppressContentEditableWarning
        className={`checkbox-content ${checked ? 'checkbox-content-checked' : 'checkbox-content-unchecked'
          }`}
      >
        {children}
      </span>
    </div>
  );
}

// Define the main CheckListsExample component
const CheckListsExample = () => {
  const renderElement = useCallback((props: ElementProps) => <Element {...props} />, []);
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

export default CheckListsExample;

