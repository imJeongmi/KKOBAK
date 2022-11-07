import React, { useEffect, useState } from "react";
import { styled } from "@mui/system";
import { Box } from "@mui/material";

import moment from "moment";
import Text from "component/atom/Text";
import TodolistItem from "component/atom/TodolistItem";

import { getTodolist, registerTodolist } from "api/todolistApi";

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
    padding: 25px 35px 25px 35px;
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
    margin: 5px 0;
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

  const [text, setText] = useState("");
  const [todolist, setTodolist] = useState([]);

  useEffect(() => {
    getTodolist(formedNowDate, getTodolistSuccess, getTodolistFail);
  }, []);

  useEffect(() => {
    getTodolist(formedNowDate, getTodolistSuccess, getTodolistFail);
  }, formedNowDate);

  function getTodolistSuccess(res) {
    setTodolist(res.data);
  }

  function getTodolistFail(res) {}

  function registerTodolistSuccess(res) {
    console.log("todoId: ", res.data);
    getTodolist(formedNowDate, getTodolistSuccess, getTodolistFail);
  }

  function registerTodolistFail(res) {}

  function onKeyPress(e) {
    const newTodolist = e.target.value;
    if (e.key == "Enter" && newTodolist) {
      registerTodolist(
        newTodolist,
        nowDate.format("YYYY-MM-DD"),
        registerTodolistSuccess,
        registerTodolistFail
      );
      setText("");
    }
  }

  return (
    <TodolistBox>
      <DateBox>
        <Box
          onClick={onClickPrevDay}
          sx={{
            cursor: "pointer",
          }}
        >
          <Text color="blue" weight="bold">
            &lt;
          </Text>
        </Box>
        <Box sx={{ width: "140px", textAlign: "center" }}>
          <Text weight="semibold">{`${formedNowDate} (${formedNowDay})`}</Text>
        </Box>
        <Box
          onClick={onClickNextDay}
          sx={{
            cursor: "pointer",
          }}
        >
          <Text color="blue" weight="bold">
            &gt;
          </Text>
        </Box>
      </DateBox>

      {todolist.map((item, index) => {
        return (
          <TodolistItem
            nowDate={formedNowDate}
            id={item.todoId}
            contents={item.contents}
            done={item.done}
          />
        );
      })}

      <TodolistInput>
        <CheckBox />
        <input
          autoFocus
          onKeyPress={onKeyPress}
          onChange={(e) => setText(e.target.value)}
          value={text}
          placeholder="오늘 할 일을 기록하세요"
        />
      </TodolistInput>
    </TodolistBox>
  );
}
