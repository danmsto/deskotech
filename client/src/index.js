import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import './index.css';
import AxiosClient from "./AxiosClient";
import { createTheme, ThemeProvider } from "@mui/material";

const root = ReactDOM.createRoot(document.getElementById('root'));
const theme = createTheme({
  components: {
    MuiInputLabel: {
      styleOverrides: {
        root: {
          color: "#F5F5F5"
        }
      }
    }
  },
  palette: {
    primary: {
      main: "#F5F5F5"
    }
  }
})

root.render(
    <React.StrictMode>
      <BrowserRouter>
        <ThemeProvider theme={theme}>
          <AxiosClient/>
        </ThemeProvider>
      </BrowserRouter>
    </React.StrictMode>
);

