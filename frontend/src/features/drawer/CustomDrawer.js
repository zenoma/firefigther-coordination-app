import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import { styled, useTheme } from "@mui/material/styles";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import CssBaseline from "@mui/material/CssBaseline";
import MuiAppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import List from "@mui/material/List";
import Typography from "@mui/material/Typography";
import Divider from "@mui/material/Divider";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import ChevronRightIcon from "@mui/icons-material/ChevronRight";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import InboxIcon from "@mui/icons-material/MoveToInbox";
import MailIcon from "@mui/icons-material/Mail";
import HomeIcon from "@mui/icons-material/Home";
import GroupsIcon from "@mui/icons-material/Groups";
import GroupWorkIcon from "@mui/icons-material/GroupWork";
import ArticleIcon from "@mui/icons-material/Article";

import { Menu, Avatar, Tooltip, MenuItem } from "@mui/material";
import LoginIcon from "@mui/icons-material/Login";
import { logout, selectToken } from "../login/LoginSlice";
import { SwitchThemeButton } from "./SwitchThemeButton";

const drawerWidth = 240;
const settings = ["Profile", "Logout"];

const DrawerHeader = styled("div")(({ theme }) => ({
  display: "flex",
  alignItems: "center",
  padding: theme.spacing(0, 1),
  // necessary for content to be below app bar
  ...theme.mixins.toolbar,
  justifyContent: "flex-end",
}));

export default function PersistentDrawerLeft() {
  const theme = useTheme();
  const [open, setOpen] = useState(false);
  const [anchorElUser, setAnchorElUser] = useState(null);

  const token = useSelector(selectToken);

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const pages = [
    {
      name: "Organizations",
      icon: <GroupsIcon />,
    },
    {
      name: "My team",
      icon: <GroupWorkIcon />,
    },
    {
      name: "PAGE_3",
      icon: <ArticleIcon />,
    },
  ];

  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };
  const handleClickUserMenu = (e) => {
    var id = e.target.id;
    switch (id) {
      case "sign up":
        navigate("/sign-up");
        break;
      case "login":
        navigate("/login");
        break;
      case "profile":
        navigate("/profile");
        break;
      case "logout":
        dispatch(logout());
        navigate("/");
        break;
      default:
        break;
    }
  };

  const handleClickItemList = (e, text) => {
    switch (text.toLocaleLowerCase()) {
      case "my team":
        navigate("/my-team");
        break;
      case "organizations":
        navigate("/organizations");
        break;
      default:
        navigate("/");
        break;
    }
    handleDrawerClose();
  };

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  return (
    <Box sx={{ display: "flex", bgcolor: "secondary.light" }}>
      <CssBaseline />
      <MuiAppBar position="fixed" open={open} sx={{ bgcolor: "primary.main" }}>
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            edge="start"
            sx={{ mr: 2, ...(open && { display: "none" }) }}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap component="div" sx={{ flexGrow: 1 }}>
            Main title
          </Typography>
          {!token && (
            <Box sx={{ flexGrow: 0 }}>
              <Tooltip title="Open settings">
                <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                  <LoginIcon />
                </IconButton>
              </Tooltip>
              <Menu
                sx={{ mt: "45px" }}
                id="menu-appbar"
                anchorEl={anchorElUser}
                anchorOrigin={{
                  vertical: "top",
                  horizontal: "right",
                }}
                keepMounted
                transformOrigin={{
                  vertical: "top",
                  horizontal: "right",
                }}
                open={Boolean(anchorElUser)}
                onClose={handleCloseUserMenu}
              >
                <MenuItem key={"login"} onClick={handleCloseUserMenu}>
                  <Typography id={"login"} textAlign="center" onClick={(e) => handleClickUserMenu(e)}>
                    Login
                  </Typography>
                </MenuItem>
              </Menu>
            </Box>
          )}
          {token && (
            <Box sx={{ flexGrow: 0 }}>
              <Tooltip title="Open settings">
                <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                  <Avatar alt="Remy Sharp" src="/static/images/avatar/2.jpg" />
                </IconButton>
              </Tooltip>
              <Menu
                sx={{ mt: "45px" }}
                id="menu-appbar"
                anchorEl={anchorElUser}
                anchorOrigin={{
                  vertical: "top",
                  horizontal: "right",
                }}
                keepMounted
                transformOrigin={{
                  vertical: "top",
                  horizontal: "right",
                }}
                open={Boolean(anchorElUser)}
                onClose={handleCloseUserMenu}
              >
                {settings.map((setting) => (
                  <MenuItem key={setting} onClick={handleCloseUserMenu}>
                    <Typography
                      id={setting.toLocaleLowerCase()}
                      textAlign="center"
                      onClick={(e) => handleClickUserMenu(e)}
                    >
                      {setting}
                    </Typography>
                  </MenuItem>
                ))}
              </Menu>
            </Box>
          )}
        </Toolbar>
      </MuiAppBar>
      <Drawer
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          "& .MuiDrawer-paper": {
            width: drawerWidth,
            boxSizing: "border-box",
          },
        }}
        variant="persistent"
        anchor="left"
        open={open}
      >
        <DrawerHeader sx={{ display: "flex", bgcolor: "secondary.light" }}>
          <List sx={{ flexGrow: 10 }}>
            <ListItem key="Home" disablePadding onClick={(e) => handleClickItemList(e, "")}>
              <ListItemButton>
                <ListItemIcon>
                  <HomeIcon />
                </ListItemIcon>
                <ListItemText primary="Home" />
              </ListItemButton>
            </ListItem>
          </List>
          <IconButton sx={{ flexGrow: 10 }} onClick={handleDrawerClose}>
            {theme.direction === "ltr" ? <ChevronLeftIcon /> : <ChevronRightIcon />}
          </IconButton>
        </DrawerHeader>

        <Divider />
        <List>
          {pages.map((item, index) => (
            <ListItem key={item.name} disablePadding onClick={(e) => handleClickItemList(e, item.name)}>
              <ListItemButton>
                <ListItemIcon>{item.icon}</ListItemIcon>
                <ListItemText primary={item.name} />
              </ListItemButton>
            </ListItem>
          ))}
        </List>
        <Divider />
        <List>
          {["PAGE 4", "PAGE 5", "PAGE 6"].map((text, index) => (
            <ListItem key={text} disablePadding>
              <ListItemButton>
                <ListItemIcon>{index % 2 === 0 ? <InboxIcon /> : <MailIcon />}</ListItemIcon>
                <ListItemText primary={text} />
              </ListItemButton>
            </ListItem>
          ))}
        </List>
        <Divider />
        <SwitchThemeButton />
      </Drawer>
    </Box>
  );
}
