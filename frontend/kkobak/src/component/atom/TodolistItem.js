import React from "react";
import { styled } from "@mui/system";
import { Box } from "@mui/material";
import Input from "./Input";

import './TodolistItem.scss';

const TodoItemBox = styled(Box)(
  () => `
    width: 278px;
    height: 25px;
    display: flex;
    align-items: center;
    `
);

const CheckBox = styled(Box)(
  () => `
    width: 20px;
    height: 20px;
    background-color: #ffffff;
    border-radius: 5px;
    border: 1px solid #CCCCCC;
    margin: 0 20px;
    `
);

export default function TodolistItem() {
  return (
    <TodoItemBox>
      <CheckBox />
      <input placeholder="오늘 할 일을 기록하세요" />
    </TodoItemBox>
  );
}
