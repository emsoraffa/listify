import { Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

export function CallToActionButton() {
  const navigate = useNavigate();
  const handleClick = () => {
    navigate("/test")
  }
  return (
    <Button onClick={handleClick} variant="contained" color="secondary" sx={{
      ':hover': {
        bgcolor: '#2a334d', // Assuming 'primary' is the button's color
        // If you need a specific color calculation:
        // bgcolor: (theme) => theme.palette.primary.light,
      }
    }}>Get started</Button>
  )
}

