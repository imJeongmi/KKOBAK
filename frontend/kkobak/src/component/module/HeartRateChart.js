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
    <Box sx={{ display: "flex" }}>
      <Box>
        <Text weight="semibold" color="red" size="18px" mt="25">
          {"심박수 정보"}
        </Text>
        <Box
          sx={{ marginTop: "50px", display: "flex", flexDirection: "column", alignItems: "start" }}
        >
          <Text my="3" weight="medium"> {`평균 심박수 : ${stat.avgBpm}`}</Text>
          <Text my="3" weight="medium"> {`최고 심박수 : ${stat.maxBpm}`}</Text>
          <Text my="3" weight="medium"> {`최저 심박수 : ${stat.minBpm}`}</Text>
        </Box>
      </Box>
      <Box sx={{fontFamily: "SUIT", marginTop: "20px"}}>
        <LineChart width={600} height={180} data={bpm}>
          <XAxis />
          {/* 데이터 타임 오는 시간 오후 4시가 16시가 아니라 04시로 옴. 
          time이 현재 2022-11-08 04:28:43으로 오는데 x축에 표시하기 위해 16:28:43 이렇게 객체 안에 하나 더 추가해줘도 좋음. checktime이런거 하나 추가해서*/}
          {/* <XAxis dataKey="time" /> */}
          <YAxis domain={[60, 160]} />
          {/* <Tooltip  /> */}
          {/* <Legend /> */}
          <Line
            type="monotone"
            dataKey="bpm"
            stroke="black"
            // activeDot={{ r: 1 }}
          />
        </LineChart>
      </Box>
    </Box>
  );
}
