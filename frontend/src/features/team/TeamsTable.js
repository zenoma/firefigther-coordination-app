import PropTypes from "prop-types";
import { useSelector } from "react-redux";

import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TablePagination from "@mui/material/TablePagination";
import TableRow from "@mui/material/TableRow";

import { Box, Button } from "@mui/material";
import { selectToken } from "../user/login/LoginSlice";

import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogTitle from "@mui/material/DialogTitle";
import { useState } from "react";
import { toast } from "react-toastify";

import DialogContent from "@mui/material/DialogContent";
import FormControl from "@mui/material/FormControl";
import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";
import React from "react";
import {
  useDeleteTeambyIdMutation,
  useUpdateTeamMutation,
} from "../../api/teamApi";
import TeamCard from "./TeamCard";

const columns = [
  { id: "code", label: "Código", minWidth: 150 },
  { id: "options", label: "Opciones", minWidth: 50 },
];

function createData(id, code) {
  return { id, code };
}

export default function TeamsTable(props) {
  const token = useSelector(selectToken);

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [openDelete, setOpenDelete] = useState(false);
  const [openEdit, setOpenEdit] = useState(false);

  const [teamId, setTeamId] = useState("");
  const [code, setCode] = useState("");
  const [data, setData] = useState("");

  const teams = props.teams;

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  const handleCloseEdit = () => {
    setOpenEdit(false);
  };

  const handleClickOpenEdit = (row) => {
    setTeamId(row.id);
    setCode(row.code);
    setOpenEdit(true);
  };

  const [updateTeam] = useUpdateTeamMutation();

  const handleEditClick = () => {
    const payload = {
      teamId: teamId,
      code: code,
      token: token,
    };
    updateTeam(payload)
      .unwrap()
      .then((payload) => {
        toast.success("Organización actualizada satisfactoriamente");
      })
      .catch((error) =>
        toast.error("No se ha podido actualizar la organización")
      );

    props.reloadData();
    handleCloseEdit();
  };

  const handleChange = (event) => {
    var id = event.target.id;
    var value = event.target.value;

    switch (id) {
      case "code":
        setCode(value);
        break;
      default:
        break;
    }
  };

  const [deleteTeambyId] = useDeleteTeambyIdMutation();

  const handleCloseDelete = () => {
    setOpenDelete(false);
  };
  const handleClickOpenDelete = (organizationId) => {
    setTeamId(organizationId);
    setOpenDelete(true);
  };

  const handleDeleteClick = () => {
    const payload = {
      token: token,
      teamId: teamId,
    };

    deleteTeambyId(payload)
      .unwrap()
      .then((payload) => {
        toast.success("Organización borrada satisfactoriamente");
      })
      .catch((error) =>
        toast.error("No se ha podido eliminar la organización")
      );
    handleCloseDelete();
    props.reloadData();
  };

  var rows = [];
  if (teams) {
    rows = [];
    teams.forEach((item, index) => {
      rows.push(createData(item.id, item.code));
    });
  }

  return (
    <Paper sx={{ overflow: "hidden" }}>
      <TableContainer sx={{ minWidth: 300, maxHeight: 500 }}>
        <Table stickyHeader aria-label="sticky table">
          <TableHead>
            <TableRow>
              {columns.map((column) => (
                <TableCell
                  key={column.id}
                  align={column.align}
                  style={{ minWidth: column.minWidth }}
                  sx={{
                    color: "secondary.light",
                    fontWeight: "bold",
                  }}
                >
                  {column.label}
                </TableCell>
              ))}
            </TableRow>
          </TableHead>
          <TableBody>
            {rows
              .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
              .map((row) => {
                return (
                  <TableRow hover role="checkbox" tabIndex={-1} key={row.code}>
                    {columns.map((column) => {
                      const value = row[column.id];
                      return (
                        <TableCell key={column.id} align={column.align}>
                          {column.format && typeof value === "number"
                            ? column.format(value)
                            : value}
                          {column.id === "options" ? (
                            <Box>
                              <Button
                                color="primary"
                                aria-label="add"
                                size="small"
                                onClick={(e) => handleClickOpenEdit(row)}
                              >
                                <EditIcon />
                              </Button>
                              <Button
                                color="primary"
                                aria-label="add"
                                size="small"
                                onClick={(e) => handleClickOpenDelete(row.id)}
                              >
                                <DeleteIcon />
                              </Button>
                            </Box>
                          ) : null}
                        </TableCell>
                      );
                    })}
                  </TableRow>
                );
              })}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        rowsPerPageOptions={[10, 25, 100]}
        component="div"
        count={rows.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
      <Dialog
        open={openDelete}
        onClose={handleCloseDelete}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          {"¿Está seguro de eliminar esta organización?"}
        </DialogTitle>
        <DialogActions>
          <Button onClick={handleCloseDelete}>Cancelar</Button>
          <Button onClick={handleDeleteClick} color="error" autoFocus>
            Aceptar
          </Button>
        </DialogActions>
      </Dialog>

      <Dialog maxWidth={"md"} open={openEdit}>
        <DialogTitle>Editar organización </DialogTitle>
        <DialogContent>
          <FormControl>
            <Grid container spacing={2}>
              <Grid item xs={12}>
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
            </Grid>
          </FormControl>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseEdit}>Cancelar</Button>
          <Button
            autoFocus
            variant="contained"
            onClick={(e) => handleEditClick(e)}
          >
            Editar
          </Button>
        </DialogActions>
      </Dialog>
    </Paper>
  );
}

TeamsTable.propTypes = {
  teams: PropTypes.array.isRequired,
};