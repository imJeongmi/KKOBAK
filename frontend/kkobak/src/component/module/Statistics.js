import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Box, styled } from "@mui/system";

import Text from "component/atom/Text";
import ChallengeMap from "component/atom/ChallengeMap";
import ChallengeAppearMap from "component/atom/ChallengeAppearMap";
import HeartRateChart from "component/module/HeartRateChart";
import BarChart from "component/module/BarChart";
import MedBarChart from "component/module/MedBarChart";
import HabitBarChart from "component/module/HabitBarChart";
import AppearBarChart from "component/module/AppearBarChart";
import CheckDayForm from "component/module/CheckDayForm";

import { requestMyChallengeDetail } from "api/userApi";

import DatePicker from "react-date-picker";
import "component/atom/DatePicker.scss";

const StatisticsBox = styled(Box)(
  () => `
  width: 90%;
  height: 100%;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  `
);

const CheckCalendarBox = styled(Box)(
  () => `
    width: 10px;
    height: 20px;
    z-index: 100;
    margin-left: 50px;
  `
);

const CardBox = styled(Box)(
  ({ height, margin }) => `
    width: 100%;
    height: ${getHeight(height)};
    margin: ${getMargin(margin)};
    border-radius: 20px;
    overflow: hidden;
  `
);

function getHeight(height) {
  if (!!height) return height;
  else return "40%";
}

function getMargin(margin) {
  if (margin === "center") return "auto";
  if (margin == 0) return "0 auto";
  else return "40px auto 0 auto";
}

export default function Statistics() {
  const chlId = Number(useParams().chlId);
  const [detailCategoryId, setDetailCategoryId] = useState("");
  const [findTime, setFindTime] = useState(new Date());

  function requestMyChallengeDetailSuccess(res) {
    const data = res.data;
    setDetailCategoryId(data.detailCategoryId);
  }

  function requestMyChallengeDetailFail(res) {}

  useEffect(() => {
    requestMyChallengeDetail(
      chlId,
      requestMyChallengeDetailSuccess,
      requestMyChallengeDetailFail
    );
  }, []);

  // detailCategoryId 1,2 는 달리기와 걷기 입니다.
  // 3은 명상입니다. 7은 출석체크 입니다. 4, 5, 6은 생활습관 입니다.

  return (
    <Box>
      <Box sx={{ position: "absolute" }}>
        <DatePicker
          calendarAriaLabel="calendar"
          locale="ko-KR"
          onChange={setFindTime}
          value={findTime}
          calendarType="US"
        />
      </Box>
      {detailCategoryId === 1 || detailCategoryId === 2 ? (
        <Box>
          <Text weight="semibold" size="14px" my="3">
            {"📍 GPS 정보"}
          </Text>
          <Box sx={{ width: "350px", display: "flex", alignItems: "center" }}>
            <ChallengeMap
              width="300px"
              height="150px"
              findTime={findTime}
              setFindTime={setFindTime}
            />
          </Box>
          <CardBox height="450px">
            <HeartRateChart findTime={findTime} setFindTime={setFindTime} />
          </CardBox>
          <CardBox height="450px">
            <BarChart findTime={findTime} setFindTime={setFindTime} />
          </CardBox>
        </Box>
      ) : detailCategoryId === 3 ? (
        <StatisticsBox>
          <CardBox margin="center">
            <HeartRateChart findTime={findTime} setFindTime={setFindTime} />
          </CardBox>
          <CardBox>
            <MedBarChart findTime={findTime} setFindTime={setFindTime} />
          </CardBox>
        </StatisticsBox>
      ) : detailCategoryId === 7 ? (
        <StatisticsBox>
          <Box
            sx={{
              width: "400px",
              height: "300px",
              display: "flex",
              alignItems: "center",
            }}
          >
            <ChallengeAppearMap
              width="400px"
              height="300px"
              findTime={findTime}
              setFindTime={setFindTime}
            />
          </Box>
          <CardBox>
            <AppearBarChart findTime={findTime} setFindTime={setFindTime} />
          </CardBox>
        </StatisticsBox>
      ) : (
        <StatisticsBox>
          <CardBox height="50px"></CardBox>

          <CardBox height="250px">
            <HabitBarChart findTime={findTime} setFindTime={setFindTime} />
          </CardBox>
        </StatisticsBox>
      )}
    </Box>
  );
}

//   detailCategoryId === 1 || detailCategoryId === 2 ? (
//     <StatisticsBox>
//       <CheckCalendarBox sx={{ height: "60px" }}>
//         <CheckDayForm findTime={findTime} setFindTime={setFindTime} />
//       </CheckCalendarBox> */}
//       {/* <CheckCalendarBox> */}
//       {/* </CheckCalendarBox>
//       <Text weight="semibold" size="14px" my="3">
//         {"📍 GPS 정보"}
//       </Text>
//       <Box sx={{ width: "350px", display: "flex", alignItems: "center" }}>
//         <ChallengeMap
//           width="300px"
//           height="150px"
//           findTime={findTime}
//           setFindTime={setFindTime}
//         />
//       </Box>
//       <CardBox height="450px">
//         <HeartRateChart findTime={findTime} setFindTime={setFindTime} />
//       </CardBox>
//       <CardBox height="450px">
//         <BarChart findTime={findTime} setFindTime={setFindTime} />
//       </CardBox>
//     </StatisticsBox>
//   ) : detailCategoryId === 3 ? (
//     <StatisticsBox>
//       <CheckCalendarBox>
//         <CheckDayForm findTime={findTime} setFindTime={setFindTime} />
//       </CheckCalendarBox>
//       <CardBox margin="center">
//         <HeartRateChart findTime={findTime} setFindTime={setFindTime} />
//       </CardBox>

//       <CardBox>
//         <MedBarChart findTime={findTime} setFindTime={setFindTime} />
//       </CardBox>
//     </StatisticsBox>
//   ) : detailCategoryId === 7 ? (
//     <StatisticsBox>
//       <CheckCalendarBox sx={{ height: "60px" }}>
//         <CheckDayForm findTime={findTime} setFindTime={setFindTime} />
//       </CheckCalendarBox>
//       <Box
//         sx={{
//           width: "400px",
//           height: "300px",
//           display: "flex",
//           alignItems: "center",
//         }}
//       >
//         <ChallengeAppearMap
//           width="400px"
//           height="300px"
//           findTime={findTime}
//           setFindTime={setFindTime}
//         />
//       </Box>
//       <CardBox>
//         <AppearBarChart findTime={findTime} setFindTime={setFindTime} />
//       </CardBox>
//     </StatisticsBox>
//   ) : (
//     <StatisticsBox>
//       <CheckCalendarBox>
//         <CheckDayForm findTime={findTime} setFindTime={setFindTime} />
//       </CheckCalendarBox>
//       <CardBox height="50px"></CardBox>

//       <CardBox height="250px">
//         <HabitBarChart findTime={findTime} setFindTime={setFindTime} />
//       </CardBox>
//     </StatisticsBox>
//   );
// }
