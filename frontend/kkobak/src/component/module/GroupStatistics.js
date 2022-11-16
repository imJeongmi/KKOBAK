import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Box, styled } from "@mui/system";

import Text from "component/atom/Text";
import Button from "component/atom/TextButton";
import ProfileImage from "component/atom/ProfileImage";

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
  height: 380px;
  margin: 20px auto 0 auto;
  overflow-x: none;
  overflow-y: auto;
  scrollbar-width: none;
  `
);

const CardBox = styled(Box)(
  ({ margin }) => `
    width: 70%;
    height: 50px;
    margin: ${getMargin(margin)};
    border-radius: 10px;
    display: flex;
    align-items: center;
    background-color: #e7f0fa;
  `
);

const ImageBox = styled(Box)(
  () => `
  width: 40px;
  height: 40px;
  margin: auto 40px auto 20px;
  // background-color: #ffffff;
  border-radius: 100%;
  overflow: hidden;
  display: flex;
  align-items: center;
  `
);

function getMargin(margin) {
  if (margin === "center") return "auto";
  else return "10px auto 15px auto";
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
    // navigate("/myChallenge");
    window.location.reload();
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
        {"참여자 리스트"}
      </Text>
      <ListBox>
        {userList.map((item) => {
          return (
            <CardBox>
              <ImageBox>
                <ProfileImage type="small" num={Number(item.imgurl)}></ProfileImage>
              </ImageBox>
              <Box sx={{ width: "100px", textAlign: "center" }}>
                <Text size="14px" weight="semibold">
                  {item.nickname}
                </Text>
              </Box>
            </CardBox>
          );
        })}
      </ListBox>
      <Button size="m" my="15px" onClick={moveToStart} disabled={check}>
        <Comment />
      </Button>
    </StatisticsBox>
  );
}
