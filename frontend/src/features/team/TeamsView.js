import PropTypes from "prop-types";
import { useSelector } from "react-redux";

import { Box, CircularProgress } from "@mui/material";

import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";

import { useTranslation } from "react-i18next";
import { useGetActiveTeamsByOrganizationIdQuery } from "../../api/teamApi";
import { selectToken } from "../user/login/LoginSlice";
import TeamCreateDialog from "./TeamCreateDialog";
import TeamsTable from "./TeamsTable";

export default function TeamsView(props) {
  const token = useSelector(selectToken);
  const { t } = useTranslation();

  const organizationId = props.organizationId;

  const payload = {
    token: token,
    organizationId: organizationId,
  };

  const {
    data: teamsList,
    isFetching,
    refetch,
  } = useGetActiveTeamsByOrganizationIdQuery(payload, {
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
          {t("teams-list")}
        </Typography>
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
