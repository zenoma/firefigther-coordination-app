import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import { Button, TextField, FormControl, FormLabel, Paper, Typography } from "@mui/material";

import { useLoginMutation } from "../../api/userApi.js";
import { selectToken, validLogin } from "./LoginSlice.js";
import { emailValidation } from "../../app/utils/validations.js";
import "./Login.css";

export default function Login() {
  const [email, setEmail] = useState("");
  const [isValidEmail, setIsValidEmail] = useState(false);
  const [password, setPassword] = useState("");

  const dispatch = useDispatch();

  const [login, { loginError }] = useLoginMutation();
  const navigate = useNavigate();

  const token = useSelector(selectToken);

  const handleChange = (event) => {
    if (event.target.id === "email") {
      setEmail(event.target.value);
      setIsValidEmail(emailValidation(event.target.value));
    } else {
      setPassword(event.target.value);
    }
  };

  const handleClick = async (e) => {
    const payload = {
      userName: email,
      password: password,
    };

    login(payload)
      .unwrap()
      .then((payload) => {
        dispatch(validLogin(payload));
        toast.info("Successfully logged in.");
        navigate("/profile");
      });
  };

  return (
    <Paper
      sx={{
        maxWidth: 500,
        padding: 3,
        textAlign: "center",
      }}
      elevation={4}
    >
      <form>
        <FormControl>
          <FormLabel>
            <Typography variant="h5">Login</Typography>
          </FormLabel>
          <TextField
            id="email"
            label="Email"
            type="email"
            margin="normal"
            autoComplete="current-email"
            error={!isValidEmail && email !== ""}
            helperText={!isValidEmail && email !== "" ? "Not valid email!" : " "}
            value={email}
            onChange={(e) => handleChange(e)}
          />
          <TextField
            id="password"
            label="Password"
            type="password"
            autoComplete="current-password"
            margin="normal"
            value={password}
            onChange={(e) => handleChange(e)}
          />
          <Button
            type="button"
            color="primary"
            className="form-button"
            disabled={!isValidEmail}
            onClick={(e) => handleClick(e)}
          >
            Log in
          </Button>
        </FormControl>
      </form>
    </Paper>
  );
}
