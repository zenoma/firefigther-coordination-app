import { useSelector } from "react-redux";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { ToastContainer } from "react-toastify";

import Profile from "./features/profile/Profile";
import Login from "./features/login/Login";
import SignUp from "./features/signUp/SignUp";
import ChangePassword from "./features/changePasword/ChangePassword";
import NestedList from "./features/list/NestedList";
import CustomDrawer from "./features/navbar/CustomDrawer";
import Home from "./features/home/Home";

import { ThemeProvider } from "@mui/material/styles";
import { darkTheme, lightTheme } from "./theme/theme";

import "react-toastify/dist/ReactToastify.css";
import "./App.css";

function App() {
  const theme = useSelector((state) => state.theme);

  return (
    <ThemeProvider theme={theme.darkTheme ? darkTheme : lightTheme}>
      <div className="App">
        <header className="App-header">
          <BrowserRouter>
            <CustomDrawer />
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/login" element={<Login />} />
              <Route path="/sign-up" element={<SignUp />} />
              <Route path="/profile" element={<Profile />} />
              <Route path="/change-password" element={<ChangePassword />} />
              <Route path="/team" element={<NestedList />} />
            </Routes>
          </BrowserRouter>
        </header>
        <ToastContainer
          position="bottom-center"
          autoClose={5000}
          hideProgressBar
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable={false}
          pauseOnHover
          theme="colored"
        />{" "}
      </div>
    </ThemeProvider>
  );
}

export default App;
