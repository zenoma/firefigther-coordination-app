import { Button } from "@mui/material";
import React from "react";
import { useNavigate } from "react-router-dom";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";

export default function BackButton() {
  const navigate = useNavigate();

  return (
    <Button
      sx={{ display: "flex"}}
      onClick={() => navigate(-1)}
      color="secondary"
    >
      <ArrowBackIcon fontSize="large" />
    </Button>
  );
}
