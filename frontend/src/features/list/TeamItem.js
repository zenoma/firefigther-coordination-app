import { useState } from "react";
import PropTypes from "prop-types";

import List from "@mui/material/List";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemText from "@mui/material/ListItemText";
import Collapse from "@mui/material/Collapse";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import Avatar from "@mui/material/Avatar";
import ExpandLess from "@mui/icons-material/ExpandLess";
import ExpandMore from "@mui/icons-material/ExpandMore";
import Paper from "@mui/material/Paper";

export default function TeamItem(props) {
  const [open, setOpen] = useState(false);
  const handleClick = () => {
    setOpen(!open);
  };

  return (
    <Paper key={props.name} sx={{ margin: 3 }} elevation={6}>
      <ListItemButton onClick={handleClick}>
        <ListItemText primary={props.name} />
        {open ? <ExpandLess /> : <ExpandMore />}
      </ListItemButton>
      <Collapse in={open} timeout="auto" unmountOnExit>
        <List component="div" disablePadding>
          <ListItemButton sx={{ pl: 4 }}>
            <ListItemAvatar>
              <Avatar />
            </ListItemAvatar>
            <ListItemText primary="John Doe" />
          </ListItemButton>
          <ListItemButton sx={{ pl: 4 }}>
            <ListItemAvatar>
              <Avatar />
            </ListItemAvatar>
            <ListItemText primary="John Doe" />
          </ListItemButton>
          <ListItemButton sx={{ pl: 4 }}>
            <ListItemAvatar>
              <Avatar />
            </ListItemAvatar>
            <ListItemText primary="John Doe" />
          </ListItemButton>
          <ListItemButton sx={{ pl: 4 }}>
            <ListItemAvatar>
              <Avatar />
            </ListItemAvatar>
            <ListItemText primary="John Doe" />
          </ListItemButton>
        </List>
      </Collapse>
    </Paper>
  );
}

TeamItem.propTypes = {
  name: PropTypes.string.isRequired,
};
