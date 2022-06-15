import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import "./Profile.css";

import { Button, Typography } from "@mui/material";

import { logout, selectUser } from "../login/LoginSlice";

export default function Profile() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const user = useSelector(selectUser);
  const userStr = JSON.stringify(user, null, 4);

  const handleClick = (e) => {
    if (e.target.id === "change-password") {
      navigate("/change-password");
    } else if (e.target.id === "logout") {
      dispatch(logout());
      navigate("/");
    }
  };

  return (
    <main style={{ padding: "1rem 0" }}>
      {user && <Typography variant="h6">${userStr}</Typography>}
      <Button
        id="change-password"
        type="button"
        color="primary"
        className="form-button"
        onClick={(e) => handleClick(e)}
      >
        Change Password
      </Button>
      <Button
        id="logout"
        type="button"
        variant="contained"
        color="primary"
        className="options-button"
        onClick={(e) => handleClick(e)}
      >
        Logout
      </Button>
    </main>
  );
}
