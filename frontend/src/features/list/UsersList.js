import PropTypes from "prop-types";
import { useState } from "react";
import { useSelector } from "react-redux";

import { selectToken } from "../user/login/LoginSlice";

import AnnouncementIcon from "@mui/icons-material/Announcement";
import {
  Button,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import Box from "@mui/material/Box";
import List from "@mui/material/List";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import { useTranslation } from "react-i18next";
import { useGetTeamsByIdQuery } from "../../api/teamApi";

import TablePagination from "@mui/material/TablePagination";

import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";

import React from "react";

const columns = [
  { id: "dni", label: "dni", minWidth: 50 },
  { id: "firstName", label: "firstName", minWidth: 100 },
  { id: "lastName", label: "lastName", minWidth: 100 },
  { id: "email", label: "email", minWidth: 100 },
  { id: "phoneNumber", label: "phoneNumber", minWidth: 150 },
  { id: "options", label: "options", minWidth: 50 },
];

function createData(id, dni, email, firstName, lastName, phoneNumber) {
  return { id, dni, email, firstName, lastName, phoneNumber };
}

export default function TeamItem(props) {
  const [open, setOpen] = useState(false);

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);

  const { t } = useTranslation();

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

  var rows = [];
  if (data) {
    rows = [];
    data.users.forEach((item, index) => {
      rows.push(
        createData(
          item.id,
          item.dni,
          item.email,
          item.firstName,
          item.lastName,
          item.phoneNumber
        )
      );
    });
  }

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

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
          <Box>
            <TableContainer sx={{ maxHeight: 400 }}>
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
                        {t(column.label)}
                      </TableCell>
                    ))}
                  </TableRow>
                </TableHead>
                <TableBody>
                  {rows
                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((row) => {
                      return (
                        <TableRow
                          hover
                          role="checkbox"
                          tabIndex={-1}
                          key={row.code}
                        >
                          {columns.map((column) => {
                            const value = row[column.id];
                            return (
                              <TableCell
                                sx={{
                                  "&:hover": {
                                    cursor: "pointer",
                                  },
                                }}
                                key={column.id}
                                align={column.align}
                              >
                                {column.format && typeof value === "number"
                                  ? column.format(value)
                                  : value}
                                {column.id === "options" ? (
                                  <Button
                                    color="primary"
                                    aria-label="add"
                                    size="small"
                                    // onClick={(e) =>
                                    //   handleClickOpenDelete(row.id)
                                    // }
                                  >
                                    <DeleteIcon />
                                  </Button>
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
              labelRowsPerPage={t("rows-per-page")}
            />
          </Box>
        ) : null}
      </List>
    </Paper>
  );
}

TeamItem.propTypes = {
  name: PropTypes.string.isRequired,
  teamId: PropTypes.number.isRequired,
};
