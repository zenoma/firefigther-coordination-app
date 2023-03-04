import { Box, Grid } from "@mui/material";
import { useNavigate, useParams } from "react-router-dom";
import TeamsView from "../team/TeamsView";
import BackButton from "../utils/BackButton";
import VehiclesView from "../vehicle/VehiclesView";

export default function OrganizationTeamsVehiclesView() {
  let { organizationId } = useParams();
  const navigate = useNavigate();

  return (
    <Box sx={{ padding: 3 }}>
      <Grid
        container
        spacing={{ xs: 3, md: 3 }}
        columns={{ xs: 4, sm: 8, md: 12 }}
      >
        <Grid item xs={4} sm={8} md={1}>
          <BackButton />
        </Grid>
        <Grid item xs={4} sm={8} md={5}>
          <Box>
            <TeamsView organizationId={organizationId} />
          </Box>
        </Grid>
        <Grid item xs={4} sm={8} md={5}>
          <Box>
            <VehiclesView organizationId={organizationId} />
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
}
