import { Box, Grid, Paper } from "@mui/material";
import React from "react";
import { useTranslation } from "react-i18next";
import { useParams } from "react-router-dom";
import CustomMap from "../map/CustomMap";
import BackButton from "../utils/BackButton";

export default function FireDetailsView() {
  let { fireId } = useParams();
  const { t } = useTranslation();

  return (
    <Box sx={{ padding: 5 }}>
      <Grid
        container
        spacing={{ xs: 2, md: 3 }}
        columns={{ xs: 4, sm: 8, md: 12 }}
      >
        <Grid item xs={1} sm={1} md={1}>
          <BackButton />
        </Grid>
        <Grid item xs={2} sm={5} md={9}>
          <Paper
            sx={{
              color: "primary.light",
              padding: 2,
            }}
            variant="outlined"
          >
            <CustomMap />
          </Paper>
        </Grid>
        <Grid item xs={1} sm={2} md={2}>
          <Paper
            sx={{
              color: "primary.light",
              padding: 2,
            }}
            variant="outlined"
          >
            Lista de cuadrantes
          </Paper>
        </Grid>
      </Grid>
    </Box>
  );
}
