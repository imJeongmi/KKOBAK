import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import { requestStatBpm } from "api/statbpm";

import { LineChart, Line } from "recharts";
import moment from "moment";
import { useParams } from "react-router-dom";

export default function LineChartPage() {
  const [year, setYear] = useState(new Date().getFullYear());
  const [month, setMonth] = useState(new Date().getMonth() + 1);
  const [day, setDay] = useState(moment(new Date()).format("DD"));

  const cid = Number(useParams().chlId);

  // 갖고오는 거 maxBpm, minBpm, avgBpm 있음.
  // 심박수 더 변화 심하게 만들기
  // 챌린지 생성에서 심박수 측정인 챌린지 인 경우에만 뜨도록 하기
  // 현재는 stat화면 다 똑같은데(id 만 다르게 같은 화면이지만 다른 아이디 갖은 상태로)
  // 카테고리별로 보여지는 화면 달라야 함.

  const [bpm, setBpm] = useState([]);

  function requestStatBpmSuccess(res) {
    setBpm(res.data.bpmList);
    console.log(res.data.bpmList);
  }

  function requestStatBpmFail(err) {
    setBpm([]);
    // console.log(err.data);
  }

  useEffect(() => {
    requestStatBpm(
      year,
      month,
      day,
      cid,
      requestStatBpmSuccess,
      requestStatBpmFail
    );
  }, [year, month, day, cid]);

  return (
    <Box>
      <Box>심박수</Box>
      <LineChart width={600} height={200} data={bpm}>
        <Line type="monotone" dataKey="bpm" stroke="white" />
      </LineChart>
    </Box>
  );
}
