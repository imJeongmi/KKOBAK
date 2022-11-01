import React from 'react';
import TimePicker from 'react-time-picker';

import "./TimePicker.scss"

export default function TimePick({ onChange, value }) {
  return (
    <TimePicker format="HH:mm" onChange={onChange} value={value} />
  )
}