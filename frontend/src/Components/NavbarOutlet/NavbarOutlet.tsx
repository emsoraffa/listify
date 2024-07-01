import { Outlet } from "react-router-dom";
import { Navbar } from "../Navbar";
import "./styles.css";

export function NavbarOutlet() {
  return (
    <div className="layout">
      <div className="navbar">

        <Navbar />
      </div>
      <div className="content">

        <Outlet />
      </div>

    </div>
  )
}
