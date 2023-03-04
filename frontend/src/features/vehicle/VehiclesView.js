import PropTypes from "prop-types";
import { useSelector } from "react-redux";

import { Box, CircularProgress, Dialog } from "@mui/material";

import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";

import InfoIcon from "@mui/icons-material/Info";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { useGetOrganizationByIdQuery } from "../../api/organizationApi";
import { useGetVehiclesByOrganizationIdQuery } from "../../api/vehicleApi";
import OrganizationDetailsCard from "../organization/OrganizationDetailsCard";
import { selectToken } from "../user/login/LoginSlice";
import VehicleCreateDialog from "./VehicleCreateDialog";
import VehicleTable from "./VehicleTable";

export default function VehiclesView(props) {
  const token = useSelector(selectToken);
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);

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

  const { data: organizationData } = useGetOrganizationByIdQuery(payload);

  const reloadData = () => {
    refetch();
  };

  const handleInfoClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <Box>
      <Paper
        sx={{
          padding: "10px",
        }}
      >
        <Typography
          variant="h4"
          margin={1}
          sx={{ fontWeight: "bold", color: "primary.light" }}
        >
          {t("vehicle-list")}
        </Typography>
        {organizationData && (
          <Box display="flex" alignItems="center" justifyContent="center">
            <Typography variant="h6" color="secondary.dark">
              {organizationData.name}
            </Typography>
            <InfoIcon
              onClick={() => handleInfoClickOpen()}
              htmlColor="grey"
              fontSize="small"
            />
          </Box>
        )}

        <Dialog open={open} onClose={handleClose}>
          <OrganizationDetailsCard data={organizationData} />
        </Dialog>
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
