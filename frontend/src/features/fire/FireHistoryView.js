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
  Typography
} from "@mui/material";

import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { MobileDateTimePicker } from "@mui/x-date-pickers/MobileDateTimePicker";

import dayjs from "dayjs";
import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useSelector } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { useGetFireByIdQuery } from "../../api/fireApi";
import {
  useGetFireLogsByFireIdQuery,
  useGetGlobalStatisticsByFireIdQuery,
} from "../../api/logApi";
import CustomMap from "../map/CustomMap";
import { selectToken } from "../user/login/LoginSlice";
import BackButton from "../utils/BackButton";

export default function FireHistoryView() {
  const token = useSelector(selectToken);
  const location = useLocation();
  const navigate = useNavigate();
  const { t } = useTranslation();

  const [quadrants, setQuadrants] = useState([]);
  const [selectedStartDate, setSelectedStartDate] = useState(null);
  const [selectedEndDate, setSelectedEndDate] = useState(null);

  const fireId = location.state.fireId;

  const { data: fireData } = useGetFireByIdQuery({
    token: token,
    fireId: fireId,
  });

  const { data: globalStatistics } = useGetGlobalStatisticsByFireIdQuery({
    token: token,
    fireId: fireId,
  });

  const payload = {
    token: token,
    fireId: fireId,
    startDate: dayjs(selectedStartDate).format("YYYY-MM-DDTHH:mm:ss"),
    endDate: dayjs(selectedEndDate).format("YYYY-MM-DDTHH:mm:ss"),
  };

  const { data: fireLogs } = useGetFireLogsByFireIdQuery(payload, {
    refetchOnMountOrArgChange: true,
  });

  useEffect(() => {
    if (fireData) {
      setSelectedStartDate(dayjs(fireData.createdAt, "DD-MM-YYYY HH:mm:ss"));
      setSelectedEndDate(dayjs(fireData.extinguishedAt, "DD-MM-YYYY HH:mm:ss"));
    }
  }, [fireData]);

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
        <Typography variant="h6" margin={1}>
          {fireData.description} ({"#" + fireId})
        </Typography>
      )}
      <Grid
        container
        spacing={{ xs: 2, md: 3 }}
        columns={{ xs: 4, sm: 8, md: 12 }}
      >
        <Grid item xs={4} sm={8} md={4}>
          <Paper
            sx={{
              color: "primary.light",
              padding: 2,
              height: "530px",
            }}
            variant="outlined"
          >
            <Typography variant="h6" sx={{ padding: 2 }}>
              {t("quadrant-map")}
            </Typography>
            <Box sx={{ height: "90%", padding: 1 }}>
              {fireLogs && <CustomMap quadrants={quadrants} />}
            </Box>
          </Paper>
        </Grid>
        <Grid item xs={2} sm={8} md={4}>
          <Paper
            sx={{
              color: "primary.light",
              padding: 2,
              height: "530px",
            }}
            variant="outlined"
          >
            <Typography variant="h6">{t("tittle-date-picker")}</Typography>
            {fireData && (
              <div
              >
                <Box
                  sx={{
                    color: "primary.light",
                    padding: 2,
                    display: "flex",
                    transform: "scale(0.9)"
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
                      format="DD-MM-YYYY HH:mm"
                    />
                  </LocalizationProvider>
                  <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <MobileDateTimePicker
                      ampm={false}
                      label={t("end-date-picker")}
                      value={selectedEndDate}
                      onChange={handleEndDateChange}
                      format="DD-MM-YYYY HH:mm"
                    />
                  </LocalizationProvider>
                </Box>
                <Typography variant="h6">{t("quadrant-list")}</Typography>
                <TableContainer
                  component={Paper}
                  elevation={3}
                  sx={{
                    maxHeight: 350,
                  }}
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
                        <TableCell
                          sx={{ color: "secondary.light" }}
                          align="right"
                        >
                          {t("quadrant-linkedAt")}
                        </TableCell>
                        <TableCell
                          sx={{ color: "secondary.light" }}
                          align="right"
                        >
                          {t("quadrant-extinguishedAt")}
                        </TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {fireLogs &&
                        fireLogs.map((row) => (
                          <TableRow
                            key={row.quadrantInfoDto.id}
                            hover
                            onClick={() =>
                              navigate("/quadrant-history", {
                                state: {
                                  quadrantId: row.quadrantInfoDto.id,
                                  startDate: dayjs(selectedStartDate).format(
                                    "YYYY-MM-DDTHH:mm:ss"
                                  ),
                                  endDate: dayjs(selectedEndDate).format(
                                    "YYYY-MM-DDTHH:mm:ss"
                                  ),
                                },
                              })
                            }
                            sx={{
                              "&:last-child td, &:last-child th": {
                                border: 0,
                              },
                            }}
                          >
                            <TableCell component="th" scope="row">
                              {row.quadrantInfoDto.id}
                            </TableCell>
                            <TableCell align="right">
                              {row.quadrantInfoDto.nombre}
                            </TableCell>
                            <TableCell align="right">{row.linkedAt}</TableCell>
                            <TableCell align="right">
                              {row.extinguishedAt}
                            </TableCell>
                          </TableRow>
                        ))}
                    </TableBody>
                  </Table>
                </TableContainer>
              </div>
            )}
          </Paper>
        </Grid>
        <Grid item xs={2} sm={8} md={4}>
          {globalStatistics && (
            <Paper
              sx={{
                padding: 2,
                height: "530px",
              }}
              variant="outlined"
            >
              <div
                style={{
                  display: "flex",
                  flexDirection: "column",
                  justifyContent: "center",
                  alignItems: "left",
                  padding: "5px",
                }}
              >
                <Typography
                  variant="h6"
                  sx={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    color: "primary.light",
                  }}
                >
                  {t("global-statistics")}
                </Typography>
                <TableContainer>
                  <Table>
                    <TableBody>
                      <TableRow>
                        <TableCell
                          component="th"
                          scope="row"
                          sx={{ color: "secondary.light", padding: "20px" }}
                        >
                          {t("teams-mobilized")}
                        </TableCell>
                        <TableCell align="center">
                          {globalStatistics.teamsMobilized}
                        </TableCell>
                      </TableRow>
                      <TableRow>
                        <TableCell
                          component="th"
                          scope="row"
                          sx={{ color: "secondary.light", padding: "20px" }}
                        >
                          {t("vehicles-mobilized")}
                        </TableCell>
                        <TableCell align="center">
                          {globalStatistics.vehiclesMobilized}
                        </TableCell>
                      </TableRow>
                      <TableRow>
                        <TableCell
                          component="th"
                          scope="row"
                          sx={{ color: "secondary.light", padding: "20px" }}
                        >
                          {t("affected-quadrants")}
                        </TableCell>
                        <TableCell align="center">
                          {globalStatistics.affectedQuadrants}
                        </TableCell>
                      </TableRow>
                      <TableRow>
                        <TableCell
                          component="th"
                          scope="row"
                          sx={{ color: "secondary.light", padding: "20px" }}
                        >
                          {t("max-burned-hectares")}
                        </TableCell>
                        <TableCell align="center">
                          {globalStatistics.maxBurnedHectares} ha
                        </TableCell>
                      </TableRow>
                    </TableBody>
                  </Table>
                </TableContainer>
              </div>
            </Paper>
          )}
        </Grid>
      </Grid>
    </Box >
  );
}

// globalStatistics.affectedQuadrants
// globalStatistics.maxBurnedHectares
// globalStatistics.teamsMobilized
// globalStatistics.vehiclesMobilized
