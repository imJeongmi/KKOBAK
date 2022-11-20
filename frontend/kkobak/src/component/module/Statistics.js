import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Box, styled } from "@mui/system";

import Text from "component/atom/Text";
import ChallengeMap from "component/atom/ChallengeMap";
import ChallengeAppearMap from "component/atom/ChallengeAppearMap";
import HeartRateChart from "component/module/HeartRateChart";
import BarChart from "component/module/BarChart";
import MedBarChart from "component/module/MedBarChart";
import HabitBarChart from "component/module/HabitBarChart";
import AppearBarChart from "component/module/AppearBarChart";
import CheckDayForm from "component/module/CheckDayForm";

import { requestMyChallengeDetail } from "api/userApi";

import DatePicker from "react-date-picker";
import "component/atom/DatePicker.scss";

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
  if (margin === 0) return "0 auto";
  else return "10px auto 20px auto";
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

  return (
    <Box sx={{ position: "relative" }}>
      <Box
        sx={{
          position: "absolute",
          top: "10px",
          right: "0",
          zIndex: "100",
        }}
      >
        <DatePicker
          calendarAriaLabel="calendar"
          locale="ko-KR"
          onChange={setFindTime}
          value={findTime}
          calendarType="US"
        />
      </Box>

      {detailCategoryId === 1 || detailCategoryId === 2 ? (
        <Box sx={{ width: "450px" }}>
          <Text weight="semibold" size="14px" mt="60">
            {"📍 GPS 정보"}
          </Text>
          <CardBox>
            <ChallengeMap
              width="300px"
              height="150px"
              findTime={findTime}
              setFindTime={setFindTime}
            />
          </CardBox>
          <Text weight="semibold" size="14px" mt="20">
            {"💗 심박수 정보"}
          </Text>
          <Box sx={{ float: "left" }}>
            <Text size="8px">[시간 / 심박수]</Text>
          </Box>
          <CardBox height="130px">
            <HeartRateChart findTime={findTime} setFindTime={setFindTime} />
          </CardBox>
          <Text weight="semibold" size="14px" mt="15">
            {"📊 전체 통계 조회"}
          </Text>
          <Box sx={{ float: "left" }}>
            <Text size="8px">[요일 / 거리(m)]</Text>
          </Box>
          <CardBox height="110px">
            <BarChart findTime={findTime} setFindTime={setFindTime} />
          </CardBox>
        </Box>
      ) : detailCategoryId === 3 ? (
        <Box sx={{ width: "450px" }}>
          <Text weight="semibold" size="14px" mt="130">
            {"💗 심박수 정보"}
          </Text>
          <Box sx={{ float: "left" }}>
            <Text size="8px">[시간 / 심박수]</Text>
          </Box>
          <CardBox height="130px">
            <HeartRateChart findTime={findTime} setFindTime={setFindTime} />
          </CardBox>
          <Text weight="semibold" size="14px" mt="70">
            {"📊 전체 통계 조회"}
          </Text>
          <Box sx={{ float: "left" }}>
            <Text size="8px">[날짜 / 진행시간(분)]</Text>
          </Box>
          <CardBox height="110px">
            <MedBarChart findTime={findTime} setFindTime={setFindTime} />
          </CardBox>
        </Box>
      ) : detailCategoryId === 7 ? (
        <Box sx={{ width: "450px" }}>
          <Text weight="semibold" size="14px" mt="70">
            {"📍 GPS 정보"}
          </Text>
          <CardBox>
            <ChallengeAppearMap
              width="300px"
              height="150px"
              findTime={findTime}
              setFindTime={setFindTime}
            />
          </CardBox>
          <Text weight="semibold" size="14px" mt="100">
            {"📊 전체 통계 조회"}
          </Text>
          <Box sx={{ float: "left" }}>
            <Text size="8px">[날짜 / 출석(횟수)]</Text>
          </Box>
          <CardBox height="110px">
            <AppearBarChart findTime={findTime} setFindTime={setFindTime} />
          </CardBox>
        </Box>
      ) : (
        // <StatisticsBox>
        //   <Box
        //     sx={{
        //       width: "400px",
        //       height: "300px",
        //       display: "flex",
        //       alignItems: "center",
        //     }}
        //   >
        //     <ChallengeAppearMap
        //       width="400px"
        //       height="300px"
        //       findTime={findTime}
        //       setFindTime={setFindTime}
        //     />
        //   </Box>
        //   <CardBox>
        //   </CardBox>
        // </StatisticsBox>
        <Box sx={{ width: "450px" }}>
          <CardBox height="50px"></CardBox>
          <Text weight="semibold" size="14px" mt="100">
            {"📊 전체 통계 조회"}
          </Text>
          <Box sx={{ float: "left" }}>
            <Text size="8px">[날짜 / 횟수]</Text>
          </Box>
          <CardBox height="250px">
            <HabitBarChart findTime={findTime} setFindTime={setFindTime} />
          </CardBox>
        </Box>
      )}
    </Box>
  );
}
