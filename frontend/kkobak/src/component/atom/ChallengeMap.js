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

  const [speedDaily, setSpeedDaily] = useState("");
  const [timeDaily, setTimeDaily] = useState("");
  const [totalDistDaily, setTotalDistDaily] = useState("");

  function requestStatGpsSuccess(res) {
    setGps(res.data.gpsList);
    setGpsStat(res.data.gpsList[0]);
    setGpsDaily(res.data);
    setSpeedDaily(res.data.avg_speed.toFixed(2));
    setTimeDaily(res.data.time_len);
    setTotalDistDaily(res.data.total_dist.toFixed(2));
  }

  function requestStatGpsFail(err) {
    setGps([]);
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
      <CardBox>
        <Box
          sx={{
            width: "100%",
            height: "100%",
            backgroundColor: "black",
            position: "relative",
          }}
        >
          <Map
            center={{ lat: 37.5016644, lng: 127.0396081 }}
            style={{
              display: "inline-block",
              width: `${getWidth(width)}`,
              height: `${getHeight(height)}`,
              opacity: 0.4,
            }}
          />
        </Box>
      </CardBox>
      <Box
        sx={{
          width: "100%",
          margin: "165px auto",
          position: "absolute",
          top: "0",
          zIndex: "100",
        }}
      >
        <Text size="15px" weight="light" color="white">
          통계가 없어요 🤔
        </Text>
      </Box>
    </Box>
  ) : (
    <Box
      sx={{
        display: "flex",
        alignItems: "center",
      }}
    >
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
            strokeColor={"#559AD9"} // 선의 색깔입니다
            strokeOpacity={1} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle={"solid"} // 선의 스타일입니다
          />
        </Map>
      </CardBox>
      <Box
        sx={{
          width: "120px",
          textAlign: "center",
        }}
      >
        <Text size="12px" weight="medium">
          평균 속력 : {speedDaily} m/s
        </Text>
        <Text size="10px" mt="10">
          {`진행 시간 : ${timeDaily.substr(1, 1)}시간 ${timeDaily.substr(
            3,
            2
          )}분 ${timeDaily.substr(6, 2)}초 `}
        </Text>
        <Text size="10px" mt="3">
          이동 거리 : {Math.round(0.1 * totalDistDaily) / 100} km
        </Text>
      </Box>
    </Box>
  );
}
