import React, { useState } from "react";
import { styled } from "@mui/system";
import { Box } from "@mui/material";

import Text from "component/atom/Text";
import CheckImage from "static/check.png";
import DeleteImage from "static/delete.png";

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

export default function TodolistItem({ item, done }) {
  const [check, setCheck] = useState(done);
  const [hover, setHover] = useState(false);
  const [cancelText, setCancelText] = useState(done);

  function onClickCheckBox() {
    setCheck(!check);
    setHover(false);
    setCancelText(!cancelText);
  }

  function onClickDelete() {
    console.log("투두리스트 삭제");
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
          {item}
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
