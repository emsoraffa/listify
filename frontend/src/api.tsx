import { useContext } from "react";
import { AuthContext } from "./context/AuthContext";
import { CheckListItemDto, ListDto } from "./dto";

const SERVER_IP = null;
const SERVER_PORT = null;
const API_URL = `http://${SERVER_IP || "localhost"}:${SERVER_PORT || "8080"}/api`;

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

export const postList = async (title: string, items: CheckListItemDto[], token: string) => {
  console.log("Token:", token);
  console.log("Items to be sent:", items);
  console.log("Serialized Items:", JSON.stringify(items));

  const response = await fetch(`${API_URL}/list`, {
    method: "POST",
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
    body: JSON.stringify(items)
  });

  if (!response.ok) {
    throw new Error(`Network response was not ok: ${response.statusText}`);
  }

  const data = await response.json();
  console.log("Response data:", data);
  return data;
}

export const fetchUserLists = async (token: string): Promise<ListDto[]> => {
  const response = await fetch(`${API_URL}/dashboard/lists`, {
    method: "GET",
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    }
  })

  if (!response.ok) {
    throw new Error(`Network response was not ok: ${response.statusText}`)
  }

  const lists: ListDto[] = await response.json();
  return lists;
}
