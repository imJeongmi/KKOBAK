import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
// import MainCarousel from "component/module/MainCarousel";
import Todolist from "component/module/Todolist";
import MainCalendar from "component/atom/MainCalendar";
import Text from "component/atom/Text";
import SideBar from "component/atom/SideBar";
import MainBox from "component/atom/MainBox";
import { requestUserInfo } from "api/userApi";

export default function Main() {
  const [user, setUser] = useState([]);

  function requestUserInfoSuccess(res) {
    setUser(res.data);
    console.log(res.data);
    // console.log(user.nickName);
  }

  function requestUserInfoFail(res) {
    setUser([]);
  }

  useEffect(() => {
    requestUserInfo(requestUserInfoSuccess, requestUserInfoFail);
  }, []);

  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "space-between",
      }}
    >
      <Box sx={{ margin: "0 auto" }}>
        <Text size="l" weight="bold" mt="30" my="15">
          안녕하세요,
          {user.nickName}
        </Text>
        <MainBox>
          <MainCalendar />
        </MainBox>
      </Box>
      <SideBar>
        <Box sx={{ paddingY: "30px", marginLeft: "20px" }}>
          {/* <MainCarousel /> */}
        </Box>

        <Box sx={{ marginTop: "30px", marginLeft: "30px" }}>
          <Todolist />
        </Box>
      </SideBar>
    </Box>
  );
}
