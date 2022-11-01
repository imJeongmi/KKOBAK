import React from "react";
import "./SideBarChallengeCreate.css";
import { styled, Box } from "@mui/system";
import char from "../../static/char.png";
import Text from "component/atom/Text";

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
      {/* <div class="container" style={{ marginLeft: "600px" }}>
        <div class="circle"></div>
        <div class="square"></div>
        <div class="triangle"></div>
        <div class="logo">Creating..</div>
        <div class="shadow"></div>
      </div> */}
      <div class="bounce" style={{ marginLeft: "130px", paddingTop: "400px" }}>
        <img src={char} style={{ width: "60px" }} />
      </div>
      <Box sx={{ marginLeft: "120px" }}>
        <Text>챌린지 생성 중..</Text>
      </Box>
    </SideBarBox>
  );
}
