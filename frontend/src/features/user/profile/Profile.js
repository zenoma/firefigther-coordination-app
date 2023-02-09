import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import "./Profile.css";

import { Button, Typography } from "@mui/material";
import Avatar from "@mui/material/Avatar";
import Paper from "@mui/material/Paper";

import { logout, selectUser } from "../login/LoginSlice";

export default function Profile() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const user = useSelector(selectUser);

  const handleClick = (e) => {
    if (e.target.id === "cambiar-password") {
      navigate("/cambiar-password");
    } else if (e.target.id === "logout") {
      dispatch(logout());
      navigate("/");
    }
  };

  return (
    <Paper
      sx={{
        maxWidth: 1000,
        padding: 3,
        textAlign: "center",
        display: "inline-block",
        boxShadow: "none",
      }}
    >
      {user && (
        <Paper sx={{ padding: 2 }} elevation={3}>
          <Avatar alt={user.firstName} src="/static/images/avatar/2.jpg" />
          <Typography variant="h4" margin={1}>
            {user.firstName} {user.lastName}
          </Typography>
          <Typography
            variant="h6"
            margin={1}
            color="primary"
            sx={{ fontWeight: "bold" }}
          >
            Email:
          </Typography>
          <Typography variant="h6" margin={1}>
            {user.email}
          </Typography>
          <Typography
            variant="h6"
            margin={1}
            color="primary"
            sx={{ fontWeight: "bold" }}
          >
            DNI:
          </Typography>
          <Typography variant="h6" margin={1}>
            {user.dni}
          </Typography>
          <Typography
            variant="h6"
            margin={1}
            color="primary"
            sx={{ fontWeight: "bold" }}
          >
            PhoneNumber:
          </Typography>
          <Typography variant="h6" margin={1}>
            {user.phoneNumber}
          </Typography>
          <Typography
            variant="h6"
            margin={1}
            color="primary"
            sx={{ fontWeight: "bold" }}
          >
            Role:
          </Typography>
          <Typography variant="h6" margin={1}>
            {user.userRole}
          </Typography>
        </Paper>
      )}
      <Button
        id="cambiar-password"
        type="button"
        color="primary"
        onClick={(e) => handleClick(e)}
      >
        <Typography margin={5} variant="caption">
          Cambiar contraseña
        </Typography>
      </Button>
      <Button
        id="logout"
        type="button"
        variant="contained"
        color="secondary"
        onClick={(e) => handleClick(e)}
      >
        <Typography variant="body1">Logout</Typography>
      </Button>
    </Paper>
  );
}
