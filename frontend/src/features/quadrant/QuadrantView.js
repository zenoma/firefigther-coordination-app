import { Box, Grid, Typography } from "@mui/material";
import React from "react";
import { useTranslation } from "react-i18next";
import { useLocation } from "react-router-dom";
import BackButton from "../utils/BackButton";
import QuadrantTeamsView from "./QuadrantTeamsView";
import QuadrantVehiclesView from "./QuadrantVehiclesView";

export default function QuadrantView() {
  const location = useLocation();
  const quadrantId = location.state.quadrantId;
  const { t } = useTranslation();

  return (
    <Box sx={{ padding: 3 }}>
      <BackButton />
      <Grid
        container
        spacing={{ xs: 3, md: 3 }}
        columns={{ xs: 4, sm: 8, md: 12 }}
      >
        <Grid item xs={4} sm={8} md={12}>
          <Typography
            variant="h4"
            margin={1}
            sx={{ fontWeight: "bold", color: "primary.light" }}
          >
            {t("quadrant-details")}
          </Typography>
        </Grid>
        <Grid item xs={4} sm={8} md={6}>
          <Box>
            <QuadrantTeamsView quadrantId={quadrantId} />
          </Box>
        </Grid>
        <Grid item xs={4} sm={8} md={6}>
          <Box>
            <QuadrantVehiclesView quadrantId={quadrantId} />
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
}
