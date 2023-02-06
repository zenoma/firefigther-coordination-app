import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import PropTypes from "prop-types";
import Container from "@mui/material/Container";
import Paper from "@mui/material/Paper";
import { CircularProgress } from "@mui/material";

import { selectToken } from "../user/login/LoginSlice";

import { useGetMyTeamQuery, useGetTeamsByIdQuery } from "../../api/teamApi";
import TeamCard from "./TeamCard";
import UsersList from "../list/UsersList";

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
      {/* TODO: Handle user without team */}
      {error ? (
        <h1>Oh no, there was an error</h1>
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
