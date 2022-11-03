import React from "react";
import { Box } from "@mui/system";
import styled from "@emotion/styled";

function getFlexDirection(flexDir) {
  switch (flexDir) {
    case "col":
      return "column";
    default:
      return "row";
  }
}

function getAlignItems(flexDir) {
  switch (flexDir) {
    case "col":
      return "center";
    default:
      return "none";
  }
}

function getJustifyContent(flexDir) {
  switch (flexDir) {
    case "col":
      return "space-between";
    default:
      return "center";
  }
}

const GreyMainBox = styled(Box)(
  ({flexDir}) => `
  width: 60vw;
  // minHeight: 80vh;
  background-color: #F7F7F7;
  border-radius: 20px;
  text-align: center;
  display: flex;
  flex-direction: ${getFlexDirection(flexDir)};
  align-items: ${getAlignItems(flexDir)};
  justify-content: ${getJustifyContent(flexDir)};
  `
);

export default function MainBox({ children, flexDir }) {
  return <GreyMainBox flexDir={flexDir}>{children}</GreyMainBox>;
}
