import { Box } from '@mui/system';
import Input from 'component/atom/Input';
import Text from 'component/atom/Text';
import Textarea from 'component/atom/Textarea';
import React from 'react';
import { useState } from 'react';
import { useRef } from 'react';

import initial from "../../static/initial.png"

const BoxStyle = {
  height: "100vh",
  width: "70vw",
  display: "flex",
  // alignItems: "center"
  flexDirection: "column",
  justifyContent: "center",
};

const CardStyle = {
  width: "95%",
  margin: "auto",
  minHeight: "80vh",
  backgroundColor: "#F5F5F5",
  textAlign: "center",
  // display: "flex",
  justifyContent: "center",
  borderRadius: "20px",
};

const inputStyle = {
  display: "none",
};


export default function ChallengeBasicForm() {
  const challengeImgInput = useRef();
  const [imgSrc, setImgSrc] = useState(initial);
  function onImgChange(e) {
    e.preventDefault()
  }
  const imgDivClick = (e) => {
    e.preventDefault();
    challengeImgInput.current.click();
  }
  return (
    <Box sx={BoxStyle}>
      <Box sx={CardStyle}>
        <Box sx={{ my: 5 }}>
          <input style={inputStyle} id='challengeImg' ref={challengeImgInput} type='file' accept='image/*' onChange={onImgChange} />
          <img onClick={imgDivClick} alt="img" src={imgSrc}></img>
        </Box>
        <Box sx={{ margin: "auto" }}>
          <Box sx={{ width: "80%", margin: "auto", display: "flex", justifyContent: "space-between", alignItems: "center" }}>

            <Text weight="bold">제목</Text>
            <Box sx={{ width: "80%" }}>
              <Input></Input>
            </Box>
          </Box>
          <Box sx={{ width: "80%", margin: "auto", display: "flex", justifyContent: "space-between", alignItems: "center" }}>

            <Text weight="bold">세부 카테고리</Text>
            <Box sx={{ width: "80%" }}>
              <Input ></Input>
            </Box>
          </Box>
          <Box sx={{ width: "80%", margin: "auto", display: "flex", justifyContent: "space-between", alignItems: "center" }}>

            <Text weight="bold">상세 설명</Text>
            <Box sx={{ width: "80%" }}>
              <Textarea></Textarea>
            </Box>
          </Box>

        </Box>

      </Box>
    </Box>




  )
}