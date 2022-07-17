import { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import { toast } from "react-toastify";

import Profile from "./features/profile/Profile";
import Login from "./features/login/Login";
import SignUp from "./features/signUp/SignUp";
import ChangePassword from "./features/changePasword/ChangePassword";
import OrganizationsList from "./features/list/OrganizationsList";
import MyTeamList from "./features/list/MyTeamList";
import CustomDrawer from "./features/drawer/CustomDrawer";
import Home from "./features/home/Home";

import { ThemeProvider } from "@mui/material/styles";
import { darkTheme, lightTheme } from "./theme/theme";

import "react-toastify/dist/ReactToastify.css";
import "./App.css";
import { useLoginFromTokenMutation } from "./api/userApi";
import { validLogin } from "./features/login/LoginSlice";

function App() {
  const dispatch = useDispatch();
  const theme = useSelector((state) => state.theme);
  const [token, setToken] = useState("");

  const [login, { loginError }] = useLoginFromTokenMutation();

  useEffect(() => {
    setToken(localStorage.getItem("token"));

    const payload = {
      token: token,
    };

    if (token !== "") {
      login(payload)
        .unwrap()
        .then((payload) => {
          dispatch(validLogin(payload));
          toast.info("Successfully logged in.");
          localStorage.setItem("token", token);
        });
    }
  }, [token]);

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
              <Route path="/organizations" element={<OrganizationsList />} />
              <Route path="/my-team" element={<MyTeamList />} />
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
