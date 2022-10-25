import "./App.css";
import Nav from "./component/module/Navbar";

import RouterConfiguration from "./configs/Router";

import { Box } from "@mui/material";

function App() {
  return (
    <Box sx={{ display: "flex" }}>
      <Box>
        <Box
          sx={{ width: "80px", height: "100vh", backgroundColor: "black" }}
        ></Box>
        <Box>
          <Nav />
        </Box>
      </Box>
      <RouterConfiguration />
    </Box>
  );
}

export default App;
