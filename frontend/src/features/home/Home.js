import * as React from "react";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import Typography from "@mui/material/Typography";
import { ToastContainer, toast } from "react-toastify";

const theme = createTheme();
const notify = () => toast("Wow so easy!");

theme.typography.h3 = {
  fontSize: "1.2rem",
  "@media (min-width:600px)": {
    fontSize: "1.5rem",
  },
  [theme.breakpoints.up("md")]: {
    fontSize: "2rem",
  },
};

function Home() {
  return (
    <div>
      <ThemeProvider theme={theme}>
        <Typography variant="h3">Welcome to my App!</Typography>
      </ThemeProvider>
    </div>
  );
}

export default Home;
