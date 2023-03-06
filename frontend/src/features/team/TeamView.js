import { Box, CircularProgress, Grid, Paper } from "@mui/material";
import { useTranslation } from "react-i18next";
import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import { useGetTeamsByIdQuery } from "../../api/teamApi";
import { selectToken } from "../user/login/LoginSlice";
import UsersList from "../user/UsersList";
import TeamCard from "./TeamCard";
import TeamUserAdd from "./TeamUserAdd";
import BackButton from "../utils/BackButton";

export default function TeamView() {
  const token = useSelector(selectToken);
  let { teamId } = useParams();

  const payload = {
    token: token,
    teamId: teamId,
  };

  const { t } = useTranslation();

  const { data, error, isLoading, refetch } = useGetTeamsByIdQuery(payload, {
    refetchOnMountOrArgChange: true,
  });

  const reloadData = () => {
    refetch();
  };

  return (
    <Box sx={{ padding: 3 }} display="flex">
      <BackButton />
      {error ? (
        <h1>{t("generic-error")}</h1>
      ) : isLoading ? (
        <CircularProgress />
      ) : data ? (
        <Grid
          container
          spacing={{ xs: 3, md: 3 }}
          columns={{ xs: 4, sm: 8, md: 12 }}
        >
          <Grid item xs={4} sm={8} md={12}>
            <TeamCard data={data} />
          </Grid>
          <Grid item xs={3} sm={7} md={12}>
            <UsersList
              teamId={data.id}
              name={teamId}
              users={data.users}
              reloadData={reloadData}
            />
            <TeamUserAdd teamId={data.id} reloadData={reloadData} />
          </Grid>
        </Grid>
      ) : null}
    </Box>
  );
}
