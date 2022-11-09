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

import { requestUserInfo, requestMyChallengeDetail } from "api/userApi";
import { styled } from "@mui/material";

const CardStyle = {
  width: "70%",
  minHeight: "50vh",
  margin: "auto",
  backgroundColor: "white",
  borderRadius: 2,
};

const ImageStyle = {
  width: "50%",
  height: "25vh",
  margin: "25px auto 15px auto",
  borderRadius: 2,
  backgroundSize: "cover",
  backgroundColor: "grey",
  overflow: "hidden",
};

const SettingTitleBox = styled(Box)(
  () => `
  width: 100px;
  display: flex;
  flex-direction: column;
  align-items: start;
  `
);

const ButtonBox = styled(Box)(
  () => `
  width: 95%;
  display: flex;
  justify-content: end;
  `
);

export default function ChallengeDetail() {
  const [user, setUser] = useState([]);
  const chlId = Number(useParams().chlId);

  const [imgurl, setImgurl] = useState("");
  const [title, setTitle] = useState("");
  const [contents, setContents] = useState("");
  const [categoryId, setCategoryId] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [alarm, setAlarm] = useState("");
  const [watch, setWatch] = useState("");
  const [kkobakChallenge, setKkobakChallenge] = useState("");
  const [detailCategoryId, setDetailCategoryId] = useState("");

  const navigate = useNavigate();

  function requestUserInfoSuccess(res) {
    setUser(res.data);
  }

  function requestUserInfoFail(res) {
    setUser([]);
  }

  function requestMyChallengeDetailSuccess(res) {
    setImgurl(res.data.imgurl);
    setTitle(res.data.title);
    setContents(res.data.contents);
    setCategoryId(res.data.categoryId);
    setStartTime(res.data.startTime);
    setEndTime(res.data.endTime);
    setAlarm(res.data.alarm);
    setWatch(res.data.watch);
    setKkobakChallenge(res.data.kkobak);
    setDetailCategoryId(res.data.detailCategoryId);
  }

  function requestMyChallengeDetailFail(res) {}

  useEffect(() => {
    requestUserInfo(requestUserInfoSuccess, requestUserInfoFail);
    requestMyChallengeDetail(
      chlId,
      requestMyChallengeDetailSuccess,
      requestMyChallengeDetailFail
    );
  }, []);

  function moveToStats(e) {
    e.preventDefault();
    navigate(`/Statistics/${chlId}`);
  }

  return detailCategoryId === 6 ||
    detailCategoryId === 5 ||
    detailCategoryId === 4 ? (
    <Box
      sx={{
        display: "flex",
        justifyContent: "space-between",
      }}
    >
      <Box sx={{ margin: "0 auto" }}>
        <Text size="l" weight="bold" mt="30" my="15">
          {user.nickName}님의 챌린지리스트
        </Text>
        <MainBox flexDir="col">
          <Box
            sx={{
              width: "100%",
              minHeight: "80vh",
              display: "flex",
              flexWrap: "wrap",
              justifyContent: "center",
            }}
          >
            <Box sx={CardStyle}>
              <Box sx={ImageStyle}>
                {<img src={imgurl} alt="img" width="100%" height="100%" />}
              </Box>
              <Text size="20px" weight="bold" mt="10">
                {title}
              </Text>
              <Box sx={{}}>
                <Text size="13px" color="grey" my="10">
                  {contents}
                </Text>
              </Box>

              {/* 스크롤 */}
              <Box sx={{ display: "flex" }}>
                <Box
                  sx={{
                    width: "100px",
                    my: "30px",
                    mx: "80px",
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "start",
                  }}
                >
                  <Text size="14px" weight="semibold" my="10">
                    카테고리
                  </Text>
                  <Text size="14px" weight="semibold" my="10">
                    기간
                  </Text>
                  <Text size="14px" weight="semibold" my="10">
                    알림
                  </Text>
                  <Text size="14px" weight="semibold" my="12">
                    워치 사용
                  </Text>
                  <Text size="14px" weight="semibold" my="12">
                    꼬박챌린지 설정
                  </Text>
                </Box>

                <Box
                  sx={{
                    width: "400px",
                    my: "30px",
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "start",
                  }}
                >
                  <Text size="14px" color="grey" my="10" mx="10">
                    {categoryId}
                  </Text>

                  <Text size="14px" color="grey" my="10" mx="10">
                    {startTime.substr(0, 10)} - {endTime.substr(0, 10)}
                  </Text>
                  <Text size="14px" color="grey" my="10" mx="10">
                    {alarm}
                  </Text>
                  <Box sx={{ mx: "8px" }}>
                    <RadioGroup
                      row
                      aria-labelledby="demo-row-controlled-radio-buttons-group"
                      name="controlled-radio-buttons-group"
                      value={watch}
                    >
                      <FormControlLabel
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
                  <Box sx={{ mx: "8px" }}>
                    <RadioGroup
                      row
                      aria-labelledby="demo-row-controlled-radio-buttons-group"
                      name="controlled-radio-buttons-group"
                      value={kkobakChallenge}
                    >
                      <FormControlLabel
                        value="true"
                        control={<Radio />}
                        label="설정"
                      />
                      <FormControlLabel
                        value="false"
                        control={<Radio />}
                        label="설정 안함"
                      />
                    </RadioGroup>
                  </Box>
                </Box>
              </Box>
            </Box>
          </Box>
        </MainBox>
      </Box>
      <SideBar />
    </Box>
  ) : (
    <Box
      sx={{
        display: "flex",
        justifyContent: "space-between",
      }}
    >
      <Box sx={{ margin: "0 auto" }}>
        <Text size="l" weight="bold" mt="30" my="15">
          {user.nickName}님의 챌린지리스트
        </Text>
        <MainBox flexDir="col">
          <Box
            sx={{
              width: "100%",
              minHeight: "80vh",
              display: "flex",
              flexWrap: "wrap",
              justifyContent: "center",
            }}
          >
            <Box sx={CardStyle}>
              <Box sx={ImageStyle}>
                {<img src={imgurl} alt="img" width="100%" height="100%" />}
              </Box>
              <Text size="20px" weight="bold" mt="10">
                {title}
              </Text>
              <Box sx={{}}>
                <Text size="13px" color="grey" my="10">
                  {contents}
                </Text>
              </Box>

              {/* 스크롤 */}
              <Box sx={{ display: "flex" }}>
                <Box
                  sx={{
                    width: "100px",
                    my: "30px",
                    mx: "80px",
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "start",
                  }}
                >
                  <Text size="14px" weight="semibold" my="10">
                    상세 카테고리
                  </Text>
                  <Text size="14px" weight="semibold" my="10">
                    기간
                  </Text>
                  <Text size="14px" weight="semibold" my="10">
                    알림
                  </Text>
                  <Text size="14px" weight="semibold" my="12">
                    워치 사용
                  </Text>
                  <Text size="14px" weight="semibold" my="12">
                    꼬박챌린지 설정
                  </Text>
                </Box>

                <Box
                  sx={{
                    width: "400px",
                    my: "30px",
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "start",
                  }}
                >                  
                  <Text size="14px" color="grey" my="10" mx="10">
                    {detailCategoryId}
                  </Text>
                  <Text size="14px" color="grey" my="10" mx="10">
                    {startTime.substr(0, 10)} - {endTime.substr(0, 10)}
                  </Text>
                  <Text size="14px" color="grey" my="10" mx="10">
                    {alarm}
                  </Text>
                  <Box sx={{ mx: "8px" }}>
                    <RadioGroup
                      row
                      aria-labelledby="demo-row-controlled-radio-buttons-group"
                      name="controlled-radio-buttons-group"
                      value={watch}
                    >
                      <FormControlLabel
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
                  <Box sx={{ mx: "8px" }}>
                    <RadioGroup
                      row
                      aria-labelledby="demo-row-controlled-radio-buttons-group"
                      name="controlled-radio-buttons-group"
                      value={kkobakChallenge}
                    >
                      <FormControlLabel
                        value={1}
                        control={<Radio />}
                        label="설정"
                      />
                      <FormControlLabel
                        value={0}
                        control={<Radio />}
                        label="설정 안함"
                      />
                    </RadioGroup>
                  </Box>
                </Box>
              </Box>
            </Box>
          </Box>
          <ButtonBox>
            <Button size="ss" my="0" onClick={moveToStats}>
              통계 확인
            </Button>
          </ButtonBox>
        </MainBox>
      </Box>
      <SideBar />
    </Box>
  );
}
