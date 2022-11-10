import React, { useEffect, useRef, useState } from "react";
import { Box, styled } from "@mui/system";
import DatePicker from "react-date-picker";
import TimePicker from "react-time-picker";

import Input from "component/atom/Input";
import Text from "component/atom/Text";
import Textarea from "component/atom/Textarea";
import Button from "component/atom/TextButton";

import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormControl from "@mui/material/FormControl";

import { uploadPhoto } from "api/S3";

import "../atom/DatePicker.scss";
import "../atom/TimePicker.scss";
import "component/module/ChallengeForm.css";

const inputStyle = {
  display: "none",
};

const ImageBox = styled(Box)(
  () => `
  width: 350px;
  height: 200px;
  margin: 25px auto 10px auto;
  border-radius: 20px;
  background-size: cover;
  overflow: hidden;
  background-color: grey;
  `
);

const SettingBox = styled(Box)(
  () => `
  width: 35vw;
  display: flex;
  flex-direction: column;
  align-items: start;
  justify-content: space-between;
  `
);

const SettingItem = styled(Box)(
  () => `
  display: flex;
  `
);

const SettingTitleBox = styled(Box)(
  (height) => `
  width: 200px;
  height: 50px;
  display: flex;
  justify-content: start;
  align-items: center;
  `
);

const SettingContentBox = styled(Box)(
  (height) => `
  width: 360px;
  height: 50px;
  display: flex;
  justify-content: start;
  align-items: center;
  `
);

const ButtonBox = styled(Box)(
  () => `
  // width: 100%;
  display: flex;
  justify-content: end;
  margin-top: 10px;
  `
);

export default function ChallengeForm({
  imgSrc,
  title,
  category,
  detailCategory,
  detailCategoryList,
  contents,
  startTime,
  endTime,
  alarm,
  watch,
  goal,
  unit,
  setImgSrc,
  setTitle,
  setCategory,
  setDetailCategory,
  setContents,
  setStartTime,
  setEndTime,
  setAlarm,
  setWatch,
  setRoomtype,
  setGoal,
  changeUnit,
  setKkobak,
  register,
}) {
  const challengeImgInput = useRef();
  const [showunit, setShowUnit] = useState("");

  function changeDetailCategory(e) {
    e.preventDefault();
    setDetailCategory(e.target.value);
    changeUnit(category, e.target.value);
    showUnit(category, e.target.value);
  }
  useEffect(() => {
    if (category && detailCategory) {
      changeUnit(category, detailCategory);
    }
  }, [category, detailCategory]);

  function showUnit(category, detailCategory) {
    if (category === "2" && detailCategory === "7") {
      setShowUnit("주소");
    } else if (category === "2") {
      setShowUnit("1 회");
    } else if (category === "1" && detailCategory === "1") {
      setShowUnit("Km");
    } else if (category === "1" && detailCategory === "2") {
      setShowUnit("Km");
    } else if (category === "1" && detailCategory === "3") {
      setShowUnit("분");
    }
  }

  function onImgChange(e) {
    e.preventDefault();
    if (!e.target.files) {
      return;
    }
    const formData = new FormData();
    formData.set("file", e.target.files[0]);
    uploadPhoto(formData, uploadSuccess, uploadFail);
  }

  function uploadSuccess(res) {
    setImgSrc(res.data);
  }

  function uploadFail(err) {
    console.log(err);
  }

  const imgDivClick = (e) => {
    e.preventDefault();
    challengeImgInput.current.click();
  };

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        alignContent: "center",
      }}
    >
      <ImageBox>
        <input
          style={inputStyle}
          id="challengeImg"
          ref={challengeImgInput}
          type="file"
          accept="image/*"
          onChange={onImgChange}
        />
        <img
          onClick={imgDivClick}
          alt="img"
          src={imgSrc}
          width="100%"
          height="100%"
        ></img>
      </ImageBox>

      <SettingBox>
        <SettingItem>
          <SettingTitleBox>
            <Text size="16px" weight="bold">
              카테고리
            </Text>
          </SettingTitleBox>
          <SettingContentBox>
            <Box sx={{ height: "50px", display: "flex", alignItems: "center" }}>
              <FormControl>
                <RadioGroup
                  row
                  aria-labelledby="demo-row-controlled-radio-buttons-group"
                  name="controlled-radio-buttons-group"
                  onChange={(e) => setCategory(e.target.value)}
                >
                  <FormControlLabel
                    value={1}
                    control={<Radio />}
                    label="운동"
                  />
                  <FormControlLabel
                    value={2}
                    control={<Radio />}
                    label="생활습관"
                  />
                </RadioGroup>
              </FormControl>
            </Box>
          </SettingContentBox>
        </SettingItem>

        <SettingItem>
          <SettingTitleBox>
            <Text size="16px" weight="bold">
              상세 카테고리
            </Text>
          </SettingTitleBox>
          <SettingContentBox>
            <select onChange={changeDetailCategory}>
              <option value={0}>선택 안함</option>
              {detailCategoryList?.map((item) => {
                return (
                  <option key={item.detailId} value={item.detailId}>
                    {item.detailName}
                  </option>
                );
              })}
            </select>
          </SettingContentBox>
        </SettingItem>

        <SettingItem>
          <SettingTitleBox>
            <Text size="16px" weight="bold">
              제목
            </Text>
          </SettingTitleBox>
          <SettingContentBox>
            <Box
              sx={{
                width: "350px",
                height: "40px",
                display: "flex",
                alignItems: "center",
              }}
            >
              <Input
                value={title}
                onChange={(e) => setTitle(e.target.value)}
              ></Input>
            </Box>
          </SettingContentBox>
        </SettingItem>

        <SettingItem>
          <SettingTitleBox sx={{ height: "90px" }}>
            <Text size="16px" weight="bold">
              상세 설명
            </Text>
          </SettingTitleBox>
          <SettingContentBox sx={{ height: "90px" }}>
            <Box
              sx={{
                width: "350px",
                height: "85px",
              }}
            >
              <Textarea
                value={contents}
                onChange={(e) => setContents(e.target.value)}
              ></Textarea>
            </Box>
          </SettingContentBox>
        </SettingItem>

        <SettingItem>
          <SettingTitleBox>
            <Text size="16px" weight="bold">
              목표
            </Text>
          </SettingTitleBox>
          <SettingContentBox>
            <Box
              sx={{
                width: "120px",
                height: "40px",
                display: "flex",
                alignItems: "center",
                marginRight: "10px",
              }}
            >
              <Input
                value={goal}
                onChange={(e) => setGoal(e.target.value)}
              ></Input>
            </Box>
            <Box sx={{ width: "20px" }}>
              <Text my="15px" size="16px" weight="medium">
                {showunit}
              </Text>
            </Box>
          </SettingContentBox>
        </SettingItem>

        <SettingItem>
          <SettingTitleBox>
            <Text size="16px" weight="bold">
              기간
            </Text>
          </SettingTitleBox>
          <SettingContentBox
            sx={{
              width: "350px",
              paddingRight: "10px",
              justifyContent: "space-between",
            }}
          >
            <Box sx={{ width: "160px", height: "40px" }}>
              <DatePicker
                calendarAriaLabel="calendar"
                locale="ko-KR"
                onChange={setStartTime}
                value={startTime}
                minDate={new Date()}
                calendarType="US"
              />
            </Box>
            <Text size="16px" weight="semibold" color="black">
              ~
            </Text>
            <Box sx={{ width: "160px", height: "40px" }}>
              <DatePicker
                calendarAriaLabel="calendar"
                locale="ko-KR"
                onChange={setEndTime}
                value={endTime}
                minDate={new Date()}
                calendarType="US"
              />
            </Box>
          </SettingContentBox>
        </SettingItem>

        <SettingItem>
          <SettingTitleBox>
            <Text size="16px" weight="bold">
              알림
            </Text>
          </SettingTitleBox>
          <SettingContentBox>
            <Box sx={{ width: "160px", height: "40px" }}>
              <TimePicker format="HH:mm" onChange={setAlarm} value={alarm} />
            </Box>
          </SettingContentBox>
        </SettingItem>

        <SettingItem>
          <SettingTitleBox>
            <Text size="16px" weight="bold">
              꼬박챌린지 설정
            </Text>
          </SettingTitleBox>
          <SettingContentBox>
            <Box sx={{ height: "50px", display: "flex", alignItems: "center" }}>
              <FormControl>
                <RadioGroup
                  row
                  aria-labelledby="demo-row-controlled-radio-buttons-group"
                  name="controlled-radio-buttons-group"
                  onChange={(e) => setKkobak(e.target.value)}
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
              </FormControl>
            </Box>
          </SettingContentBox>
        </SettingItem>

        <SettingItem>
          <SettingTitleBox>
            <Text size="16px" weight="bold">
              워치 사용
            </Text>
          </SettingTitleBox>
          <SettingContentBox>
            <Box sx={{ height: "50px", display: "flex", alignItems: "center" }}>
              <FormControl>
                <RadioGroup
                  row
                  aria-labelledby="demo-row-controlled-radio-buttons-group"
                  name="controlled-radio-buttons-group"
                  onChange={(e) => setWatch(e.target.value)}
                >
                  <FormControlLabel
                    value={true}
                    control={<Radio />}
                    label="사용"
                  />
                  <FormControlLabel
                    value={false}
                    control={<Radio />}
                    label="사용 안함"
                  />
                </RadioGroup>
              </FormControl>
            </Box>
          </SettingContentBox>
        </SettingItem>
      </SettingBox>

      {/* <ButtonBox>
        <Button size="ss" onClick={register} my="5px">
          챌린지 등록
        </Button>
      </ButtonBox> */}
    </Box>
  );
}
