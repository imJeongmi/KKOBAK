import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import Box from "@mui/material/Box";
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

import { requestAppearStat } from "api/Challenge";

export default function BarChartPage() {
  const cid = Number(useParams().chlId);

  const [stat, setStat] = useState([]);

  function requestAppearStatSuccess(res) {
    setStat(res.data);
  }

  function requestAppearStatFail(err) {
    setStat([]);
    console.log(err);
  }

  useEffect(() => {
    requestAppearStat(cid, requestAppearStatSuccess, requestAppearStatFail);
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
        <XAxis dataKey="date" />
        <YAxis dataKey="cnt" domain={[0, 2]} />
        <Bar type="monotone" dataKey="cnt" fill="#F0F6FB" stroke="#578AAF" />
      </BarChart>
    </Box>
  );
}
