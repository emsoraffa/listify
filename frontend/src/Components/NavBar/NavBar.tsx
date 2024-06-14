import "./styles.css";
import { Button } from "@mui/material";
import { CallToActionButton } from "../CallToActionButton/CallToActionButton";

export function Navbar() {
  return (<div className='navbar-container'>
    <div className="navbar-item-container">
      <a>Listify</a>
    </div>
    <div className="navbar-item-container">
      <a>Features</a>
    </div>
    <div className="navbar-item-container">
      <a>Sign in</a>
    </div>



    <div className="navbar-item-container">

      <CallToActionButton />
    </div>
  </div>)
}
