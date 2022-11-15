import "./App.css";

import RouterConfiguration from "./configs/Router";

import { Box } from "@mui/material";

function App() {
  return (
    <Box
      sx={{
        overflow: "hidden",
      }}
    >
      <Box>
        <RouterConfiguration />
      </Box>
    </Box>
  );
}

export default App;
