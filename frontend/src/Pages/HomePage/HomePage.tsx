import React from 'react';
import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import styles from './styles.module.css';
import { useMediaQuery } from 'react-responsive';

export function HomePage() {
  const navigate = useNavigate();
  const isMobile = useMediaQuery({ query: '(max-width:1224px)' });

  const handleSignUp = () => {
    navigate('/login');
  };

  return (
    <div className={isMobile ? styles.mobileContentContainer : styles.contentContainer}>
      <div className={isMobile ? styles.mobileLandingContainer : styles.landingContainer}>
        <h1 className={isMobile ? styles.smallLandingHeader : styles.bigLandingHeader}>
          Simplify your shopping and spending habits!
        </h1>
        <p className={isMobile ? styles.smallDescription : styles.description}>
          With the click of a button, create and share shopping lists with the entire family.
          Use the power of artificial intelligence to plan your shopping and track your spending!
        </p>
        <div className={isMobile ? styles.btnCol : styles.btnRow}>
          <div className={styles.signupBtn}>
            <Button variant="contained" color="secondary" onClick={handleSignUp}>
              Signup for free
            </Button>
          </div>
          <div className={styles.tutorialBtn}>
            <Button variant="outlined" color="primary">
              Watch video
            </Button>
          </div>
        </div>
      </div>
      <br />
      <div className={styles.showcaseContainer}>
        <h2 className={styles.showcaseHeader}>Shopping and budgeting, all in one package!</h2>
      </div>
    </div>
  );
}
