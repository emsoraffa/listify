import { getListItems } from "../../api";
import { useEffect, useState } from "react";

interface ListItem {
  name: string;
}

export const ListPage = () => {
  const [listItem, setListItem] = useState<ListItem | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    getListItems()
      .then(setListItem)
      .catch((err) => setError(err.message));
  }, []);

  if (error) {
    return <div>Error: {error}</div>;
  }

  if (!listItem) {
    return <div>Loading...</div>;
  }

  return <div>{listItem.name}</div>;
};
