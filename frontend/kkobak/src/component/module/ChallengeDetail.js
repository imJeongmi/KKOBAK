import { Box } from "@mui/system";
import Text from "component/atom/Text";
import React from "react";

const BoxStyle = {
  height: "100vh",
  width: "70vw",
  display: "flex",
  // alignItems: "center"
  flexDirection: "column",
  justifyContent: "center",
};

const CardStyle = {
  width: "95%",
  margin: "auto",
  minHeight: "80vh",
  backgroundColor: "#F5F5F5",
  textAlign: "center",
  display: "flex",
  justifyContent: "center",
  borderRadius: "20px",
};

export default function ChallengeDetail() {
  return (
    <Box sx={BoxStyle}>
      <Box sx={CardStyle}>
        <img alt="img" width="40%" height="40%"></img>
        <Text></Text>
        <Text></Text>
        <br />
        
      </Box>
    </Box>
  );
}
