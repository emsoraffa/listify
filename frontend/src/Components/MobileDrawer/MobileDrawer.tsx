
import React, { useState } from 'react';
import { Button, SwipeableDrawer, List, ListItem, ListItemIcon, ListItemText } from '@mui/material';
import { AccountCircle, Dashboard, Category, Settings, ReceiptLong } from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { useUser } from '../../context/UserContext/UserContext';
import { useAuth } from '../../context/AuthContext/AuthContext';
import MenuOpenIcon from '@mui/icons-material/MenuOpen';
import MenuIcon from '@mui/icons-material/Menu';

export function MobileDrawer() {
  const [open, setOpen] = useState(false);
  const { logout } = useAuth();
  const { user } = useUser();
  const navigate = useNavigate();

  const toggleDrawer = (open: boolean) => () => {
    setOpen(open);
  };

  const list = () => (
    <div
      role="presentation"
      onClick={toggleDrawer(false)}
      onKeyDown={toggleDrawer(false)}
    >
      <List>
        <ListItem button onClick={() => navigate('/dashboard')}>
          <ListItemIcon><Dashboard /></ListItemIcon>
          <ListItemText primary="Dashboard" />
        </ListItem>
        <ListItem button onClick={() => navigate('/categories')}>
          <ListItemIcon><Category /></ListItemIcon>
          <ListItemText primary="Categories" />
        </ListItem>
        <ListItem button onClick={() => navigate('/settings')}>
          <ListItemIcon><Settings /></ListItemIcon>
          <ListItemText primary="Settings" />
        </ListItem>
        <ListItem button onClick={() => navigate('/profile')}>
          <ListItemIcon><AccountCircle /></ListItemIcon>
          <ListItemText />
          {user && <div>{user.name}</div>}
        </ListItem>
        <ListItem button onClick={() => {
          logout();
          navigate('/login');
        }}>
          <ListItemIcon><ReceiptLong /></ListItemIcon>
          <ListItemText primary="Log out" />
        </ListItem>
      </List>
    </div>
  );

  return (
    <div>
      <Button onClick={toggleDrawer(true)}>
        <MenuIcon fontSize="large" />
      </Button>
      <SwipeableDrawer
        anchor='left'
        open={open}
        onClose={toggleDrawer(false)}
        onOpen={toggleDrawer(true)}
      >
        {list()}
      </SwipeableDrawer>
    </div>
  );
}

