import "./App.css";
import Nav from "./component/module/Navbar";

import RouterConfiguration from "./configs/Router";

import { Box } from "@mui/material";
import WatchToggle from "./component/atom/WatchToggle";

function App() {
  return (
    // <Box
    //   sx={{
    //     overflow: "hidden",
    //   }}
    // >
    //   <Box
    //     sx={{
    //       float: "left",
    //     }}
    //   >
    //     <Box
    //       sx={{
    //         width: "80px",
    //         height: "100vh",
    //         backgroundColor: "black",
    //       }}
    //     ></Box>
    //     <Nav />
    //   </Box>
    <Box>
      <Box>
        {/* <RouterConfiguration /> */}
        <WatchToggle />
      </Box>
    </Box>
  );
}

export default App;
