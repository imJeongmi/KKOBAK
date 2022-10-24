import React from "react";

const ImageButtonBase = styled(Button)(
  ({ size, variant, color }) => `
  margin: 3px;
  width: ${getWidthHeight(size)};
  height: ${getWidthHeight(size)};
  background-color: ${getBackgroundColor(variant)};
  &: hover{
    background-color: ${getHoverColor(variant)};
  };
  text-align: center;
  font-family: "gmarketSansMedium"
  `,
);

function getWidthHeight(size) {
  switch (size) {
    case "l":
      return "18vw";
    case "m":
      return "3.5vw";
    case "s":
      return "2vw";
    default:
      return "3.5vw";
  }
}


function getIcon(icon) {
  switch (icon) {
    case "water":
      return WaterIcon;
    default:
      return CameraIcon;
  }
}

export default function ImageButton({ size }) {
  return (
    <ImageButtonBase size={size} onClick={onClick}>
      <img src={getIcon(icon)}></img>
    </ImageButtonBase>
  )
}