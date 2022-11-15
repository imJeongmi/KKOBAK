import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import { requestTotalMedStat } from "api/Challenge";

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
  const cid = Number(useParams().chlId);

  const [stat, setStat] = useState([]);

  function requestTotalMedStatSuccess(res) {
    setStat(res.data);
  }

  function requestTotalMedStatFail(err) {
    setStat([]);
  }

  useEffect(() => {
    requestTotalMedStat(
      cid,
      requestTotalMedStatSuccess,
      requestTotalMedStatFail
    );
  }, [cid]);

  return (
    <Box sx={{ display: "flex", flexDirection: "column" }}>
      <Text weight="semibold" size="15px" my="3">
        {"전체 통계 조회"}
      </Text>

      <Box sx={{ fontFamily: "SUIT-Medium", fontSize: "12px" }}>
        <BarChart width={400} height={100} data={stat}>
          <XAxis dataKey="day" />
          <YAxis />
          <Bar type="monotone" dataKey="timelen" stroke="black" />
        </BarChart>
      </Box>
    </Box>
  );
}
