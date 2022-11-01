import React from "react";
import { Box } from "@mui/system";

const MainBoxStyle = {
  width: "60vw",
  minHeight: "80vh",
  backgroundColor: "#F5F5F5",
  borderRadius: "20px",
  textAlign: "center",
  display: "flex",
  flexDirection: "column",
  alignItems: "center",
};

export default function MainBox({ children }) {
  return <Box sx={MainBoxStyle}>{children}</Box>;
}
