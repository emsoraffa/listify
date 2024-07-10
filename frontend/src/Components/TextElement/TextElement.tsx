
import React from 'react';
import { CustomElement, CustomText } from "../../types/slate";
import { isCheckListItemElement } from "../../types/TypeGuards";
import { CheckListItemElement } from "../CheckListItemElement";

interface TextElementAttributes {
  [key: string]: any; // Or more specific properties
}

export type TextElementProps = {
  attributes: TextElementAttributes;
  children: React.ReactNode;
  element: CustomElement;
};

export function TextElement({ attributes, children, element }: TextElementProps) {
  // Conditionally render based on the element type
  if (isCheckListItemElement(element)) {
    return <CheckListItemElement attributes={attributes} children={children} element={element} />;
  } else if (element.type === 'paragraph') {
    return <p {...attributes}>{children}</p>;
  } else if (element.type === 'title') {
    return <h1 {...attributes}>{children}</h1>;
  } else {
    return <div {...attributes}>{children}</div>;
  }
}

