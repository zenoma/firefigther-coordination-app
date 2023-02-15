import { useSelector } from "react-redux";

import {
  Box,
  Button,
  CircularProgress,
  Container,
  IconButton,
} from "@mui/material";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";

import { useTranslation } from "react-i18next";
import { useNavigate, useParams } from "react-router-dom";
import { useGetTeamsByOrganizationIdQuery } from "../../api/teamApi";
import { selectToken } from "../user/login/LoginSlice";
import TeamCreateDialog from "./TeamCreateDialog";
import TeamsTable from "./TeamsTable";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";

export default function TeamsView() {
  const token = useSelector(selectToken);
  const { t } = useTranslation();
  const navigate = useNavigate();

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

  const reloadData = () => {
    refetch();
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
          {t("teams")}
        </Typography>
        {organizationId && <div>OrgID = {organizationId}</div>}
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
