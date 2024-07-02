import { useMediaQuery } from 'react-responsive';
import { AccountCircle, Dashboard, Category, Settings, ReceiptLong } from '@mui/icons-material';
import './styles.css';
import { Button } from '@mui/material';
import { useContext } from 'react';
import { AuthContext } from '../../context/AuthContext/AuthContext';
import { useNavigate } from 'react-router-dom';

export function Sidebar() {
  const isMobile = useMediaQuery({ query: '(max-width:1224px)' });
  const authState = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    authState?.logout();
    navigate('/login');
  }
  return (
    <div className={isMobile ? "sidebar-container" : "large-sidebar-container"}>
      <div className="top-section">
        <div className={isMobile ? "sidebar-item" : "large-sidebar-item"} style={{ marginBottom: '0' }}>
          <ReceiptLong fontSize='large' className="icon" />
          {!isMobile && <h2 className="sidebar-link" >Listify</h2>}
        </div>
        <hr className="custom-hr" />
        <div className={isMobile ? "sidebar-item" : "large-sidebar-item"}>
          <AccountCircle className="icon" />
          {!isMobile && <div className="sidebar-link">Profile</div>}
        </div>
        <div className={isMobile ? "sidebar-item" : "large-sidebar-item"}>
          <Dashboard className="icon" />
          {!isMobile && <div className="sidebar-link">Dashboard</div>}
        </div>
        <div className={isMobile ? "sidebar-item" : "large-sidebar-item"}>
          <Category className="icon" />
          {!isMobile && <div className="sidebar-link">Categories</div>}
        </div>
        <div className={isMobile ? "sidebar-item" : "large-sidebar-item"}>
          <Settings className="icon" />
          {!isMobile && <div className="sidebar-link">Settings</div>}
        </div>
      </div>

      <div className="bottom-section">
        {!isMobile && <Button variant="outlined" onClick={handleLogout}>Log out</Button>}
      </div>
    </div>
  );
}

