import { useMediaQuery } from "react-responsive";
import { Outlet } from "react-router-dom";
import { MobileDrawer } from "../MobileDrawer";
import { Sidebar } from "../Sidebar";
import styles from "./styles.module.css";

export function SidebarOutlet() {

  const isMobile = useMediaQuery({ query: '(max-width:1224px)' });
  return (
    <div className={styles.sidebarLayout}>
      <div>
        {isMobile ? <MobileDrawer /> : <Sidebar />}
      </div>
      <div className={styles.content}>

        <Outlet />
      </div>

    </div>

  )
}
