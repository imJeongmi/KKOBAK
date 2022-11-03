import { Box } from '@mui/system';
import Input from 'component/atom/Input';
import Text from 'component/atom/Text';
import Textarea from 'component/atom/Textarea';
import React from 'react';
import { useRef } from 'react';

import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import DatePicker from "react-date-picker";
import "../atom/DatePicker.scss"
import TimePicker from 'react-time-picker';
import "../atom/TimePicker.scss"

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


export default function ChallengeBasicForm(
  { imgSrc,
    title,
    contents,
    startTime,
    endTime,
    alarm,
    watch,
    setImgSrc,
    setTitle, 
    setContents,
    setStartTime,
    setEndTime,
    setAlarm,
    setWatch
  }
  ) {
  const challengeImgInput = useRef();
  
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
        <Box sx={{ my: 2 }}>
          <input style={inputStyle} id='challengeImg' ref={challengeImgInput} type='file' accept='image/*' onChange={onImgChange} />
          <img onClick={imgDivClick} alt="img" src={imgSrc}></img>
        </Box>
        <Box sx={{ margin: "auto" }}>
          <Box sx={{ width: "80%", margin: "auto", display: "flex", justifyContent: "space-between", alignItems: "center" }}>

            <Text weight="bold">제목</Text>
            <Box sx={{ width: "90%" }}>
              <Input value={title} onChange={(e) => setTitle(e.target.value)}></Input>
            </Box>
          </Box>

          <Box sx={{ width: "80%", margin: "auto", display: "flex", justifyContent: "space-between", alignItems: "center" }}>

            <Text weight="bold">상세 설명</Text>
            <Box sx={{ width: "90%" }}>
              <Textarea value={contents} onChange={(e) => setContents(e.target.value)}></Textarea>
            </Box>
          </Box>
          <Box sx={{ width: "80%", margin: "auto", display: "flex", justifyContent: "space-between", alignItems: "center" }}>
            <Text weight="bold" >기간ㅤㅤㅤ</Text>
            <Box sx={{ marginLeft: "8%", width: "30%" }}>
              <DatePicker calendarAriaLabel="calendar" locale="ko-KR" onChange={setStartTime} value={startTime} minDate={new Date()} calendarType="US" />
            </Box>
            <Box sx={{ width: "30%" }}>
              <DatePicker calendarAriaLabel="calendar" locale="ko-KR" onChange={setEndTime} value={endTime} minDate={new Date()} calendarType="US" />

            </Box>
            <Box sx={{ width: "30%" }}>
              <TimePicker format="HH:mm" onChange={setAlarm} value={alarm} />
            </Box>
          </Box>
          <Box sx={{ width: "80%", margin: "auto", display: "flex", alignItems: "center" }}>

            <Text weight="bold">워치 사용</Text>
            <FormControl sx={{ marginLeft: "15%" }}>
              <RadioGroup
                row
                aria-labelledby="demo-row-controlled-radio-buttons-group"
                name="controlled-radio-buttons-group"
              >
                <FormControlLabel onSelect={(e) => setWatch(e.target.value)} value={true} control={<Radio />} label="사용" />
                <FormControlLabel onSelect={(e) => setWatch(e.target.value)} value={false} control={<Radio />} label="사용 안함" />
              </RadioGroup>
            </FormControl>
          </Box>
        </Box>

      </Box>
    </Box>




  )
}