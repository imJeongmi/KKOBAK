import React, { useState } from "react";
import { styled } from "@mui/system";
import Box from "@mui/material/Box";
import { ToggleButton, ToggleButtonGroup } from "@mui/material";

const ToggleBox = styled(ToggleButtonGroup)(
  () => `
width: 132px;
height: 32px;
border-radius: 20px;
    `
);

export default function WatchToggle() {
  const [filter, setFilter] = useState("all");
  const handleFilter = (event, newFilter) => {
    setFilter(newFilter);
  };

  return (
    <ToggleBox
      color="primary"
      value={filter}
      exclusive
      onChange={handleFilter}
      aria-label="watch filter"
    >
      <ToggleButton value="ti" aria-label="ti">
        <Box sx={{ width: "50px" }}>제목</Box>
      </ToggleButton>
      <ToggleButton value="na" aria-label="na">
        <Box sx={{ width: "50px" }}>닉네임</Box>
      </ToggleButton>
      <ToggleButton value="ta" aria-label="ta">
        <Box sx={{ width: "50px" }}>태그</Box>
      </ToggleButton>
    </ToggleBox>
  );
}
