import { useState } from "react";
import { useSelector } from "react-redux";

import ListSubheader from "@mui/material/ListSubheader";
import { CircularProgress } from "@mui/material";
import List from "@mui/material/List";

import { selectToken } from "../login/LoginSlice";
import { useGetOrganizationsQuery } from "../../api/organizationApi";

import TeamItem from "./TeamItem";

export default function NestedList() {
  const [list, setList] = useState("");

  const token = useSelector(selectToken);
  const { data, error, isLoading } = useGetOrganizationsQuery(token);

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
          Teams
        </ListSubheader>
      }
    >
      {error ? (
        <h1>Oh no, there was an error</h1>
      ) : isLoading ? (
        <CircularProgress />
      ) : data ? (
        data.map((item, index) => {
          return <TeamItem name={item.name} />;
        })
      ) : null}
    </List>
  );
}
