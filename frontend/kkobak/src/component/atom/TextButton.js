import React from "react";
import Button from "@mui/material/Button";
import { styled } from "@mui/system";

// const style = {

//   borderWidth: "6px",
//   border: "solid",
// }

const StyledButton = styled(Button)(
  ({ size, color, my }) => `
  width: ${getWidthBySize(size)};
  height: ${getHeightBySize(size)};
  margin: ${getmarginY(my)} 1vw;
  border-radius: 10px;
  text-align: center;
  font-size: 15px;
  font-weight: bold;
  color: white;
  background-color: ${getColor(color)};
  &:hover {
    background-color: ${getHoverColor(color)};
  }
  `
);

function getWidthBySize(size) {
  switch (size) {
    case "l":
      return "25vw";
    case "m":
      return "20vw";
    case "s":
      return "10vw";
    case "ss":
      return "120px";
    default:
      return "30vw";
  }
}

function getHeightBySize(size) {
  switch (size) {
    case "l":
      return "6vh";
    case "m":
      return "5vh";
    case "s":
      return "3vh";
    case "ss":
      return "30px";
    default:
      return "7vh";
  }
}

function getColor(color) {
  switch (color) {
    case "primary":
      return "#99B9D6";
    case "secondary":
      return "#adadad";
    default:
      return "#99B9D6";
  }
}

function getHoverColor(color) {
  switch (color) {
    case "primary":
      return "#ADCDEA";
    case "secondary":
      return "#d6d6d6";
    default:
      return "#ADCDEA";
  }
}

function getmarginY(my) {
  if (!my) {
    return "3vh";
  } else {
    return my;
  }
}

export default function TextButton({ size, onClick, color, my, children }) {
  return (
    // color : primary(핑크), grey(회색)
    // size : s, m, l
    // onClick : 클릭 시 필요한 함수 상위 컴포넌트에서 작성하면 됨!
    <StyledButton size={size} onClick={onClick} color={color} my={my}>
      {children}
    </StyledButton>
  );
}
