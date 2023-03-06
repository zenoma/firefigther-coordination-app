import {
  Box,
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
import React from "react";
import { useTranslation } from "react-i18next";
import { useSelector } from "react-redux";
import { Navigate, useNavigate, useParams } from "react-router-dom";
import { useGetFireByIdQuery } from "../../api/fireApi";
import CustomMap from "../map/CustomMap";
import { selectToken } from "../user/login/LoginSlice";
import BackButton from "../utils/BackButton";

export default function FireDetailsView() {
  const token = useSelector(selectToken);
  const { fireId } = useParams();
  const { t } = useTranslation();
  const navigate = useNavigate();

  const payload = { token: token, fireId: fireId };

  const { data } = useGetFireByIdQuery(payload);

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
              <TableContainer component={Paper} sx={{ maxHeight: 500 }}>
                <Table aria-label="simple table">
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
            </Paper>
          )}
        </Grid>
      </Grid>
    </Box>
  );
}
