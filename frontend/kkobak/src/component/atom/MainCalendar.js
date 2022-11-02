import React, { useState } from "react";
import Calendar from "react-calendar";

import "react-calendar/dist/Calendar.css";
import "./MainCalendar.css";

import smile from "../../static/emoji/smile.png";
import cry from "../../static/emoji/cry.png";
import { Box } from "@mui/system";

import TutorialButton from "component/atom/TutorialButton";

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

export default function MainCalendar() {
  const [value, onChange] = useState(new Date());
  return (
    <Box
      sx={{
        height: "85vh",
        width: "60vw",
        display: "flex",
        // alignItems: "center"
        flexDirection: "column",
        justifyContent: "center",
      }}
    >
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
                {true ? (
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
