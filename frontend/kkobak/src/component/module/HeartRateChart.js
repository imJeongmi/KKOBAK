import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import { requestStatBpm } from "api/statbpm";

import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  CartesianGrid,
} from "recharts";
import moment from "moment";
import { useParams } from "react-router-dom";

export default function LineChartPage() {
  const year = new Date().getFullYear();
  const month = new Date().getMonth() + 1;
  const day = moment(new Date()).format("DD");
  const cid = Number(useParams().chlId);

  const [bpm, setBpm] = useState([]);
  const [stat, setStat] = useState([]);

  function requestStatBpmSuccess(res) {
    setBpm(res.data.bpmList);
    setStat(res.data);
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
    <Box sx={{ display: "flex", marginTop: "15px" }}>
      <Box>
        <Box>심박수 정보</Box>
        <Box sx={{ marginTop: "70px" }}>
          <Box sx={{ display: "flex" }}>
            <Box sx={{ float: "left" }}> 평균 심박수 : </Box>
            <Box>{stat.avgBpm}</Box>
          </Box>
          <Box sx={{ display: "flex" }}>
            <Box sx={{ float: "left" }}> 최고 심박수 : </Box>
            <Box>{stat.maxBpm}</Box>
          </Box>
          <Box sx={{ display: "flex" }}>
            <Box sx={{ float: "left" }}> 최저 심박수 : </Box>
            <Box>{stat.minBpm}</Box>
          </Box>
        </Box>
      </Box>
      <Box>
        <LineChart width={600} height={180} data={bpm}>
          <XAxis />
          <YAxis domain={[60, 160]} />
          {/* <Tooltip  /> */}
          {/* <Legend /> */}
          <Line
            type="monotone"
            dataKey="bpm"
            stroke="black"
            // activeDot={{ r: 3 }}
          />
        </LineChart>
      </Box>
    </Box>
  );
}
