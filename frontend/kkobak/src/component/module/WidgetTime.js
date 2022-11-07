import React, { useState, useEffect } from "react";
import moment from "moment";
import Box from "@mui/material/Box";
import { styled } from "@mui/system";

import Text from "component/atom/Text";

const TimeBox = styled(Box)(
  () => `
  height: 140px;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  `
);

export default function ClockContainer() {
  let timer = null;
  const [time, setTime] = useState(moment());

  function getDay(num) {
    switch (num) {
      case 0:
        return "일";
      case 1:
        return "월";
      case 2:
        return "화";
      case 3:
        return "수";
      case 4:
        return "목";
      case 5:
        return "금";
      case 6:
        return "토";
    }
  }

  useEffect(() => {
    timer = setInterval(() => {
      setTime(moment());
    }, 1000);
    return () => {
      clearInterval(timer);
    };
  }, []);

  return (
    <TimeBox>
      <Text size="xl" weight="bold">
        {time.format("HH : mm")}
      </Text>
      <Text size="14" weight="medium" mt="10">
        {time.format("YYYY년 MM월 DD일")}
        {` (${getDay(time.day())}) `}
      </Text>
    </TimeBox>
  );
}
