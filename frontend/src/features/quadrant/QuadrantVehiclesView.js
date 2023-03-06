import PropTypes from "prop-types";
import { useSelector } from "react-redux";

import { Box, CircularProgress } from "@mui/material";

import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";

import { useTranslation } from "react-i18next";
import { useGetQuadrantByIdQuery } from "../../api/quadrantApi";
import { selectToken } from "../user/login/LoginSlice";
import QuadrantVehicleTable from "./QuadrantVehicleTable";

export default function QuadrantVehiclesView(props) {
  const token = useSelector(selectToken);
  const { t } = useTranslation();

  const quadrantId = props.quadrantId;

  const payload = {
    token: token,
    quadrantId: quadrantId,
  };

  const {
    data: quadrantInfo,
    isFetching,
    refetch,
  } = useGetQuadrantByIdQuery(payload, {
    refetchOnMountOrArgChange: true,
  });

  const reloadData = () => {
    refetch();
  };

  return (
    <Box>
      <Paper
        sx={{
          padding: "10px",
        }}
      >
        <Typography
          variant="h6"
          margin={1}
          sx={{ fontWeight: "bold", color: "primary.light" }}
        >
          {t("quadrant-vehicles-deployed")}
        </Typography>
        {isFetching ? (
          <CircularProgress />
        ) : quadrantInfo ? (
          <QuadrantVehicleTable
            reloadData={reloadData}
            vehicles={quadrantInfo.vehicleDtoList}
            quadrantId={quadrantId}
          />
        ) : null}

        {/* <VehicleCreateDialog
          reloadData={reloadData}
          organizationId={quadrantId}
        /> */}
      </Paper>
    </Box>
  );
}

QuadrantVehiclesView.propTypes = {
  quadrantId: PropTypes.number.isRequired,
};
