import React from "react";
import { Box } from "@mui/system";
import styled from "@emotion/styled";

function getWidth(width) {
  if (!!width) return width;
  return 60;
}

function getHeight(height) {
  if (!!height) return height;
  return "88vh";
}

function getAlignItems(alignItems) {
  if (!!alignItems) return alignItems;
  return "center"
}

function getJustifyContent(justifyContent) {
  if (!!justifyContent) return justifyContent;
  return "start"
}

const GreyMainBox = styled(Box)(
  ({width, height, alignItems, justifyContent}) => `
  width: ${getWidth(width)}vw;
  height: ${getHeight(height)};
  background-color: #F7F7F7;
  border-radius: 20px;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: ${getAlignItems(alignItems)};
  justify-content: ${getJustifyContent(justifyContent)};
  `
);

export default function MainBox({ children, width, height, alignItems, justifyContent}) {
  return <GreyMainBox width={width} height={height} alignItems={alignItems} justifyContent={justifyContent}>{children}</GreyMainBox>;
}
