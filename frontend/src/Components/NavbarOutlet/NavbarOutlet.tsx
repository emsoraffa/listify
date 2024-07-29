import { useMediaQuery } from "react-responsive";
import { Outlet } from "react-router-dom";
import { Navbar } from "../Navbar";
import { NavbarDrawer } from "../NavbarDrawer";
import "./styles.css";

export function NavbarOutlet() {


  const isMobile = useMediaQuery({ query: '(max-width:1224px)' });

  return (
    <div className="layout">
      <div className="navbar">
        {isMobile ? <NavbarDrawer /> : <Navbar />}
      </div>

      <Outlet />

    </div>
  )
}
