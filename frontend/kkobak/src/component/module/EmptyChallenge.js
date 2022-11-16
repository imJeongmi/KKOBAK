import React from "react";
import { useNavigate } from "react-router-dom";

import Box from "@mui/material/Box";
import styled from "@emotion/styled";

import Button from "component/atom/TextButton";
import Text from "component/atom/Text";
import char from "static/char.png";

export default function EmptyChallenge() {
  const navigate = useNavigate();
  function moveToRegister(e) {
    e.preventDefault();
    navigate("/register");
  }
  return (
    <Box sx={{ display: "flex", flexDirection: "column" }}>
      <Box
        sx={{
          width: "500px",
          height: "500px",
          backgroundColor: "#f0f6fb",
          borderRadius: 5,
          textAlign: "center",
        }}
      >
        <Box sx={{ flex: 1, marginY: "20px" }}>
          <Text>현재 생성된 챌린지가 없습니다.</Text>
        </Box>
        <Box sx={{ flex: 1 }}>
          <img src={char} width="350px" height="350px" />
        </Box>

        <Box sx={{ flex: 1 }}>
          <Button size="l" my="0" onClick={moveToRegister}>
            챌린지 생성
          </Button>
        </Box>
      </Box>
    </Box>
  );
}
