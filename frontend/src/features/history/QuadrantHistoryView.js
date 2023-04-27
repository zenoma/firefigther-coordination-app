import { Box, Grid, Typography } from "@mui/material";
import dayjs from "dayjs";
import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useSelector } from "react-redux";
import { useLocation } from "react-router-dom";
import {
  useGetTeamLogsByQuadrantIdQuery,
  useGetVehicleLogsByQuadrantIdQuery,
} from "../../api/logApi";
import { selectToken } from "../user/login/LoginSlice";
import BackButton from "../utils/BackButton";
import QuadrantHistoryTeamsTable from "./QuadrantHistoryTeamsTable";
import QuadrantHistoryVehiclesTable from "./QuadrantHistoryVehiclesTable";

export default function QuadrantHistoryView() {
  const location = useLocation();
  const token = useSelector(selectToken);

  const [quadrantInfo, setQuadrantInfo] = useState([]);

  const quadrantId = location.state.quadrantId;
  const startDate = dayjs(location.state.startDate, "DD-MM-YYYY HH:mm:ss");
  const endDate = dayjs(location.state.endDate, "DD-MM-YYYY HH:mm:ss");

  const { t } = useTranslation();

  const payload = {
    token: token,
    quadrantId: quadrantId,
    startDate: dayjs(startDate).format("YYYY-MM-DDTHH:mm:ss"),
    endDate: dayjs(endDate).format("YYYY-MM-DDTHH:mm:ss"),
  };


  const { data: teamLogs } = useGetTeamLogsByQuadrantIdQuery(payload);
  const { data: vehicleLogs } = useGetVehicleLogsByQuadrantIdQuery(payload);

  useEffect(() => {
    if (teamLogs && teamLogs.length > 0) {
      setQuadrantInfo(teamLogs[0].quadrantInfoDto);
    }
  }, [teamLogs]);

  return (
    <Box sx={{ padding: 3 }}>
      <BackButton />
      <Grid
        container
        spacing={{ xs: 3, md: 3 }}
        columns={{ xs: 4, sm: 8, md: 12 }}
      >
        <Grid item xs={4} sm={8} md={12}>
          <Typography
            variant="h4"
            margin={1}
            sx={{ fontWeight: "bold", color: "primary.light" }}
          >
            {t("quadrant-history-details")}
          </Typography>
          {quadrantInfo && startDate && endDate && (
            <div>
              <Typography variant="h6" margin={1}>
                {quadrantInfo.nombre} ({"#" + quadrantId})
              </Typography>
              <Typography variant="body1" color="textSecondary">
                <span style={{ fontWeight: 600 }}>
                  {t("start-date-picker")}:{" "}
                </span>
                {dayjs(startDate).format("DD-MM-YYYY HH:mm")}
                <br />
                <span style={{ fontWeight: 600 }}>
                  {t("end-date-picker")}:{" "}
                </span>
                {dayjs(endDate).format("DD-MM-YYYY HH:mm")}
              </Typography>
            </div>
          )}
        </Grid>
        <Grid item xs={4} sm={8} md={6}>
          <Box>
            {teamLogs && <QuadrantHistoryTeamsTable teamLogs={teamLogs} />}
          </Box>
        </Grid>
        <Grid item xs={4} sm={8} md={6}>
          <Box>
            {vehicleLogs && (
              <QuadrantHistoryVehiclesTable vehicleLogs={vehicleLogs} />
            )}
          </Box>
        </Grid>
      </Grid>
    </Box>
  );
}
