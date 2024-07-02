import "./styles.css";
import { CallToActionButton } from "../CallToActionButton/CallToActionButton";
import { useMediaQuery } from "react-responsive";
import { Button, Icon, IconButton, Menu, MenuItem } from "@mui/material";
import React from "react";
import MenuIcon from '@mui/icons-material/Menu';
import MenuOpenIcon from '@mui/icons-material/MenuOpen';

export function Navbar() {
  const isMobile = useMediaQuery({ query: '(max-width:1224px)' })
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  }
  const handleClose = () => {
    setAnchorEl(null);
  };
  return (<div className='navbar-container'>
    {!isMobile &&
      <div className="navbar-left-container">
        <div className="navbar-item-container">
          <a>Listify</a>
        </div>
        <div className="navbar-item-container">
          <a>Features</a>
        </div>
        <div className="navbar-item-container">
          <a>About</a>
        </div>

      </div>}

    <div className={isMobile ? "navbar-small-container" : "navbar-right-container"}>
      {isMobile ? <div className="navbar-item-container">
        <Button variant="contained" color="secondary" style={{ borderRadius: 50 }}>Get started</Button> </div> : null}

      <div className="navbar-item-container">

        {isMobile ?
          <div>
            <IconButton
              id="basic-button"
              aria-controls={open ? 'hamburger-menu' : undefined}
              aria-haspopup="true"
              aria-expanded={open ? 'true' : undefined}
              onClick={handleClick}
            >
              {open ? <MenuOpenIcon fontSize="large" /> : <MenuIcon fontSize="large" />}
            </IconButton>
            <Menu
              id="hamburger-menu"
              anchorEl={anchorEl}
              open={open}
              onClose={handleClose}
              MenuListProps={{
                'aria-labelledby': 'basic-button',
              }}
            >
              <MenuItem onClick={handleClose}>About</MenuItem>
              <MenuItem onClick={handleClose}>Features</MenuItem>
              <MenuItem onClick={handleClose}>Logout</MenuItem>
            </Menu>
          </div> : <CallToActionButton />}
      </div>

    </div>
  </div>)
}
