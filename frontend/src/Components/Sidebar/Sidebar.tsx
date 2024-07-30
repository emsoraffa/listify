
import { AccountCircle, Dashboard, Category, Settings, ReceiptLong } from '@mui/icons-material';
import styles from './styles.module.css';
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
    <div className={styles.sidebarContainer}>
      <div className={styles.topSection}>
        <div className={styles.sidebarItem} style={{ marginBottom: '0' }}>
          <ReceiptLong fontSize="large" className={styles.icon} />
          <h2 className={styles.sidebarLink}>Listify</h2>
        </div>
        <hr className={styles.customHr} />
        <div className={styles.sidebarItem}>
          <AccountCircle className={styles.icon} />
          <div className={styles.sidebarLink}>
            {user && <div>{user.name}</div>}
          </div>
        </div>
        <div className={styles.sidebarItem}>
          <Dashboard className={styles.icon} />
          <div className={styles.sidebarLink}>Dashboard</div>
        </div>
        <div className={styles.sidebarItem}>
          <Category className={styles.icon} />
          <div className={styles.sidebarLink}>Categories</div>
        </div>
        <div className={styles.sidebarItem}>
          <Settings className={styles.icon} />
          <div className={styles.sidebarLink}>Settings</div>
        </div>
      </div>
      <div className={styles.bottomSection}>
        <Button variant="outlined" onClick={handleLogout}>Log out</Button>
      </div>
    </div>
  );
}

