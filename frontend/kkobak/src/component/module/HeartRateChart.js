import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import { requestStatBpm } from "api/statbpm";

import { LineChart, Line, XAxis, YAxis } from "recharts";
import moment from "moment";
import { useParams } from "react-router-dom";
import Text from "component/atom/Text";

export default function LineChartPage({ findTime }) {
  const year = findTime.getFullYear();
  const month = findTime.getMonth() + 1;
  const day = moment(findTime).format("DD");
  const cid = Number(useParams().chlId);

  const [bpm, setBpm] = useState([]);
  const [stat, setStat] = useState([]);

  function requestStatBpmSuccess(res) {
    setBpm(res.data.bpmList);
    setStat(res.data);
  }

  function requestStatBpmFail(err) {
    setBpm([]);
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
    <Box sx={{ display: "flex", alignItems: "center" }}>
      <Box
        sx={{
          width: "320px",
          marginTop: "10px",
          fontFamily: "SUIT",
          fontSize: "11px",
        }}
      >
        <LineChart width={300} height={120} data={bpm}>
          <XAxis dataKey="time" />
          <YAxis domain={[60, 130]} />
          <Line type="linear" dataKey="bpm" stroke="#36577D" dot={false} />
        </LineChart>
      </Box>
      <Box sx={{ width: "120px" }}>
        <Text
          size="12px"
          weight="medium"
        >{`평균 심박수 : ${stat.avgBpm}`}</Text>
        <Text size="10px" mt="10">{`최고 심박수 : ${stat.maxBpm}`}</Text>
        <Text size="10px" mt="3">{`최저 심박수 : ${stat.minBpm}`}</Text>
      </Box>
    </Box>
  );
}
