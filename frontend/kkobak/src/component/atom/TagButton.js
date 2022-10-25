import { styled } from "@mui/system";
import React from "react";

const TagSpan = styled(`Button`)(
  () => `
  color: black;
  background-color: #CDE3B5; 
  border-radius: 10px;
  border-style: none;
  height: 5vh;
  min-height: 30px;
  `
)

export default function TagButton({ children, onClick }) {
  return (
    <TagSpan onClick={onClick}>{children}</TagSpan>
  );
}