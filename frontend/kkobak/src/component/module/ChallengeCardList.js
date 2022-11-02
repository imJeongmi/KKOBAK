import React, { useState } from "react";
import Box from "@mui/material/Box";
import Pagination from "@mui/material/Pagination";
import Stack from "@mui/material/Stack";

import Button from "component/atom/TextButton";
import MainBox from "component/atom/MainBox";
import WatchToggle from "component/atom/WatchToggle";
import ChallengeCard from "component/module/ChallengeCard";

export default function ChallengeCardList() {
  const [ChallengeList, setChallengeList] = useState([]);

  return (
    <MainBox flexDir="col">
      <Box
        sx={{
          width: "90%",
          margin: "20px 20px 0 20px",
          display: "flex",
          justifyContent: "start",
        }}
      >
        <WatchToggle />
      </Box>
      <Box
        sx={{
          width: "100%",
          minHeight: "70vh",
          display: "flex",
          flexWrap: "wrap",
          justifyContent: "center",
        }}
      >
        <ChallengeCard />
        <ChallengeCard />
        <ChallengeCard />
        <ChallengeCard />
        <ChallengeCard />
        <ChallengeCard />
      </Box>
      <Box
        sx={{
          width: "95%",
          height: "40px",
          my: "5px",
          display: "flex",
          justifyContent: "end",
        }}
      >
        <Button size="ss" my="0">챌린지 생성</Button>
      </Box>

      <Box>
        <Stack spacing={2}>
          <Pagination count={10} shape="rounded" />
        </Stack>
      </Box>
    </MainBox>
  );
}
