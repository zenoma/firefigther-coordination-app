import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import Paper from "@mui/material/Paper";
import Container from "@mui/material/Container";
import { CircularProgress } from "@mui/material";

import { selectToken } from "../user/login/LoginSlice";

import { useGetMyTeamQuery, useGetTeamsByIdQuery } from "../../api/teamApi";
import TeamCard from "./TeamCard";
import UsersList from "../list/UsersList";

export default function MyTeamView(props) {
  const token = useSelector(selectToken);

  const teamId = useParams()["id"];

  const payload = {
    token: token,
    teamId: teamId,
  };

  const { data, error, isLoading } = useGetMyTeamQuery(payload, {
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
      {/* TODO: Handle user without team */}
      {error ? (
        <h1>Oh no, there was an error</h1>
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
