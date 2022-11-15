import React, { useEffect, useState } from "react";
import { Map, Polyline } from "react-kakao-maps-sdk";
import { requestStatGps } from "api/statgps";
import moment from "moment";
import Box from "@mui/material/Box";
import { useParams } from "react-router-dom";
import Text from "component/atom/Text";
import styled from "@emotion/styled";

function getWidth(width) {
  if (!!width) return width;
  else return "90vw";
}

function getHeight(height) {
  if (!!height) return height;
  else return "350px";
}

const CardBox = styled(Box)(
  ({ height, margin }) => `
    width: 300px;
    height: 150px;
    margin: 0 auto;
    border-radius: 20px;
    overflow: hidden;
  `
);

export default function ChallengeMap({ findTime, width, height }) {
  const year = findTime.getFullYear();
  const month = findTime.getMonth() + 1;
  const day = moment(findTime).format("DD");
  const cid = Number(useParams().chlId);

  const [gps, setGps] = useState([]);
  const [gpsStat, setGpsStat] = useState({ lat: 37.5016644, lng: 127.0396081 });
  const [gpsDaily, setGpsDaily] = useState([]);

  function requestStatGpsSuccess(res) {
    setGps(res.data.gpsList);
    setGpsStat(res.data.gpsList[0]);
    setGpsDaily(res.data);
  }

  function requestStatGpsFail(err) {
    setGps([]);
    // console.log(err.data);
    setGpsStat([]);
    setGpsDaily([]);
  }

  useEffect(() => {
    requestStatGps(
      year,
      month,
      day,
      cid,
      requestStatGpsSuccess,
      requestStatGpsFail
    );
  }, [year, month, day, cid]);

  // 역삼역 위치 { lat: 37.5016644, lng: 127.0396081 }
  return gpsStat === undefined ? (
    <Box>
      <Map
        center={{ lat: 37.5016644, lng: 127.0396081 }}
        style={{
          display: "inline-block",
          width: `${getWidth(width)}`,
          height: `${getHeight(height)}`,
        }}
      >
        <Polyline
          path={gps}
          strokeWeight={5} // 선의 두께 입니다
          strokeColor={"#000000"} // 선의 색깔입니다
          strokeOpacity={1} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
          strokeStyle={"solid"} // 선의 스타일입니다
        />
      </Map>
      <Box
        sx={{
          height: "100%",
        }}
      >
        <Text size="10px">해당 통계가 없습니다.</Text>
      </Box>
    </Box>
  ) : (
    <Box sx={{ display: "flex" }}>
      <CardBox>
        <Map
          center={gpsStat}
          style={{
            display: "inline-block",
            width: `${getWidth(width)}`,
            height: `${getHeight(height)}`,
          }}
        >
          <Polyline
            path={gps}
            strokeWeight={5} // 선의 두께 입니다
            strokeColor={"#000000"} // 선의 색깔입니다
            strokeOpacity={1} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle={"solid"} // 선의 스타일입니다
          />
        </Map>
      </CardBox>
      <Box
        sx={{
          height: "100%",
        }}
      >
        <Text size="10px">평균 속력 : {gpsDaily.avg_speed}</Text>
        <Text size="10px">진행 시간 : {gpsDaily.time_len}</Text>
        <Text size="10px">이동 거리 : {gpsDaily.total_dist}</Text>
      </Box>
    </Box>
  );
}
