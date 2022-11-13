import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";

import Text from "component/atom/Text";
import SideBar from "component/atom/SideBar";
import Todolist from "component/module/Todolist";
import MyChallengeCardList from "component/module/MyChallengeCardList";
import MyChallengeCarousel from "component/module/MyChallengeCarousel";

import { requestUserInfo } from "api/userApi";

export default function Challenge() {
  const [user, setUser] = useState([]);
  function requestUserInfoSuccess(res) {
    setUser(res.data);
  }

  function requestUserInfoFail(res) {
    setUser([]);
  }

  useEffect(() => {
    requestUserInfo(requestUserInfoSuccess, requestUserInfoFail);
  }, []);

  return (
    <Box sx={{ display: "flex", justifyContent: "space-between" }}>
      <Box sx={{ margin: "0 auto" }}>
        <Box sx={{ display: "flex" }}>
          <Text size="m" weight="bold" mt="30" my="15" color="blue">
            {user.nickName}
          </Text>
          <Text size="m" weight="semibold" mt="30" my="15">
            {"님의 챌린지 리스트"}
          </Text>
        </Box>
        <MyChallengeCardList />
      </Box>
      {/* <SideBar>
        <Box sx={{ margin: "40px 30px 50px 30px" }}>
          <MyChallengeCarousel />
        </Box>
        <Box sx={{ marginTop: "30px", marginLeft: "30px" }}>
          <Todolist />
        </Box>
      </SideBar> */}
      <Box sx={{width: "150px", height: "100vh", backgroundColor: "white"}}></Box>
      {/* <Box sx={{width: "150px", height: "100vh", backgroundColor: "#f8f8f8"}}></Box> */}
    </Box>
  );
}
