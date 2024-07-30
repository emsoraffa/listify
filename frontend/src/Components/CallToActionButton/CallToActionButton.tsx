import { Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

export function CallToActionButton() {
  //TODO: consdier removing.
  const navigate = useNavigate();
  const handleClick = () => {
    navigate("/test")
  }
  return (
    <Button onClick={handleClick} variant="contained" color="secondary" sx={{
      ':hover': {
        bgcolor: '#343402',
      }
    }}>Get started</Button>
  )
}

