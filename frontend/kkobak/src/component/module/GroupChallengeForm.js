import React, { useEffect, useRef, useState } from "react";
import { Box, styled } from "@mui/system";
import DatePicker from "react-date-picker";
import TimePicker from "react-time-picker";

import Input from "component/atom/Input";
import Text from "component/atom/Text";
import Textarea from "component/atom/Textarea";

import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormControl from "@mui/material/FormControl";

import { uploadPhoto } from "api/S3";

import "component/atom/DatePicker.scss";
import "component/atom/TimePicker.scss";
import "component/module/ChallengeForm.css";

const inputStyle = {
  display: "none",
};

const ImageBox = styled(Box)(
  () => `
  width: 360px;
  height: 200px;
  margin: 25px auto 0 auto;
  border-radius: 20px;
  background-size: cover;
  overflow: hidden;
  `
);

const SettingBox = styled(Box)(
  () => `
  display: flex;
  flex-direction: column;
  align-items: start;
  justify-content: start;
  `
);

const SettingItem = styled(Box)(
  () => `
  width: 33vw;
  margin: 5px;
  display: flex;
  `
);

const SettingTitleBox = styled(Box)(
  (height) => `
  width: 150px;
  height: 50px;
  display: flex;
  justify-content: start;
  align-items: center;
  `
);

const SettingContentBox = styled(Box)(
  (height) => `
  width: 300px;
  height: 50px;
  display: flex;
  justify-content: start;
  align-items: center;
  `
);

export default function GroupChallengeForm({
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
    if (detailCategory === "1") {
      setShowUnit("km");
      setWatch(true);
    } else if (detailCategory === "2") {
      setShowUnit("km");
      setWatch(true);
    } else if (detailCategory === "3") {
      setShowUnit("분");
      setWatch(true);
    } else if (detailCategory === "7") {
      setShowUnit("주소");
      setWatch(true);
    } else if (category === "1") {
      setShowUnit("분");
    } else if (category === "2") {
      setShowUnit("회");
    } else {
      setWatch(false);
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
    // console.log(err);
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
      <Box sx={{ display: "flex", flexDirection: "row", mt: 5 }}>
        <SettingBox>
          <SettingItem>
            <SettingTitleBox>
              <Text size="15px" weight="bold">
                {"🔎\u00A0 카테고리"}
              </Text>
            </SettingTitleBox>
            <SettingContentBox>
              <Box
                sx={{ height: "50px", display: "flex", alignItems: "center" }}
              >
                <FormControl>
                  <RadioGroup
                    row
                    aria-labelledby="demo-row-controlled-radio-buttons-group"
                    name="controlled-radio-buttons-group"
                    onChange={(e) => setCategory(e.target.value)}
                  >
                    <FormControlLabel
                      value={1}
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
                      label={<Text size="14px">운동</Text>}
                    />
                    <FormControlLabel
                      value={2}
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
                      label={<Text size="14px">생활습관</Text>}
                    />
                  </RadioGroup>
                </FormControl>
              </Box>
            </SettingContentBox>
          </SettingItem>

          <SettingItem>
            <SettingTitleBox>
              <Text size="15px" weight="bold">
                {"🔖\u00A0 상세 카테고리"}
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
              <Text size="15px" weight="bold">
                {"📌\u00A0 제목"}
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
              <Text size="15px" weight="bold">
                {"💬\u00A0 상세 설명"}
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
        </SettingBox>
        <SettingBox>
          <SettingItem>
            <SettingTitleBox>
              <Text size="15px" weight="bold">
                {"🔥\u00A0 목표"}
              </Text>
            </SettingTitleBox>
            <SettingContentBox>
              <Box
                sx={{
                  width: "150px",
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
                <Text my="15px" size="14px" weight="medium">
                  {showunit}
                </Text>
              </Box>
            </SettingContentBox>
          </SettingItem>
          <SettingItem>
            <SettingTitleBox>
              <Text size="15px" weight="bold">
                {"⌚️\u00A0 워치 사용"}
              </Text>
            </SettingTitleBox>
            <SettingContentBox>
              <Box
                sx={{ height: "50px", display: "flex", alignItems: "center" }}
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
                      label={<Text size="14px">사용</Text>}
                    />
                    <FormControlLabel
                      value={false}
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
                      label={<Text size="14px">사용 안함</Text>}
                    />
                  </RadioGroup>
                </FormControl>
              </Box>
            </SettingContentBox>
          </SettingItem>

          <SettingItem>
            <SettingTitleBox>
              <Text size="15px" weight="bold">
                {"🔔\u00A0 알림"}
              </Text>
            </SettingTitleBox>
            <SettingContentBox>
              <Box sx={{ width: "150px", height: "40px" }}>
                <TimePicker format="HH:mm" onChange={setAlarm} value={alarm} />
              </Box>
            </SettingContentBox>
          </SettingItem>

          <SettingItem>
            <SettingTitleBox>
              <Text size="15px" weight="bold">
                {"📅\u00A0 기간"}
              </Text>
            </SettingTitleBox>
            <SettingContentBox
              sx={{
                width: "350px",
                paddingRight: "10px",
                justifyContent: "space-between",
              }}
            >
              <Box sx={{ width: "150px", height: "40px" }}>
                <DatePicker
                  calendarAriaLabel="calendar"
                  locale="ko-KR"
                  onChange={setStartTime}
                  value={startTime}
                  minDate={new Date()}
                  calendarType="US"
                />
              </Box>
              <Text size="14px" weight="semibold" color="black">
                ~
              </Text>
              <Box sx={{ width: "150px", height: "40px" }}>
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
        </SettingBox>
      </Box>
    </Box>
  );
}
