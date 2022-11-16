import React from "react";
import { Box, styled } from "@mui/system";
import ProfileImage from "./ProfileImage";
import Text from "./Text";

import first from "static/1st-place-medal.png"
import second from "static/2nd-place-medal.png"
import third from "static/3rd-place-medal.png"

const RankingBox = styled("div")(
	() => `
    width: 80%;
    height: 50px;
    border-radius: 10px;
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.25);
    background-color: white;
    margin: 1.5vh auto;
    position: relative;
    display: flex;
    align-items: center;
    flex: 0 0 auto;
    `,
);

function getRank(rankNum) {
	switch (rankNum) {
		case 1:
			return <img src={first} width="30px" />;
		case 2:
			return <img src={second} width="30px" />;
		case 3:
			return <img src={third} width="30px" />;
		default:
			return (
				<Box sx={{ mx: 1}}>
					<Text weight="bold">{rankNum}</Text>
				</Box>
			);
	}
}

export default function RankingList({ rankNum, userName, speed }) {
	return (
		<RankingBox>
			<Box sx={{ position: "absolute", left: "5%" }}>{getRank(rankNum)}</Box>
			<Box sx={{ position: "absolute", left: "20%" }}>
				<ProfileImage />
			</Box>
			<Box sx={{ position: "absolute", left: "50%", transform: "translate(-50%, 0)" }}>
				<Text size="m">{userName}</Text>
			</Box>
			<Box sx={{ position: "absolute", right: "10%" }}>
				<Text size="s">{speed}</Text>
			</Box>
		</RankingBox>
	);
}