import React, { useEffect, useState } from "react";
import Text from "component/atom/Text";
import Box from "@mui/material/Box";
import MyChallengeCardList from "component/module/MyChallengeCardList";
import SideBar from "component/atom/SideBar";
import CategoryToggle from "component/atom/CategoryToggle";
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
        <Text size="l" weight="bold" mt="30" my="15">
          {user.nickName}님의 챌린지리스트
        </Text>
        <MyChallengeCardList />
      </Box>
      <SideBar>
        {/* paddingY: "30px" 임시로 아래 패딩Y 뺐음. 화면 확인 위해 */}
        <Box sx={{ marginLeft: "10px" }}>
          <Text size="l" weight="bold">
            {user.nickName}님의 챌린지 현황
          </Text>
        </Box>
        <Box sx={{ float: "right", marginRight: "40px" }}>
          <MyChallengeCarousel />
        </Box>

        <Box sx={{ marginTop: "180px", marginLeft: "10px" }}>
          <Text size="l" weight="bold">
            카테고리
          </Text>
        </Box>
        <CategoryToggle />
      </SideBar>
    </Box>
  );
}
