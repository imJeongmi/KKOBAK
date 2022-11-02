import React from "react";
import Text from "component/atom/Text";
import Box from "@mui/material/Box";
import ChallengeCardList from "component/module/ChallengeCardList";
import SideBar from "component/atom/SideBar";
import CategoryToggle from "component/atom/CategoryToggle";
import MyChallengeCarousel from "component/module/MyChallengeCarousel";

export default function Challenge() {
  return (
    <Box sx={{ display: "flex", justifyContent: "space-between" }}>
      <Box sx={{ margin: "0 auto" }}>
        <Text size="l" weight="bold" my="25px">
          경원님의 챌린지리스트
        </Text>
        <ChallengeCardList />
      </Box>
      <SideBar>
        <Box sx={{ paddingY: "30px", marginLeft: "10px" }}>
          <Text size="l" weight="bold">
            승리님의 챌린지 현황
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
        <Box sx={{ marginTop: "30px" }}>
          <CategoryToggle />
        </Box>
      </SideBar>
    </Box>
  );
}
