import React, { useState, useEffect } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import "./MainCalendar.css";
import smile from "../../static/emoji/smile.png";
import cry from "../../static/emoji/cry.png";
import { Box } from "@mui/system";
import Text from "component/atom/Text";

import moment from "moment";
import { requestCalendarCheckChallenge } from "api/Challenge";
import { logController } from "api/log";

const CalendarBox = {
  // margin: "auto",
  // marginLeft: "20px",
  width: "100%",
  minHeight: "80vh",
  backgroundColor: "#F5F5F5",
  borderRadius: "20px",
  textAlign: "center",
  display: "flex",
  // alignItems: "center",
  justifyContent: "center",
};

// year, month, chlId 수정하기
// 달력 클릭시 바로 수정되기!(현재는 클릭하고 새롣고침 해야함)

export default function MainCalendar({ chlId, startTime, endTime }) {
  const [mark, setMark] = useState([]);
  const [year, setYear] = useState(new Date().getFullYear());
  const [month, setMonth] = useState(new Date().getMonth() + 1);

  // >>>>>>>>>>>>>>로그 월별로 조회 부분
  function requestCalendarCheckSuccess(res) {
    setMark(res.data);
    // console.log(res.data);
    // console.log(user.nickName);
  }

  function requestCalendarCheckFail(err) {
    setMark([]);
  }

  useEffect(() => {
    requestCalendarCheckChallenge(
      chlId,
      year,
      month,
      requestCalendarCheckSuccess,
      requestCalendarCheckFail
    );
  }, [chlId, year, month]);
  // >>>>>>>>>>>>>>

  // >>>>>>>>>>>>>>로그 찍는 부분
  function logControllerSuccess(res) {
    const today = moment(new Date()).format("YYYY-MM-DD");
    if (mark.find((x) => x === today)) {
      let filtered = mark.filter((element) => element !== today);
      setMark(filtered);
    } else {
      setMark([...mark, today]);
    }
  }

  function logControllerFail(err) {}

  // useEffect(() => {
  //   logController(chlId, day, logControllerSuccess, logControllerFail);
  // }, []);
  // >>>>>>>>>>>>>>

  function clickDay(e) {
    if (isSameDate(e, new Date())) {
      const day = moment(e).format("YYYY-MM-DD");
      logController(chlId, day, logControllerSuccess, logControllerFail);
    }
  }

  function isDone(date) {
    // console.log(date);
    return mark.find((x) => x === moment(date).format("YYYY-MM-DD"));
    // 해당 날짜까지의 값이랑 월별로 갖고 온 날짜의 done을 비교해서 true false 판단
  }

  const isSameDate = (date1, date2) => {
    return (
      date1.getFullYear() === date2.getFullYear() &&
      date1.getMonth() === date2.getMonth() &&
      date1.getDate() === date2.getDate()
    );
  };

  function onDateChange(e) {
    setValue(e);
    setYear(e.getFullYear());
    setMonth(e.getMonth() + 1);
  }

  const [value, setValue] = useState(new Date());
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
        {startTime} - {endTime}
      </Text>
      <Box sx={CalendarBox}>
        <Calendar
          next2Label={null}
          prev2Label={null}
          calendarType="US"
          locale="en-US"
          showNeighboringMonth={false}
          minDetail="month"
          onChange={onDateChange}
          onClickDay={clickDay}
          minDate={new Date(startTime)}
          maxDate={new Date(endTime)}
          value={value}
          formatDay={(locale, date) =>
            date.toLocaleString("en", { day: "numeric" })
          }
          tileContent={({ date, view }) => {
            if (
              // 챌린지 기간 내인지?

              (isSameDate(new Date(startTime), date) ||
                new Date(startTime) <= date) &&
              date < new Date(endTime)
            ) {
              if (date <= new Date()) {
                return (
                  <Box
                    sx={{
                      left: "50%",
                      transform: "translate(-50%)",
                      width: "8vw",
                      height: "8vh",
                    }}
                  >
                    {isDone(date) ? (
                      <img object-fit="cover" src={smile} alt="smile" />
                    ) : (
                      <img object-fit="cover" src={cry} alt="cry" />
                    )}

                    {/* <div className="dot"></div> */}
                  </Box>
                );
              }
            }
          }}
        />
      </Box>
    </Box>
  );
}
