import PropTypes from "prop-types";
import { useState } from "react";
import { useSelector } from "react-redux";

import { selectToken } from "../user/login/LoginSlice";

import AnnouncementIcon from "@mui/icons-material/Announcement";
import Avatar from "@mui/material/Avatar";
import Box from "@mui/material/Box";
import List from "@mui/material/List";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import { useGetTeamsByIdQuery } from "../../api/teamApi";

export default function TeamItem(props) {
  const [open, setOpen] = useState(false);
  
  const handleClick = () => {
    setOpen(!open);
  };

  const token = useSelector(selectToken);

  const payload = {
    token: token,
    teamId: props.teamId,
  };

  const { data, error, isLoading } = useGetTeamsByIdQuery(payload, {
    refetchOnMountOrArgChange: true,
  });

  return (
    <Paper key={props.name} sx={{ margin: 3 }} elevation={6}>
      <Typography variant="h6" sx={{ padding: 3 }}>
        Lista de usuarios
      </Typography>
      <List component="div" disablePadding>
        {error ? (
          <h1>Oh no, there was an error</h1>
        ) : isLoading ? (
          <Typography variant="body1"> No data</Typography>
        ) : data.users.length === 0 ? (
          <Box sx={{ margin: "auto", textAlign: "center" }}>
            <AnnouncementIcon color="warning"></AnnouncementIcon>
            <Typography variant="body1" display="block">
              Todavía no hay usuarios en este equipo.
            </Typography>
          </Box>
        ) : data.users.length !== 0 ? (
          data.users.map((item, index) => {
            return (
              <div key={item.id}>
                <ListItemButton sx={{ pl: 4 }}>
                  <ListItemAvatar>
                    <Avatar
                      alt={item.firstName}
                      src="/static/images/avatar/2.jpg"
                    />
                  </ListItemAvatar>
                  <ListItemText
                    primary={item.firstName}
                    secondary={item.lastName}
                  />
                </ListItemButton>
              </div>
            );
          })
        ) : null}
      </List>
    </Paper>
  );
}

TeamItem.propTypes = {
  name: PropTypes.string.isRequired,
  teamId: PropTypes.number.isRequired,
};
