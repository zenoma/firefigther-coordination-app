import { useState } from "react";
import { useSelector } from "react-redux";

import ListSubheader from "@mui/material/ListSubheader";
import { CircularProgress, ListItemButton, Typography, ListItemText, Divider } from "@mui/material";
import List from "@mui/material/List";

import { selectToken } from "../user/login/LoginSlice";

import { useGetMyNoticesQuery } from "../../api/noticeApi";

export default function MyTeamList() {
  const [list, setList] = useState("");

  const token = useSelector(selectToken);

  const payload = {
    token: token,
  };

  const { data, error, isLoading } = useGetMyNoticesQuery(payload);

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
            My notices
          </Typography>
        </ListSubheader>
      }
    >
      {error ? (
        <h1>Oh no, there was an error</h1>
      ) : isLoading ? (
        <CircularProgress />
      ) : data ? (
        data.map((item, index) => {
          return (
            <div key={item.id}>
              <ListItemButton>
                <ListItemText primary={item.body} secondary={"Status: " + item.status} />
              </ListItemButton>
              <Divider component="li" />
            </div>
          );
        })
      ) : null}
    </List>
  );
}
