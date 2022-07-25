import * as React from "react";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import Typography from "@mui/material/Typography";
import Grid from "@mui/material/Grid";

import CustomMap from "../map/CustomMap";
import Notice from "../notice/Notice";
import MyNoticesList from "../list/MyNoticesList";
import { Card, CardHeader, CardMedia } from "@mui/material";

const theme = createTheme();

theme.typography.h3 = {
  fontSize: "1.2rem",
  "@media (min-width:600px)": {
    fontSize: "1.5rem",
  },
  [theme.breakpoints.up("md")]: {
    fontSize: "2rem",
  },
};

export default function Dashboard() {
  return (
    <div>
      <ThemeProvider theme={theme}>
        <div style={{ padding: "10px" }}>
          <Typography variant="h2" mb="4">
            My dashboard
          </Typography>

          <Grid container spacing={2} alignItems="flex-start">
            <Grid item xs>
              <Card sx={{ minWidth: "800px", minHeight: "600px" }} variant="outlined">
                <CardHeader title="Fire Map" />
                <CardMedia>
                  <CustomMap />
                </CardMedia>
              </Card>
            </Grid>
            <Grid item xs={4}>
              <Card sx={{ padding: 2 }} variant="outlined">
                <CardHeader title="Notices" />
                <CardMedia>
                  <Notice />
                </CardMedia>
              </Card>
            </Grid>
            <Grid item xs={2}>
              <Card sx={{ padding: 2 }} variant="outlined">
                <CardHeader title="CARD 2" />
              </Card>
            </Grid>
            <Grid item xs={2}>
              <Card sx={{ padding: 2 }} variant="outlined">
                <CardHeader title="CARD 3" />
              </Card>
            </Grid>
          </Grid>
        </div>
      </ThemeProvider>
    </div>
  );
}
