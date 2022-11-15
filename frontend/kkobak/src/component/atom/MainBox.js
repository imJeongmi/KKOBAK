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

function getDirection(flexDir) {
  switch (flexDir) {
    case "row":
      return "row";
    default:
      return "column";
  }
}

function getAlignItems(alignItems) {
  if (!!alignItems) return alignItems;
  return "center";
}

function getJustifyContent(justifyContent) {
  if (!!justifyContent) return justifyContent;
  return "start";
}

const GreyMainBox = styled(Box)(
  ({ width, height, flexDir, alignItems, justifyContent }) => `
  width: ${getWidth(width)}vw;
  height: ${getHeight(height)};
  background-color: #F7F7F7;
  border-radius: 20px;
  text-align: center;
  position: relative;
  display: flex;
  flex-direction: ${getDirection(flexDir)};
  align-items: ${getAlignItems(alignItems)};
  justify-content: ${getJustifyContent(justifyContent)};
  `
);

export default function MainBox({
  children,
  width,
  height,
  flexDir,
  alignItems,
  justifyContent,
}) {
  return (
    <GreyMainBox
      width={width}
      height={height}
      flexDir={flexDir}
      alignItems={alignItems}
      justifyContent={justifyContent}
    >
      {children}
    </GreyMainBox>
  );
}
