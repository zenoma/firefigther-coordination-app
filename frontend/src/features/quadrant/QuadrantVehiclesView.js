import AddIcon from "@mui/icons-material/Add";
import PropTypes from "prop-types";
import { useSelector } from "react-redux";

import {
  Box,
  Button,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Fab,
} from "@mui/material";

import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";

import { useState } from "react";
import { useTranslation } from "react-i18next";
import { useGetQuadrantByIdQuery } from "../../api/quadrantApi";
import { selectToken } from "../user/login/LoginSlice";
import QuadrantVehicleTable from "./QuadrantVehicleTable";
import VehicleDataGrid from "../vehicle/VehicleDataGrid";
import { useDeployVehicleMutation } from "../../api/vehicleApi";
import { toast } from "react-toastify";

export default function QuadrantVehiclesView(props) {
  const token = useSelector(selectToken);
  const { t } = useTranslation();
  const [open, setOpen] = useState(false);

  const [selectedId, setSelectedId] = useState(-1);

  const quadrantId = props.quadrantId;

  const [deployVehicle] = useDeployVehicleMutation();

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleClick = () => {
    const payload = {
      vehicleId: selectedId,
      token: token,
      gid: quadrantId,
    };
    if (selectedId === -1) {
      toast.error(t("vehicle-deployed-no-selected"));
    } else {
      deployVehicle(payload)
        .unwrap()
        .then(() => {
          toast.success(t("vehicle-deployed-successfully"));
          reloadData();
          handleClose();
        })
        .catch((error) => toast.error(t("vehicle-deployed-error")));
    }
  };

  const childToParent = (childdata) => {
    setSelectedId(childdata.id);
  };

  const payload = {
    token: token,
    quadrantId: quadrantId,
  };

  const {
    data: quadrantInfo,
    isFetching,
    refetch,
  } = useGetQuadrantByIdQuery(payload, {
    refetchOnMountOrArgChange: true,
  });

  const reloadData = () => {
    setSelectedId(-1);
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
          {t("quadrant-vehicles-deployed")}
        </Typography>
        {isFetching ? (
          <CircularProgress />
        ) : quadrantInfo ? (
          <QuadrantVehicleTable
            reloadData={reloadData}
            vehicles={quadrantInfo.vehicleDtoList}
            quadrantId={quadrantId}
          />
        ) : null}
        <Box m={1}>
          <Fab color="primary" aria-label="add" onClick={handleClickOpen}>
            <AddIcon />
          </Fab>
        </Box>
        <Dialog fullWidth open={open} onClose={handleClose}>
          <DialogTitle sx={{ color: "primary.light" }}>{t("vehicle-deploy-title")} </DialogTitle>
          <DialogContent>
            <VehicleDataGrid childToParent={childToParent} />
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose}>{t("cancel")}</Button>
            <Button autoFocus variant="contained" onClick={() => handleClick()}>
              {t("deploy")}
            </Button>
          </DialogActions>
        </Dialog>
      </Paper>
    </Box>
  );
}

QuadrantVehiclesView.propTypes = {
  quadrantId: PropTypes.number.isRequired,
};
