import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Box, styled } from "@mui/system";

import Text from "component/atom/Text";

import RankingList from "component/atom/RankList";
import Ranking from "component/atom/Ranking";
import { requestRanking } from "api/Challenge";

const StatisticsBox = styled(Box)(
  () => `
  width: 90%;
  height: 100%;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  `
);

const ListBox = styled(Box)(
  () => `
  width: 100%;
  height: 80%;
  margin: 20px auto 0 auto;
  overflow-y: auto;
  scrollbar-width: none;
  overflow-x: none;
  `
);

export default function GroupStatistics() {
  const chlId = Number(useParams().chlId);
  const [rankingList, setRankingList] = useState([]);

  useEffect(() => {
    requestRanking(chlId, requestRankingSuccess, requestRankingFail);
  }, [chlId]);

  function requestRankingSuccess(res) {
    setRankingList(res.data);
  }

  function requestRankingFail() {}

  return rankingList.length === 5 ? (
    <StatisticsBox>
      <Text mt="40" weight="semibold" size="18px">
        오늘의 순위
      </Text>
      <ListBox>
        <Ranking topThreeList={rankingList.slice(0, 3)} />
        <RankingList
          rankNum={1}
          userName={rankingList[0]?.nickname}
          speed={rankingList[0]?.avg_speed.toFixed(2)}
          num={rankingList[0]?.imgurl}
        ></RankingList>
        <RankingList
          rankNum={2}
          userName={rankingList[1]?.nickname}
          speed={rankingList[1]?.avg_speed.toFixed(2)}
          num={rankingList[1]?.imgurl}
        ></RankingList>
        <RankingList
          rankNum={3}
          userName={rankingList[2]?.nickname}
          speed={rankingList[2]?.avg_speed.toFixed(2)}
          num={rankingList[2]?.imgurl}
        ></RankingList>
        <RankingList
          rankNum={4}
          userName={rankingList[3]?.nickname}
          speed={rankingList[3]?.avg_speed.toFixed(2)}
          num={rankingList[3]?.imgurl}
        ></RankingList>
        <RankingList
          rankNum={5}
          userName={rankingList[4]?.nickname}
          speed={rankingList[4]?.avg_speed.toFixed(2)}
          num={rankingList[4]?.imgurl}
        ></RankingList>
      </ListBox>
    </StatisticsBox>
  ) : (
    <Box>
      <Text mt="270" weight="semibold" size="18px">
        참가 인원이 부족합니다
      </Text>
      <Text weight="semibold" size="18px">
        챌린지 참여 인원을 더 모아볼까요?
      </Text>
    </Box>
  );
}
