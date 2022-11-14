import React from "react";
import { styled } from "@mui/system";
import { Avatar } from "@mui/material";

function importAllImgs(r) {
  return r.keys().map(r);
}

const ProfileImgs = importAllImgs(require.context("static/avatars", false, /\.(png)$/));

const Image = styled(Avatar)(
  ({ type, mb }) => `
  margin: auto;
  width: ${getSizeByType(type)};
  height: ${getSizeByType(type)};
  border-radius: 50%
  margin-bottom: ${getMarginY(mb)};
`,
);


function getSizeByType(type) {
  switch (type) {
    case "big":
      return "150px";
    case "small":
      return "30px";
    default:
      return "45px";
  }
}

function getMarginY(mb) {
  if (!mb) {
    return "auto";
  } else {
    return mb;
  }
}

export default function ProfileImage({ type, mb, src, num }) {
  if (num >= 0) {
    return <Image type={type} src={ProfileImgs[num]} mb={mb}></Image>;
    // } else if (!!src) {
    //   return <Image type={type} src={src} mb={mb}></Image>;
    // }
  } else {
    return <Image type={type} src={src} mb={mb}></Image>;
  }
}
