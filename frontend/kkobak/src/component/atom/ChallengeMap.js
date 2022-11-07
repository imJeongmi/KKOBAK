import React from "react";
import { Map, Polyline } from "react-kakao-maps-sdk";

export default function ChallengeMap() {
  return (
    <Map
      center={{ lat: 33.452344169439975, lng: 126.56878163224233 }}
      style={{ display: "inline-block", width: "50%", height: "360px" }}
    >
      <Polyline
        path={[
          [
            { lat: 33.452344169439975, lng: 126.56878163224233 },
            { lat: 33.452739313807456, lng: 126.5709308145358 },
            { lat: 33.45178067090639, lng: 126.572688693875 },
          ],
        ]}
        strokeWeight={5} // 선의 두께 입니다
        strokeColor={"#000000"} // 선의 색깔입니다
        strokeOpacity={1} // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle={"solid"} // 선의 스타일입니다
      />
    </Map>)
}