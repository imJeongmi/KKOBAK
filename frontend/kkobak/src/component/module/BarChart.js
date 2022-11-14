import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import { requestStatBpm } from "api/statbpm";

import {
  BarChart,
  XAxis,
  YAxis,
  Cell,
  Bar,
  Tooltip,
  Legend,
  CartesianGrid,
} from "recharts";
import moment from "moment";
import { useParams } from "react-router-dom";
import Text from "component/atom/Text";

export default function BarChartPage({ findTime }) {
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
    <Box sx={{ display: "flex", flexDirection: "column" }}>
      <Text weight="semibold" size="15px" my="3">
        {"전체 통계 조회"}
      </Text>

      <Box sx={{ fontFamily: "SUIT-Medium", fontSize: "12px" }}>
        <BarChart width={400} height={100} data={bpm}>
          <XAxis />
          <YAxis />
          <Bar
            type="monotone"
            dataKey="bpm"
            stroke="black"
            // activeDot={{ r: 1 }}
          />
        </BarChart>
      </Box>
    </Box>
  );
}
