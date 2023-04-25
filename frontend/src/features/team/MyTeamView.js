import { CircularProgress } from "@mui/material";
import Container from "@mui/material/Container";
import Paper from "@mui/material/Paper";
import { useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";

import { selectToken } from "../user/login/LoginSlice";

import { useGetMyTeamQuery } from "../../api/teamApi";
import UsersList from "../user/UsersList";
import TeamCard from "./TeamCard";
import { useTranslation } from "react-i18next";
import TeamNotFoundPage from "../../errors/TeamNotFound";

export default function MyTeamView(props) {
  const token = useSelector(selectToken);

  const navigate = useNavigate();

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
    >
      {error ? (
        <TeamNotFoundPage />
      ) : isLoading ? (
        <CircularProgress />
      ) : data ? (
        <Container>
          <TeamCard data={data} />
          <UsersList teamId={data.id} name={data.code} users={data.users} />
        </Container>
      ) : null}
    </Paper>
  );
}
