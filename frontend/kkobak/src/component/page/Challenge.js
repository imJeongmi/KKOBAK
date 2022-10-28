import React from "react";
import Text from "component/atom/Text";
import Box from "@mui/material/Box";
import ChallengeCardList from "component/module/ChallengeCardList";
import SideBar from "component/atom/SideBar";
import SearchInput from "component/atom/SearchInput";
import SearchToggle from "component/atom/SearchToggle";
import CategoryToggle from "component/atom/CategoryToggle";

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
          <Box sx={{ float: "right", marginRight: "150px" }}>
            <SearchToggle />
          </Box>
          <Box>
            <SearchInput />
          </Box>
          <Box sx={{ marginTop: "30px" }}>
            <Text size="l" weight="bold">
              카테고리
            </Text>
          </Box>
          <Box sx={{ marginLeft: "40px", marginTop: "30px" }}>
            <CategoryToggle />
          </Box>
        </SideBar>
      </Box>
    </Box>
  );
}
