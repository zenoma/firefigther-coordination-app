import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { Box, Button } from "@mui/material";
import { useNavigate, useParams } from "react-router-dom";
import TeamsView from "../team/TeamsView";
import VehiclesView from "../vehicle/VehiclesView";

export default function OrganizationTeamsVehiclesView() {
  let { organizationId } = useParams();
  const navigate = useNavigate();

  return (
    <Box>
      <Box display="flex" justifyContent="space-evenly" maxHeight="600px">
        <TeamsView organizationId={organizationId} />
        <VehiclesView organizationId={organizationId} />
      </Box>
      <Button
        sx={{ display: "flex", margin: "10px" }}
        onClick={() => navigate("/organizations")}
        color="secondary"
        variant="contained"
      >
        <ArrowBackIcon fontSize="large" />
      </Button>
    </Box>
  );
}
