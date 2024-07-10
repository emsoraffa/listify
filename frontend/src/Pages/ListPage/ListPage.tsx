import { Button } from "@mui/material";
import { useCallback, useContext, useRef, useState } from "react";
import { postList } from "../../api";
import { ListEditor } from "../../Components/ListEditor";
import { TitleEditor } from "../../Components/TitleEditor";
import { AuthContext } from "../../context/AuthContext";
import { isCheckListItemElement, isElement } from "../../types/TypeGuards";
import { Descendant, Element as SlateElement } from "slate";
import { debounce } from 'lodash';
import "./styles.css";


export function ListPage() {
  //TODO: title and listitems should be fetched by useEffect.

  const titleEditorRef = useRef<any>(null);
  const listEditorRef = useRef<any>(null);

  const handleFocusListEditor = () => {
    listEditorRef.current?.focus();
  };

  const initialListItems: Descendant[] = [
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


  const authState = useContext(AuthContext);
  const [title, setTitle] = useState<Descendant[]>([
    { type: 'title', children: [{ text: 'My List Title' }] }
  ]);
  const [listItems, setListItems] = useState<Descendant[]>(initialListItems);

  const debouncedSave = useCallback(debounce(() => {
    handleSave();
  }, 2000), []); // 2000 ms delay

  const handleSave = () => {
    const titleElement = title.find(isElement);
    const titleText = titleElement ? titleElement.children[0].text.trim() : "";

    if (!titleText) {
      console.error('The title is mandatory');
      return;
    }

    const itemsToSave = listItems
      .filter(isElement) // Ensure we only handle elements
      .filter(isCheckListItemElement)
      .filter(el => el.type === 'check-list-item')
      .map(item => ({
        text: item.children[0].text,
        checked: item.checked,
      }));

    if (authState?.token) {
      postList(titleText, itemsToSave, authState.token)
        .then(response => console.log('Data posted successfully:', response))
        .catch(error => console.error('Error posting data:', error));
    }
  }; return (
    <div className="container">
      <TitleEditor title={title} ref={titleEditorRef} onFocusNext={handleFocusListEditor} setTitle={setTitle} />
      <ListEditor listItems={listItems} ref={listEditorRef} setListItems={setListItems} debouncedSave={debouncedSave} />
      <Button onClick={handleSave}>Save</Button>
    </div>);
};
