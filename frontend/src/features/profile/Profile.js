import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import "./Profile.css";

import { Button, Typography } from "@mui/material";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
import Avatar from "@mui/material/Avatar";
import Paper from "@mui/material/Paper";

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
      {user && (
        <Container>
          <Grid container>
            <Grid item xs={1}>
              <Avatar alt={user.firstName} src="/static/images/avatar/2.jpg" />
            </Grid>
            <Grid item flex={10} alignItems="flex-start" justifyItems="flex-start">
              <Paper elevation={3}>
                <Typography variant="h4" margin={1}>
                  {user.firstName} {user.lastName}
                </Typography>
                <Typography margin={1}>Email: {user.email}</Typography>
                <Typography margin={1}>DNI: {user.dni}</Typography>
                <Typography margin={1}>PhoneNumber: {user.phoneNumber}</Typography>
              </Paper>
            </Grid>
          </Grid>
        </Container>
      )}

      <Button id="change-password" type="button" color="primary" onClick={(e) => handleClick(e)}>
        <Typography margin={5} variant="caption">
          Change Password
        </Typography>
      </Button>
      <Button id="logout" type="button" variant="contained" color="primary" onClick={(e) => handleClick(e)}>
        <Typography variant="body1">Logout</Typography>
      </Button>
    </main>
  );
}
