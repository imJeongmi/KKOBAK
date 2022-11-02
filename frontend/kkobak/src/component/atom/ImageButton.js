import styled from "@emotion/styled";
import { Box, Button } from "@mui/material";
import React from "react";
import Text from "./Text"

const ImageButtonBase = styled(Button)(
  () => `
  margin: 3px;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  padding-top: 100%;
  background: linear-gradient(135deg, #FFFFFF 0%, #F8F8F8 29.48%, #F3F3F3 52.92%, #EFEFEF 71.15%, #ECECEC 86.77%, #E9E9E9 100%);
  box-shadow: -5px 5px 10px rgba(207, 207, 207, 0.2), 5px -5px 10px rgba(207, 207, 207, 0.2), -5px -5px 10px rgba(255, 255, 255, 0.9), 5px 5px 13px rgba(207, 207, 207, 0.9), inset 1px 1px 2px rgba(255, 255, 255, 0.3), inset -1px -1px 2px rgba(207, 207, 207, 0.5);
  border-radius: 20px;
  &: hover{
    background-color: #d6d6d6;
  };
  text-align: center;
  `,
);

export default function ImageButton({ size, children, onClick }) {
  function getWidthHeight(size) {
    switch (size) {
      case "l":
        return "18vw";
      case "m":
        return "5vw";
      case "s":
        return "50px";
      default:
        return "18vw";
    }
  }
  return (
    <Box sx={{ position: "relative", weight: `${getWidthHeight(size)}`, height: `${getWidthHeight(size)}` }}>
      <ImageButtonBase size={size} onClick={onClick}>
        {/* <img alt="icon"></img> */}
        <Text weight="semibold" size="m" color="grey">{children}</Text>
      </ImageButtonBase>
    </Box>
  )
}