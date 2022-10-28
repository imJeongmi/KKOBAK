import * as React from "react";
import TextField from "@mui/material/TextField";
// import Paper from "@mui/material/Paper";
// import InputBase from "@mui/material/InputBase";
// import Divider from "@mui/material/Divider";
import IconButton from "@mui/material/IconButton";
import SearchIcon from "@mui/icons-material/Search";
import KeyboardVoiceIcon from "@mui/icons-material/KeyboardVoice";
import Box from "@mui/material/Box";

export default function CustomizedInputBase() {
  return (
    <Box
      component="form"
      sx={{
        p: "2px 4px",
        display: "flex",
        alignItems: "center",
        width: 280,
        // border: 1,
        // borderRadius: 3,
      }}
    >
      <Box sx={{ p: "10px" }}>
        <SearchIcon style={{ color: "gray" }} />
      </Box>
      {/* <InputBase sx={{ ml: 1, flex: 1 }} /> */}
      <Box
        component="form"
        sx={{
          "& > :not(style)": { m: 1, width: "21ch" },
        }}
        noValidate
        autoComplete="off"
      >
        <TextField id="standard-basic" variant="standard" />
      </Box>
      {/* <IconButton type="button" sx={{ p: "10px" }}>
        <SearchIcon />
      </IconButton> */}
      {/* <Divider sx={{ height: 28, m: 0.5 }} orientation="vertical" /> */}
      <IconButton type="button" sx={{ p: "10px" }}>
        <KeyboardVoiceIcon style={{ color: "#7FB1CD" }} />
      </IconButton>
    </Box>
  );
}
