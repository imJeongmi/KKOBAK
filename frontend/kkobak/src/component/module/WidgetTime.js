import React, { Component } from "react";
import Box from "@mui/material/Box";
import Text from "../atom/Text";

//시간위젯
export default function WidgetTime() {
  return (
    <Box
      sx={{
        width: "280px",
        height: "130px",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "white",
        borderRadius: 2,
      }}
    >
      <Box>
        <Text size="xl">12:45</Text>
        <Box>
          <Text>2022년 10월 19일 (수)</Text>
        </Box>
      </Box>
    </Box>
  );
}
