import "./App.css";

// import RouterConfiguration from "./configs/Router";

import Main from "component/page/Main";

import { Box } from "@mui/material";

function App() {
  return (
    <Box
      sx={{
        overflow: "hidden",
      }}
    >
      <Box
        sx={{
          float: "left",
        }}
      >
        {/* <Box
          sx={{
            width: "80px",
            height: "100vh",
            backgroundColor: "black",
          }}
        ></Box> */}
      </Box>
      <Box>
        <Main></Main>
        {/* <RouterConfiguration /> */}
      </Box>
    </Box>
  );
}

export default App;
