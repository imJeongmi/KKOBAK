import React, { useRef } from "react";
import { Box, styled } from "@mui/system";
import DatePicker from "react-time-picker";
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
  `
);

export default function ChallengeBasicForm({
  imgSrc,
  title,
  detailCategoryList,
  contents,
  startTime,
  endTime,
  alarm,
  watch,
  setImgSrc,
  setTitle,
  setCategory,
  setDetailCategory,
  setContents,
  setStartTime,
  setEndTime,
  setAlarm,
  setWatch,
  register,
}) {
  const challengeImgInput = useRef();

  function onImgChange(e) {
    e.preventDefault();
    if (!e.target.files) {
      return;
    }
    console.log(e.target.files[0]);
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
          <Text size="15px" weight="bold" my="13" mt="70">
            기간
          </Text>
          <Text size="15px" weight="bold" my="12">
            워치 사용
          </Text>
          <Text size="15px" weight="bold" my="12">
            꼬박챌린지 설정
          </Text>
        </SettingTitleBox>

        <SettingContentBox>
          <Box
            sx={{
              height: "44px",
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
              height: "44px",
              paddingX: "55px",
              display: "flex",
              alignItems: "center",
            }}
          >
            <select onChange={(e) => setDetailCategory(e.target.value)}>
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
          <Box sx={{ width: "80%", height: "50px" }}>
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
          <Box
            sx={{
              width: "80%",
              height: "50px",
              paddingX: "40px",
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
            }}
          >
            <Box sx={{ width: "25%" }}>
              <DatePicker
                calendarAriaLabel="calendar"
                locale="ko-KR"
                onChange={setStartTime}
                value={startTime}
                minDate={new Date()}
                calendarType="US"
              />
            </Box>
            <Box sx={{ width: "25%" }}>
              <DatePicker
                calendarAriaLabel="calendar"
                locale="ko-KR"
                onChange={setEndTime}
                value={endTime}
                minDate={new Date()}
                calendarType="US"
              />
            </Box>
            <Box sx={{ width: "25%" }}>
              <TimePicker format="HH:mm" onChange={setAlarm} value={alarm} />
            </Box>
          </Box>
          <Box
            sx={{
              height: "44px",
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
          <Box
            sx={{
              height: "44px",
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
        <Button size="ss" my="0" onClick={register}>
          챌린지 등록
        </Button>
      </ButtonBox>
    </Box>
  );
}
