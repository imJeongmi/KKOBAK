import React from "react";
import Box from "@mui/material/Box";

// export default function HeartRate() {
//   return <Box>안녕하세요</Box>;
// }

import { LineChart, Line } from "recharts";

export default function renderLineChart() {
  const data = [
    { name: "Page D", uv: 250, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 200, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 300, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 200, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 200, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 200, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 200, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 200, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 200, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 200, pv: 2400, amt: 2400 },
    { name: "Page D", uv: 200, pv: 2400, amt: 2400 },
  ];
  return (
    <Box>
      <Box>심박수</Box>
      <LineChart width={600} height={200} data={data}>
        <Line type="monotone" dataKey="uv" stroke="white" />
      </LineChart>
    </Box>
  );
}
