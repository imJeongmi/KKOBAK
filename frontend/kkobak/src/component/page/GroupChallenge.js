import React from "react";
import Box from "@mui/material/Box";

import Text from "component/atom/Text";
import GroupChallengeCardList from "component/module/GroupChallengeCardList";

export default function GroupChallenge() {
  return (
    <Box sx={{ display: "flex", justifyContent: "space-between" }}>
      <Box sx={{ margin: "0 auto" }}>
        <Box
          sx={{ display: "flex", alignItems: "end", letterSpacing: "0.3px" }}
        >
          <Text size="m" weight="bold" mt="30" my="15" color="blue">
            {"꼬박이들"}
          </Text>
          <Text size="17px" weight="bold" mt="30" my="15">
            {"의 단체 챌린지 🏴"}
          </Text>
        </Box>
        <GroupChallengeCardList />
      </Box>
    </Box>
  );
}
