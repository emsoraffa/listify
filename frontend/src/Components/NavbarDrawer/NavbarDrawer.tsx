
import React, { useState } from 'react';
import { IconButton, SwipeableDrawer, List, ListItem, ListItemText, Button } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import { Link } from 'react-router-dom';
import styles from "./styles.module.css";
import { CallToActionButton } from "../CallToActionButton/CallToActionButton";

export function NavbarDrawer() {
  const [drawerOpen, setDrawerOpen] = useState<boolean>(false);

  const toggleDrawer = (open: boolean) => () => {
    setDrawerOpen(open);
  };

  const drawerList = (): JSX.Element => (
    <div
      role="presentation"
      onClick={toggleDrawer(false)}
      onKeyDown={toggleDrawer(false)}
    >
      <List>
        <ListItem button component={Link} to="/home">
          <ListItemText primary="Listify" />
        </ListItem>
        <ListItem button component={Link} to="/features">
          <ListItemText primary="Features" />
        </ListItem>
        <ListItem button component={Link} to="/about">
          <ListItemText primary="About" />
        </ListItem>
      </List>
    </div>
  );

  return (
    <div className={styles.navbarDrawerContainer}>
      <Button variant="contained" color="secondary" style={{ borderRadius: 50, marginRight: '1.5rem' }}>
        Get Started
      </Button>
      <IconButton
        edge="start"
        color="inherit"
        aria-label="menu"
        onClick={toggleDrawer(true)}
      >
        <MenuIcon fontSize="large" />
      </IconButton>
      <SwipeableDrawer
        anchor="top"
        open={drawerOpen}
        onClose={toggleDrawer(false)}
        onOpen={toggleDrawer(true)}
      >
        {drawerList()}
      </SwipeableDrawer>
    </div>
  );
}

