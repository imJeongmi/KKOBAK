import React, { useEffect } from "react";
import { useState } from "react";
import { Box } from "@mui/material";

import { getDetailCategoryList } from "api/Category";
import ChallengeBasicForm from "component/module/ChallengeForm";

import { registerChallenge } from "api/Challenge";

import SideBarChallengeCreate from "component/atom/SideBarChallengeCreate";

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
      category,
      contents,
      detailCategory,
      endTime,
      1,
      imgSrc,
      1,
      1,
      startTime,
      [],
      title,
      "회",
      watch,
      "",
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
        <ChallengeBasicForm
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
          setImgSrc={setImgSrc}
          setTitle={setTitle}
          setCategory={setCategory}
          setDetailCategory={setDetailCategory}
          setContents={setContents}
          setStartTime={setStartTime}
          setEndTime={setEndTime}
          setAlarm={setAlarm}
          setWatch={setWatch}
          register={register}
        ></ChallengeBasicForm>
      </Box>
      <SideBarChallengeCreate />
    </Box>
  );
}
