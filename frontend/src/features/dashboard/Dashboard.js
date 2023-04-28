import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import React, { useEffect } from "react";

import { Card, CardHeader, CardMedia } from "@mui/material";
import CustomMap from "../map/CustomMap";
import Notice from "../notice/Notice";

import Box from "@mui/material/Box";
import Paper from "@mui/material/Paper";
import { useTranslation } from "react-i18next";
import { useGetQuadrantWithActiveFiresQuery } from "../../api/quadrantApi";

export default function Dashboard() {
  const { t } = useTranslation();

  const { data: quadrants, refetch } = useGetQuadrantWithActiveFiresQuery();

  useEffect(() => {
    refetch();
  }, [refetch]);

  return (
    <Box sx={{ padding: 5 }}>
      <Grid
        container
        spacing={{ xs: 2, md: 3 }}
        columns={{ xs: 4, sm: 8, md: 12 }}
      >
        <Grid item xs={8} sm={8} md={8}>
          <Card
            sx={{
              color: "primary.light",
              padding: 2,
              minHeight: 500,
            }}
            variant="outlined"
          >
            <CardHeader
              title={
                <Typography variant="h4">{t("geographic-map")}</Typography>
              }
            />
            <CardMedia>
              <Box sx={{ height: 500 }}>
                {quadrants ? (
                  <CustomMap quadrants={quadrants} />
                ) : (
                  <Typography variant="body1">{t("loading")}</Typography>
                )}
              </Box>
            </CardMedia>
          </Card>
        </Grid>
        <Grid item xs={2} sm={4} md={4}>
          <Paper variant="outlined" sx={{ padding: 2 }}>
            <Typography variant="h4" color="primary.light">
              {t("notices")}
            </Typography>
            <Notice />
          </Paper>
        </Grid>

        <Grid item xs={2} sm={4} md={4}>
          <Paper variant="outlined">
            <Typography variant="h4" color="primary">
              CARD 2
            </Typography>
          </Paper>
        </Grid>
        <Grid item xs={8}>
          <Paper variant="outlined">
            <Typography variant="h4" color="primary">
              CARD 3
            </Typography>
          </Paper>
        </Grid>
      </Grid>
    </Box>
  );
}
