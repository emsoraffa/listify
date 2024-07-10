// Type guard for check-list-item

import { Descendant } from "slate";
import { CustomElement, CustomText } from "./slate";

export function isElement(node: Descendant): node is CustomElement {
  return (node as CustomElement).children !== undefined;
}

export function isCheckListItemElement(element: CustomElement): element is { type: 'check-list-item'; checked: boolean; children: CustomText[] } {
  return element.type === 'check-list-item';
}
