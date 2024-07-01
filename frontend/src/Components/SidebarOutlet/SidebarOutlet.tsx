import { Outlet } from "react-router-dom";
import { Sidebar } from "../Sidebar";
import "./styles.css";

export function SidebarOutlet() {
  return (
    <div className="sidebar-layout">
      <div className="sidebar">

        <Sidebar />
      </div>
      <div className="content">

        <Outlet />
      </div>

    </div>

  )
}
