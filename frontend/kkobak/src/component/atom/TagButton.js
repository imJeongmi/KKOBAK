import { styled } from "@mui/system";
import React from "react";
import Button from "@mui/material/Button";

const TagSpan = styled(`button`)(
  () => `
  color: black;
  background-color: #E8F2F9; 
  border-radius: 10px;
  border-style: none;
  height: 5vh;
  min-height: 30px;
  `
);

export default function TagButton({ children, onClick }) {
  return <TagSpan onClick={onClick}>{children}</TagSpan>;
}
