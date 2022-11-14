import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Box, styled } from "@mui/system";

import Text from "component/atom/Text";
import Button from "component/atom/TextButton";

import {
  requestChallengeParticipate,
  requestChallengeUserList,
  requestParticipateCheck,
} from "api/Challenge";

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

const CardBox = styled(Box)(
  ({ margin }) => `
    width: 90%;
    height: 85px;
    margin: ${getMargin(margin)};
    border-radius: 20px;
    display: flex;
    align-items: center;
    background-color: #e7f0fa;
  `
);

const ContentBox = styled(Box)(
  ({ width, alignItems }) => `
    width: ${getWidth(width)};
    display: flex;
    flex-direction: column;
    align-items: ${getAlignItems(alignItems)};
    justify-content: center;
  `
);

const ImageBox = styled(Box)(
  () => `
  width: 55px;
  height: 55px;
  border-radius: 100%;
  overflow: hidden;
  `
);

function getWidth(width) {
  if (!!width) return width;
  else return "50%";
}

function getMargin(margin) {
  if (margin === "center") return "auto";
  else return "0 auto 15px auto";
}

function getAlignItems(alignItems) {
  if (!!alignItems) return alignItems;
  else return "center";
}

export default function GroupStatistics() {
  const chlId = Number(useParams().chlId);

  const [check, setCheck] = useState([]);
  const [userList, setUserList] = useState([]);
  const [participate, setParticipate] = useState([]);

  function requestParticipateCheckSuccess(res) {
    setCheck(res.data);
  }

  function requestParticipateCheckFail(res) {
    setCheck([]);
  }

  function requestChallengeUserListSuccess(res) {
    setUserList(res.data);
  }

  function requestChallengeUserListFail(res) {
    setUserList([]);
  }

  function requestChallengeParticipateSuccess(res) {
    setParticipate(res.data);
  }

  function requestChallengeParticipateFail(err) {
    setParticipate([]);
    alert("해당 챌린지에 참여 중입니다.");
  }

  function moveToStart(e) {
    e.preventDefault();
    requestChallengeParticipate(
      chlId,
      requestChallengeParticipateSuccess,
      requestChallengeParticipateFail
    );
  }

  function Comment() {
    if (check) return "현재 챌린지 참여 중입니다.";
    else return "단체 챌린지 참여하기";
  }

  useEffect(() => {
    requestParticipateCheck(
      chlId,
      requestParticipateCheckSuccess,
      requestParticipateCheckFail
    );
    requestChallengeUserList(
      chlId,
      requestChallengeUserListSuccess,
      requestChallengeUserListFail
    );
  }, []);

  return (
    <StatisticsBox>
      <Text mt="40" weight="semibold" size="18px">
        단체 랭킹 조회
      </Text>
      <ListBox>
        <Text>가장 빠르게 달린 유저 랭킹 1,2,3위</Text>
        <Text>누적 가장 많이 이동한 유저 랭킹 1,2,3위</Text>
      </ListBox>
    </StatisticsBox>
  );
}
