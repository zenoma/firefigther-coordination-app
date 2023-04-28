import { Box, Paper, Typography } from '@mui/material'
import React from 'react'
import { useTranslation } from 'react-i18next'
import BackButton from '../../utils/BackButton'
import UserDataGrid from "../UserDataGrid"

export default function UserManagementView() {
    const { t } = useTranslation();


    return (
        <Box sx={{ padding: 3 }}>
            <BackButton />
            <Typography
                variant="h4"
                margin={1}
                sx={{ fontWeight: "bold", color: "primary.light" }}
            >
                {t("user-management")}
            </Typography>
            <Paper
                sx={{
                    color: "primary.light",
                    padding: 2,
                    minWidth: "900px",
                }}
                variant="outlined"
            >
                <UserDataGrid />
            </Paper>

        </Box>
    )
}
