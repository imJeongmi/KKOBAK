
import React from "react";
import DatePicker from "react-date-picker";
import "./DatePicker.scss"

export default function DatePick({ value, onChange }) {

  return (

    <DatePicker calendarAriaLabel="calendar" locale="ko-KR" onChange={onChange} value={value} minDate={new Date()} calendarType="US" />
  );
}
