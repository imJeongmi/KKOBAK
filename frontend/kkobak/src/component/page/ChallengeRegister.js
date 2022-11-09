import React, { useEffect } from "react";
import { useState } from "react";
import { Box } from "@mui/material";

import SideBarChallengeCreate from "component/atom/SideBarChallengeCreate";
import ChallengeForm from "component/module/ChallengeForm";

import { getDetailCategoryList } from "api/Category";
import { registerChallenge } from "api/Challenge";
import MainBox from "component/atom/MainBox";
import Text from "component/atom/Text"

export default function ChallengeRegister() {
  const [category, setCategory] = useState(1);
  const [detailCategory, setDetailCategory] = useState(1);
  const [detailCategoryList, setDetailCategoryList] = useState([]);
  // const [imgSrc, setImgSrc] = useState(initial);
  // default imgsrc로 등록해둠!
  const [imgSrc, setImgSrc] = useState(
    "https://initpjtbucket.s3.ap-northeast-2.amazonaws.com/images/cdd6bd23-f356-4073-91b9-ee89d9542c58.png"
  );
  const [watch, setWatch] = useState(true);
  const [kkobak, setKkobak] = useState(0);
  const [startTime, setStartTime] = useState(new Date());
  const [endTime, setEndTime] = useState(new Date());
  const [alarm, setAlarm] = useState("00:00");
  const [title, setTitle] = useState("");
  const [contents, setContents] = useState("");

  function getDetailCategoryListSuccess(res) {
    setDetailCategoryList(res.data);
  }

  function getDetailCategoryListFail(err) {}

  useEffect(() => {
    getDetailCategoryList(
      category,
      getDetailCategoryListSuccess,
      getDetailCategoryListFail
    );
  }, [category]);

  function registerSuccess() {
    console.log("성공");
  }

  function registerFail(err) {
    console.log(err);
  }

  function register(e) {
    e.preventDefault();
    registerChallenge(
      alarm,
      0,
      category,
      contents,
      detailCategory,
      endTime,
      1,
      imgSrc,
      kkobak,
      1,
      "",
      1,
      startTime,
      [],
      title,
      "회",
      watch,
      registerSuccess,
      registerFail
    );
  }

  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "space-between",
      }}
    >
      <Box sx={{ margin: "0 auto" }}>
        <Text size="l" weight="bold" mt="30" my="15">
          새 챌린지 등록
        </Text>
        <MainBox>
          <ChallengeForm
            imgSrc={imgSrc}
            category={category}
            detailCategory={detailCategory}
            detailCategoryList={detailCategoryList}
            title={title}
            contents={contents}
            startTime={startTime}
            endTime={endTime}
            alarm={alarm}
            watch={watch}
            kkobak={kkobak}
            setImgSrc={setImgSrc}
            setTitle={setTitle}
            setCategory={setCategory}
            setDetailCategory={setDetailCategory}
            setContents={setContents}
            setStartTime={setStartTime}
            setEndTime={setEndTime}
            setAlarm={setAlarm}
            setWatch={setWatch}
            setKkobak={setKkobak}
            register={register}
          ></ChallengeForm>
        </MainBox>
      </Box>
      <SideBarChallengeCreate />
    </Box>
  );
}
