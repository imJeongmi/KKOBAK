import React, { useState, useEffect } from "react";
import moment from "moment";
import Box from "@mui/material/Box";

// 시간 실시간으로 적용되도록 수정했음.

export default function ClockContainer() {
  // let timer: any = null;
  let timer = null;
  const [time, setTime] = useState(moment());
  useEffect(() => {
    timer = setInterval(() => {
      setTime(moment());
    }, 1000);
    return () => {
      clearInterval(timer);
    };
  }, []);

  return (
    <Box
      sx={{
        width: "280px",
        height: "130px",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "white",
        borderRadius: 2,
      }}
    >
      <Box>
        <div className="neon blue" style={{ fontSize: "50px" }}>
          {time.format("HH : mm")}
        </div>
        <div className="neon pink" style={{ fontFamily: "alarm_clock" }}>
          &nbsp;{time.format("YYYY년 MM월 DD일")}
        </div>
      </Box>
    </Box>
  );
}
