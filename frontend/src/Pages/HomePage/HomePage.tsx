import React from 'react';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import './styles.css';
import { useMediaQuery } from 'react-responsive';

export function HomePage() {
  const navigate = useNavigate();
  const isMobile = useMediaQuery({ query: '(max-width:1224px)' })

  const handleSignUp = () => {
    navigate('/login');
  };

  return (
    <div className={isMobile ? "mobile-content-container" : "content-container"}>
      <h1 className={isMobile ? "small-landing-header" : "big-landing-header"}>Simplify your shopping and spending habits!</h1>
      <p className={isMobile ? "small-description" : "description"}>
        With the click of a button, create and share shopping lists with the entire family.
        Use the power of artificial intelligence to plan your shopping and track your spending!</p>
      <div className={isMobile ? "btn-col" : "btn-row"}>
        <div className="signup-btn">
          <Button variant="contained" color="secondary" onClick={handleSignUp}>
            Signup for free
          </Button>
        </div>
        <div className="tutorial-btn">
          <Button variant="outlined" color="primary">
            Watch video
          </Button>

        </div>

      </div>
    </div>
  );
}

