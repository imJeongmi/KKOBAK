import React, { useState } from "react";
import Calendar from "react-calendar";

import "react-calendar/dist/Calendar.css";
import "./MainCalendar.css";

import smile from "../../static/emoji/smile.png";
import cry from "../../static/emoji/cry.png";
import { Box } from "@mui/system";

import Text from "component/atom/Text";

const CalendarBox = {
  // margin: "auto",
  // marginLeft: "20px",
  width: "100%",
  minHeight: "80vh",
  backgroundColor: "#F5F5F5",
  textAlign: "center",
  display: "flex",
  // alignItems: "center",
  justifyContent: "center",
};

export default function MainCalendar({ id, startTime, endTime }) {
  const [value, onChange] = useState(new Date());
  return (
    <Box
      sx={{
        height: "80vh",
        width: "60vw",
        display: "flex",
        // alignItems: "center"
        flexDirection: "column",
        justifyContent: "center",
      }}
    >
      <Text my="5" size="12px" color="grey">
        {startTime.substr(0, 10)} - {endTime.substr(0, 10)}
      </Text>
      <Box sx={CalendarBox}>
        <Calendar
          next2Label={null}
          prev2Label={null}
          calendarType="US"
          locale="en-US"
          showNeighboringMonth={false}
          minDetail="month"
          onChange={onChange}
          value={value}
          formatDay={(locale, date) =>
            date.toLocaleString("en", { day: "numeric" })
          }
          tileContent={({ date, view }) => {
            return (
              <Box
                sx={{
                  // textAlign: "center",
                  left: "50%",
                  transform: "translate(-50%)",
                  width: "8vw",
                  height: "8vh",
                }}
              >
                {false ? (
                  <img object-fit="cover" src={smile} />
                ) : (
                  <img object-fit="cover" src={cry} />
                )}
              </Box>
            );
          }}
        />
      </Box>
    </Box>
  );
}
