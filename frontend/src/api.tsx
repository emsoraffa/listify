const SERVER_IP = null;
const SERVER_PORT = null;
const API_URL = `https//${SERVER_IP || "localhost"}:${SERVER_PORT || "8080"}`;

export const getListItems = () => {
  return fetch(`${API_URL}/getListItems`, { method: "GET" })
}
