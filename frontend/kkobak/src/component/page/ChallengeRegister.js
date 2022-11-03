import React, { useEffect } from "react";
import { useState } from "react";
import { Box } from "@mui/material";

import { getDetailCategoryList } from "api/Category";
import ChallengeBasicForm from "component/module/ChallengeForm";

import initial from "../../static/initial.png"

export default function ChallengeRegister() {
  const [category, setCategory] = useState(1);
  const [detailCategory, setDetailCategory] = useState(1);
  const [detailCategoryList, setDetailCategoryList] = useState([]);
  const [imgSrc, setImgSrc] = useState(initial);
  const [watch, setWatch] = useState(true);
  const [startTime, setStartTime] = useState(new Date());
  const [endTime, setEndTime] = useState(new Date());
  const [alarm, setAlarm] = useState('00:00');
  const [title, setTitle] = useState('');
  const [contents, setContents] = useState('');
  
  function getDetailCategoryListSuccess(res) {
    setDetailCategoryList(res.data)
  }

  function getDetailCategoryListFail(err) {
  }
  
  useEffect(() => {
    getDetailCategoryList(category, getDetailCategoryListSuccess, getDetailCategoryListFail)
  }, [category])

  return (
    <Box>
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
        setWatch={setWatch} />
    </Box>
  );
}