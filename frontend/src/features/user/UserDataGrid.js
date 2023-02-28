import { Alert, Box } from "@mui/material";
import {
  DataGrid,
  esES,
  GridToolbarColumnsButton,
  GridToolbarContainer,
  GridToolbarDensitySelector,
  GridToolbarFilterButton,
} from "@mui/x-data-grid";
import * as React from "react";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import { useSelector } from "react-redux";
import { useGetUsersQuery } from "../../api/userApi";
import { selectToken } from "./login/LoginSlice";

export default function UserDataGrid({ childToParent }) {
  const token = useSelector(selectToken);

  const { t } = useTranslation();
  const { i18n } = useTranslation("home");

  const [showWarning, setShowWarning] = useState(false);

  var localeText;

  if (i18n.language === "es") {
    localeText = esES.components.MuiDataGrid.defaultProps.localeText;
  }

  const [pageSize, setPageSize] = React.useState(10);

  const {
    data: users,
    error,
    isLoading,
  } = useGetUsersQuery(
    { token: token },
    {
      refetchOnMountOrArgChange: true,
    }
  );

  const initData = (users) => {
    if (users) {
      return users.map((user) => ({
        ...user,
        hasTeam: user.teamId !== undefined,
      }));
    }
  };

  const data = {
    columns: [
      {
        field: "dni",
        headerName: t("dni"),
        groupable: false,
        aggregable: false,
      },
      {
        field: "email",
        headerName: t("email"),
        width: 150,
        hide: true,
      },
      {
        field: "firstName",
        headerName: t("first-name"),
        groupable: false,
        width: 100,
        aggregable: false,
      },
      {
        field: "lastName",
        headerName: t("last-name"),
        groupable: false,
        width: 100,
        aggregable: false,
      },

      {
        field: "phoneNumber",
        headerName: t("phone-number"),
        width: 150,
        hide: true,
      },
      {
        field: "hasTeam",
        headerName: t("has-team?"),
        type: "boolean",
        width: 200,
      },
    ],
    rows: initData(users),
    initialState: {
      columns: {
        columnVisibilityModel: {
          id: false,
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

  function handleRowClick(e) {
    setShowWarning(e.row.hasTeam);
    return childToParent(e.row);
  }

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
    <Box style={{ height: 400 }}>
      {showWarning && (
        <Alert severity="warning">{t("user-selected-has-team-warning")}</Alert>
      )}
      {error ? (
        <h1>{t("generic-error")}</h1>
      ) : isLoading ? (
        <div>Loading</div>
      ) : users ? (
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
          onRowClick={(e) => handleRowClick(e)}
        />
      ) : null}
    </Box>
  );
}
