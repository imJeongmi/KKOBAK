import React, { useState } from "react";
import ChallengeCard from "./ChallengeCard";
import Box from "@mui/material/Box";

export default function ChallengeCardList() {
  const [ChallengeList, setChallengeList] = useState([]);

  return (
    <Box
      sx={{
        width: "900px",
        minHeight: "578px",
        backgroundColor: "#F5F5F5",
        verticalAlign: "middle",
        display: "flex",
        flexWrap: "wrap",
      }}
    >
      {/* 받아온 챌린지리스트 순회하기 */}
      <ChallengeCard />
      <ChallengeCard />
      <ChallengeCard />
      <ChallengeCard />
      <ChallengeCard />
      <ChallengeCard />
    </Box>
  );
}
