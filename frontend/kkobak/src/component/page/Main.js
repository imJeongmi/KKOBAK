import React from "react";
import Box from "@mui/material/Box";
import MainCarousel from "component/module/MainCarousel";
import Todolist from "component/module/Todolist";
import MainCalendar from "component/atom/MainCalendar";
import Text from "component/atom/Text";
import SideBar from "component/atom/SideBar";
import MainBox from "component/atom/MainBox";

export default function Main() {
  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "space-between",
      }}
    >
      <Box sx={{ margin: "0 auto" }}>
        <Text size="l" weight="bold" mt="30" my="15">
          안녕하세요, 정미님
        </Text>
        <MainBox>
          <MainCalendar />
        </MainBox>
      </Box>
      <SideBar>
        <Box sx={{ paddingY: "30px", marginLeft: "20px" }}>
          <MainCarousel />
        </Box>

        <Box sx={{ marginTop: "30px", marginLeft: "30px" }}>
          <Todolist />
        </Box>
      </SideBar>
    </Box>
  );
}
