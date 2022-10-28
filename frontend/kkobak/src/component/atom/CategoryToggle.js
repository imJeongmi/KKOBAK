import React, { useState } from "react";
import { styled } from "@mui/material/styles";
import Box from "@mui/material/Box";
import Text from "component/atom/Text";

import Paper from "@mui/material/Paper";
import ToggleButton from "@mui/material/ToggleButton";
import ToggleButtonGroup from "@mui/material/ToggleButtonGroup";

const StyledToggleButtonGroup = styled(ToggleButtonGroup)(({ theme }) => ({
  "& .MuiToggleButtonGroup-grouped": {
    margin: theme.spacing(0.5),
    border: 0,
    "&.Mui-disabled": {
      border: 0,
    },
    "&:not(:first-of-type)": {
      borderRadius: theme.shape.borderRadius,
    },
    "&:first-of-type": {
      borderRadius: theme.shape.borderRadius,
    },
  },
}));

export default function CustomizedDividers() {
  const [filter, setFilter] = useState("all");

  const handleFilter = (event, newFilter) => {
    setFilter(newFilter);
  };

  return (
    <div>
      <Paper
        elevation={0}
        sx={{
          display: "flex",
          flexWrap: "wrap",
        }}
      >
        <StyledToggleButtonGroup
          size="small"
          value={filter}
          exclusive
          onChange={handleFilter}
          aria-label="text alignment"
          orientation="vertical"
          sx={{ width: "240px" }}
          color="primary"
        >
          <ToggleButton value="1" aria-label="1">
            <Box sx={{ fontSize: "24px" }}>전체</Box>
          </ToggleButton>
          <ToggleButton value="2" aria-label="2">
            <Box sx={{ fontSize: "24px" }}>식습관</Box>
          </ToggleButton>
          <ToggleButton value="3" aria-label="3">
            <Box sx={{ fontSize: "24px" }}>구기 종목</Box>
          </ToggleButton>
          <ToggleButton value="4" aria-label="4">
            <Box sx={{ fontSize: "24px" }}>맨몸 운동</Box>
          </ToggleButton>
          <ToggleButton value="5" aria-label="5">
            <Box sx={{ fontSize: "24px" }}>격기</Box>
          </ToggleButton>
          <ToggleButton value="6" aria-label="6">
            <Box sx={{ fontSize: "24px" }}>생활 식습관</Box>
          </ToggleButton>
          <ToggleButton value="7" aria-label="7">
            <Box sx={{ fontSize: "24px" }}>라켓 스포츠</Box>
          </ToggleButton>
          <ToggleButton value="8" aria-label="8">
            <Box sx={{ fontSize: "24px" }}>기타</Box>
          </ToggleButton>
        </StyledToggleButtonGroup>
      </Paper>
    </div>
  );
}
