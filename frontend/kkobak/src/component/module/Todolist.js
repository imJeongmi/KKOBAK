import React, { useEffect, useState } from "react";
import { styled } from "@mui/system";
import { Box } from "@mui/material";

import moment from "moment";
import Text from "component/atom/Text";
import TodolistItem from "component/atom/TodolistItem";

import CheckImage from "static/check.png";

import "./Todolist.scss";

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

const TodolistInput = styled(Box)(
  () => `
    width: 278px;
    height: 30px;
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
    setFormedNowDate(nowDate.format("YYYY.MM.DD"));
    setFormedNowDay(getDay(nowDate.day()));
  }

  function onClickNextDay() {
    setNowDate(nowDate.add(1, "day"));
    setFormedNowDate(nowDate.format("YYYY.MM.DD"));
    setFormedNowDay(getDay(nowDate.day()));
  }

  const [todolist, setTodolist] = useState([]);

  function onKeyPress(e) {
    if (e.key == "Enter") {
      setTodolist((todolist) => [...todolist, e.target.value]);
      console.log("todolist: ", todolist);
    }
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

      {/* todolist 배열 추가될 때마다 목록 띄우기 (TodolistItem 형태로) */}
      <TodolistItem item="하루 8잔 물 마시기"/>
      <TodolistItem item="1시간마다 일어서기"/>

      <TodolistInput>
        <CheckBox>
          <img src={CheckImage} width="20px" />
        </CheckBox>
        <input
          autoFocus
          onKeyPress={onKeyPress}
          placeholder="오늘 할 일을 기록하세요"
        />
      </TodolistInput>

      {/* <TodolistItem setTodolist={setTodolist} /> */}
      {/* {console.log("todolist: ", todolist)} */}
      {/* {useEffect(() => {
        todolist.map((index, item) => {
          <Text>{item}</Text>;
        });
      }, todolist)} */}
    </TodolistBox>
  );
}
