import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";

import { selectUser, selectToken } from "../login/LoginSlice";

import { Button, TextField, FormControl, FormLabel } from "@mui/material";
import { useChangePassowrdMutation } from "../../api/userApi";

export default function ChangePassword() {
  const [newPassword, setNewPassword] = useState("");
  const [oldPassword, setOldPassword] = useState("");

  const user = useSelector(selectUser);
  const token = useSelector(selectToken);
  const navigate = useNavigate();

  const [changePassword, error] = useChangePassowrdMutation();

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
    <form>
      <FormControl>
        <FormLabel>Change Password</FormLabel>
        <TextField
          id="old-password"
          label="OldPassword"
          type="password"
          margin="normal"
          autoComplete="current-password"
          value={oldPassword}
          onChange={(e) => handleChange(e)}
        />
        <TextField
          id="new-password"
          label="NewPassword"
          type="password"
          autoComplete="current-password"
          margin="normal"
          value={newPassword}
          onChange={(e) => handleChange(e)}
        />
        <Button type="button" color="primary" className="form-button" onClick={(e) => handleClick(e)}>
          Summit
        </Button>
      </FormControl>
    </form>
  );
}
