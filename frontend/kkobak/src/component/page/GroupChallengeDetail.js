import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import { Box } from "@mui/system";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";

import MainBox from "component/atom/MainBox";
import SideBar from "component/atom/SideBar";
import Text from "component/atom/Text";
import Button from "component/atom/TextButton";

import { useNavigate } from "react-router-dom";

import {
  getChallengeDetail,
  requestChallengeParticipate,
  requestChallengeUserList,
  requestParticipateCheck,
} from "api/Challenge";
import { styled } from "@mui/material";

const CardBox = styled(Box)(
  () => `
  background-color: #ffffff;
  width: 480px;
  height: 90%;
  border-radius: 20px;
  margin: 0 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  `
);

const ImageBox = styled(Box)(
  () => `
  width: 230px;
  height: 140px;
  margin: 40px auto 15px auto;
  border-radius: 10px;
  background-size: cover;
  overflow: hidden;
  `
);

const SettingBox = styled(Box)(
  () => `
  width: 70%;
  height: 50%;
  margin: 40px;
  display: flex;
  flex-direction: column;
  align-items: start;
  // background-color: skyblue;
  `
);

const SettingItem = styled(Box)(
  () => `
  width: 100%;
  height: 40px;
  // margin: 5px auto;
  display: flex;
  `
);

const SettingTitleBox = styled(Box)(
  (height) => `
  width: 130px;
  height: 100%;
  display: flex;
  justify-content: start;
  align-items: center;
  `
);

const SettingContentBox = styled(Box)(
  (height) => `
  width: 200px;
  height: 100%;
  display: flex;
  justify-content: start;
  align-items: center;
  `
);

function getCategory(categoryId) {
  switch (categoryId) {
    case 1:
      return "운동";
    case 2:
      return "생활습관";
    case 3:
      return "기타";
  }
}

function getDetailCategory(detailCategoryId) {
  switch (detailCategoryId) {
    case 1:
      return "달리기";
    case 2:
      return "걷기";
    case 3:
      return "명상";
    case 4:
      return "물 마시기";
    case 5:
      return "영양제 먹기";
    case 6:
      return "일어서기";
    case 7:
      return "출석";
    default:
      return "기타";
  }
}

export default function ChallengeDetail() {
  const chlId = Number(useParams().chlId);

  const navigate = useNavigate();

  const [imgurl, setImgurl] = useState("");
  const [title, setTitle] = useState("");
  const [contents, setContents] = useState("");
  const [categoryId, setCategoryId] = useState("");
  const [detailCategoryId, setDetailCategoryId] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [alarm, setAlarm] = useState("");
  const [watch, setWatch] = useState("");
  const [goal, setGoal] = useState("");
  const [unit, setUnit] = useState("");

  function getChallengeDetailSuccess(res) {
    const data = res.data;
    setImgurl(data.imgurl);
    setTitle(data.title);
    setContents(data.contents);
    setCategoryId(data.categoryId);
    setDetailCategoryId(data.detailCategoryId);
    setStartTime(data.startTime);
    setEndTime(data.endTime);
    setAlarm(data.alarm);
    setWatch(data.watch);
    setGoal(data.goal);
    setUnit(data.unit);
  }

  function getChallengeDetailFail(res) {}

  useEffect(() => {
    getChallengeDetail(
      chlId,
      getChallengeDetailSuccess,
      getChallengeDetailFail
    );
  }, []);

  const [check, setCheck] = useState([]);

  function requestParticipateCheckSuccess(res) {
    setCheck(res.data);
  }

  function requestParticipateCheckFail(res) {
    setCheck([]);
  }

  useEffect(() => {
    requestParticipateCheck(
      chlId,
      requestParticipateCheckSuccess,
      requestParticipateCheckFail
    );
  }, []);

  const [userList, setUserList] = useState([]);

  function requestChallengeUserListSuccess(res) {
    setUserList(res.data);
  }

  function requestChallengeUserListFail(res) {
    setUserList([]);
  }

  useEffect(() => {
    requestChallengeUserList(
      chlId,
      requestChallengeUserListSuccess,
      requestChallengeUserListFail
    );
  }, []);

  const [participate, setParticipate] = useState([]);

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
    {
      if (check) {
        return <Box>현재 챌린지 참여 중입니다.</Box>;
      } else {
        return <Box>단체 챌린지 참여하기</Box>;
      }
    }
  }

  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "space-between",
      }}
    >
      <Box sx={{ margin: "0 auto" }}>
        <Box sx={{ display: "flex" }}>
          <Text size="m" weight="bold" mt="30" my="15" color="blue">
            단체 챌린지 상세 화면
          </Text>
        </Box>
        <MainBox width="75" flexDir="row" justifyContent="center">
          <CardBox>
            <ImageBox>
              {<img src={imgurl} alt="img" width="100%" height="100%" />}
            </ImageBox>
            <Text size="18px" weight="semibold">
              {title}
            </Text>
            <Text size="12px" color="grey" mt="8">
              {contents}
            </Text>

            <SettingBox>
              <SettingItem>
                <SettingTitleBox>
                  <Text size="13px" weight="semibold">
                    카테고리
                  </Text>
                </SettingTitleBox>
                <SettingContentBox>
                  <Text size="13px" color="grey">
                    {`${getCategory(categoryId)}`}
                  </Text>
                </SettingContentBox>
              </SettingItem>

              <SettingItem>
                <SettingTitleBox>
                  <Text size="13px" weight="semibold">
                    상세 카테고리
                  </Text>
                </SettingTitleBox>
                <SettingContentBox>
                  <Text size="13px" color="grey">
                    {`${getDetailCategory(detailCategoryId)}`}
                  </Text>
                </SettingContentBox>
              </SettingItem>

              <SettingItem>
                <SettingTitleBox>
                  <Text size="13px" weight="semibold">
                    기간
                  </Text>
                </SettingTitleBox>
                <SettingContentBox>
                  <Text size="13px" color="grey">
                    {startTime.substr(0, 10)} ~ {endTime.substr(0, 10)}
                  </Text>
                </SettingContentBox>
              </SettingItem>

              <SettingItem>
                <SettingTitleBox>
                  <Text size="13px" weight="semibold">
                    알림
                  </Text>
                </SettingTitleBox>
                <SettingContentBox>
                  <Text size="13px" color="grey">
                    {alarm}
                  </Text>
                </SettingContentBox>
              </SettingItem>

              <SettingItem>
                <SettingTitleBox>
                  <Text size="13px" weight="semibold">
                    목표
                  </Text>
                </SettingTitleBox>
                <SettingContentBox>
                  <Text size="13px" color="grey">
                    {`${goal} ${unit}`}
                  </Text>
                </SettingContentBox>
              </SettingItem>

              <SettingItem>
                <SettingTitleBox>
                  <Text size="13px" weight="semibold">
                    워치 사용
                  </Text>
                </SettingTitleBox>
                <SettingContentBox>
                  <Box>
                    <RadioGroup
                      row
                      aria-labelledby="demo-row-controlled-radio-buttons-group"
                      name="controlled-radio-buttons-group"
                      value={watch}
                    >
                      <FormControlLabel
                        className="FormControlLabel"
                        value="true"
                        control={<Radio />}
                        label="사용"
                      />
                      <FormControlLabel
                        value="false"
                        control={<Radio />}
                        label="사용 안함"
                      />
                    </RadioGroup>
                  </Box>
                </SettingContentBox>
              </SettingItem>
            </SettingBox>
          </CardBox>
          <CardBox>
            <Box> 참가 인원 </Box>
            <Box>--------------</Box>
            <Box>
              {userList.map((item) => {
                return (
                  <Box>
                    <Text>닉네임 : {item.nickname}</Text>
                    <Text>
                      프로필이미지 :
                      {
                        <img
                          src={item.imgurl}
                          alt="img"
                          width="100%"
                          height="100%"
                        />
                      }
                    </Text>
                    <Text>성공률 : {item.sucRatio}</Text>
                    <Text>성공 횟수 : {item.sucCnt}</Text>
                    <Text>실패 횟수 : {item.failCnt}</Text>
                    <Box>--------------</Box>
                  </Box>
                );
              })}
            </Box>
            <Button size="m" my="0" onClick={moveToStart} disabled={check}>
              <Comment></Comment>
            </Button>
          </CardBox>
        </MainBox>
      </Box>
      <SideBar />
    </Box>
  );
}
