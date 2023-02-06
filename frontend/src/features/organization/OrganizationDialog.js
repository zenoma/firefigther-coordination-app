import { Box, Fab, MenuItem, Select } from "@mui/material";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import FormControl from "@mui/material/FormControl";
import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";
import PropTypes from "prop-types";
import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";

import { toast } from "react-toastify";
import { useCreateOrganizationMutation } from "../../api/organizationApi";
import CoordinatesMap from "../map/CoordinatesMap";
import { selectToken, selectUser } from "../user/login/LoginSlice";

import AddIcon from "@mui/icons-material/Add";

export default function OrganizationDialog(props) {
  const [code, setCode] = useState("");
  const [name, setName] = useState("");
  const [headquartersAddress, setHeadquartersAddress] = useState("");
  const [coordinates, setCoordinates] = useState("");
  const [open, setOpen] = useState(false);
  const [data, setData] = useState("");
  const [organizationTypeId, setOrganizationTypeId] = useState(
    "Elije un tipo de organización"
  );

  const childToParent = (childdata) => {
    setData({ lat: childdata[0], lng: childdata[1] });
  };

  const user = useSelector(selectUser);
  const token = useSelector(selectToken);
  const organizationTypesList = props.organizationTypes;

  const [createOrganization, error] = useCreateOrganizationMutation();

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleChange = (event) => {
    var id = event.target.id;
    var value = event.target.value;

    switch (id) {
      case "code":
        setCode(value);
        break;
      case "name":
        setName(value);
        break;
      case "headquartersAddress":
        setHeadquartersAddress(value);
        break;
      default:
        setOrganizationTypeId(value);
        break;
    }
  };

  const handleClick = async (e) => {
    const payload = {
      code: code,
      name: name,
      headquartersAddress: headquartersAddress,
      coordinates: data,
      id: user.id,
      token: token,
      organizationTypeId: organizationTypeId,
    };

    if (data == "") {
      toast.error(
        "Por favor indique la dirección de la organización en el mapa."
      );
    } else {
      createOrganization(payload).unwrap();

      handleClose();
    }
  };

  
  useEffect(() => {}, [open]);

  return (
    <div>
      <Box m={1}>
        <Fab color="primary" aria-label="add">
          <AddIcon onClick={handleClickOpen} />
        </Fab>
      </Box>
      <Dialog
        fullWidth={true}
        maxWidth={"md"}
        open={open}
        onClose={handleClose}
      >
        <DialogTitle>Crear nueva organización </DialogTitle>
        <DialogContent>
          <FormControl>
            <Grid container spacing={2}>
              <Grid item xs={6}>
                <TextField
                  id="code"
                  label="Código"
                  type="text"
                  autoComplete="current-code"
                  margin="normal"
                  value={code}
                  onChange={(e) => handleChange(e)}
                  helperText=" "
                  required
                  sx={{ display: "flex" }}
                />
              </Grid>
              <Grid item xs={6}>
                <TextField
                  id="name"
                  label="Nombre"
                  type="text"
                  autoComplete="current-name"
                  margin="normal"
                  value={name}
                  onChange={(e) => handleChange(e)}
                  helperText=" "
                  required
                  sx={{ display: "flex" }}
                />
              </Grid>
              <Grid item xs={6}>
                <Select
                  id="organizationTypes"
                  labelId="organizationTypes"
                  defaultValue="asdasd"
                  placeholder="Selecciona un tipo de organización"
                  value={organizationTypeId}
                  sx={{ display: "flex" }}
                  onChange={(e) => handleChange(e)}
                  required
                >
                  <MenuItem value="Elije un tipo de organización" disabled>
                    <em>Elije un tipo de organización</em>
                  </MenuItem>
                  {organizationTypesList.map((item, index) => (
                    <MenuItem key={index} value={item.id}>
                      {item.name}
                    </MenuItem>
                  ))}
                </Select>
              </Grid>

              <Grid item xs={12}>
                <TextField
                  id="headquartersAddress"
                  label="Dirección de la organización"
                  type="text"
                  autoComplete="current-headquartersAddress"
                  margin="normal"
                  value={headquartersAddress}
                  onChange={(e) => handleChange(e)}
                  helperText=" "
                  required
                  sx={{ display: "flex" }}
                />
              </Grid>
            </Grid>
            <CoordinatesMap childToParent={childToParent} />
          </FormControl>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancelar</Button>
          <Button onClick={(e) => handleClick(e)}>Crear</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}

OrganizationDialog.propTypes = {
  organizationTypes: PropTypes.array.isRequired,
};
