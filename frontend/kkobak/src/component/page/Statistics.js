import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import Box from "@mui/material/Box";
import styled from "@emotion/styled";
import moment from "moment";

import Text from "component/atom/Text";
import MainBox from "component/atom/MainBox";
import SideBar from "component/atom/SideBar";
import ChallengeMap from "component/atom/ChallengeMap";
import HeartRateChart from "component/module/HeartRateChart";
import CheckDayForm from "component/module/CheckDayForm";

import { requestMyChallengeDetail } from "api/userApi";

function getHeight(height) {
  if (!!height) return height;
  else return "200px";
}

const CardBox = styled(Box)(
  (height) => `
    width: 85%;
    height: ${getHeight(height)};
    margin: 20px auto;
    background-color: white;
    border-radius: 20px;
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;

  `
);

export default function Statistics() {
  const chlId = Number(useParams().chlId);
  const [title, setTitle] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [detailCategoryId, setDetailCategoryId] = useState("");
  const [findTime, setFindTime] = useState(new Date());

  function requestMyChallengeDetailSuccess(res) {
    const data = res.data;
    setTitle(data.title);
    setStartTime(moment(data.startTime).format("YYYY/MM/DD"));
    setEndTime(moment(data.endTime).format("YYYY/MM/DD"));
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
    <Box
      sx={{
        display: "flex",
        justifyContent: "space-between",
      }}
    >
      <Box sx={{ margin: "0 auto" }}>
        <Box sx={{ display: "flex" }}>
          <Text size="l" weight="bold" mt="30" my="15" color="blue">
            {`${title}` + "\u00A0"}
          </Text>
          <Text size="l" weight="bold" mt="30" my="15">
            {"챌린지"}
          </Text>
        </Box>
        <MainBox>
          <Box
            sx={{
              width: "100%",
              minHeight: "80vh",
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
              flexWrap: "wrap",
            }}
          >
            <Text weight="bold" size="18px" mt="30px" my="5px">
              {`${startTime} - ${endTime}`}
            </Text>

            {detailCategoryId === 1 || detailCategoryId === 2 ? (
              <Box
                sx={{
                  width: "95%",
                }}
              >
                <CardBox height="350px">
                  <ChallengeMap findTime={findTime} setFindTime={setFindTime} />
                </CardBox>
                <CardBox>
                  <HeartRateChart
                    findTime={findTime}
                    setFindTime={setFindTime}
                  />
                </CardBox>
              </Box>
            ) : detailCategoryId === 3 ? (
              <Box
                sx={{
                  width: "100%",
                  marginY: "100px",
                }}
              >
                <CardBox height="300px">
                  <HeartRateChart
                    findTime={findTime}
                    setFindTime={setFindTime}
                  />
                </CardBox>
              </Box>
            ) : (
              <Box
                sx={{
                  width: "95%",
                }}
              >
                <CardBox height="350px">
                  <ChallengeMap findTime={findTime} setFindTime={setFindTime} />
                </CardBox>
                <CardBox height="200px">
                  <Text>{"출석 정보 통계 들어갈 곳"}</Text>
                </CardBox>
              </Box>
            )}
          </Box>
        </MainBox>
      </Box>
      <SideBar>
        <Box
          sx={{
            width: "100%",
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Text size="18px" weight="semibold" my="70px">
            {"해당 날짜 챌린지 조회하기"}
          </Text>
        </Box>
        <Box sx={{ paddingLeft: "55px" }}>
          <CheckDayForm findTime={findTime} setFindTime={setFindTime} />
        </Box>
      </SideBar>
    </Box>
  );
}
