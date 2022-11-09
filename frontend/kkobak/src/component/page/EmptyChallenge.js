import React from "react";
import Box from "@mui/material/Box";
import Button from "component/atom/TextButton";
import styled from "@emotion/styled";
import { useNavigate } from "react-router-dom";
import char from "../../static/char.png";

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
          backgroundColor: "#F9D0CF",
          borderRadius: 5,
          textAlign: "center",
        }}
      >
        <Box sx={{ flex: 1, marginTop: "10px" }}>
          현재 생성된 챌린지가 없습니다.
        </Box>
        <Box sx={{ flex: 1 }}>
          <img src={char} width="400px" height="400px" />
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
