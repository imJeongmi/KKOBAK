import React from "react";
import Box from "@mui/material/Box";
import Text from "../atom/Text";
import Watch from "../../static/watch.svg";
import TagLabel from "../atom/TagLabel";

export default function ChallengeCard(props) {
  return (
    <div style={{ marginLeft: 35, marginTop: 12 }}>
      <Box
        sx={{
          width: "260px",
          height: "260px",
          backgroundColor: "white",
          borderRadius: 2,
          boxShadow: 5,
        }}
      >
        <Box sx={{ height: "10px" }}></Box>
        <Box
          sx={{
            marginX: "10px",
            width: "240px",
            height: "140px",
            backgroundColor: "gray",
            borderRadius: 2,
            overflow: "hidden",
          }}
        >
          {/* <img src={props.src} width="100%" /> */}
          <img
            src="https://t1.daumcdn.net/thumb/R720x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/2xMI/image/eRlaPLwEH3RjexrX_uVmse2g-LU.jpg"
            width="100%"
          />
        </Box>
        <Box>
          <Box
            sx={{
              marginLeft: "10px",
              width: "170px",
              height: "50px",
              backgroundColor: "white",
              marginTop: "5px",
              float: "left",
            }}
          >
            {/* 태그들 앞에 3개만 보이도록 수정 map 활용 태그 모양 수정 필요*/}
            <TagLabel>{props.tag}</TagLabel>
          </Box>
          <Box
            sx={{
              marginRight: "10px",
              marginTop: "10px",
              width: "40px",
              height: "40px",
              float: "right",
            }}
          >
            {/* 워치 이용 판단 */}
            <img src={Watch} width={34} height={34} />
          </Box>
        </Box>
        <Box sx={{ marginLeft: "10px" }}>
          <Box height="55px" />
          <Text>{props.title}</Text>
          <Text size="m">매일 아침 러닝!</Text>
          <br />
          <Text>{props.period}</Text>
          <Text size="s">기간</Text>
        </Box>
      </Box>
    </div>
  );
}
