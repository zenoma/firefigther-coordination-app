import { useState } from "react";
import PropTypes from "prop-types";
import { useSelector } from "react-redux";

import ListSubheader from "@mui/material/ListSubheader";
import { CircularProgress } from "@mui/material";
import List from "@mui/material/List";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import AnnouncementIcon from "@mui/icons-material/Announcement";

import { selectToken } from "../login/LoginSlice";
import { useGetTeamsByOrganizationIdQuery } from "../../api/teamApi";

import UsersList from "./UsersList";

export default function TeamsList(props) {
  const [list, setList] = useState("");

  const token = useSelector(selectToken);

  const payload = {
    token: token,
    organizationId: props.organizationId,
  };

  const { data, error, isLoading } = useGetTeamsByOrganizationIdQuery(payload);

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
            Teams
          </Typography>
        </ListSubheader>
      }
    >
      {error ? (
        <h1>Oh no, there was an error</h1>
      ) : isLoading ? (
        <CircularProgress />
      ) : data.length === 0 ? (
        <Paper sx={{ width: 100, height: 100, margin: "auto", textAlign: "center" }} elevation={6}>
          <AnnouncementIcon color="warning"></AnnouncementIcon>
          <Typography variant="body1" display="block">
            No teams
          </Typography>
        </Paper>
      ) : data ? (
        data.map((item, index) => {
          return <UsersList name={item.code} teamId={item.id} key={item.code} />;
        })
      ) : null}
    </List>
  );
}

TeamsList.propTypes = {
  organizationId: PropTypes.number.isRequired,
};
