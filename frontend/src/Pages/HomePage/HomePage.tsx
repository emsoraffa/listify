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
    <div className="content-container">
      <h1 className={isMobile ? "small-landing-header" : "big-landing-header"}>Grocery shopping made simple</h1>
      <p>Effortlessly create and share shopping lists with the entire family.
        Haven't decided what to eat? Let Listify make suggestions for you!</p>
      <div className={isMobile ? "btn-col" : "btn-row"}>
        <Button variant="contained" color="secondary" onClick={handleSignUp}>
          Sign me up!
        </Button>
        <Button variant="outlined" color="primary">
          Quickstart
        </Button>
      </div>
    </div>
  );
}

