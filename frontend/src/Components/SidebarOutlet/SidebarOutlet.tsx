import { useMediaQuery } from "react-responsive";
import { Outlet } from "react-router-dom";
import { MobileDrawer } from "../MobileDrawer";
import { Sidebar } from "../Sidebar";
import "./styles.css";

export function SidebarOutlet() {

  const isMobile = useMediaQuery({ query: '(max-width:1224px)' });
  return (
    <div className="sidebar-layout">
      <div className="sidebar">
        {isMobile ? <MobileDrawer /> : <Sidebar />}
      </div>
      <div className="content">

        <Outlet />
      </div>

    </div>

  )
}
