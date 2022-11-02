import React, { useEffect } from "react";
import Box from "@mui/material/Box";
import MainCarousel from "component/module/MainCarousel";
import Todolist from "component/module/Todolist";
import MainCalendar from "component/atom/MainCalendar";
import Text from "component/atom/Text";
import SideBar from "component/atom/SideBar";

export default function Main() {
  return (
    <Box sx={{ display: "flex", flexDirection: "row" }}>
      <Box sx={{ marginLeft: "40px", flex: 8 }}>
        <Box sx={{ paddingTop: "30px", marginLeft: "10px" }}>
          <Text size="l" weight="bold">
            안녕하세요, 정미님
          </Text>
        </Box>
        <Box>
          <MainCalendar />
        </Box>
      </Box>
      <Box sx={{ float: "right", flex: 1 }}>
        <SideBar>
          <Box sx={{ paddingY: "30px", marginLeft: "20px" }}>
            <MainCarousel />
          </Box>

          <Box sx={{ marginTop: "30px", marginLeft: "30px" }}>
            <Todolist />
          </Box>
        </SideBar>
      </Box>
    </Box>
  );
}
