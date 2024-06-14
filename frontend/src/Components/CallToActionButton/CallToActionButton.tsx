import { Button } from "@mui/material";

export function CallToActionButton() {
  return (
    <Button variant="contained" color="secondary" sx={{
      ':hover': {
        bgcolor: '#2a334d', // Assuming 'primary' is the button's color
        // If you need a specific color calculation:
        // bgcolor: (theme) => theme.palette.primary.light,
      }
    }}>Get started</Button>
  )
}

