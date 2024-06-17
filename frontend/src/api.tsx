const SERVER_IP = null;
const SERVER_PORT = null;
const API_URL = `http://${SERVER_IP || "localhost"}:${SERVER_PORT || "8080"}`;

interface ListItem {
  name: string;
}

export const getListItems = async (): Promise<any> => {
  const response = await fetch(`${API_URL}/test`, { method: "GET" });

  if (!response.ok) {
    throw new Error(`Network response was not ok: ${response.statusText}`);
  }
  const list: ListItem = await response.json();
  return list;
};

