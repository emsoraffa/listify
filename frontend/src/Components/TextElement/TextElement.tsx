import { CustomElement, CustomText } from "../../types/slate";
import { isCheckListItemElement } from "../../types/TypeGuards";
import { CheckListItemElement } from "../CheckListItemElement";

export type TextElementProps = {
  attributes: any;  // Consider defining a more specific type here
  children: React.ReactNode;    // Consider using React.ReactNode here
  element: CustomElement;
};

export function TextElement(props: TextElementProps) {
  const { attributes, children, element } = props;

  if (isCheckListItemElement(element)) {
    return <CheckListItemElement attributes={attributes} children={children} element={element} />;
  } else {
    return <p {...attributes}>{children}</p>;
  }
};
