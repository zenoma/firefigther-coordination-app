import { Box, Typography } from "@mui/material";
import {
  DataGrid,
  esES,
  GridToolbarColumnsButton,
  GridToolbarContainer,
  GridToolbarDensitySelector,
  GridToolbarFilterButton,
} from "@mui/x-data-grid";
import * as React from "react";
import { useTranslation } from "react-i18next";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { useGetFiresQuery } from "../../api/fireApi";
import { selectToken } from "../user/login/LoginSlice";

export default function FireDataGrid() {
  const token = useSelector(selectToken);

  const { t } = useTranslation();
  const { i18n } = useTranslation("home");

  const navigate = useNavigate();

  var localeText;

  if (i18n.language === "es") {
    localeText = esES.components.MuiDataGrid.defaultProps.localeText;
  }

  const [pageSize, setPageSize] = React.useState(10);

  const {
    data: fires,
    error,
    isLoading,
  } = useGetFiresQuery(
    { token: token },
    {
      refetchOnMountOrArgChange: true,
    }
  );

  const data = {
    columns: [
      {
        field: "id",
        headerName: t("fire-id"),
        groupable: false,
        aggregable: false,
      },
      {
        field: "description",
        headerName: t("fire-description"),
        minWidth: 200,
        hide: true,
      },
      {
        field: "type",
        headerName: t("fire-type"),
        groupable: false,
        minWidth: 150,
        aggregable: false,
      },
      {
        field: "fireIndex",
        headerName: t("fire-index"),
        groupable: false,
        minWidth: 100,
        aggregable: false,
      },

      {
        field: "createdAt",
        headerName: t("fire-created-at"),
        minWidth: 200,
        aggregable: false,
      },
      {
        field: "extinguishedAt",
        headerName: t("fire-extinguished-at"),
        minWidth: 200,
        aggregable: false,
      },
    ],
    rows: fires,
    initialState: {
      columns: {
        columnVisibilityModel: {
          id: true,
          email: true,
          firstName: true,
          lastName: true,
          dni: true,
          phoneNumber: true,
          userRole: true,
          hasTeam: true,
        },
      },
    },
  };

  const handleRowClick = (id) => {
    navigate("/fire-management/" + id);
  };

  function CustomToolbar() {
    return (
      <GridToolbarContainer>
        <GridToolbarColumnsButton />
        <GridToolbarFilterButton />
        <GridToolbarDensitySelector />
      </GridToolbarContainer>
    );
  }

  return (
    <Box style={{ height: 600 }}>
      {error ? (
        <h1>{t("generic-error")}</h1>
      ) : isLoading ? (
        <div>Loading</div>
      ) : fires ? (
        <Box style={{ height: 250, width: "100%" }}>
          <Typography
            variant="h4"
            margin={1}
            sx={{ fontWeight: "bold", color: "primary.light" }}
          >
            {t("fire-list")}
          </Typography>
          <DataGrid
            {...data}
            components={{ Toolbar: CustomToolbar }}
            componentsProps={{
              pagination: {
                labelRowsPerPage: t("rows-per-page"),
              },
            }}
            pageSize={pageSize}
            onPageSizeChange={(newPageSize) => setPageSize(newPageSize)}
            rowsPerPageOptions={[10, 25, 50]}
            pagination
            localeText={localeText}
            onRowClick={(e) => handleRowClick(e.row.id)}
          />
        </Box>
      ) : null}
    </Box>
  );
}
