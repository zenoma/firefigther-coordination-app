import { useSelector } from "react-redux";

import { Box, Button, CircularProgress, Dialog } from "@mui/material";

import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import InfoIcon from "@mui/icons-material/Info";
import { useTranslation } from "react-i18next";
import { useNavigate, useParams } from "react-router-dom";
import { useGetOrganizationByIdQuery } from "../../api/organizationApi";
import { useGetTeamsByOrganizationIdQuery } from "../../api/teamApi";
import { selectToken } from "../user/login/LoginSlice";
import TeamCreateDialog from "./TeamCreateDialog";
import TeamsTable from "./TeamsTable";
import OrganizationDetailsCard from "../organization/OrganizationDetailsCard";
import { useState } from "react";

export default function TeamsView() {
  const token = useSelector(selectToken);
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);

  let { organizationId } = useParams();

  const payload = {
    token: token,
    organizationId: organizationId,
  };

  const {
    data: teamsList,
    isFetching,
    refetch,
  } = useGetTeamsByOrganizationIdQuery(payload, {
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
      <Button onClick={() => navigate("/organizations")} color="secondary">
        <ArrowBackIcon />
      </Button>
      <Paper
        sx={{
          display: "inline-block",
          padding: "10px",
        }}
        elevation={5}
      >
        <Typography
          variant="h4"
          margin={1}
          sx={{ fontWeight: "bold", color: "primary.light" }}
        >
          {t("teams-list")}
        </Typography>
        {organizationData && (
          <Box display="flex" alignItems="center" justifyContent="center">
            <Typography variant="h6" color="secondary.dark">
              {organizationData.name}
            </Typography>
            <InfoIcon onClick={() => handleInfoClickOpen()} htmlColor="grey" fontSize="small" />
          </Box>
        )}

        <Dialog open={open} onClose={handleClose}>
          <OrganizationDetailsCard data={organizationData} />
        </Dialog>
        {isFetching ? (
          <CircularProgress />
        ) : teamsList ? (
          <TeamsTable reloadData={reloadData} teams={teamsList} />
        ) : null}

        <TeamCreateDialog
          reloadData={reloadData}
          organizationId={organizationId}
        />
      </Paper>
    </Box>
  );
}
