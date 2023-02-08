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
import { useDeleteOrganizationByIdMutation } from "../../api/organizationApi";
import { selectToken } from "../user/login/LoginSlice";

import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import { useState } from "react";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import { toast } from "react-toastify";

const columns = [
  { id: "code", label: "Código", minWidth: 50 },
  { id: "name", label: "Nombre", minWidth: 250 },
  { id: "address", label: "Dirección", minWidth: 250 },
  { id: "options", label: "Opciones", minWidth: 50 },
];

function createData(id, code, name, address) {
  return { id, code, name, address };
}

export default function OrganizationTable(props) {
  const token = useSelector(selectToken);

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [open, setOpen] = useState(false);
  const [organizationId, setOrganizationId] = useState("");

  const organizations = props.organizations;

  const [deleteOrganizationById] = useDeleteOrganizationByIdMutation();

  const handleClickOpen = (organizationId) => {
    setOrganizationId(organizationId);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  const handleDeleteClick = () => {
    const payload = {
      token: token,
      organizationId: organizationId,
    };

    deleteOrganizationById(payload)
      .unwrap()
      .then((payload) => {
        toast.success("Organización borrada satisfactoriamente");
      });

    handleClose();
    props.realoadData();
  };

  const handleEditClick = (event) => {};

  var rows = [];
  if (organizations) {
    rows = [];
    organizations.forEach((item, index) => {
      rows.push(
        createData(item.id, item.code, item.name, item.headquartersAddress)
      );
    });
  }

  return (
    <Paper sx={{ overflow: "hidden" }}>
      <TableContainer sx={{ minWidth: 600, maxHeight: 500 }}>
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
                                onClick={(e) => handleEditClick(row.id)}
                              >
                                <EditIcon />
                              </Button>
                              <Button
                                color="primary"
                                aria-label="add"
                                size="small"
                                onClick={(e) => handleClickOpen(row.id)}
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
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          {"¿Está seguro de eliminar esta organización?"}
        </DialogTitle>
        <DialogActions>
          <Button onClick={handleClose}>Cancelar</Button>
          <Button onClick={handleDeleteClick} color="error" autoFocus>
            Aceptar
          </Button>
        </DialogActions>
      </Dialog>
    </Paper>
  );
}

OrganizationTable.propTypes = {
  organizations: PropTypes.array.isRequired,
};
