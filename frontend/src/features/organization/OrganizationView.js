import { useSelector } from "react-redux";

import { CircularProgress } from "@mui/material";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";

import FormControl from "@mui/material/FormControl";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormLabel from "@mui/material/FormLabel";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";

import { useState } from "react";
import { useTranslation } from "react-i18next";
import { useGetOrganizationsByOrganizationTypeQuery } from "../../api/organizationApi";
import { useGetOrganizationTypesQuery } from "../../api/organizationTypeApi";
import { selectToken } from "../user/login/LoginSlice";
import OrganizationCreateDialog from "./OrganizationCreateDialog";
import OrganizationTable from "./OrganizationTable";

export default function OrganizationsView() {
  const token = useSelector(selectToken);
  const [selectedOrganizationType, setSelectedOrganizationType] = useState(".");
  const { t } = useTranslation();

  const payload = {
    token: token,
    organizationTypeName: selectedOrganizationType,
  };

  const {
    data: organizationTypes,
    errorOrganizationTypesQuery,
    isLoadingOrganizationTypesQuery,
  } = useGetOrganizationTypesQuery(token);

  const {
    data: organizationsList,
    isFetching,
    refetch,
  } = useGetOrganizationsByOrganizationTypeQuery(payload, {
    refetchOnMountOrArgChange: true,
  });

  const handleRadioChange = (e) => {
    setSelectedOrganizationType(e.target.value);
    reloadData();
  };

  const reloadData = () => {
    refetch();
  };

  return (
    <Paper
      sx={{
        display: "inline-block",
        padding: "10px",
      }}
      elevation={5}
    >
      <Typography
        variant="h4"
        margin={1}
        sx={{ fontWeight: "bold", color: "primary.light" }}
      >
        {t("organizations")}
      </Typography>
      {errorOrganizationTypesQuery ? (
        <h1>{t("generic-error")}</h1>
      ) : isLoadingOrganizationTypesQuery ? (
        <CircularProgress />
      ) : organizationTypes ? (
        <FormControl>
          <FormLabel id="demo-row-radio-buttons-group-label">
            <Typography
              variant="h6"
              margin={1}
              sx={{
                color: "secondary.light",
                padding: "2px",
              }}
            >
              {t("select-organization-type")}
            </Typography>
            <RadioGroup
              onChange={(e) => handleRadioChange(e)}
              row
              aria-labelledby="demo-row-radio-buttons-group-label"
              value={selectedOrganizationType}
              name="row-radio-buttons-group"
              sx={{ padding: "10px" }}
            >
              {organizationTypes.map((item, index) => (
                <FormControlLabel
                  value={item.name}
                  key={index}
                  control={<Radio />}
                  label={item.name}
                  sx={{ margin: "10px", display: "flex" }}
                />
              ))}
            </RadioGroup>
          </FormLabel>

          {isFetching ? (
            <CircularProgress />
          ) : organizationsList ? (
            <OrganizationTable
              reloadData={reloadData}
              organizations={organizationsList}
              organizationTypesList={organizationTypes}
            />
          ) : null}

          <OrganizationCreateDialog
            organizationTypes={organizationTypes}
            reloadData={reloadData}
          />
        </FormControl>
      ) : null}
    </Paper>
  );
}
