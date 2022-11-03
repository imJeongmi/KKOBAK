import React, { useEffect, useState } from "react";
import { styled } from "@mui/system";
import { Box } from "@mui/material";

import moment from "moment";
import Text from "component/atom/Text";
import TodolistItem from "component/atom/TodolistItem";

const TodolistBox = styled(Box)(
  () => `
    width: 278px;
    height: 600px;
    background-color: #FAFAFA;
    border-radius: 20px;
    `
);

const DateBox = styled(Box)(
  () => `
    padding: 25px 35px 35px 35px;
    display: flex;
    justify-content: space-around;
    `
);

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
  const today = moment();
  const [nowDate, setNowDate] = useState(today);
  const [formedNowDate, setFormedNowDate] = useState(
    nowDate.format("YYYY.MM.DD")
  );
  const [formedNowDay, setFormedNowDay] = useState(getDay(nowDate.day()));

  function onClickPrevDay() {
    setNowDate(nowDate.subtract(1, "day"));
    // console.log(nowDate.format("YYYY.MM.DD"), getDay(nowDate.day()));
    setFormedNowDate(nowDate.format("YYYY.MM.DD"));
    setFormedNowDay(getDay(nowDate.day()));
  }

  function onClickNextDay() {
    setNowDate(nowDate.add(1, "day"));
    setFormedNowDate(nowDate.format("YYYY.MM.DD"));
    setFormedNowDay(getDay(nowDate.day()));
  }

  return (
    <TodolistBox>
      <DateBox>
        <Box onClick={onClickPrevDay}>
          <Text color="blue" weight="bold">
            &lt;
          </Text>
        </Box>
        <Box sx={{ width: "140px", textAlign: "center" }}>
          <Text weight="semibold">{`${formedNowDate} (${formedNowDay})`}</Text>
        </Box>
        <Box onClick={onClickNextDay}>
          <Text color="blue" weight="bold">
            &gt;
          </Text>
        </Box>
      </DateBox>
      <TodolistItem />
    </TodolistBox>
  );
}
