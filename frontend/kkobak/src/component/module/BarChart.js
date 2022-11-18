import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import { requestTotalRunStat } from "api/Challenge";

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

  function requestTotalRunStatSuccess(res) {
    setStat(res.data);
  }

  function requestTotalRunStatFail(err) {
    setStat([]);
  }

  useEffect(() => {
    requestTotalRunStat(
      cid,
      requestTotalRunStatSuccess,
      requestTotalRunStatFail
    );
  }, [cid]);

  return (
    <Box
      sx={{
        marginTop: "10px",
        display: "flex",
        alignItems: "center",
        fontFamily: "SUIT",
        fontSize: "11px",
      }}
    >
      <BarChart width={410} height={100} data={stat}>
        <XAxis dataKey="day" />
        <YAxis />
        <Bar type="monotone" dataKey="dist" fill="#F0F6FB" stroke="#578AAF" />
      </BarChart>
    </Box>
  );
}
