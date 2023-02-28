import { CircularProgress, Container, Paper } from "@mui/material";
import { useTranslation } from "react-i18next";
import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import { useGetMyTeamQuery, useGetTeamsByIdQuery } from "../../api/teamApi";
import { selectToken } from "../user/login/LoginSlice";
import UsersList from "../list/UsersList";
import TeamCard from "./TeamCard";

export default function TeamView() {
  const token = useSelector(selectToken);
  let { teamId } = useParams();

  const payload = {
    token: token,
    teamId: teamId,
  };

  const { t } = useTranslation();

  const { data, error, isLoading } = useGetTeamsByIdQuery(payload, {
    refetchOnMountOrArgChange: true,
  });

  return (
    <Paper
      sx={{
        width: "100%",
        maxWidth: 1000,
        maxHeight: 1000,
        display: "inline-block",
        padding: "10px",
      }}
      elevation={5}
    >
      {error ? (
        <h1>{t("generic-error")}</h1>
      ) : isLoading ? (
        <CircularProgress />
      ) : data ? (
        <Container>
          <TeamCard data={data} />
          <UsersList teamId={data.id} name={teamId} />
        </Container>
      ) : null}
    </Paper>
  );
}
