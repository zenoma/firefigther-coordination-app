
import { Box } from "@mui/material";
import { useParams } from "react-router-dom";
import TeamsView from "../team/TeamsView";
import VehiclesView from "../vehicle/VehiclesView";

export default function OrganizationTeamsVehiclesView() {

  let { organizationId } = useParams();

  return (
    <Box display="flex" justifyContent="space-evenly">
      <TeamsView organizationId={organizationId} />
      <VehiclesView organizationId={organizationId} />
    </Box>
  );
}
