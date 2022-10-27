import React from "react";
import Box from "@mui/material/Box";
import Text from "../atom/Text";

//시간위젯
export default function WidgetTime() {
  let today = new Date();
  let time = {
    year: today.getFullYear(),
    month: today.getMonth() + 1,
    date: today.getDate(),
    hour: today.getHours(),
    minutes: today.getMinutes(),
  };

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
        <Box>
          <Text size="xl">
            {time.hour} : {time.minutes}
          </Text>
          <br />
          <Text>
            {time.year}년{" "}
            {time.month > 9 ? time.month : "0" + String(time.month)}월{" "}
            {time.date > 9 ? time.date : "0" + String(time.date)}일
          </Text>
        </Box>
      </Box>
    </Box>
  );
}
