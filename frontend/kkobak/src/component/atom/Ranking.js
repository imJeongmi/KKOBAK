import styled from "@emotion/styled";
import { Box } from "@mui/system";
import React from "react";
import ProfileImage from "./ProfileImage";
import Text from "./Text";

const AwardsBox = styled(Box)(
  () => `
  width: 30vw ;
  margin-y: 2vh;
  display: flex;
  justify-content: space-around;
  `,
);

export default function Ranking() {
  // const [topThreeList, setTopThreeList] = useState([]);
  const topThreeList = [
    { nickname: "경원", imgurl: "3" },
    { nickname: "정미", imgurl: "4" },
    { nickname: "승리", imgurl: "7" },
  ];

  function getRank(rankNum) {
    switch (rankNum) {
      case 1:
        return (
          <img
            src={require("static/1st_place_medal.svg").default}
            width="60px"
            alt="gold medal"
          />
        );
      case 2:
        return (
          <img
            src={require("static/2nd_place_medal.svg").default}
            width="50px"
            alt="silver medal"
          />
        );
      case 3:
        return (
          <img
            src={require("static/3rd_place_medal.svg").default}
            width="50px"
            alt="bronze medal"
          />
        );
      default:
        return rankNum;
    }
  }

  function AwardsList({ rankNum, playerName, imgNum }) {
    return (
      <Box
        sx={{ display: "flex", flexDirection: "column", alignItems: "center" }}
      >
        {rankNum === 1 ? (
          <ProfileImage type="m" mb="5vh" num={imgNum} />
        ) : (
          <ProfileImage mb="10vh" num={imgNum} />
        )}
        <Text>{playerName}</Text>
        {getRank(rankNum)}
      </Box>
    );
  }
  return (
    <AwardsBox>
      <AwardsList
        rankNum={2}
        playerName={topThreeList[1]?.nickname}
        imgNum={topThreeList[1]?.imgurl}
      />
      <AwardsList
        rankNum={1}
        playerName={topThreeList[0]?.nickname}
        imgNum={topThreeList[0]?.imgurl}
      />
      <AwardsList
        rankNum={3}
        playerName={topThreeList[2]?.nickname}
        imgNum={topThreeList[2]?.imgurl}
      />
    </AwardsBox>
  );
}
