import React from "react";
import { styled } from "@mui/system"
import { Box } from "@mui/material"

import TutorialImage from "static/tutorial.png"

const TutorialBox = styled(Box)(
    () => `
    width: 15px;
    height: 15px;
    border-radius: 100%;
    border: 1px solid black;
    display: flex;
    align-items: center;
    justify-content: center;
    `
)

export default function TutorialButton() {
    return (
        <TutorialBox>
            <img src={TutorialImage} width="10px" />
        </TutorialBox>
    );
}