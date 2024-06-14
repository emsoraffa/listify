import { useEffect, useState } from "react"
import { getListItems } from "../../api"

export function ListPage() {
  const [list, setList] = useState<string>("tomt")
  useEffect(() => {
    getListItems().then((list) => setList(String(list)));
  })

  return (<>
    <div>{list}</div></>)

}
