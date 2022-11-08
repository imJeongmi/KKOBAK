import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import { requestStatBpm } from "api/statbpm";

import { LineChart, Line } from "recharts";
import moment from "moment";

export default function LineChartPage() {
  const data = [
    { name: "Page D", uv: 250, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 200, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 300, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 500, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 200, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 200, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 300, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 200, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 100, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 200, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 500, pv: 2400, amt: 2400 },
  ];

  const [year, setYear] = useState(new Date().getFullYear());
  const [month, setMonth] = useState(new Date().getMonth() + 1);
  const [day, setDay] = useState(moment(new Date()).format("DD"));

  const cid = 83;

  const [bpm, setBpm] = useState("");
  // 현재 data를 직접 작성했지만 api 갖고오면
  // const data = bpm.bpmList
  // 사용하기

  function requestStatBpmSuccess(res) {
    setBpm(res.data);
    console.log(res.data);
  }

  function requestStatBpmFail(err) {
    setBpm([]);
    console.log(err.data);
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
      <LineChart width={600} height={200} data={data}>
        <Line type="monotone" dataKey="uv" stroke="white" />
      </LineChart>
    </Box>
  );
}
