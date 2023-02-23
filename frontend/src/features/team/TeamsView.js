import PropTypes from "prop-types";
import { useSelector } from "react-redux";

import { Box, CircularProgress, Dialog } from "@mui/material";

import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";

import InfoIcon from "@mui/icons-material/Info";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import { useGetOrganizationByIdQuery } from "../../api/organizationApi";
import { useGetTeamsByOrganizationIdQuery } from "../../api/teamApi";
import OrganizationDetailsCard from "../organization/OrganizationDetailsCard";
import { selectToken } from "../user/login/LoginSlice";
import TeamCreateDialog from "./TeamCreateDialog";
import TeamsTable from "./TeamsTable";

export default function TeamsView(props) {
  const token = useSelector(selectToken);
  const { t } = useTranslation();
  const [open, setOpen] = useState(false);

  const organizationId = props.organizationId;

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
    <Box maxWidth="600px">
      <Paper
        sx={{
          display: "inline-block",
          minWidth: "450px",
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

TeamsView.propTypes = {
  organizationId: PropTypes.number.isRequired,
};
