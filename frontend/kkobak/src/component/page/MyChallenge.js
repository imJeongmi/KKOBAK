import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";

import Text from "component/atom/Text";
import SideBar from "component/atom/SideBar";
import Todolist from "component/module/Todolist";
import MyChallengeCardList from "component/module/MyChallengeCardList";
import MyChallengeCarousel from "component/module/MyChallengeCarousel";
import WidgetStatisticsIng from "component/module/WidgetStatisticsIng";
import WidgetStatisticsRlt from "component/module/WidgetStatisticsRlt";

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
    <Box
      sx={{
        display: "flex",
        justifyContent: "space-between",
      }}
    >
      <Box sx={{ margin: "0 auto" }}>
        <Box sx={{ flex: 1, display: "flex", justifyContent: "space-between" }}>
          <Box>
            <Box sx={{ float: "left" }}>
              <Text size="l" weight="bold" mt="30" my="15" color="red">
                {user.nickName}
              </Text>
            </Box>
            <Box sx={{ float: "left" }}>
              <Text>&nbsp;</Text>
            </Box>
            <Box sx={{ marginTop: "6px", float: "left" }}>
              <Text size="m" weight="semibold" mt="30" my="15">
                {"님의 챌린지 리스트"}
              </Text>
            </Box>
            <Box>
              <Text size="ss" weight="light" mt="5" my="5" color="red">
                {user.email}
              </Text>
            </Box>
          </Box>
          <Box sx={{ marginY: "20px" }}>
            <Box sx={{ float: "left" }}>
              <WidgetStatisticsIng />
            </Box>
            {/* <Box sx={{ float: "left" }}>
              <WidgetStatisticsRlt />
            </Box> */}
          </Box>
        </Box>

        <Box>
          <MyChallengeCardList />
        </Box>
      </Box>
      {/* <SideBar>
        <Box sx={{ margin: "40px 30px 50px 30px" }}>
          <MyChallengeCarousel />
        </Box>
        <Box sx={{ marginTop: "30px", marginLeft: "30px" }}>
          <Todolist />
        </Box>
      </SideBar> */}
    </Box>
  );
}
