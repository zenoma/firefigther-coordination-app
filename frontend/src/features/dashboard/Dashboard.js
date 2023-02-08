import * as React from "react";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import Typography from "@mui/material/Typography";
import Grid from "@mui/material/Grid";
import Container from "@mui/material/Container";

import CustomMap from "../map/CustomMap";
import Notice from "../notice/Notice";
import { Card, CardHeader, CardMedia } from "@mui/material";

import Box from "@mui/material/Box";
import Paper from "@mui/material/Paper";


export default function Dashboard() {
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
            }}
            variant="outlined"
          >
            <CardHeader
              title={<Typography variant="h4">Mapa geográfico</Typography>}
            />
            <CardMedia>
              <CustomMap />
            </CardMedia>
          </Card>
        </Grid>
        <Grid item xs={2} sm={4} md={4}>
          <Paper variant="outlined" sx={{ padding: 2 }}>
            <Typography variant="h4" color="primary.light">
              Avisos
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
