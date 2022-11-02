import React from "react";
import Box from "@mui/material/Box";
import { styled } from "@mui/system";

import Text from "component/atom/Text";
import TagLabel from "component/atom/TagLabel";
import Watch from "static/watch.svg";

const CardBox = styled(Box)(
  () => `
  width: 260px;
  height: 260px;
  margin: 20px 10px;
  background-color: white;
  border-radius: 8px;
  `
);

const ImageBox = styled(Box)(
  () => `
  width: 240px;
  height: 140px;
  margin: 10px;
  border-radius: 8px;
  overflow: hidden;
  `
);

const FilterBox = styled(Box)(
  () => `
  // width: 170px;
  height: 30px;
  margin: 5px 15px;
  display: flex;
  justify-content: space-between;
  `
);

const TextBox = styled(Box)(
  () => `
  margin: 5px 15px;
  display: flex;
  flex-direction: column;
  align-items: start;
  `
);

export default function ChallengeCard(props) {
  return (
    <CardBox>
      <ImageBox>
        {/* <img src={props.src} width="100%" height="100%" /> */}
        <img
          src="https://t1.daumcdn.net/thumb/R720x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/2xMI/image/eRlaPLwEH3RjexrX_uVmse2g-LU.jpg"
          width="100%"
          height="100%"
        />
      </ImageBox>
      <FilterBox>
        {/* 태그들 앞에 3개만 보이도록 수정 map 활용 태그 모양 수정 필요*/}
        <TagLabel>{props.tag}</TagLabel>
        <img src={Watch} width="25px" />
      </FilterBox>
      <TextBox>
        {/* <Text>{props.title}</Text> */}
        <Text size="m" weight="medium">매일 아침 러닝!</Text>
        {/* <Text my="2">{props.period}</Text> */}
        <Text my="5" size="12px" color="grey">
          기간
        </Text>
      </TextBox>
    </CardBox>
  );
}
