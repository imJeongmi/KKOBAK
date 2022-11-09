import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import { styled } from "@mui/system";

import char from "static/char.png";
import smile from "static/emoji/smile.png";

import { requestMyChallengeStatistics } from "api/userApi";

const Text = styled("span")(
  () => `
  font-family: "SUIT-Medium";
  font-size: 16px;
  color: #333333;
  `
);

const WidgetBox = styled(Box)(
  () => `
  width: 280px;
  height: 140px;
  background-color: #F0F6FB;
  border-radius: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  `
);

const ItemBox = styled(Box)(
  () => `
  height: 37px;
  display: flex;
  align-items: center;
  `
);

export default function WidgetStatsIng() {
  const [stats, setStats] = useState([]);

  useEffect(() => {
    requestMyChallengeStatistics(
      requestMyChallengeStatisticsSuccess,
      requestMyChallengeStatisticsFail
    );
  }, []);

  function requestMyChallengeStatisticsSuccess(res) {
    setStats(res.data);
  }

  function requestMyChallengeStatisticsFail(err) {
    setStats([]);
  }

  return (
    <WidgetBox>
      <ItemBox>
        <Text>{`성공한 챌린지 : \u00A0`}</Text>
        <img src={smile} style={{ width: "30px", float: "left" }} />
        <Text>{`\u00A0 × \u00A0`}</Text>
        {stats.totalChl}
      </ItemBox>
      <ItemBox>
        <Text>{`진행중인 챌린지 : \u00A0`}</Text>
        <img src={char} style={{ width: "30px", float: "left" }} />
        <Text>{`\u00A0 × \u00A0`}</Text>
        {stats.ingChl}
      </ItemBox>
    </WidgetBox>
  );
}
