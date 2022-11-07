import React, { useState } from "react";
import { styled } from "@mui/system";
import { Box } from "@mui/material";

import moment from "moment";
import Text from "component/atom/Text";
import CheckImage from "static/check.png";
import DeleteImage from "static/delete.png";

import { deleteTodolist, updateTodolistStatus } from "api/todolistApi";

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
    width: 20px;
    height: 20px;
    background-color: #ffffff;
    border-radius: 5px;
    border: 1px solid #CCCCCC;
    margin: 0 20px;
    `
);

export default function TodolistItem({ nowDate, id, contents, done }) {
  const today = moment().format("YYYY.MM.DD");

  const [check, setCheck] = useState(done);
  const [hover, setHover] = useState(false);
  const [cancelText, setCancelText] = useState(done);

  function updateTodolistStatusSuccess(res) {
    window.location.reload();}

  function updateTodolistStatusFail(res) {}

  function deleteTodolistSuccess(res) {
    // 새로고침 안되게 코드 수정
    // window.location.replace("/");
    window.location.reload();
  }

  function deleteTodolistFail(res) {}

  function onClickCheckBox() {
    setHover(false);
    if (nowDate === today) {
      setCheck(!check);
      setCancelText(!cancelText);
      updateTodolistStatus(
        id,
        updateTodolistStatusSuccess,
        updateTodolistStatusFail
      );
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
        {check ? (
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
        <Text size="14px" weight="medium" py="1" px="2" done={cancelText}>
          {contents}
        </Text>
      </Box>
      <Box
        onClick={onClickDelete}
        sx={{ width: "30px", textAlign: "center", opacity: "0.6" }}
      >
        <img src={DeleteImage} alt="img" width="20px" />
      </Box>
    </TodolistItemBox>
  );
}
