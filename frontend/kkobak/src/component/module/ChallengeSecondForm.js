import { Box } from '@mui/system';
import Input from 'component/atom/Input';
import Text from 'component/atom/Text';
import Textarea from 'component/atom/Textarea';
import React, { useState } from 'react';

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


export default function ChallengeSecondForm() {
  const [watch, setWatch] = useState('true');
  const [lock, setLock] = useState('true');
  const [startTime, setStartTime] = useState(new Date());
  const [endTime, setEndTime] = useState(new Date());
  const [limitPeople, setLimitPeople] = useState(1);
  const [goal, setGoal] = useState(0);
  const [alarm, setAlarm] = useState(0);

  const watchHandleChange = (event) => {
    setWatch(event.target.value);
  };

  const lockHandleChange = (event) => {
    setLock(event.target.value);
  };

  return (
    <Box sx={BoxStyle}>
      <Box sx={CardStyle}>

        <Box sx={{ width: "80%", margin: "auto", display: "flex", justifyContent: "space-between", alignItems: "center", mb: 1, mt: 6 }}>
          <Text weight="bold" >기간ㅤㅤㅤ</Text>
          <Box sx={{ marginLeft: "5%", width: "40%" }}>
            <DatePick onChange={setStartTime} value={startTime}></DatePick>
          </Box>
          <Box sx={{ width: "40%" }}>
            <DatePick onChange={setEndTime} value={endTime}></DatePick>
          </Box>
        </Box>
        <Box sx={{ width: "80%", margin: "auto", display: "flex", justifyContent: "space-between", alignItems: "center", my: 1 }}>

          <Text weight="bold">알림 시간ㅤ</Text>
          <Box sx={{ marginLeft: "5%", width: "40%" }}>
            <TimePick value={alarm} onChange={setAlarm}></TimePick>
          </Box>
          <Box sx={{ width: "40%" }}>
          </Box>
        </Box>
        <Box sx={{ width: "80%", margin: "auto", display: "flex", justifyContent: "space-between", alignItems: "center", my: 1 }}>

          <Text weight="bold">최대 인원ㅤ</Text>
          <Box sx={{ marginLeft: "5%", width: "40%" }}>
            <Input type="number" value={limitPeople} onChange={setLimitPeople}></Input>
          </Box>
          <Box sx={{ width: "40%" }}>
          </Box>
        </Box>
        <Box sx={{ width: "80%", margin: "auto", display: "flex", justifyContent: "space-between", alignItems: "center", my: 1 }}>

          <Text weight="bold">목표치ㅤㅤ</Text>

          <Box sx={{ marginLeft: "5%", width: "40%" }}>
            <Input type="number" value={goal} onChange={setGoal}></Input>
          </Box>
          <Box sx={{ width: "40%" }}>
            <Input></Input>

          </Box>
        </Box>
        <Box sx={{ width: "80%", margin: "auto", display: "flex", alignItems: "center", my: 1 }}>

          <Text weight="bold">워치 사용</Text>
          <FormControl sx={{ marginLeft: "15%" }}>
            <RadioGroup
              row
              aria-labelledby="demo-row-controlled-radio-buttons-group"
              name="controlled-radio-buttons-group"
              value={watch}
              onChange={watchHandleChange}
            >
              <FormControlLabel value="true" control={<Radio />} label="사용" />
              <FormControlLabel value="false" control={<Radio />} label="사용 안함" />
            </RadioGroup>
          </FormControl>
        </Box>
        <Box sx={{ width: "80%", margin: "auto", display: "flex", alignItems: "center", my: 1 }}>

          <Text weight="bold">잠금 설정</Text>
          <FormControl sx={{ marginLeft: "15%" }}>
            <RadioGroup
              row
              aria-labelledby="demo-row-controlled-radio-buttons-group"
              name="controlled-radio-buttons-group"
              value={lock}
              onChange={lockHandleChange}
            >
              <FormControlLabel value="true" control={<Radio />} label="사용" />
              <FormControlLabel value="false" control={<Radio />} label="사용 안함" />
            </RadioGroup>
          </FormControl>
        </Box>
        <Box sx={{ width: "80%", margin: "auto", display: "flex", justifyContent: "space-between", alignItems: "center", mt: 1, mb: 5 }}>

          <Text weight="bold">해시 태그</Text>
          <Box sx={{ marginLeft: "5%", width: "85%" }}>
            <Textarea></Textarea>
          </Box>
        </Box>

      </Box>

    </Box>





  )
}