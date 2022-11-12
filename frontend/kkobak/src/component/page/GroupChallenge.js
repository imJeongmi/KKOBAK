import React from "react";
import Box from "@mui/material/Box";

import Text from "component/atom/Text";
import GroupChallengeCardList from "component/module/GroupChallengeCardList";

export default function Challenge() {
  return (
    <Box sx={{ display: "flex", justifyContent: "space-between" }}>
      <Box sx={{ margin: "0 auto" }}>
        <Box sx={{ display: "flex" }}>
          <Text size="m" weight="bold" mt="30" my="15" color="blue">
            단체 챌린지 리스트
          </Text>
        </Box>
        <GroupChallengeCardList />
      </Box>
      <Box
        sx={{ width: "150px", height: "100vh", backgroundColor: "white" }}
      ></Box>
    </Box>
  );
}
