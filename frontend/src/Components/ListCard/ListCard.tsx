import "./styles.css";

interface ListCardProps {
  list_name: string;
  author: string;
  onClick: () => void; // onClick should not take any arguments
}

export function ListCard({ list_name, author, onClick }: ListCardProps) {
  return (
    <div className="listcard-container" onClick={onClick}>
      <h3 className="list-name">{list_name}</h3>
      <p className="author">Author: {author}</p>
    </div>
  );
}
