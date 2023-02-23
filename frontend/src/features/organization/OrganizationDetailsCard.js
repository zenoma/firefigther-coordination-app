import PropTypes from "prop-types";

import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";

export default function OrganizationDetailsCard(props) {
  return (
    <Container
      sx={{
        maxWidth: "500px",
      }}
    >
      <Typography
        variant="h4"
        margin="10px"
        sx={{
          fontWeight: "bold",
          color: "primary.light",
        }}
      >
        Detalles de organización
      </Typography>
      <Typography
        variant="h6"
        sx={{
          fontWeight: "bold",
          display: "inline-block",
          color: "secondary.light",
        }}
      >
        Código organización:
      </Typography>
      <Typography variant="body" margin={1} sx={{ display: "inline" }}>
        {props.data["code"]}
      </Typography>
      <Typography
        variant="h6"
        sx={{
          fontWeight: "bold",
          display: "inline-block",
          color: "secondary.light",
        }}
      >
        Nombre organización:
      </Typography>
      <Typography variant="body" margin={1} sx={{ display: "inline-block" }}>
        {props.data["name"]}
      </Typography>
      <Typography
        variant="h6"
        sx={{
          fontWeight: "bold",
          display: "inline-block",
          color: "secondary.light",
        }}
      >
        Dirección de la organización:
      </Typography>
      <Typography variant="body" margin={1} sx={{ display: "inline-block" }}>
        {props.data["headquartersAddress"]}
      </Typography>
      <Typography
        variant="h6"
        sx={{
          fontWeight: "bold",
          display: "inline-block",
          color: "secondary.light",
        }}
      >
        Tipo de organización:
      </Typography>
      <Typography variant="body" margin={1} sx={{ display: "inline-block" }}>
        {props.data["organizationType"]}
      </Typography>
    </Container>
  );
}

OrganizationDetailsCard.propTypes = {
  data: PropTypes.object,
};
