import { Transforms, Element as SlateElement } from "slate";
import { ReactEditor, useReadOnly, useSlateStatic } from "slate-react";
import { CustomText } from "../../types/slate";


// Define the CheckListItemElement component
interface CheckListItemElementProps {
  attributes: any;
  children: any;
  element: { type: 'check-list-item'; id: number; checked: boolean; children: CustomText[] };
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


