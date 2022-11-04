import React, { useState } from "react";
import { styled } from "@mui/system";
import { Box } from "@mui/material";

// import "./TodolistItem.scss";

import CheckImage from "static/check.png";

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

export default function TodolistItemTest(props) {
  const [text, setText] = useState([]);
  const [isClicked, setIsClicked] = useState(0);
  const [isHovering, setIsHovering] = useState(0);

  function onKeyPress(e) {
    if (e.key == "Enter") {
      setText((text) => [...text, e.target.value]);
      console.log("text: ", text);
      props.setTodolist(text)
    }
  }

  return (
    <TodoItemBox>
      <CheckBox
        onClick={() => setIsClicked(1)}
        onMouseOver={() => setIsHovering(1)}
        onMouseOut={() => setIsHovering(0)}
      >
        {isHovering && !isClicked ? (
          <Box sx={{ opacity: 0.5 }}>
            <img src={CheckImage} width="20px" />
          </Box>
        ) : (
          ""
        )}
        {isClicked && !text ? setIsClicked(0) : ""}
        {isClicked && text ? <img src={CheckImage} width="20px" /> : ""}
      </CheckBox>

      <input
        autoFocus
        // onChange={onChangeInput}
        // value={text}
        onKeyPress={onKeyPress}
        placeholder="오늘 할 일을 기록하세요"
      />
    </TodoItemBox>
  );
}
