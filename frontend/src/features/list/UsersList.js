import { useState } from "react";
import { useSelector } from "react-redux";
import PropTypes from "prop-types";

import { selectToken } from "../user/login/LoginSlice";

import List from "@mui/material/List";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";
import Collapse from "@mui/material/Collapse";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import Avatar from "@mui/material/Avatar";
import ExpandLess from "@mui/icons-material/ExpandLess";
import ExpandMore from "@mui/icons-material/ExpandMore";
import AnnouncementIcon from "@mui/icons-material/Announcement";
import Paper from "@mui/material/Paper";
import Box from "@mui/material/Box";
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

  const { data, error, isLoading } = useGetTeamsByIdQuery(payload, { refetchOnMountOrArgChange: true });

  return (
    <Paper key={props.name} sx={{ margin: 3 }} elevation={6}>
      <ListItemButton onClick={handleClick}>
        <ListItemText primary={props.name} />
        {open ? <ExpandLess /> : <ExpandMore />}
      </ListItemButton>
      <Collapse in={open} timeout="auto" unmountOnExit>
        <List component="div" disablePadding>
          {error ? (
            <h1>Oh no, there was an error</h1>
          ) : isLoading ? (
            <Typography variant="body1"> No data</Typography>
          ) : data.users.length === 0 ? (
            <Box sx={{ margin: "auto", textAlign: "center" }}>
              <AnnouncementIcon color="warning"></AnnouncementIcon>
              <Typography variant="body1" display="block">
                No users
              </Typography>
            </Box>
          ) : data.users.length !== 0 ? (
            data.users.map((item, index) => {
              return (
                <div key={item.id}>
                  <ListItemButton sx={{ pl: 4 }}>
                    <ListItemAvatar>
                      <Avatar alt={item.firstName} src="/static/images/avatar/2.jpg" />
                    </ListItemAvatar>
                    <ListItemText primary={item.firstName} secondary={item.lastName} />
                  </ListItemButton>
                </div>
              );
            })
          ) : null}
        </List>
      </Collapse>
    </Paper>
  );
}

TeamItem.propTypes = {
  name: PropTypes.string.isRequired,
  teamId: PropTypes.number.isRequired,
};
