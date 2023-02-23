import { CircularProgress } from "@mui/material";
import Container from "@mui/material/Container";
import Paper from "@mui/material/Paper";
import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";

import { selectToken } from "../user/login/LoginSlice";

import { useGetMyTeamQuery } from "../../api/teamApi";
import UsersList from "../list/UsersList";
import TeamCard from "./TeamCard";
import { useTranslation } from "react-i18next";

export default function MyTeamView(props) {
  const token = useSelector(selectToken);

  const teamId = useParams()["id"];
  const { t } = useTranslation();

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
