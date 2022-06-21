import { useDispatch, useSelector } from "react-redux";
import { Box, IconButton } from "@mui/material";
import DarkIcon from "@mui/icons-material/DarkMode";
import LightIcon from "@mui/icons-material/LightMode";
import React from "react";

import { toggleTheme } from "../theme/themeSlice";

export const SwitchThemeButton = () => {
  const dispatch = useDispatch();
  const theme = useSelector((state) => state.theme);

  return (
    <Box>
      <IconButton sx={{ ml: 1 }} color="inherit" onClick={() => dispatch(toggleTheme())}>
        {theme.darkTheme ? <LightIcon /> : <DarkIcon />}
      </IconButton>
    </Box>
  );
};
