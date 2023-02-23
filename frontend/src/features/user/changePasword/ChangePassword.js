import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";

import { selectUser, selectToken } from "../login/LoginSlice";

import { Button, TextField, FormControl, FormLabel } from "@mui/material";
import { useChangePasswordMutation } from "../../../api/userApi";

export default function ChangePassword() {
  const [newPassword, setNewPassword] = useState("");
  const [oldPassword, setOldPassword] = useState("");

  const user = useSelector(selectUser);
  const token = useSelector(selectToken);
  const navigate = useNavigate();

  const [changePassword] = useChangePasswordMutation();

  const handleChange = (event) => {
    if (event.target.id === "old-password") {
      setOldPassword(event.target.value);
    } else if (event.target.id === "new-password") {
      setNewPassword(event.target.value);
    }
  };

  const handleClick = async (e) => {
    const payload = {
      oldPassword: oldPassword,
      newPassword: newPassword,
      id: user.id,
      token: token,
    };

    changePassword(payload)
      .unwrap()
      .then((payload) => {
        navigate("/profile");
      });
  };

  return (
    <Paper
      sx={{
        minWidth: 400,
        maxWidth: 1000,
        padding: 3,
        textAlign: "center",
        display: "inline-block",
        boxShadow: "none",
      }}
    >
      <form>
        <FormControl>
          <FormLabel>
            <Typography variant="h5">Cambiar Contraseña</Typography>
          </FormLabel>
          <TextField
            id="old-password"
            label="Contraseña Vieja"
            type="password"
            margin="normal"
            autoComplete="current-password"
            value={oldPassword}
            onChange={(e) => handleChange(e)}
          />
          <TextField
            id="new-password"
            label="Nueva Contraseña"
            type="password"
            autoComplete="current-password"
            margin="normal"
            value={newPassword}
            onChange={(e) => handleChange(e)}
          />
          <Button
            type="button"
            color="primary"
            className="form-button"
            onClick={(e) => handleClick(e)}
            variant="contained"
          >
            Enviar
          </Button>
        </FormControl>
      </form>
    </Paper>
  );
}
