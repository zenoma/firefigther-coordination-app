import { useState } from "react";
import { useSelector } from "react-redux";

import ListSubheader from "@mui/material/ListSubheader";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import { CardActionArea, IconButton } from "@mui/material";
import Typography from "@mui/material/Typography";
import CardContent from "@mui/material/CardContent";
import { CircularProgress } from "@mui/material";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import List from "@mui/material/List";

import { selectToken } from "../user/login/LoginSlice";
import { useGetOrganizationsQuery } from "../../api/organizationApi";
import TeamsList from "./TeamsList";

export default function OrganizationsList() {
  const [list, setList] = useState("");
  const [organizationId, setOrganizationId] = useState("");
  const [showOrganizations, setShowOrganizations] = useState(true);
  const [showTeams, setShowTeams] = useState(false);

  const token = useSelector(selectToken);

  const { data, error, isLoading } = useGetOrganizationsQuery(token, { refetchOnMountOrArgChange: true });

  if (data === "") {
    setList(data);
  }

  const handleClick = (e, organizationId) => {
    setShowOrganizations(false);
    setShowTeams(true);
    setOrganizationId(organizationId);
  };
  const initView = (e) => {
    setShowOrganizations(true);
    setShowTeams(false);
    setOrganizationId("");
  };

  return (
    <Box
      sx={{
        width: "100%",
        maxWidth: 500,
        maxHeight: 500,
        overflow: "auto",
      }}
    >
      {showOrganizations && (
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
                Organizations
              </Typography>
            </ListSubheader>
          }
        >
          {error ? (
            <h1>Oh no, there was an error</h1>
          ) : isLoading ? (
            <CircularProgress />
          ) : data ? (
            <Grid container spacing={{ xs: 2, md: 3 }} columns={{ xs: 8, sm: 8, md: 12 }}>
              {data.map((item, index) => (
                <Grid key={item.name} item xs={4}>
                  <CardActionArea onClick={(e) => handleClick(e, item.id)}>
                    <Card
                      sx={{
                        width: 150,
                        height: 150,
                        textAlign: "center",
                        bgcolor: "secondary.light",
                      }}
                    >
                      <CardContent>
                        <Typography variant="h6" component="h6" align="center">
                          {item.name}
                        </Typography>
                      </CardContent>
                    </Card>
                  </CardActionArea>
                </Grid>
              ))}
            </Grid>
          ) : null}
        </List>
      )}
      {showTeams && <TeamsList organizationId={organizationId} />}
      {showTeams && (
        <IconButton>
          <ArrowBackIcon onClick={(e) => initView(e)} />
        </IconButton>
      )}
    </Box>
  );
}
