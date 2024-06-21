import { Button } from "@mui/material";

//TODO: Remove inline css
export function LoginPage() {
  const googleLogin = () => {
    window.location.href = 'http://localhost:8080/oauth2/authorization/google';
  }

  return (
    <>
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '45vh' }}>
        <Button onClick={googleLogin} variant="contained" color="primary">
          Login with Google
        </Button>
      </div>    </>)
}
