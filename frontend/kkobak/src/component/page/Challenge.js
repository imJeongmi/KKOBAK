import React from "react";
import Text from "component/atom/Text";
import Box from "@mui/material/Box";
import ChallengeCardList from "component/module/ChallengeCardList";
import SideBar from "component/atom/SideBar";

export default function Challenge() {
  return (
    <Box>
      <Box sx={{ marginLeft: "10%" }}>
        <Box sx={{ marginY: 3 }}>
          <Text size="l" weight="bold">
            챌린지리스트
          </Text>
        </Box>
        <ChallengeCardList />
      </Box>

      <Box sx={{ marginTop: "-130px", float: "right" }}>
        <SideBar>
          <Box>
            <Text size="l" weight="bold">
              검색
            </Text>
          </Box>
          <Box>검색 필터링 토글</Box>
          <Box>검색 인풋창</Box>
          <Box>
            <Text size="l" weight="bold">
              카테고리
            </Text>
          </Box>
          <Box>카테고리토글</Box>
        </SideBar>
      </Box>
    </Box>
  );
}
