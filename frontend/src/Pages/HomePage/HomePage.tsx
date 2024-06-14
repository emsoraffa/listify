import { Button } from "@mui/material";
import "./styles.css";

export function HomePage() {
  return (
    <div className="content-container">
      <h1 className="landing-header">Grocery shopping made simple</h1>
      <p>Effortlessly create and share shopping lists with the entire family.
        Havent decided what to it? Let Listify make suggestions for you!</p>
      <div className="btn-row">
        <Button variant="contained" color="secondary">Sign me up!</Button>
        <Button variant="outlined" color="primary">Quickstart</Button>
      </div>
    </div>
  )
}

