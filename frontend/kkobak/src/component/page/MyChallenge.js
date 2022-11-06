import React, { useEffect, useState } from "react";
import Text from "component/atom/Text";
import Box from "@mui/material/Box";
import MyChallengeCardList from "component/module/MyChallengeCardList";
import SideBar from "component/atom/SideBar";
import MyChallengeCarousel from "component/module/MyChallengeCarousel";
import { requestUserInfo } from "api/userApi";
import Todolist from "component/module/Todolist";

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
        <Box sx={{ flex: 1, display: "flex" }}>
          <Box sx={{ float: "left" }}>
            <Text size="l" weight="bold" mt="30" my="15" color="red">
              {user.nickName}
            </Text>
          </Box>
          <Box>
            <Text>&nbsp;</Text>
          </Box>
          <Box sx={{ marginTop: "6px" }}>
            <Text size="m" weight="semibold" mt="30" my="15">
              님의 챌린지 리스트
            </Text>
          </Box>
        </Box>
        <MyChallengeCardList />
      </Box>
      <SideBar>
        <Box sx={{ marginBottom: "-30px", marginLeft: "20px" }}>
          <Text size="m" weight="semibold" mt="30" my="15">
            챌린지 현황
          </Text>
        </Box>
        <Box sx={{ paddingY: "30px", marginLeft: "20px" }}>
          <MyChallengeCarousel />
        </Box>
        <Box sx={{ marginTop: "30px", marginLeft: "30px" }}>
          <Todolist />
        </Box>
      </SideBar>
    </Box>
  );
}
