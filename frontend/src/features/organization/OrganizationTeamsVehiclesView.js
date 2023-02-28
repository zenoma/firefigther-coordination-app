import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { Box, Button } from "@mui/material";
import { useNavigate, useParams } from "react-router-dom";
import TeamsView from "../team/TeamsView";
import BackButton from "../utils/BackButton";
import VehiclesView from "../vehicle/VehiclesView";

export default function OrganizationTeamsVehiclesView() {
  let { organizationId } = useParams();
  const navigate = useNavigate();

  return (
    <Box>
      <BackButton />
      <Box display="flex" justifyContent="space-evenly" maxHeight="600px">
        <TeamsView organizationId={organizationId} />
        <VehiclesView organizationId={organizationId} />
      </Box>
    </Box>
  );
}
