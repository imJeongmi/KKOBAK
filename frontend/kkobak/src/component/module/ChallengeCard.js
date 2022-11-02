import React from "react";
import Box from "@mui/material/Box";
import Text from "../atom/Text";
import WatchImg from "../../static/watch.svg";
import TagLabel from "../atom/TagLabel";

export default function ChallengeCard({
  imgurl,
  tagList,
  title,
  startTime,
  endTime,
  watch,
}) {
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
          <img src={imgurl} width="100%" />
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
            <TagLabel>{tagList[0]}</TagLabel>
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
            <Box>
              <img src={WatchImg} width={34} height={34} />
            </Box>
          </Box>
        </Box>
        <Box sx={{ marginLeft: "10px" }}>
          <Box height="55px" />
          <Text size="m">{title}</Text>
          <br />
          <Text>{startTime}</Text>
          <Text>{endTime}</Text>
        </Box>
      </Box>
    </div>
  );
}
