import { useMediaQuery } from "react-responsive";
import { Outlet } from "react-router-dom";
import { Navbar } from "../Navbar";
import { NavbarDrawer } from "../NavbarDrawer";
import styles from "./styles.module.css";

export function NavbarOutlet() {


  const isMobile = useMediaQuery({ query: '(max-width:1224px)' });

  return (
    <div className={styles.layout}>
      <div className={styles.navbar}>
        {isMobile ? <NavbarDrawer /> : <Navbar />}
      </div>

      <Outlet />

    </div>
  )
}
