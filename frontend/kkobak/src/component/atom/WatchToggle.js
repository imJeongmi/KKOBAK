import React, { useState } from "react";
import { styled } from "@mui/system";
import { Box, Select } from "@mui/material";
import { ToggleButton, ToggleButtonGroup } from "@mui/material";

import WatchImage from "static/watch.png";
import NoWatchImage from "static/noWatch.png";

const ToggleBox = styled(ToggleButtonGroup)(
  () => `
width: 130px;
height: 32px;
background-color : white;
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
      <ToggleButton value="yes" aria-label="yes">
        <img src={WatchImage} width="20px" />
      </ToggleButton>
      <ToggleButton value="all" aria-label="all">
        <Box sx={{ width: "20px" }} />
      </ToggleButton>
      <ToggleButton value="no" aria-label="no">
        <img src={NoWatchImage} width="20px" />
      </ToggleButton>
    </ToggleBox>
  );
}
