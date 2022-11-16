import React, { useEffect, useState } from "react";
import { Map, Polyline } from "react-kakao-maps-sdk";
import { requestStatGps } from "api/statgps";
import moment from "moment";
import Box from "@mui/material/Box";
import { useParams } from "react-router-dom";
import Text from "component/atom/Text";
import styled from "@emotion/styled";

import { requestUserInfo, requestMyChallengeDetail } from "api/userApi";

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
    width: 400px;
    height: 400px;
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

  const [checkLat, setCheckLat] = useState("");
  const [checkLng, setCheckLng] = useState("");

  //   const [gpsStat, setGpsStat] = useState({ lat: 37.5016644, lng: 127.0396081 });

  function requestMyChallengeDetailSuccess(res) {
    const data = res.data;
    setCheckLng(data.unit.split(",")[0]);
    setCheckLat(data.unit.split(",")[1]);
  }

  function requestMyChallengeDetailFail(res) {}

  useEffect(() => {
    requestMyChallengeDetail(
      cid,
      requestMyChallengeDetailSuccess,
      requestMyChallengeDetailFail
    );
  }, []);

  //   ({lat: {checkLat}, lng: {checkLng}})
  // 37.5012767241426, 127.039600248343
  // 역삼역 위치 { lat: 37.5016644, lng: 127.0396081 }
  return (
    <Box sx={{ display: "flex", flexDirection: "column" }}>
      <Box sx={{ height: "30px", marginTop: "100px" }}>출석 위치 입니다.</Box>
      <CardBox>
        <Map
          center={{ lat: checkLat, lng: checkLng }}
          style={{
            display: "inline-block",
            width: "400px",
            height: "300px",
          }}
        >
          <Polyline
            path={[{ lat: checkLat, lng: checkLng }]}
            strokeWeight={100} // 선의 두께 입니다
            strokeColor={"#4b79a6"} // 선의 색깔입니다
            strokeOpacity={0.3} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle={"solid"} // 선의 스타일입니다
          />
        </Map>
      </CardBox>
    </Box>
  );
}
