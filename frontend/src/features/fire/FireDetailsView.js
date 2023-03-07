import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Fab,
  Grid,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from "@mui/material";

import AddIcon from "@mui/icons-material/Add";
import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import { useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { useGetFireByIdQuery } from "../../api/fireApi";
import CustomMap from "../map/CustomMap";
import { selectToken } from "../user/login/LoginSlice";
import BackButton from "../utils/BackButton";
import QuadrantDataGrid from "../quadrant/QuadrantDataGrid";
import { useLinkFireMutation } from "../../api/quadrantApi";
import { toast } from "react-toastify";

export default function FireDetailsView() {
  const token = useSelector(selectToken);
  const { fireId } = useParams();
  const { t } = useTranslation();
  const navigate = useNavigate();

  const [open, setOpen] = useState(false);

  const [selectedId, setSelectedId] = useState(true);

  const payload = { token: token, fireId: fireId };

  const { data, refetch } = useGetFireByIdQuery(payload);

  const [linkFire] = useLinkFireMutation();

  const childToParent = (childdata) => {
    setSelectedId(childdata);
  };

  const handleOpenClick = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleClick = () => {
    const payload = {
      token: token,
      fireId: fireId,
      quadrantId: selectedId,
    };

    linkFire(payload)
      .unwrap()
      .then(() => {
        toast.success(t("quadrant-linked-successfully"));
        refetch();
        handleClose();
      })
      .catch((error) => toast.error(t("quadrant-linked-error")));
  };

  return (
    <Box sx={{ padding: 3 }}>
      <BackButton />
      <Typography
        variant="h4"
        margin={1}
        sx={{ fontWeight: "bold", color: "primary.light" }}
      >
        {t("fire-details-title")}
      </Typography>
      <Grid
        container
        spacing={{ xs: 2, md: 3 }}
        columns={{ xs: 4, sm: 8, md: 12 }}
      >
        <Grid item xs={4} sm={8} md={9}>
          <Paper
            sx={{
              color: "primary.light",
              padding: 2,
            }}
            variant="outlined"
          >
            <Typography variant="h6">{t("quadrant-map")}</Typography>
            <CustomMap />
          </Paper>
        </Grid>
        <Grid item xs={4} sm={8} md={3}>
          {data && (
            <Paper
              sx={{
                color: "primary.light",
                padding: 2,
              }}
              variant="outlined"
            >
              <Typography variant="h6">{t("quadrant-list")}</Typography>
              <TableContainer
                component={Paper}
                elevation={3}
                sx={{ maxHeight: 420 }}
              >
                <Table stickyHeader aria-label="sticky table">
                  <TableHead>
                    <TableRow>
                      <TableCell sx={{ color: "secondary.light" }}>
                        {t("quadrant-id")}
                      </TableCell>
                      <TableCell
                        sx={{ color: "secondary.light" }}
                        align="right"
                      >
                        {t("quadrant-name")}
                      </TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {data.quadrants.map((row) => (
                      <TableRow
                        key={row.id}
                        hover
                        sx={{
                          "&:last-child td, &:last-child th": { border: 0 },
                        }}
                        onClick={() => navigate("/quadrant/" + row.id)}
                      >
                        <TableCell component="th" scope="row">
                          {row.id}
                        </TableCell>
                        <TableCell align="right">{row.nombre}</TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
              <Box m={1}>
                <Fab
                  color="primary"
                  aria-label="add"
                  onClick={() => handleOpenClick()}
                >
                  <AddIcon />
                </Fab>
              </Box>
            </Paper>
          )}
        </Grid>
      </Grid>

      <Dialog open={open} fullWidth maxWidth="md">
        <DialogTitle>{t("fire-create-title")} </DialogTitle>
        <DialogContent>
          <QuadrantDataGrid childToParent={childToParent} />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>{t("cancel")}</Button>
          <Button autoFocus variant="contained" onClick={() => handleClick()}>
            {t("create")}
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}
