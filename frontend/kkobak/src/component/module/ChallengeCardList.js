import React, { useState } from "react";
import ChallengeCard from "./ChallengeCard";
import Box from "@mui/material/Box";
import Pagination from "@mui/material/Pagination";
import Stack from "@mui/material/Stack";
import Button from "component/atom/TextButton";
import WatchToggle from "component/atom/WatchToggle";

export default function ChallengeCardList() {
  const [ChallengeList, setChallengeList] = useState([]);

  return (
    <Box>
      <Box
        sx={{
          width: "924px",
          minHeight: "765px",
          backgroundColor: "#F5F5F5",
          verticalAlign: "middle",
          display: "flex",
          flexWrap: "wrap",
          borderRadius: 5,
        }}
      >
        {/* 받아온 챌린지리스트 순회하기 */}
        <Box sx={{ marginTop: "60px" }}>
          <ChallengeCard />
        </Box>
        <Box sx={{ marginTop: "60px" }}>
          <ChallengeCard />
        </Box>
        <Box sx={{ marginTop: "60px" }}>
          <ChallengeCard />
        </Box>
        <Box sx={{ marginBottom: "100px" }}>
          <ChallengeCard />
        </Box>
        <ChallengeCard />
        <ChallengeCard />
      </Box>
      <Box sx={{ marginLeft: "275px", marginTop: "-55px" }}>
        <Stack spacing={2}>
          <Pagination count={10} shape="rounded" />
        </Stack>
      </Box>
      <Box sx={{ marginLeft: "735px", marginTop: "-100px" }}>
        <Button size="ss">챌린지 생성</Button>
      </Box>
      <Box sx={{ marginLeft: "750px", marginTop: "-710px" }}>
        <WatchToggle></WatchToggle>
      </Box>
    </Box>
  );
}
