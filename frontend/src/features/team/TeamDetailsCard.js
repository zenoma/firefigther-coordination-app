import { CircularProgress } from "@mui/material";
import Container from "@mui/material/Container";
import Paper from "@mui/material/Paper";
import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";

import { selectToken } from "../user/login/LoginSlice";

import { useGetTeamsByIdQuery } from "../../api/teamApi";
import UsersList from "../list/UsersList";
import TeamCard from "./TeamCard";

export default function TeamView(props) {
  const token = useSelector(selectToken);

  const teamId = useParams()["id"];

  const payload = {
    token: token,
    teamId: teamId,
  };

  const { data, error, isLoading } = useGetTeamsByIdQuery(payload, {
    refetchOnMountOrArgChange: true,
  });

  return (
    <Paper
      sx={{
        maxWidth: 500,
        padding: 3,
        textAlign: "center",
        display: "inline-block",
        boxShadow: "none",
      }}
    >
      {error ? (
        <h1>{t("generic-error")}</h1>
      ) : isLoading ? (
        <CircularProgress />
      ) : data ? (
        <Container>
          <TeamCard data={data} />
          <UsersList teamId={teamId} name={teamId} />
        </Container>
      ) : null}
    </Paper>
  );
}
