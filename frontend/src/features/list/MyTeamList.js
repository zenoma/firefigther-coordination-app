import { useState } from "react";
import { useSelector } from "react-redux";

import ListSubheader from "@mui/material/ListSubheader";
import { CircularProgress, Typography } from "@mui/material";
import List from "@mui/material/List";

import { selectToken, selectUser } from "../user/login/LoginSlice";
import { useGetMyTeamQuery } from "../../api/teamApi";

import UsersList from "./UsersList";

export default function MyTeamList() {
  const [list, setList] = useState("");

  const token = useSelector(selectToken);

  const payload = {
    token: token,
  };

  const { data, error, isLoading } = useGetMyTeamQuery(payload, { refetchOnMountOrArgChange: true });

  if (data === "") {
    setList(data);
  }

  return (
    <List
      sx={{
        width: "100%",
        maxWidth: 500,
        maxHeight: 500,
        overflow: "auto",
      }}
      component="nav"
      aria-labelledby="nested-list-subheader"
      subheader={
        <ListSubheader component="div" id="nested-list-subheader">
          <Typography variant="h3" sx={{ padding: 3 }}>
            My team
          </Typography>
        </ListSubheader>
      }
    >
      {error ? (
        <h1>Oh no, there was an error</h1>
      ) : isLoading ? (
        <CircularProgress />
      ) : data ? (
        <UsersList name={data.code} teamId={data.id} />
      ) : null}
    </List>
  );
}
