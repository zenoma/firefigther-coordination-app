import PropTypes from "prop-types";

import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import { useTranslation } from "react-i18next";

export default function OrganizationDetailsCard(props) {
  const { t } = useTranslation();

  return (
    <Container
      sx={{
        padding: "10px",
        textAlign:"center"
      }}
    >
      <Typography
        variant="h4"
        margin="10px"
        sx={{
          color: "primary.light",
          textAlign: "center",
        }}
      >
        {t("organization-details-title")}
      </Typography>
      <Typography
        variant="h6"
        sx={{
          color: "secondary.light",
        }}
      >
        {t("organization-details-code")}
      </Typography>
      <Typography variant="body" margin={1} sx={{ display: "block" }}>
        {props.data["code"]}
      </Typography>
      <Typography
        variant="h6"
        sx={{
          color: "secondary.light",
        }}
      >
        {t("organization-details-name")}
      </Typography>
      <Typography variant="body" margin={1} sx={{ display: "block" }}>
        {props.data["name"]}
      </Typography>
      <Typography
        variant="h6"
        sx={{
          color: "secondary.light",
        }}
      >
        {t("organization-address")}
      </Typography>
      <Typography variant="body" margin={1} sx={{ display: "block" }}>
        {props.data["headquartersAddress"]}
      </Typography>
      <Typography
        variant="h6"
        sx={{
          color: "secondary.light",
        }}
      >
        {t("organization-type-name")}
      </Typography>
      <Typography variant="body" margin={1} sx={{ display: "block" }}>
        {props.data["organizationType"]}
      </Typography>
    </Container>
  );
}

OrganizationDetailsCard.propTypes = {
  data: PropTypes.object,
};
