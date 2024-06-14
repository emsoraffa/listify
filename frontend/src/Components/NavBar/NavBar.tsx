import "./styles.css";
import { CallToActionButton } from "../CallToActionButton/CallToActionButton";

export function Navbar() {
  return (<div className='navbar-container'>
    <div className="navbar-left-container">
      <div className="navbar-item-container">
        <a>Listify</a>
      </div>
      <div className="navbar-item-container">
        <a>Features</a>
      </div>
      <div className="navbar-item-container">
        <a>About</a>
      </div>

    </div>
    <div className="navbar-right-container">
      <div className="navbar-item-container">
        <a>Sign in</a>
      </div>



      <div className="navbar-item-container">

        <CallToActionButton />
      </div>

    </div>
  </div>)
}
