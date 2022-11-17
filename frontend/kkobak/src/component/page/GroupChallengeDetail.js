import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";

import { Box } from "@mui/system";
import { styled } from "@mui/material";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";

import MainBox from "component/atom/MainBox";
import Text from "component/atom/Text";
import GroupStatistics from "component/module/GroupStatistics";
import WidgetGroupStat from "component/module/WidgetGroupStat";
import Button from "@mui/material/Button";
import CloseIcon from "@mui/icons-material/Close";

import { getChallengeDetail } from "api/Challenge";
import { requestUserInfo } from "api/userApi";

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
  width: 150px;
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
    default:
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
  const [user, setUser] = useState([]);

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

  const navigate = useNavigate();

  function backPage() {
    navigate(`/`);
  }

  function requestUserInfoSuccess(res) {
    setUser(res.data);
  }

  function requestUserInfoFail(res) {
    setUser([]);
  }

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

  function getChallengeDetailFail(res) { }

  useEffect(() => {
    requestUserInfo(requestUserInfoSuccess, requestUserInfoFail);
    getChallengeDetail(
      chlId,
      getChallengeDetailSuccess,
      getChallengeDetailFail
    );
  }, []);

  function checkDetailCategory(detailCategoryId) {
    if (detailCategoryId === 1) {
      return true;
    } else if (detailCategoryId === 2) {
      return true;
    }
    return false;
  }

  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "space-between",
      }}
    >
      <Box sx={{ margin: "0 auto" }}>
        <Box sx={{ display: "flex", alignItems: "end" }}>
          <Text size="m" weight="bold" mt="30" my="15" color="blue">
              {title}
          </Text>
          <Text size="17px" weight="bold" mt="30" my="15">
            {`\u00A0단체 현황`}
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
                    {"🔎\u00A0\u00A0 카테고리"}
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
                    {"🔖\u00A0\u00A0 상세 카테고리"}
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
                    {"📅\u00A0\u00A0 기간"}
                  </Text>
                </SettingTitleBox>
                <SettingContentBox>
                  <Text size="13px" color="grey">
                    {startTime.substr(0, 10).replace(/-/gi, '.')} ~ {endTime.substr(0, 10).replace(/-/gi, '.')}
                  </Text>
                </SettingContentBox>
              </SettingItem>

              <SettingItem>
                <SettingTitleBox>
                  <Text size="13px" weight="semibold">
                    {"🔔\u00A0\u00A0 알림"}
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
                    {"🔥\u00A0\u00A0 목표"}
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
                    {"⌚️\u00A0\u00A0 워치 사용"}
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
                        control={
                          <Radio
                            size="small"
                            sx={{
                              color: "default",
                              "&.Mui-checked": {
                                color: "#99b9d6",
                              },
                            }}
                          />
                        }
                        label={
                          <Text size="13px" color="grey">
                            사용
                          </Text>
                        }
                      />
                      <FormControlLabel
                        value="false"
                        control={
                          <Radio
                            size="small"
                            sx={{
                              color: "default",
                              "&.Mui-checked": {
                                color: "#99b9d6",
                              },
                            }}
                          />
                        }
                        label={
                          <Text size="13px" color="grey">
                            사용 안함
                          </Text>
                        }
                      />
                    </RadioGroup>
                  </Box>
                </SettingContentBox>
              </SettingItem>
            </SettingBox>
          </CardBox>

          <Box
            sx={{
              backgroundColor: "#ffffff",
              marginTop: "10px",
              width: "480px",
              height: "90%",
              borderRadius: "20px",
              margin: "0 20px",
              display: "flex",
              flexDirection: "column",
            }}
          >
            {checkDetailCategory(detailCategoryId) ? <WidgetGroupStat /> : <GroupStatistics />}

          </Box>
          <Button
            onClick={backPage}
            sx={{
              color: "gray",
              "&.MuiButtonBase-root:hover": {
                bgcolor: "transparent",
              },
              position: "absolute",
              right: "1%",
              top: "2%",
            }}
          >
            <CloseIcon />
          </Button>
        </MainBox>
      </Box>
    </Box>
  );
}
