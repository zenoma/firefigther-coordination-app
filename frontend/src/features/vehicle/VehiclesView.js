import PropTypes from "prop-types";
import { useSelector } from "react-redux";

import { Box, CircularProgress } from "@mui/material";

import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";

import { useTranslation } from "react-i18next";
import { useGetVehiclesByOrganizationIdQuery } from "../../api/vehicleApi";
import { selectToken } from "../user/login/LoginSlice";
import VehicleCreateDialog from "./VehicleCreateDialog";
import VehicleTable from "./VehicleTable";

export default function VehiclesView(props) {
  const token = useSelector(selectToken);
  const { t } = useTranslation();

  const organizationId = props.organizationId;

  const payload = {
    token: token,
    organizationId: organizationId,
  };

  const {
    data: vehicleList,
    isFetching,
    refetch,
  } = useGetVehiclesByOrganizationIdQuery(payload, {
    refetchOnMountOrArgChange: true,
  });

  const reloadData = () => {
    refetch();
  };

  return (
    <Box>
      <Paper
        sx={{
          padding: "10px",
        }}
      >
        <Typography
          variant="h6"
          margin={1}
          sx={{ fontWeight: "bold", color: "primary.light" }}
        >
          {t("vehicle-list")}
        </Typography>
        {isFetching ? (
          <CircularProgress />
        ) : vehicleList ? (
          <VehicleTable reloadData={reloadData} vehicles={vehicleList} />
        ) : null}

        <VehicleCreateDialog
          reloadData={reloadData}
          organizationId={organizationId}
        />
      </Paper>
    </Box>
  );
}

VehiclesView.propTypes = {
  organizationId: PropTypes.number.isRequired,
};
