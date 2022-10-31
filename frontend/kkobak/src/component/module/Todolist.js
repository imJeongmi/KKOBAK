import React from "react";
import { styled } from "@mui/system";
import { Box } from "@mui/material";

import moment from "moment";
import Text from "component/atom/Text";
import TodolistItem from "component/atom/TodolistItem";

const TodolistBox = styled(Box)(
  () => `
    width: 278px;
    height: 655px;
    background-color: #FBFBFB;
    border-radius: 20px;

    `
);

const DateBox = styled(Box)(
    () => `
    padding: 25px 35px 35px 35px;
    display: flex;
    justify-content: space-around;
    `
)

function getDay(num) {
  switch (num) {
    case 0:
      return "SUN";
    case 1:
      return "MON";
    case 2:
      return "TUE";
    case 3:
      return "WED";
    case 4:
      return "THU";
    case 5:
      return "FRI";
    case 6:
      return "SAT";
  }
}

export default function Todolist() {
  const nowDate = moment().format("YYYY.MM.DD");
  const nowDay = getDay(moment().day());

  return (
    <TodolistBox>
      <DateBox>
        <Text color="blue" weight="bold">&lt;</Text>
        <Text weight="semibold">{nowDate + " (" + nowDay + ")"}</Text>
        <Text color="blue" weight="bold">&gt;</Text>
      </DateBox>
      <TodolistItem />
    </TodolistBox>
  );
}
