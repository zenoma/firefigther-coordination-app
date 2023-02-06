import { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import { toast } from "react-toastify";

import Profile from "./features/user/profile/Profile";
import Login from "./features/user/login/Login";
import SignUp from "./features/user/signUp/SignUp";
import ChangePassword from "./features/user/changePasword/ChangePassword";
import OrganizationsView from "./features/organization/OrganizationsView";
import MyNoticesList from "./features/list/MyNoticesList";
import CustomDrawer from "./features/drawer/CustomDrawer";
import Dashboard from "./features/dashboard/Dashboard";
import PageNotFound from "./errors/PageNotFound";

import { ThemeProvider } from "@mui/material/styles";
import { darkTheme, lightTheme } from "./theme/theme";

import "react-toastify/dist/ReactToastify.css";
import "./App.css";
import { useLoginFromTokenMutation } from "./api/userApi";
import { validLogin, selectToken, selectUser } from "./features/user/login/LoginSlice";
import MyTeamView from "./features/team/MyTeamView";
import TeamView from "./features/team/TeamView";

function App() {
  const dispatch = useDispatch();
  const theme = useSelector((state) => state.theme);
  const [token, setToken] = useState("");

  const [login, { loginError }] = useLoginFromTokenMutation();

  const logged = useSelector(selectToken);

  const userRole = useSelector(selectUser).userRole;

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
        <BrowserRouter>
          <body>
            <CustomDrawer />
            <Routes>
              <Route path="/" element={<Dashboard />} />
              <Route path="/login" element={<Login />} />
              <Route path="/sign-up" element={!logged ? <SignUp /> : <Navigate replace to={"/"} />} />
              <Route path="/perfil" element={logged ? <Profile /> : <Navigate replace to={"/"} />} />
              <Route path="/cambiar-password" element={<ChangePassword />} />
              <Route
                path="/organizaciones"
                // TODO: Change when roles implemented
                element={userRole === "USER" ? <OrganizationsView /> : <Navigate to="/" />}
              />
              <Route path="/mi-equipo" element={<MyTeamView />} />
              <Route path="/detalles-equipo/:id" element={<TeamView />} />
              <Route path="/mis-avisos" element={<MyNoticesList />} />
              <Route path="*" element={<PageNotFound />} />
            </Routes>
          </body>
        </BrowserRouter>
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
        />
      </div>
    </ThemeProvider>
  );
}

export default App;
