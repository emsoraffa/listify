
import { Button, CircularProgress } from "@mui/material";
import { useCallback, useEffect, useRef, useState } from "react";
import { fetchListById, postList } from "../../api";
import { ListEditor } from "../../Components/ListEditor";
import { TitleEditor } from "../../Components/TitleEditor";
import { isCheckListItemElement, isElement } from "../../types/TypeGuards";
import { Descendant, Element as SlateElement } from "slate";
import { debounce } from 'lodash';
import styles from "./styles.module.css";
import { CheckListItemDto } from "../../dto";
import { useParams, useNavigate } from "react-router-dom";
import { useUser } from "../../context/UserContext/UserContext";
import { useAuth } from "../../context/AuthContext/AuthContext";
import { estimateCosts } from "../../AIapi";
import { useMediaQuery } from "react-responsive";

export function ListPage() {
  const titleEditorRef = useRef<any>(null);
  const listEditorRef = useRef<any>(null);

  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const isMobile = useMediaQuery({ query: '(max-width:1224px)' });

  const { token, isAuthenticated } = useAuth();
  const { user } = useUser();

  const [costs, setCosts] = useState<string>();
  const [isLoading, setLoading] = useState<boolean>(false);

  const [title, setTitle] = useState<Descendant[]>([
    { type: 'title', children: [{ text: 'Loading...' }] }
  ]);

  const [listItems, setListItems] = useState<Descendant[]>([{
    type: 'check-list-item',
    checked: false,
    id: null,
    children: [{ text: '...' }],
  }]);
  const [titleEditorKey, setTitleEditorKey] = useState(0);
  const [listEditorKey, setListEditorKey] = useState(1);

  const parseCost = (response: string): string => {
    const match = response.match(/^estimated costs:\s*(\d+(\.\d+)?)\s*kr$/i);
    if (match) {
      return `Estimated cost: ${match[1]} kr`;
    } else {
      return 'There was an error estimating your costs. TIP: try being as specific as possible';
    }
  };

  const handleEstimate = async () => {
    setLoading(true);
    const itemNames = listItems
      .filter(isElement)
      .filter(isCheckListItemElement)
      .map(item => item.children[0].text)
      .join(', '); // Create a comma-separated string of item names

    try {
      const estimatedCost = await estimateCosts(itemNames);
      console.log('Estimated cost:', estimatedCost);
      const formattedEstimatedCost = parseCost(estimatedCost);
      setLoading(false);
      setCosts(formattedEstimatedCost);
    } catch (error) {
      console.error('Error estimating costs:', error);
      setLoading(false);
    }
  };

  const handleSave = useCallback(() => {
    const titleElement = title.find(isElement);
    const listName = titleElement ? titleElement.children[0].text.trim() : "";

    if (!listName) {
      console.error('The title is mandatory');
      return;
    }

    const listItemsToSave = listItems
      .filter(isElement)
      .filter(isCheckListItemElement)
      .map(item => ({
        id: item.id,
        text: item.children[0].text,
        checked: item.checked,
      }));

    if (token) {
      const listId: number | null = id && id !== 'new' ? parseInt(id) : null;
      postList({ id: listId, list_name: listName, list_items: listItemsToSave }, token)
        .then(response => {
          console.log('Data posted successfully:', response);
          if (id === 'new' && response.id) {
            navigate(`/list/${response.id}`); // Navigate to the new list's page after creation
          }
        })
        .catch(error => console.error('Error posting data:', error));
    }
  }, [title, listItems, token, id, navigate]);

  const debouncedSave = useCallback(debounce(() => {
    console.log("debounce");
    handleSave();
  }, 2000), [handleSave]);

  useEffect(() => {
    if (token && id && id !== 'new') {
      fetchListById(token, parseInt(id))
        .then(data => {
          console.log("Fetched listname:" + data.list_name + ", and listitems: " + JSON.stringify(data.list_items));
          setTitle([{ type: 'title', children: [{ text: data.list_name }] }]);
          setListItems(data.list_items.map(item => ({
            type: 'check-list-item',
            id: item.id,
            checked: item.checked,
            children: [{ text: item.text || '', fontSize: "24px" }]
          })));
          setTitleEditorKey(prevKey => prevKey + 1);
          setListEditorKey(prevKey => prevKey + 1);
          console.log("States are set");
        })
        .catch(error => {
          console.error('Failed to fetch list:', error);
        });
    } else if (id === 'new') {
      setTitle([{ type: 'title', children: [{ text: 'New List', fontSize: "32px" }] }]);
      setListItems([{
        type: 'check-list-item',
        checked: false,
        id: null,
        children: [{ text: '', fontSize: "24px" }],
      }]);
      setTitleEditorKey(prevKey => prevKey + 1);
      setListEditorKey(prevKey => prevKey + 1);
    }
  }, [id, token]);

  return (
    <div className={isMobile ? styles.container : styles.largeContainer}>
      <div className={styles.titleContainer}>
        <TitleEditor title={title}
          ref={titleEditorRef}
          onFocusNext={() => listEditorRef.current?.focus()}
          setTitle={setTitle}
          key={titleEditorKey} />
      </div>
      <div className={styles.editorContainer}>
        <ListEditor listItems={listItems}
          ref={listEditorRef}
          setListItems={setListItems}
          debouncedSave={debouncedSave}
          key={listEditorKey} />
      </div>
      <Button onClick={handleSave}>Save</Button>
      <Button onClick={handleEstimate}>Estimate costs</Button>
      <div className={styles.outputContainer}>
        {!isLoading && costs && <div>{costs}</div>}
        {isLoading && <CircularProgress />}
      </div>
    </div>
  );
}

