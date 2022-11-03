import { Box } from "@mui/system";
import React from "react";

import ArrowBackIosNewIcon from '@mui/icons-material/ArrowBackIosNew';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';

const CircleButtonBase = styled(Button)(
	() => `
  margin: 3px;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  padding-top: 100%;
  background: linear-gradient(135deg, #FFFFFF 0%, #F8F8F8 29.48%, #F3F3F3 52.92%, #EFEFEF 71.15%, #ECECEC 86.77%, #E9E9E9 100%);
  box-shadow: -5px 5px 10px rgba(207, 207, 207, 0.2), 5px -5px 10px rgba(207, 207, 207, 0.2), -5px -5px 10px rgba(255, 255, 255, 0.9), 5px 5px 13px rgba(207, 207, 207, 0.9), inset 1px 1px 2px rgba(255, 255, 255, 0.3), inset -1px -1px 2px rgba(207, 207, 207, 0.5);
  border-radius: 100px;
  &: hover{
    background-color: #d6d6d6;
  };
  text-align: center;
  `,
);

export default function CircleButton({ onClick, label }) {
	return (

		<CircleButtonBase onClick={onClick}>
			{(label === "previous" ? <ArrowBackIosNewIcon /> : <ArrowForwardIosIcon />)}
		</CircleButtonBase>
	)
}