import React, { useState, useEffect } from "react";
import { Box } from "@mui/system";
import moment from "moment";

import Calendar from "react-calendar";
import smile from "static/emoji/smile.png";
import cry from "static/emoji/cry.png";

import { requestCalendarCheckChallenge } from "api/Challenge";
import { requestChallengeCount, getChallengeDetail } from "api/Challenge";

import "react-calendar/dist/Calendar.css";
import "./MainCalendar.css";

const CalendarBox = {
  width: "100%",
  minHeight: "90%",
  textAlign: "center",
  display: "flex",
  justifyContent: "center",
};

export default function MainCalendar({
  chlId,
  startTime,
  endTime,
  watch,
  title,
}) {
  const [mark, setMark] = useState([]);
  const [value, setValue] = useState(new Date());
  const [year, setYear] = useState(new Date().getFullYear());
  const [month, setMonth] = useState(new Date().getMonth() + 1);
  const [categoryId, setCategoryId] = useState();

  function requestCalendarCheckSuccess(res) {
    setMark(res.data);
  }

  function requestCalendarCheckFail(err) {
    setMark([]);
  }

  function requestChallengeCountSuccess(res) {
    requestCalendarCheckChallenge(
      chlId,
      year,
      month,
      requestCalendarCheckSuccess,
      requestCalendarCheckFail
    );
  }

  function requestChallengeCountFail(err) {}

  function getChallengeDetailSuccess(res) {
    setCategoryId(res.data.categoryId);
  }

  function getChallengeDetailFail(err) {}

  function isDone(date) {
    return mark.find((x) => x === moment(date).format("YYYY-MM-DD"));
  }

  function clickDay(e) {
    if (!isSameDate(e, new Date())) return;
    if (watch === true) return;

    if (categoryId === 1) {
      if (isDone(e)) {
        requestChallengeCount(
          chlId,
          2,
          requestChallengeCountSuccess,
          requestChallengeCountFail
        );
      } else {
        requestChallengeCount(
          chlId,
          1,
          requestChallengeCountSuccess,
          requestChallengeCountFail
        );
      }
    } else if (categoryId === 2) {
      requestChallengeCount(
        chlId,
        1,
        requestChallengeCountSuccess,
        requestChallengeCountFail
      );
    }
  }

  function isSameDate(date1, date2) {
    return (
      date1.getFullYear() === date2.getFullYear() &&
      date1.getMonth() === date2.getMonth() &&
      date1.getDate() === date2.getDate()
    );
  }

  function onDateChange(e) {
    setValue(e);
    setYear(e.getFullYear());
    setMonth(e.getMonth() + 1);
  }

  useEffect(() => {
    getChallengeDetail(
      chlId,
      getChallengeDetailSuccess,
      getChallengeDetailFail
    );
  }, [chlId]);

  useEffect(() => {
    requestCalendarCheckChallenge(
      chlId,
      year,
      month,
      requestCalendarCheckSuccess,
      requestCalendarCheckFail
    );
  }, [chlId, year, month]);

  return (
    <Box
      sx={{
        height: "80vh",
        width: "60vw",
        display: "flex",
        flexDirection: "column",
        justifyContent: "start",
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
              (isSameDate(new Date(startTime), date) ||
                new Date(startTime) <= date) &&
              date < new Date(endTime)
            ) {
              if (date <= new Date()) {
                return (
                  <Box
                    sx={{
                      width: "100%",
                      height: "100%",
                      display: "flex",
                      alignItems: "center",
                      justifyContent: "center",
                    }}
                  >
                    {isDone(date) ? (
                      <img object-fit="cover" src={smile} alt="smile" />
                    ) : (
                      <img object-fit="cover" src={cry} alt="cry" />
                    )}
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
