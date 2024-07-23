import "./styles.css";

interface ListCardProps {
  list_name: string,
  author: string,
}
export function ListCard({ list_name, author }: ListCardProps) {
  return (<div className="container">
    <h3>{list_name}</h3>
    <p>Author: {author}</p>
  </div>)
}
