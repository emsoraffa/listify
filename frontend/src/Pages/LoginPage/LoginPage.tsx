import styles from "./styles.module.css";
import { Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

interface LoginPageProps {
  isMobileTest: boolean;
}

export function LoginPage({ isMobileTest }: LoginPageProps) {
  //TODO: remove inline css.
  const navigate = useNavigate();
  const baseUrl = process.env.REACT_APP_BASE_URL || 'http://localhost:8080';

  const googleLogin = () => {
    console.log(baseUrl)
    window.location.href = `${baseUrl}/oauth2/authorization/google`;
  }

  //Fetch a fake token for testing on other devices in mobile dev mode
  const handleBypassLogin = async () => {
    try {
      console.log(baseUrl)
      const response = await fetch(`http://10.0.0.249:8080/dev/login`, {
        method: "GET",
      });
      const data = await response.json();
      localStorage.setItem('accessToken', data.token);
      navigate('/dashboard');
    } catch (error) {
      console.error('Error fetching fake token:', error);
    }
  };
  return (
    <>
      <div className={styles.loginContainer}>
        <Button onClick={googleLogin} variant="contained" color="primary">
          Login with Google
        </Button>
        <Button variant="text" color="primary">
          Sign up with email
        </Button>
        {isMobileTest && (
          <Button onClick={handleBypassLogin} variant="contained" color="secondary" style={{ marginTop: '20px' }}>
            Bypass Login (Mobile test Only)
          </Button>
        )}
      </div>
    </>
  );
}

