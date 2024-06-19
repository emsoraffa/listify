import React from 'react';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import './styles.css';

export function HomePage() {
  const navigate = useNavigate();

  const handleSignUp = () => {
    navigate('/login');
  };

  return (
    <div className="content-container">
      <h1 className="landing-header">Grocery shopping made simple</h1>
      <p>Effortlessly create and share shopping lists with the entire family.
        Haven't decided what to eat? Let Listify make suggestions for you!</p>
      <div className="btn-row">
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

