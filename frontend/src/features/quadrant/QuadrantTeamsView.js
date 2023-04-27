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
import { toast } from "react-toastify";
import { useGetQuadrantByIdQuery } from "../../api/quadrantApi";
import { useDeployTeamMutation } from "../../api/teamApi";
import TeamDataGrid from "../team/TeamDataGrid";
import { selectToken } from "../user/login/LoginSlice";
import QuadrantTeamsTable from "./QuadrantTeamsTable";

export default function QuadrantTeamsView(props) {
  const token = useSelector(selectToken);
  const { t } = useTranslation();
  const [open, setOpen] = useState(false);
  const [selectedId, setSelectedId] = useState(-1);

  const quadrantId = props.quadrantId;

  const [deployTeam] = useDeployTeamMutation();

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleClick = () => {
    const payload = {
      teamId: selectedId,
      token: token,
      gid: quadrantId,
    };

    if (selectedId === -1) {
      toast.error(t("team-deployed-no-selected"));
    } else {
      deployTeam(payload)
        .unwrap()
        .then(() => {
          toast.success(t("team-deployed-successfully"));
          reloadData();
          handleClose();
        })
        .catch((error) => toast.error(t("team-deployed-error")));
    }
  };

  const childToParent = (childdata) => {
    setSelectedId(childdata.id);
  };

  const payload = {
    vehicleId: selectedId,
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
          {t("quadrant-teams-deployed")}
        </Typography>
        {isFetching ? (
          <CircularProgress />
        ) : quadrantInfo ? (
          <QuadrantTeamsTable
            reloadData={reloadData}
            teams={quadrantInfo.teamDtoList}
            quadrantId={quadrantId}
          />
        ) : null}
        <Box m={1}>
          <Fab color="primary" aria-label="add" onClick={handleClickOpen}>
            <AddIcon />
          </Fab>
        </Box>
        <Dialog fullWidth open={open} onClose={handleClose}>
          <DialogTitle sx={{ color: "primary.light" }}>{t("team-deploy-title")} </DialogTitle>
          <DialogContent>
            <TeamDataGrid childToParent={childToParent} />
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

QuadrantTeamsView.propTypes = {
  quadrantId: PropTypes.number.isRequired,
};
