import React from "react";
import { styled, Box } from "@mui/system";

const SideBarBox = styled(Box)(
  () => `
    width: 340px;
    height: 100vh;
    border-right: 2px solid #D9D9D9;
    border-left: 2px solid #D9D9D9;
    `
);

export default function SideBar({ children }) {
  return <SideBarBox>{children}</SideBarBox>;
}
