import React, { useState } from "react";
import { useSelector } from "react-redux";
import { Button, TextField, Box } from "@mui/material";
import { transformCoordinates } from "../../app/utils/coordinatesTransformations";
import { useCreateNoticeMutation } from "../../api/noticeApi";
import { selectToken } from "../user/login/LoginSlice";
import { toast } from "react-toastify";

export default function Notice() {
  const [body, setBody] = useState("");

  const [coordinates, setCoordinates] = useState("");

  const token = useSelector(selectToken);

  const [createNotice] = useCreateNoticeMutation();

  const handleChange = (event) => {
    navigator.geolocation.getCurrentPosition(function (position) {
      setCoordinates(transformCoordinates(position.coords.longitude, position.coords.latitude));
    });

    if (event.target.id === "notice-body") {
      setBody(event.target.value);
    }
  };

  const handleClick = async (e) => {
    const payload = { body: body, coordinates: coordinates, token: token };

    createNotice(payload).unwrap();
    setBody("");
    toast.info("Notice created");
  };

  return (
    <Box>
      <TextField
        fullWidth
        id="notice-body"
        label="Comment"
        type="normal"
        margin="normal"
        autoComplete="notice-body"
        value={body}
        onChange={(e) => handleChange(e)}
      />
      <Button
        type="button"
        variant="contained"
        color="secondary"
        className="form-button"
        onClick={(e) => handleClick(e)}
      >
        Create notice
      </Button>
    </Box>
  );
}
