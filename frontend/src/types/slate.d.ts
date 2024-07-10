import { BaseEditor } from "slate";
import { HistoryEditor } from "slate-history";
import { ReactEditor } from "slate-react";

type CustomElement =
  | { type: 'paragraph'; children: CustomText[] }
  | { type: 'check-list-item'; checked: boolean; children: CustomText[] }
  | { type: 'title'; children: CustomText[] };
type CustomText = { text: string; bold?: true };

declare module 'slate' {
  interface CustomTypes {
    Editor: BaseEditor & ReactEditor & HistoryEditor;
    Element: CustomElement;
    Text: CustomText;
  }
}

