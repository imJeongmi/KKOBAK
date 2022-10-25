import React from 'react';
import { styled, Box } from "@mui/system"

const SideBarBox = styled(Box)(
    () => `
    width: 340px;
    height: 900px;
    border-right: 2px solid #D9D9D9;
    `
)

export default function SideBar() {
    return (
        <SideBarBox />
    );
}