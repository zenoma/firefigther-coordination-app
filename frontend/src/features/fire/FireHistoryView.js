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

import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { MobileDateTimePicker } from "@mui/x-date-pickers/MobileDateTimePicker";

import dayjs from "dayjs";
import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useSelector } from "react-redux";
import { useLocation } from "react-router-dom";
import { useGetFireByIdQuery } from "../../api/fireApi";
import { useGetFireLogsByFireIdQuery } from "../../api/logApi";
import CustomMap from "../map/CustomMap";
import { selectToken } from "../user/login/LoginSlice";
import BackButton from "../utils/BackButton";
import { toast } from "react-toastify";

export default function FireHistoryView() {
  const token = useSelector(selectToken);
  const location = useLocation();
  const { t } = useTranslation();

  const [quadrants, setQuadrants] = useState([]);
  const [selectedStartDate, setSelectedStartDate] = useState(null);
  const [selectedEndDate, setSelectedEndDate] = useState(null);

  const fireId = location.state.fireId;

  const payload = {
    token: token,
    fireId: fireId,
    startDate: dayjs(selectedStartDate).format("YYYY-MM-DDTHH:mm:ss"),
    endDate: dayjs(selectedEndDate).format("YYYY-MM-DDTHH:mm:ss"),
  };

  const { data: fireData } = useGetFireByIdQuery({
    token: token,
    fireId: fireId,
  });
  const { data: fireLogs } = useGetFireLogsByFireIdQuery(payload);

  const handleStartDateChange = (date) => {
    const oldDate = selectedStartDate;
    if (date > oldDate) {
      toast.error(t("start-date-after-end-date"));
      setSelectedStartDate(oldDate);
    } else {
      setSelectedStartDate(date);
    }
  };

  const handleEndDateChange = (date) => {
    if (date < selectedStartDate) {
      alert("La fecha de fin no puede ser anterior a la fecha de inicio");
      return;
    }
    setSelectedEndDate(date);
  };

  useEffect(() => {
    if (fireData) {
      setSelectedStartDate(dayjs(fireData.createdAt, "DD-MM-YYYY HH:mm:ss"));
      setSelectedEndDate(dayjs(fireData.extinguishedAt, "DD-MM-YYYY HH:mm:ss"));
    }
  }, [fireData]);

  useEffect(() => {
    function getQuadrants() {
      const quadrants = [];

      for (let i = 0; i < fireLogs.length; i++) {
        const quadrant = fireLogs[i].quadrantInfoDto;
        quadrants.push(quadrant);
      }

      setQuadrants(quadrants);
    }
    if (fireLogs) {
      getQuadrants(fireLogs);
    }
  }, [fireLogs]);

  return (
    <Box sx={{ padding: 3 }}>
      <BackButton />
      <Typography
        variant="h4"
        margin={1}
        sx={{ fontWeight: "bold", color: "primary.light" }}
      >
        {t("fire-history-title")}
      </Typography>
      {fireData && (
        <Typography variant="body" margin={1}>
          {fireData.description} ({"#" + fireId})
        </Typography>
      )}
      <Grid
        container
        spacing={{ xs: 2, md: 3 }}
        columns={{ xs: 4, sm: 8, md: 12 }}
      >
        <Grid item xs={4} sm={8} md={6}>
          <Paper
            sx={{
              color: "primary.light",
              padding: 2,
            }}
            variant="outlined"
          >
            <Typography variant="h6">{t("quadrant-map")}</Typography>
            {fireLogs && <CustomMap quadrants={quadrants} />}
          </Paper>
        </Grid>
        <Grid item xs={2} sm={8} md={5}>
          <Paper
            sx={{
              color: "primary.light",
              padding: 2,
            }}
            variant="outlined"
          >
            <Typography variant="h6">{t("tittle-date-picker")}</Typography>
            {fireData && (
              <div>
                <Box
                  sx={{
                    color: "primary.light",
                    padding: 3,
                    display: "inline-block",
                  }}
                  variant="outlined"
                >
                  <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <MobileDateTimePicker
                      disableToolbar
                      ampm={false}
                      label={t("start-date-picker")}
                      value={selectedStartDate}
                      onChange={handleStartDateChange}
                    />
                  </LocalizationProvider>
                </Box>
                <Box
                  sx={{
                    color: "primary.light",
                    padding: 3,
                    display: "inline-block",
                  }}
                  variant="outlined"
                >
                  <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <MobileDateTimePicker
                      ampm={false}
                      label={t("end-date-picker")}
                      value={selectedEndDate}
                      onChange={handleEndDateChange}
                    />
                  </LocalizationProvider>
                </Box>
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
                      {quadrants.map((row) => (
                        <TableRow
                          key={row.id}
                          hover
                          sx={{
                            "&:last-child td, &:last-child th": { border: 0 },
                          }}
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
              </div>
            )}
          </Paper>
        </Grid>
        <Grid item xs={2} sm={8} md={6}>
          <Paper
            sx={{
              color: "primary.light",
              padding: 2,
            }}
            variant="outlined"
          ></Paper>
        </Grid>
      </Grid>
    </Box>
  );
}
