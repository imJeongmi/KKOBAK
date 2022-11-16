import React, { useState } from "react";
import { styled } from "@mui/system";
import { Box } from "@mui/material";

import moment from "moment";
import Text from "component/atom/Text";
import CheckImage from "static/check.png";
import PlusImage from "static/plus.png";
import DeleteImage from "static/delete.png";

import { deleteTodolist, updateTodolistStatus } from "api/todolistApi";
import { requestChallengeCount } from "api/Challenge";

const TodolistItemBox = styled(Box)(
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

const ImageBox = styled(Box)(
  () => `
    width: 16px;
    height: 16px;
    display: flex;
    align-items: center;
    justify-content: center;
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
  watch,
  category,
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

  function requestChallengeCountSuccess(res) {
    setRefresh(!refresh);
  }

  function requestChallengeCountFail(res) {}

  function onClickCheckBox() {
    setHover(false);
    if (nowDate !== today) return;
    if (done && category === 2) return;

    setCheck(!check);
    setCancelText(!cancelText);
    if (!chlId) {
      updateTodolistStatus(
        id,
        updateTodolistStatusSuccess,
        updateTodolistStatusFail
      );
    } else if (category === 1) {
      if (done) {
        requestChallengeCount(
          chlId,
          2,
          requestChallengeCountSuccess,
          requestChallengeCountFail
        );
      } else {
        requestChallengeCount(
          chlId,
          1,
          requestChallengeCountSuccess,
          requestChallengeCountFail
        );
      }
    } else if (category === 2) {
      requestChallengeCount(
        chlId,
        1,
        requestChallengeCountSuccess,
        requestChallengeCountFail
      );
    }
  }

  function onClickDelete() {
    deleteTodolist(id, deleteTodolistSuccess, deleteTodolistFail);
  }

  function getImage() {
    if (category === 2) return PlusImage;
    return CheckImage;
  }

  return (
    <TodolistItemBox>
      <CheckBox
        onClick={onClickCheckBox}
        onMouseOver={() => setHover(true)}
        onMouseOut={() => setHover(false)}
        sx={{ cursor: "pointer" }}
      >
        {check && done ? (
          <ImageBox>
            <img src={CheckImage} width="100%" height="100%" />
          </ImageBox>
        ) : hover && !watch ? (
          <ImageBox sx={{ opacity: "0.4" }}>
            <img src={getImage()} width="100%" height="100%" />
          </ImageBox>
        ) : (
          ""
        )}
      </CheckBox>
      <Box sx={{ width: "160px" }}>
        <Text
          size="12px"
          color={color}
          weight={weight}
          done={cancelText && done}
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
