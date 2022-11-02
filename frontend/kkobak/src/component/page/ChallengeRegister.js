import { Box } from "@mui/material";
import ChallengeCategory from "component/module/ChallengeCategory";
import ChallengeBasicForm from "component/module/ChallengeForm";
import React, { useEffect } from "react";
import { useState } from "react";

import initial from "../../static/initial.png"

export default function ChallengeRegister() {
  const [page, setPage] = useState(2);
  const [imgSrc, setImgSrc] = useState(initial);
  const [watch, setWatch] = useState('');
  const [startTime, setStartTime] = useState(new Date());
  const [endTime, setEndTime] = useState(new Date());
  const [alarm, setAlarm] = useState(0);
  const [title, setTitle] = useState('');
  const [contents, setContents] = useState('');
  
  useEffect(() => {
    console.log(title.data)
  }, [title])

  return (
    <Box>
      {(page === 1 ? <ChallengeCategory /> 
      :
        <ChallengeBasicForm 
        imgSrc={imgSrc}
        title={title}
        contents={contents}
        startTime={startTime}
        endTime={endTime}
        alarm={alarm}
        watch={watch}
        setImgSrc={setImgSrc}
        setTitle={setTitle}
        setContents={setContents}
        setStartTime={setStartTime}
        setEndTime={setEndTime}
        setAlarm={setAlarm}
        setWatch={setWatch} /> )}

    </Box>
  );
}