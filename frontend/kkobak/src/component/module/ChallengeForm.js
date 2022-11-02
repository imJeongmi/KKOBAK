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
import DatePick from 'component/atom/DatePicker';
import TimePick from 'component/atom/TimePicker';

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
              <Textarea value={contents} onChange={setContents}></Textarea>
            </Box>
          </Box>
          <Box sx={{ width: "80%", margin: "auto", display: "flex", justifyContent: "space-between", alignItems: "center" }}>
            <Text weight="bold" >기간ㅤㅤㅤ</Text>
            <Box sx={{ marginLeft: "8%", width: "30%" }}>
              <DatePick onChange={setStartTime} value={startTime}></DatePick>
            </Box>
            <Box sx={{ width: "30%" }}>
              <DatePick onChange={setEndTime} value={endTime}></DatePick>
            </Box>
            <Box sx={{ width: "30%" }}>
              <TimePick value={alarm} onChange={setAlarm}></TimePick>
            </Box>
          </Box>
          <Box sx={{ width: "80%", margin: "auto", display: "flex", alignItems: "center" }}>

            <Text weight="bold">워치 사용</Text>
            <FormControl sx={{ marginLeft: "15%" }}>
              <RadioGroup
                row
                aria-labelledby="demo-row-controlled-radio-buttons-group"
                name="controlled-radio-buttons-group"
                value={watch}
                
              >
                <FormControlLabel onSelect={(e) => setWatch(e.target.value)} value="true" control={<Radio />} label="사용" />
                <FormControlLabel onSelect={(e) => setWatch(e.target.value)} value="false" control={<Radio />} label="사용 안함" />
              </RadioGroup>
            </FormControl>
          </Box>
        </Box>

      </Box>
    </Box>




  )
}