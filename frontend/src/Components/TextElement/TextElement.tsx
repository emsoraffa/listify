import { CustomElement, CustomText } from "../../types/slate";
import { CheckListItemElement } from "../CheckListItemElement";

export type TextElementProps = {
  attributes: any;  // Consider defining a more specific type here
  children: React.ReactNode;    // Consider using React.ReactNode here
  element: CustomElement;
};


// Type guard for check-list-item
//TODO: make generic typeguard
function isCheckListItemElement(element: CustomElement): element is { type: 'check-list-item'; checked: boolean; children: CustomText[] } {
  return element.type === 'check-list-item';
}
export function TextElement(props: TextElementProps) {
  const { attributes, children, element } = props;

  if (isCheckListItemElement(element)) {
    return <CheckListItemElement attributes={attributes} children={children} element={element} />;
  } else {
    return <p {...attributes}>{children}</p>;
  }
};
