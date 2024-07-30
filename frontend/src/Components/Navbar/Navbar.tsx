import { Button } from "@mui/material";

import styles from "./styles.module.css";

export function Navbar() {
  return (
    <div className={styles.navbarContainer}>
      <div className={styles.leftPartition}>
        <Button variant="text" color="primary" style={{ borderRadius: 50, marginRight: '10px' }}>
          Listify
        </Button>
        <Button variant="text" color="primary" style={{ borderRadius: 50, marginRight: '10px' }}>
          Features
        </Button>
        <Button variant="text" color="primary" style={{ borderRadius: 50, marginRight: '10px' }}>
          About
        </Button>
      </div>
      <div className={styles.rightPartition}>
        <Button variant="text" color="primary" style={{ borderRadius: 50, marginRight: '10px' }}>
          Sign in
        </Button>
        <Button variant="contained" color="secondary" style={{ borderRadius: 50, marginRight: '10px' }}>
          Get Started
        </Button>
      </div>
    </div>
  );
}

