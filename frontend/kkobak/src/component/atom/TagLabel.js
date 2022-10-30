import { styled } from "@mui/system";
import React from "react";

const TagSpan = styled(`Button`)(
  () => `
  color: black;
  background-color: #E8F2F9; 
  border-radius: 3px;
  border-style: none;
  height: 25px;
  min-height: 25px;
  `
);

export default function TagButton({ children }) {
  return <TagSpan>{children}</TagSpan>;
}
