import { useState } from "react";
import { useSelector } from "react-redux";

import { CircularProgress, Divider, ListItemText, Paper, Typography } from "@mui/material";
import List from "@mui/material/List";

import { selectToken } from "../user/login/LoginSlice";

import { useTranslation } from "react-i18next";
import { useGetMyNoticesQuery } from "../../api/noticeApi";

export default function MyTeamList() {
  const [list, setList] = useState("");
  const { t } = useTranslation();

  const token = useSelector(selectToken);

  const payload = {
    token: token,
  };

  const { data, error, isLoading } = useGetMyNoticesQuery(payload, { refetchOnMountOrArgChange: true });

  if ((data === "") & (list === "")) {
    setList(data);
  }

  return (
    <Paper
      sx={{
        display: "inline-block",
        padding: "10px",
        minWidth: "1000px",
      }}
      variant="outlined"
    >
      <Typography
        variant="h4"
        margin={1}
        sx={{ fontWeight: "bold", color: "primary.light" }}
      >
        {t("my-notices")}
      </Typography>
      <List
        sx={{
          display: "inline-block",
          minWidth: 500,
        }}
        component="nav"
        aria-labelledby="nested-list-subheader"
      >
        {error ? (
          t("generic-error")
        ) : isLoading ? (
          <CircularProgress />
        ) : data ? (
          data.map((item, index) => {
            return (
              <div key={item.id}>
                <ListItemText primary={item.body} secondary={"Status: " + item.status} />
                <Divider component="li" />
              </div>
            );
          })
        ) : null}
      </List>
    </Paper >
  );
}
