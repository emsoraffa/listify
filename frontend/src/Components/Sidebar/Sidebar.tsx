import { Hidden } from "@mui/material";
import { useMediaQuery } from "react-responsive";
import "./styles.css";

export function Sidebar() {
  const isMobile = useMediaQuery({ query: '(max-width:1224px)' })
  const isDesktop = useMediaQuery({ query: '(min-width:1224px' })

  return (<>
    <div className={isMobile ? "sidebar-container" : "large-sidebar-container"}>
      <div className="sidebar-item">
        <div>Icon </div>
        {isDesktop && <div>Logo</div>}
      </div>
      <div className="sidebar-item">
        <div>Icon </div>
        {isDesktop && <div>profile</div>}
      </div>
      <div className="sidebar-item">
        <div>Icon </div>
        {isDesktop && <div>Dashboard</div>}
      </div>
      <div className="sidebar-item">
        <div>Icon </div>
        {isDesktop && <div>Categories</div>}
      </div>
      <div className="sidebar-item">
        <div>Icon </div>
        {isDesktop && <div>Settings</div>}
      </div>
    </div></>)
}
