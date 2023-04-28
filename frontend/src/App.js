import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";

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
import "./App.css";
import { useLoginFromTokenMutation } from "./api/userApi";
import MyTeamView from "./features/team/MyTeamView";
import {
  selectToken,
  selectUser,
  validLogin,
} from "./features/user/login/LoginSlice";

import { CircularProgress } from "@mui/material";
import { withTranslation } from "react-i18next";
import FireDetailsView from "./features/fire/FireDetailsView";
import FireHistoryView from "./features/fire/FireHistoryView";
import FireManagementView from "./features/fire/FireManagementView";
import QuadrantHistoryView from "./features/history/QuadrantHistoryView";
import OrganizationTeamsVehiclesView from "./features/organization/OrganizationTeamsVehiclesView";
import QuadrantView from "./features/quadrant/QuadrantView";
import TeamView from "./features/team/TeamView";
import UserManagementView from "./features/user/management/UserManagementView";

function App({ t }) {

  const dispatch = useDispatch();
  const theme = useSelector((state) => state.theme);

  const [login] = useLoginFromTokenMutation();

  const logged = useSelector(selectToken);

  const userRole = useSelector(selectUser).userRole;


  const [token] = useState(localStorage.getItem("token") || "");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const payload = {
      token: token,
    };

    if (token !== "") {
      login(payload)
        .unwrap()
        .then((payload) => {
          dispatch(validLogin(payload));
          toast.info(t("succesfully-login"));
          localStorage.setItem("token", token);
          setLoading(false);
        })
        .catch(() => {
          setLoading(false);
        });
    } else {
      setLoading(false);
    }
  }, [token, dispatch, login, t]);

  if (loading) {
    return (
      <div style={{ display: "flex", justifyContent: "center", alignItems: "center", height: "100vh" }}>
        <CircularProgress />
      </div>
    );
  }

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
                userRole ? <OrganizationView /> : <Navigate to="/" />
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
            <Route path="/quadrant" element={<QuadrantView />} />
            <Route path="/quadrant-history" element={<QuadrantHistoryView />} />
            <Route path="/fire-history" element={<FireHistoryView />} />
            <Route path="/user-management" element={<UserManagementView />} />
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
