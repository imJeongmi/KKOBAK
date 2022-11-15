import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";

import Text from "component/atom/Text";
import MyChallengeCardList from "component/module/MyChallengeCardList";

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
      <Box sx={{width: "150px", height: "100vh", backgroundColor: "white"}} />
    </Box>
  );
}
