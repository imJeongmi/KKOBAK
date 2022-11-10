import React, { useEffect, useState } from "react";
import { Map, Polyline } from "react-kakao-maps-sdk";
import { requestStatGps } from "api/statgps";
import moment from "moment";
import Box from "@mui/material/Box";
import { useParams } from "react-router-dom";

export default function ChallengeMap({ findTime }) {
  const year = findTime.getFullYear();
  const month = findTime.getMonth() + 1;
  const day = moment(findTime).format("DD");
  const cid = Number(useParams().chlId);

  const [gps, setGps] = useState([]);
  const [gpsStat, setGpsStat] = useState({ lat: 37.5016644, lng: 127.0396081 });

  function requestStatGpsSuccess(res) {
    setGps(res.data.gpsList);
    setGpsStat(res.data.gpsList[0]);
  }

  function requestStatGpsFail(err) {
    setGps([]);
    // console.log(err.data);
    setGpsStat([]);
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
    <Map
      center={{ lat: 37.5016644, lng: 127.0396081 }}
      style={{ display: "inline-block", width: "90vw", height: "350px" }}
    >
      <Polyline
        path={gps}
        strokeWeight={5} // 선의 두께 입니다
        strokeColor={"#000000"} // 선의 색깔입니다
        strokeOpacity={1} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle={"solid"} // 선의 스타일입니다
      />
    </Map>
  ) : (
    <Map
      center={gpsStat}
      style={{ display: "inline-block", width: "50vw", height: "300px" }}
    >
      <Polyline
        path={gps}
        strokeWeight={5} // 선의 두께 입니다
        strokeColor={"#000000"} // 선의 색깔입니다
        strokeOpacity={1} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle={"solid"} // 선의 스타일입니다
      />
    </Map>
  );
}
