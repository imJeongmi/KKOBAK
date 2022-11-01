import React from "react";
import "./SideBarChallengeCreateTest1.css";
import { styled, Box } from "@mui/system";

const SideBarBox = styled(Box)(
  () => `
    width: 340px;
    height: 100vh;
    border-right: 2px solid #D9D9D9;
    border-left: 2px solid #D9D9D9;
    `
);

export default function SideBar() {
  return (
    <SideBarBox>
      <div class="container" style={{ marginLeft: "600px" }}>
        <div class="circle"></div>
        <div class="square"></div>
        <div class="triangle"></div>
        <div class="logo">Creating..</div>
        <div class="shadow"></div>
      </div>
    </SideBarBox>
  );
}
