import { useState } from "react";
import PropTypes from "prop-types";
import { useSelector } from "react-redux";

import ListSubheader from "@mui/material/ListSubheader";
import { CircularProgress } from "@mui/material";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import AnnouncementIcon from "@mui/icons-material/Announcement";

import { selectToken } from "../user/login/LoginSlice";
import { useGetTeamsByOrganizationIdQuery } from "../../api/teamApi";

import { useNavigate } from "react-router-dom";

export default function TeamsList(props) {
  const [list, setList] = useState("");
  const navigate = useNavigate();

  const token = useSelector(selectToken);

  const payload = {
    token: token,
    organizationId: props.organizationId,
  };

  const { data, error, isLoading } = useGetTeamsByOrganizationIdQuery(payload, { refetchOnMountOrArgChange: true });

  if (data === "") {
    setList(data);
  }
  const handleClick = (e, id) => {
    navigate("/detalles-equipo/" + id);
  };

  return (
    <Paper>
      <List
        sx={{
          width: "50%",
          overflow: "auto",
        }}
        component="nav"
        aria-labelledby="nested-list-subheader"
      >
        {error ? (
          <h1>Oh no, there was an error</h1>
        ) : isLoading ? (
          <CircularProgress />
        ) : data.length === 0 ? (
          <Paper sx={{ height: 100, margin: "auto", textAlign: "center" }}>
            <AnnouncementIcon color="warning"></AnnouncementIcon>
            <Typography variant="body1" display="block">
              Aún no hay equipos en esta organización
            </Typography>
          </Paper>
        ) : data ? (
          <Container>
            <Typography
              variant="h5"
              display="block"
              sx={{
                color: "primary.dark",
              }}
            >
              Equipos
            </Typography>
            {data.map((item, index) => (
              <Container key={item.code}>
                <ListItem sx={{ border: 1, borderRadius: "5px", margin: "5px" }}>
                  <ListItemButton onClick={(e) => handleClick(e, item.id)}>
                    <Typography variant="body1" component="body1" align="center">
                      {item.code}
                    </Typography>
                  </ListItemButton>
                </ListItem>
              </Container>
            ))}
          </Container>
        ) : null}
      </List>
    </Paper>
  );
}

TeamsList.propTypes = {
  organizationId: PropTypes.number.isRequired,
};
