import React, { useRef } from "react";
import { Box, styled } from "@mui/system";
import DatePicker from "react-date-picker";

import "../atom/DatePicker.scss";

const SettingBox = styled(Box)(
  () => `
  width: 50vw;
  display: flex;
  justify-content: center;
  `
);

const SettingContentBox = styled(Box)(
  () => `
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: start;
  `
);

export default function CheckDayForm({ findTime, setFindTime }) {
  return (
    <Box
      sx={{
        height: "80vh",
        display: "flex",
        flexDirection: "column",
        alignContent: "center",
      }}
    >
      <SettingBox>
        <SettingContentBox>
          <Box
            sx={{
              width: "60%",
              height: "55px",
              paddingX: "35px",
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
            }}
          >
            <Box sx={{ width: "155px" }}>
              <DatePicker
                calendarAriaLabel="calendar"
                locale="ko-KR"
                onChange={setFindTime}
                value={findTime}
                // minDate={new Date()}
                calendarType="US"
              />
            </Box>
          </Box>
        </SettingContentBox>
      </SettingBox>
    </Box>
  );
}
