import React from "react";
import { styled } from "@mui/system";
import { Box } from "@mui/material";

const TextBox = styled(Box)(
  ({ size, color, weight, my, mx, mt, py, px, done }) => `
  font-size: ${getSize(size)};
  color: ${getColor(color)};
  margin: ${getMarginTop(mt, my)}px ${getMarginX(mx)}px ${getMarginY(my)}px ${getMarginX(mx)}px;
  font-family: ${getWeight(weight)};
  padding: ${getPaddingY(py)}px ${getPaddingX(px)}px;
  text-decoration: ${getStyle(done)};
  `
);

function getSize(size) {
  switch (size) {
    case "xl":
      return "55px";
    case "l":
      return "25px";
    case "m":
      return "20px";
    case "s":
      return "10px";
    case size:
      return size;
    default:
      return "15px";
  }
}

function getColor(color) {
  switch (color) {
    case "red":
      return "#DE5E5F";
    case "pink":
      return "#DB9394";
    case "orange":
      return "#E8BC88";
    case "yellow":
      return "#EBE5A4";
    case "green":
      return "#7B9888";
    case "blue":
      return "#4B79A6";
    case "navy":
      return "#36577D";
    case "purple":
      return "#8F5E97";
    case "grey":
      return "#9A9A9A";
    case "white":
      return "#ffffff";
    case "black":
      return "#000000";
    default:
      return "#333333";
  }
}

function getWeight(weight) {
  switch (weight) {
    case "bold":
      return "SUIT-Bold";
    case "semibold":
      return "SUIT-SemiBold";
    case "medium":
      return "SUIT-Medium";
    case "light":
      return "SUIT-Light";
    default:
      return "SUIT";
  }
}

function getMarginY(my) {
  if (!!my) return my;
  return 0;
}

function getMarginX(mx) {
  if (!!mx) return mx;
  return 0;
}

function getMarginTop(mt, my) {
  if (!!mt) return mt;
  return my;
}

function getPaddingY(py) {
  if (!!py) return py;
  return 0;
}

function getPaddingX(px) {
  if (!!px) return px;
  return 0;
}

function getStyle(done) {
  if (done === "true") return "line-through";
  return "none";
}

export default function Text({
  children,
  size,
  color,
  weight,
  my,
  mx,
  mt,
  py,
  px,
  done,
}) {
    return (
      <TextBox
        size={size}
        color={color}
        weight={weight}
        my={my}
        mx={mx}
        mt={mt}
        py={py}
        px={px}
        done={done + ""}
      >
        {children}
      </TextBox>
    );
}
