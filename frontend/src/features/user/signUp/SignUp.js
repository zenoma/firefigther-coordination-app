import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import { Button, TextField, FormControl, FormLabel, Grid, Link, Paper, Typography } from "@mui/material";

import { useSignUpMutation } from "../../../api/userApi";
import { emailValidation, dniValidation, phoneNumberValidation } from "../../../app/utils/validations.js";
import "./SignUp.css";

export default function Login() {
  const [email, setEmail] = useState("");
  const [isValidEmail, setIsValidEmail] = useState(false);
  const [password, setPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [dni, setDni] = useState("");
  const [isValidDni, setIsValidDni] = useState(false);
  const [phoneNumber, setPhoneNumber] = useState("");
  const [isValidPhoneNumber, setIsValidPhoneNumber] = useState(false);


  const [signUp] = useSignUpMutation();
  const navigate = useNavigate();

  const handleChange = (event) => {
    var id = event.target.id;
    var value = event.target.value;

    switch (id) {
      case "email":
        setEmail(value);
        setIsValidEmail(emailValidation(value));
        break;
      case "password":
        setPassword(value);
        break;
      case "firstName":
        setFirstName(value);
        break;
      case "lastName":
        setLastName(value);
        break;
      case "dni":
        setDni(value);
        setIsValidDni(dniValidation(value));
        break;
      case "phoneNumber":
        setPhoneNumber(value);
        setIsValidPhoneNumber(phoneNumberValidation(value));
        break;

      default:
        break;
    }
  };

  const handleLinkClick = () => {
    setEmail("");
    setIsValidEmail(true);
    setPassword("");
    setFirstName("");
    setLastName("");
    setDni("");
    setIsValidDni(true);
    setPhoneNumber("");
    setIsValidPhoneNumber(true);

    navigate("/login");
  };

  const handleClick = async () => {
    const payload = {
      email: email,
      password: password,
      firstName: firstName,
      lastName: lastName,
      dni: dni,
      phoneNumber: phoneNumber,
    };

    signUp(payload)
      .unwrap()
      .then(() => {
        toast.info("Successfully signed up.");
        navigate("/login");
      });
  };

  return (
    <Paper
      sx={{
        maxWidth: 500,
        padding: 3,
        textAlign: "center",
        display: "inline-block",
        boxShadow: "none",
      }}
      elevation={4}
    >
      <form>
        <FormControl>
          <FormLabel>
            <Typography variant="h5">Sign Up</Typography>
          </FormLabel>
          <Grid container spacing={2}>
            <Grid item xs={6}>
              <TextField
                id="firstName"
                label="First Name"
                type="text"
                autoComplete="current-firstName"
                margin="dense"
                value={firstName}
                onChange={(e) => handleChange(e)}
                helperText=" "
                required
                variant="standard"
              />
            </Grid>
            <Grid item xs={6}>
              <TextField
                id="lastName"
                label="Last Name"
                type="text"
                autoComplete="current-lastName"
                margin="dense"
                value={lastName}
                onChange={(e) => handleChange(e)}
                helperText=" "
                required
                variant="standard"
              />
            </Grid>
            <Grid item xs={6}>
              <TextField
                id="dni"
                label="DNI"
                type="text"
                autoComplete="current-dni"
                margin="dense"
                value={dni}
                error={!isValidDni && dni !== ""}
                helperText={!isValidDni && dni !== "" ? "Must have 8 numbers and a letter" : " "}
                onChange={(e) => handleChange(e)}
                required
                variant="standard"
              />
            </Grid>
            <Grid item xs={6}>
              <TextField
                id="phoneNumber"
                label="Phone Number"
                type="text"
                autoComplete="current-phoneNumber"
                margin="dense"
                value={phoneNumber}
                error={!isValidPhoneNumber && phoneNumber !== ""}
                helperText={!isValidPhoneNumber && phoneNumber !== "" ? `Must have 9 digits` : " "}
                onChange={(e) => handleChange(e)}
                required
                variant="standard"
              />
            </Grid>
            <Grid item xs={6}>
              <TextField
                id="email"
                label="Email"
                type="email"
                margin="dense"
                autoComplete="current-email"
                error={!isValidEmail && email !== ""}
                helperText={!isValidEmail && email !== "" ? "Not valid email!" : " "}
                value={email}
                onChange={(e) => handleChange(e)}
                variant="standard"
              />
            </Grid>
            <Grid item xs={6}>
              <TextField
                id="password"
                label="Password"
                type="password"
                autoComplete="current-password"
                margin="dense"
                value={password}
                onChange={(e) => handleChange(e)}
                helperText=" "
                variant="standard"
              />
            </Grid>
          </Grid>
          <Button
            type="button"
            color="primary"
            className="form-button"
            disabled={!isValidEmail}
            onClick={(e) => handleClick(e)}
          >
            Sign Up
          </Button>
          <Link component="button" variant="body2" fontSize={15} onClick={(e) => handleLinkClick(e)}>
            Already have a account?
          </Link>
        </FormControl>
      </form>
    </Paper>
  );
}
