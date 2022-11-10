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

const inputStyle = {
  display: "none",
};

const ImageBox = styled(Box)(
  () => `
  width: 50%;
  height: 20vh;
  margin: 20px auto 20px auto;
  border-radius: 20px;
  background-size: cover;
  overflow: hidden;
  background-color: grey;
  `
);

const SettingBox = styled(Box)(
  () => `
  width: 50vw;
  display: flex;
  justify-content: center;
  `
);

const SettingTitleBox = styled(Box)(
  () => `
  width: 130px;
  margin: 0 50px 20px 35px;
  display: flex;
  flex-direction: column;
  align-items: start;
  `
);

const SettingContentBox = styled(Box)(
  () => `
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: start;
  `
);

const ButtonBox = styled(Box)(
  () => `
  width: 100%;
  display: flex;
  justify-content: end;
  margin-bottom: 10px;
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
    e.preventDefault()
    setDetailCategory(e.target.value)
    changeUnit(category, e.target.value)
    showUnit(category, e.target.value)
  }
  useEffect(() => {
    if (category && detailCategory) {
      changeUnit(category, detailCategory)
    }
  }, [category, detailCategory])

  function showUnit(category, detailCategory) {
    if (category === "2" && detailCategory === "7") {
      setShowUnit('주소')
    } else if (category === "2") {
      setShowUnit('1 회')
    } else if (category === "1" && detailCategory === "1") {
      setShowUnit('Km')
    } else if (category === "1" && detailCategory === "2") {
      setShowUnit('Km')
    } else if (category === "1" && detailCategory === "3") {
      setShowUnit('분')
    }
  }

  function onImgChange(e) {
    e.preventDefault();
    if (!e.target.files) {
      return;
    }
    // console.log(e.target.files[0]);
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
        height: "80vh",
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
        <SettingTitleBox>
          <Text size="15px" weight="bold" my="12">
            카테고리
          </Text>
          <Text size="15px" weight="bold" my="13">
            상세 카테고리
          </Text>
          <Text size="15px" weight="bold" my="14">
            제목
          </Text>
          <Text size="15px" weight="bold" my="17">
            상세 설명
          </Text>
          <Text size="15px" weight="bold" mt="60">
            목표
          </Text>
          <Text size="15px" weight="bold" my="15" mt="20">
            기간
          </Text>
          <Text size="15px" weight="bold" my="10">
            알림
          </Text>
          <Text size="15px" weight="bold" my="10">
            꼬박챌린지 설정
          </Text>
          <Text size="15px" weight="bold" my="10">
            워치 사용
          </Text>
        </SettingTitleBox>

        <SettingContentBox>
          <Box
            sx={{
              height: "40px",
              paddingX: "55px",
              display: "flex",
              alignItems: "center",
            }}
          >
            <FormControl>
              <RadioGroup
                row
                aria-labelledby="demo-row-controlled-radio-buttons-group"
                name="controlled-radio-buttons-group"
                onChange={(e) => setCategory(e.target.value)}
              >
                <FormControlLabel value={1} control={<Radio />} label="운동" />
                <FormControlLabel
                  value={2}
                  control={<Radio />}
                  label="생활습관"
                />
              </RadioGroup>
            </FormControl>
          </Box>
          <Box
            sx={{
              height: "45px",
              paddingX: "55px",
              display: "flex",
              alignItems: "center",
            }}
          >
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
          </Box>
          <Box sx={{ width: "80%", height: "45px" }}>
            <Input
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            ></Input>
          </Box>
          <Box sx={{ width: "80%", height: "100px" }}>
            <Textarea
              value={contents}
              onChange={(e) => setContents(e.target.value)}
            ></Textarea>
          </Box>
          <Box sx={{ ml: "40px", width: "30%", height: "45px", display: "flex", verticalAlign: "middle" }}>
            {(category === "2" && detailCategory !== "7") ? null :
              <Input
                value={goal}
                onChange={(e) => setGoal(e.target.value)}
              ></Input>}

            <Box sx={{ width: "60%", height: '45px', verticalAlign: "middle" }}>
              <Text my="15px" size="15px">{showunit}</Text>
            </Box>
          </Box>
          <Box
            sx={{
              width: "60%",
              height: "45px",
              paddingX: "35px",
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
            }}
          >
            <Box sx={{ width: "155px" }}>
              <DatePicker
                calendarAriaLabel="calendar"
                locale="ko-KR"
                onChange={setStartTime}
                value={startTime}
                minDate={new Date()}
                calendarType="US"
              />
            </Box>
            <Box sx={{ width: "155px" }}>
              <DatePicker
                calendarAriaLabel="calendar"
                locale="ko-KR"
                onChange={setEndTime}
                value={endTime}
                minDate={new Date()}
                calendarType="US"
              />
            </Box>
          </Box>
          <Box
            sx={{
              width: "60%",
              height: "45px",
              paddingX: "35px",
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
            }}
          >
            <Box sx={{ width: "155px" }}>
              <TimePicker format="HH:mm" onChange={setAlarm} value={alarm} />
            </Box>
          </Box>
          <Box
            sx={{
              height: "40px",
              paddingX: "55px",
              display: "flex",
              alignItems: "center",
            }}
          >
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
          <Box
            sx={{
              height: "35px",
              paddingX: "55px",
              display: "flex",
              alignItems: "center",
            }}
          >
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
      </SettingBox>
      <ButtonBox>
        <Button size="ss" mx="1" my="2" onClick={register}>
          챌린지 등록
        </Button>
      </ButtonBox>
    </Box>
  );
}
