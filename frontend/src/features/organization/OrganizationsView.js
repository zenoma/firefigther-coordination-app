import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

import { Box, CircularProgress } from "@mui/material";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";

import FormControl from "@mui/material/FormControl";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormLabel from "@mui/material/FormLabel";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";

import { useGetOrganizationTypesQuery } from "../../api/organizationTypeApi";
import { selectToken } from "../user/login/LoginSlice";
import OrganizationDialog from "./OrganizationDialog";
import OrganizationTable from "./OrganizationTable";

export default function OrganizationsView() {
  const token = useSelector(selectToken);
  const [value, setValue] = useState("");

  const { data, error, isLoading } = useGetOrganizationTypesQuery(token);

  const handleRadioChange = (e) => {
    setValue(e.target.value);
  };

  const handleClick = () => {
    setValue("");
  };

  return (
    <Paper
      sx={{
        width: "100%",
        maxWidth: 1000,
        maxHeight: 1000,
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
        Organizaciones
      </Typography>
      {error ? (
        <h1>Oh no, ha ocurrido un error</h1>
      ) : isLoading ? (
        <CircularProgress />
      ) : data ? (
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
              Seleciona el tipo de organización a mostrar
            </Typography>
            <RadioGroup
              onChange={(e) => handleRadioChange(e)}
              row
              aria-labelledby="demo-row-radio-buttons-group-label"
              value={value}
              name="row-radio-buttons-group"
              sx={{ padding: "10px" }}
            >
              {data.map((item, index) => (
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

          {value && <OrganizationTable organizationTypeName={value} />}

          <Box onClick={handleClick}>
            <OrganizationDialog organizationTypes={data} />
          </Box>
        </FormControl>
      ) : null}
    </Paper>
  );
}
