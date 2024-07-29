
import { AccountCircle, Dashboard, Category, Settings, ReceiptLong } from '@mui/icons-material';
import './styles.css';
import { Button } from '@mui/material';
import { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { useUser } from '../../context/UserContext/UserContext';
import { useAuth } from '../../context/AuthContext/AuthContext';

export function Sidebar() {
  const { logout } = useAuth();
  const { user } = useUser();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="sidebar-container">
      <div className="top-section">
        <div className="sidebar-item" style={{ marginBottom: '0' }}>
          <ReceiptLong fontSize='large' className="icon" />
          <h2 className="sidebar-link">Listify</h2>
        </div>
        <hr className="custom-hr" />
        <div className="sidebar-item">
          <AccountCircle className="icon" />
          <div className="sidebar-link">
            {user && <div>{user.name}</div>}
          </div>
        </div>
        <div className="sidebar-item">
          <Dashboard className="icon" />
          <div className="sidebar-link">Dashboard</div>
        </div>
        <div className="sidebar-item">
          <Category className="icon" />
          <div className="sidebar-link">Categories</div>
        </div>
        <div className="sidebar-item">
          <Settings className="icon" />
          <div className="sidebar-link">Settings</div>
        </div>
      </div>

      <div className="bottom-section">
        <Button variant="outlined" onClick={handleLogout}>Log out</Button>
      </div>
    </div>
  );
}

