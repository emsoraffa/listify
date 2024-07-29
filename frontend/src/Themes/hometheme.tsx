import { createTheme } from '@mui/material/styles';

// Create a theme instance.
const theme = createTheme({
  palette: {
    primary: {
      main: '#000814', // Example primary color
    },
    secondary: {
      main: '#FBB703',

    },
  },
  typography: {
    fontFamily: [
      'Roboto',
      'sans-serif'
    ].join(','),
  },
});

export default theme;
