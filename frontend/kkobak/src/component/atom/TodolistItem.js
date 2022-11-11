import React, { useState } from "react";
import { styled } from "@mui/system";
import { Box } from "@mui/material";

import moment from "moment";
import Text from "component/atom/Text";
import CheckImage from "static/check.png";
import DeleteImage from "static/delete.png";

import { deleteTodolist, updateTodolistStatus } from "api/todolistApi";
import { logController } from "api/log";

const TodolistItemBox = styled(Box)(
  () => `
    width: 278px;
    height: 30px;
    margin: 5px 0;
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

export default function TodolistItem({
  refresh,
  setRefresh,
  nowDate,
  id,
  contents,
  done,
  chlId,
  dashedNowDate,
  weight,
  color,
}) {
  const today = moment().format("YYYY.MM.DD");

  const [check, setCheck] = useState(done);
  const [hover, setHover] = useState(false);
  const [cancelText, setCancelText] = useState(done);

  function updateTodolistStatusSuccess(res) {
    setRefresh(!refresh);
  }

  function updateTodolistStatusFail(res) {}

  function deleteTodolistSuccess(res) {
    setRefresh(!refresh);
  }

  function deleteTodolistFail(res) {}

  function changeKkobakChallengeDoneSuccess(res) {
    // 캘린더 새로고침 연결
    console.log("success");
  }

  function changeKkobakChallengeDoneFail(res) {
    console.log(res);
  }

  function onClickCheckBox() {
    setHover(false);
    if (nowDate === today) {
      setCheck(!check);
      setCancelText(!cancelText);
      if (!!chlId) {
        logController(
          chlId,
          dashedNowDate,
          changeKkobakChallengeDoneSuccess,
          changeKkobakChallengeDoneFail
        );
      } else {
        updateTodolistStatus(
          id,
          updateTodolistStatusSuccess,
          updateTodolistStatusFail
        );
      }
    }
  }

  function onClickDelete() {
    deleteTodolist(id, deleteTodolistSuccess, deleteTodolistFail);
  }

  return (
    <TodolistItemBox>
      <CheckBox
        onClick={onClickCheckBox}
        onMouseOver={() => setHover(true)}
        onMouseOut={() => setHover(false)}
      >
        {(check && done) ? (
          <img src={CheckImage} width="20px" />
        ) : hover ? (
          <Box sx={{ opacity: 0.5 }}>
            <img src={CheckImage} width="20px" />
          </Box>
        ) : (
          ""
        )}
      </CheckBox>
      <Box sx={{ width: "170px" }}>
        <Text
          size="12px"
          color={color}
          weight={weight}
          py="1"
          px="2"
          done={cancelText && done} // 이 부분 테스트 필요
        >
          {contents}
        </Text>
      </Box>
      <Box
        onClick={onClickDelete}
        sx={{ width: "30px", textAlign: "center", opacity: "0.6" }}
      >
        {!chlId ? <img src={DeleteImage} alt="img" width="17px" /> : ""}
      </Box>
    </TodolistItemBox>
  );
}
