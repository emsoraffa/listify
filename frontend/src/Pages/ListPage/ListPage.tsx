import { Button } from "@mui/material";
import { useCallback, useContext, useEffect, useRef, useState } from "react";
import { fetchListById, postList } from "../../api";
import { ListEditor } from "../../Components/ListEditor";
import { TitleEditor } from "../../Components/TitleEditor";
import { AuthContext } from "../../context/AuthContext";
import { isCheckListItemElement, isElement } from "../../types/TypeGuards";
import { Descendant, Element as SlateElement } from "slate";
import { debounce } from 'lodash';
import "./styles.css";
import { CheckListItemDto } from "../../dto";
import { useParams } from "react-router-dom";

export function ListPage() {
  //TODO: programatically change editor states.
  const titleEditorRef = useRef<any>(null);
  const listEditorRef = useRef<any>(null);

  const { id } = useParams<string>(); // assuming id is string, adjust as necessary
  const authState = useContext(AuthContext);

  const [title, setTitle] = useState<Descendant[]>([
    { type: 'title', children: [{ text: 'My List Title' }] }
  ]);
  const [listItems, setListItems] = useState<Descendant[]>([{
    type: 'check-list-item',
    checked: false,
    children: [{ text: 'Milk' }],
  }]);

  const handleSave = useCallback(() => {
    const titleElement = title.find(isElement);
    const list_name = titleElement ? titleElement.children[0].text.trim() : "";

    if (!list_name) {
      console.error('The title is mandatory');
      return;
    }

    const listitems = listItems
      .filter(isElement)
      .filter(isCheckListItemElement)
      .map(item => ({
        text: item.children[0].text,
        checked: item.checked,
      }));

    if (authState?.token && id) {
      postList({ id: parseInt(id), list_name, listitems }, authState.token)
        .then(response => console.log('Data posted successfully:', response))
        .catch(error => console.error('Error posting data:', error));
    }
  }, [title, listItems, authState, id]);

  const debouncedSave = useCallback(debounce(() => {
    handleSave();
  }, 2000), [handleSave]);

  useEffect(() => {
    if (authState?.token && id) {
      fetchListById(authState.token, parseInt(id))
        .then(data => {
          console.log("Fetched listname:" + data.list_name + ", and listitems: " + data.listitems)
          setTitle([{ type: 'title', children: [{ text: data.list_name }] }]);
          setListItems(data.listitems.map(item => ({
            type: 'check-list-item',
            checked: item.checked,
            children: [{ text: item.text }]
          })));
          console.log("States are set")
        })
        .catch(error => {
          console.error('Failed to fetch list:', error);
        });
    }
  }, [id, authState?.token]);

  return (
    <div className="container">
      <TitleEditor title={title} ref={titleEditorRef} onFocusNext={() => listEditorRef.current?.focus()} setTitle={setTitle} />
      <ListEditor listItems={listItems} ref={listEditorRef} setListItems={setListItems} debouncedSave={debouncedSave} />
      <Button onClick={handleSave}>Save</Button>
    </div>
  );
}

