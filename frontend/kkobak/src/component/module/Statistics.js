import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Box, styled } from "@mui/system";

import Text from "component/atom/Text";
import ChallengeMap from "component/atom/ChallengeMap";
import HeartRateChart from "component/module/HeartRateChart";
import CheckDayForm from "component/module/CheckDayForm";

import { requestMyChallengeDetail } from "api/userApi";

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

const CardBox = styled(Box)(
  ({ height, margin }) => `
    width: 100%;
    height: ${getHeight(height)};
    margin: ${getMargin(margin)};
    border-radius: 20px;
    overflow: hidden;
  `
);

function getHeight(height) {
  if (!!height) return height;
  else return "40%";
}

function getMargin(margin) {
  if (margin === "center") return "auto";
  else return "40px auto 0 auto";
}

export default function Statistics() {
  const chlId = Number(useParams().chlId);
  const [detailCategoryId, setDetailCategoryId] = useState("");
  const [findTime, setFindTime] = useState(new Date());

  function requestMyChallengeDetailSuccess(res) {
    const data = res.data;
    setDetailCategoryId(data.detailCategoryId);
  }

  function requestMyChallengeDetailFail(res) {}

  useEffect(() => {
    requestMyChallengeDetail(
      chlId,
      requestMyChallengeDetailSuccess,
      requestMyChallengeDetailFail
    );
  }, []);

  return detailCategoryId === 1 || detailCategoryId === 2 ? (
    <StatisticsBox>
      <CardBox height="250px">
        <ChallengeMap findTime={findTime} setFindTime={setFindTime} />
      </CardBox>
      <CardBox>
        <HeartRateChart findTime={findTime} setFindTime={setFindTime} />
      </CardBox>
    </StatisticsBox>
  ) : detailCategoryId === 3 ? (
    <StatisticsBox>
      <CardBox margin="center">
        <HeartRateChart findTime={findTime} setFindTime={setFindTime} />
      </CardBox>
    </StatisticsBox>
  ) : (
    <StatisticsBox>
      <CardBox height="250px">
        <ChallengeMap findTime={findTime} setFindTime={setFindTime} />
      </CardBox>
      <CardBox>
        <Text>{"출석 정보 통계 들어갈 곳"}</Text>
      </CardBox>
    </StatisticsBox>
  );
}
