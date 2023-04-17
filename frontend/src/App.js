import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { toast, ToastContainer } from "react-toastify";

import PageNotFound from "./errors/PageNotFound";
import Dashboard from "./features/dashboard/Dashboard";
import CustomDrawer from "./features/drawer/CustomDrawer";
import MyNoticesList from "./features/notice/MyNoticesList";
import OrganizationView from "./features/organization/OrganizationView";
import ChangePassword from "./features/user/changePasword/ChangePassword";
import Login from "./features/user/login/Login";
import Profile from "./features/user/profile/Profile";
import SignUp from "./features/user/signUp/SignUp";

import { ThemeProvider } from "@mui/material/styles";
import { darkTheme, lightTheme } from "./theme/theme";

import "react-toastify/dist/ReactToastify.css";
import { useLoginFromTokenMutation } from "./api/userApi";
import "./App.css";
import MyTeamView from "./features/team/MyTeamView";
import {
  selectToken,
  selectUser,
  validLogin,
} from "./features/user/login/LoginSlice";

import { withTranslation } from "react-i18next";
import OrganizationTeamsVehiclesView from "./features/organization/OrganizationTeamsVehiclesView";
import TeamView from "./features/team/TeamView";
import FireManagementView from "./features/fire/FireManagementView";
import FireDetailsView from "./features/fire/FireDetailsView";
import QuadrantView from "./features/quadrant/QuadrantView";

function App({ t }) {
  const dispatch = useDispatch();
  const theme = useSelector((state) => state.theme);
  const [token, setToken] = useState("");

  const [login] = useLoginFromTokenMutation();

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
  }, [token, dispatch, login]);

  return (
    <ThemeProvider theme={theme.darkTheme ? darkTheme : lightTheme}>
      <div className="App">
        <BrowserRouter>
          <CustomDrawer />
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/login" element={<Login />} />
            <Route
              path="/sign-up"
              element={!logged ? <SignUp /> : <Navigate replace to={"/"} />}
            />
            <Route
              path="/profile"
              element={logged ? <Profile /> : <Navigate replace to={"/"} />}
            />
            <Route path="/change-password" element={<ChangePassword />} />
            <Route
              path="/organizations"
              // TODO: Change when roles implemented
              element={
                userRole === "USER" ? <OrganizationView /> : <Navigate to="/" />
              }
            />
            <Route path="/teams" element={<TeamView />} />
            <Route path="/my-team" element={<MyTeamView />} />
            <Route
              path="/organizations/teams"
              element={<OrganizationTeamsVehiclesView />}
            />
            <Route path="/my-notices" element={<MyNoticesList />} />{" "}
            <Route path="/fire-management" element={<FireManagementView />} />
            <Route path="/fire-details/" element={<FireDetailsView />} />

            {/* // fixme: */}
            <Route path="/fire-history" element={<FireDetailsView />} />
            <Route path="/quadrant" element={<QuadrantView />} />
            <Route path="*" element={<PageNotFound />} />
          </Routes>
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

export default withTranslation()(App);
