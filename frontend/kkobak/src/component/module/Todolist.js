import React, { useEffect, useState } from "react";
import { styled } from "@mui/system";
import { Box } from "@mui/material";

import moment from "moment";
import Text from "component/atom/Text";
import TodolistItem from "component/atom/TodolistItem";

import { getKkobakChallengeList } from "api/userApi";
import { getTodolist, registerTodolist } from "api/todolistApi";

import "./Todolist.scss";

const TodolistBox = styled(Box)(
  () => `
    width: 250px;
    height: 500px;
    margin-top: 40px;
    `
);

const DateBox = styled(Box)(
  () => `
    padding: 25px 35px 15px 35px;
    display: flex;
    justify-content: space-around;
    `
);

const TodolistInput = styled(Box)(
  () => `
    width: 278px;
    height: 25px;
    margin: 5px 0;
    padding-left: 25px;
    display: flex;
    align-items: center;
    `
);

const CheckBox = styled(Box)(
  () => `
    width: 17px;
    height: 17px;
    background-color: #ffffff;
    border-radius: 5px;
    border: 1px solid #CCCCCC;
    margin: 0 15px;
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
  const [refresh, setRefresh] = useState(true);
  const [todolist, setTodolist] = useState([]);
  const [kkobaklist, setKkobaklist] = useState([]);

  useEffect(() => {
    getKkobakChallengeList(
      nowDate.format("YYYY-MM-DD"),
      getKkobakChallengeListSuccess,
      getKkobakChallengeListFail
    );
    getTodolist(formedNowDate, getTodolistSuccess, getTodolistFail);
  }, []);

  useEffect(() => {
    getKkobakChallengeList(
      nowDate.format("YYYY-MM-DD"),
      getKkobakChallengeListSuccess,
      getKkobakChallengeListFail
    );
    getTodolist(formedNowDate, getTodolistSuccess, getTodolistFail);
  }, [formedNowDate, refresh]);

  function getKkobakChallengeListSuccess(res) {
    const data = res.data;
    const list = [];
    data.map((item) => {
      if (item.kkobak === 1 && item.watch === false) {
        list.push(item);
      }
    });
    setKkobaklist(list);
  }

  function getKkobakChallengeListFail(res) {
    // console.log(res);
  }

  function getTodolistSuccess(res) {
    setTodolist(res.data);
  }

  function getTodolistFail(res) {
    // console.log(res);
  }

  function registerTodolistSuccess(res) {
    getTodolist(formedNowDate, getTodolistSuccess, getTodolistFail);
  }

  function registerTodolistFail(res) {
    // console.log(res);
  }

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
    <TodolistBox className="todolist-box">
      <DateBox>
        <Box
          onClick={onClickPrevDay}
          sx={{
            cursor: "pointer",
          }}
        >
          <Text color="navy" weight="bold" size="14px">
            &lt;
          </Text>
        </Box>
        <Box sx={{ width: "140px", textAlign: "center" }}>
          <Text
            weight="semibold"
            size="14px"
          >{`${formedNowDate} (${formedNowDay})`}</Text>
        </Box>
        <Box
          onClick={onClickNextDay}
          sx={{
            cursor: "pointer",
          }}
        >
          <Text color="navy" weight="bold" size="14px">
            &gt;
          </Text>
        </Box>
      </DateBox>

      {kkobaklist.map((item) => {
        return (
          <TodolistItem
            key={item.chlId}
            refresh={refresh}
            setRefresh={setRefresh}
            nowDate={formedNowDate}
            id={item.chlId}
            contents={item.title}
            done={item.done}
            chlId={item.chlId}
            watch={item.watch}
            category={item.categoryId}
            dashedNowDate={nowDate.format("YYYY-MM-DD")}
            weight="semibold"
            color="navy"
          />
        );
      })}

      {todolist.map((item) => {
        return (
          <TodolistItem
            key={item.todoId}
            refresh={refresh}
            setRefresh={setRefresh}
            nowDate={formedNowDate}
            id={item.todoId}
            contents={item.contents}
            done={item.done}
            chlId={false}
            weight="medium"
          />
        );
      })}

      <TodolistInput>
        <CheckBox sx={{ cursor: "pointer" }} />
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
