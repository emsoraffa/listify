import styles from "./styles.module.css";

interface ListCardProps {
  list_name: string;
  author: string;
  onClick: () => void; // onClick should not take any arguments
}

export function ListCard({ list_name, author, onClick }: ListCardProps) {
  return (
    <div className={styles.listCardContainer} onClick={onClick}>
      <h3 className={styles.listName}>{list_name}</h3>
      <p className={styles.listAuthor}>Author: {author}</p>
    </div>
  );
}
