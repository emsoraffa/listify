import { useContext } from "react";
import { AuthContext } from "./context/AuthContext";
import { CheckListItemDto, DashboardListDto, ListDto } from "./dto";

const SERVER_IP = null;
const SERVER_PORT = null;
//const API_URL = `http://${SERVER_IP || "localhost"}:${SERVER_PORT || "8080"}/api`;
const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';
console.log(API_URL)

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

export const postList = async (list: ListDto, token: string) => {
  console.log("Token:", token);
  console.log("Items to be sent:", list);
  console.log("Serialized Items:", JSON.stringify(list));

  const response = await fetch(`${API_URL}/list`, {
    method: "POST",
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
    body: JSON.stringify(list)
  });

  if (!response.ok) {
    throw new Error(`Network response was not ok: ${response.statusText}`);
  }

  const data = await response.json();
  console.log("Response data:", data);
  return data;
}

export const fetchListById = async (token: string, id: number): Promise<ListDto> => {
  const response = await fetch(`${API_URL}/li/${id}`, {
    method: "GET",
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    }
  })
  if (!response.ok) {
    throw new Error(`Network response was not ok: ${response.statusText}`)
  }

  const list: ListDto = await response.json();
  return list;

}

export const fetchUserLists = async (token: string): Promise<DashboardListDto[]> => {
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

  const data = await response.json();
  return data.items;
}
