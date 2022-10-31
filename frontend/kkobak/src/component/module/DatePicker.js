import { Box } from "@mui/system";
import React, { useState } from "react";
import DatePicker from "react-date-picker";

import "react-date-picker/dist/DatePicker.css"
import "react-calendar/dist/Calendar.css"

export default function DatePick() {
  const [value, onChange] = useState(new Date());
  return (
    <Box>
      <DatePicker onChange={onChange} value={value} minDate={new Date()}/>
    </Box>
  );
}
