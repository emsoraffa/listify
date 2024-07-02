// Type guard for check-list-item

import { CustomElement, CustomText } from "./slate";

//TODO: make generic typeguard
export function isCheckListItemElement(element: CustomElement):
  element is {
    type: 'check-list-item';
    checked: boolean;
    children: CustomText[]
  } {
  return element.type === 'check-list-item';
}

