import { Button } from "@mui/material";
import "./styles.css";

export function Navbar() {
  return (
    <div className="navbar-container">
      <div className="left-partition">
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
      <div className="right-partition">
        <Button variant="text" color="primary" style={{ borderRadius: 50, marginRight: '10px' }}>
          Sign in
        </Button>

        <Button variant="contained" color="secondary" style={{ borderRadius: 50, marginRight: '10px' }}>
          Get Started
        </Button>

      </div>

    </div>

  )
}
