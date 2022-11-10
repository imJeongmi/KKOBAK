import React from "react";
import { useNavigate } from "react-router-dom";
import Box from "@mui/material/Box";
import { styled } from "@mui/system";

import Text from "component/atom/Text";
// import WatchImg from "static/watch.svg";
import WatchImg from "static/watch.svg";

import Watch from "@mui/icons-material/Watch";

const CardBox = styled(Box)(
  () => `
  width: 230px;
  height: 230px;
  margin: 0px 20px;
  background-color: white;
  border-radius: 10px;
  Box-shadow: 12px 12px 2px 1px;
  `
);

const ImageBox = styled(Box)(
  () => `
  width: 220px;
  height: 160px;
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
  margin : 5px 5px 0px 5px;
  overflow: hidden;
  `
);

const FilterBox = styled(Box)(
  () => `
  height: 20px;
  margin: 0 10px;
  display: flex;
  justify-content: end;
  `
);

const TextBox = styled(Box)(
  () => `
  height: 40px;
  margin: 0 15px;
  display: flex;
  flex-direction: column;
  align-items: start;
  `
);

export default function ChallengeCard({
  chlId,
  imgurl,
  tagList,
  title,
  contents,
  categoryId,
  startTime,
  endTime,
  alarm,
  watch,
}) {
  const navigate = useNavigate();

  function onClickCard() {
    navigate(`/myChallenge/${chlId}`);
  }

  return watch ? (
    <CardBox onClick={onClickCard} style={{ backgroundColor: "#99B9D6" }}>
      <ImageBox>
        <img src={imgurl} width="100%" height="100%" />
      </ImageBox>
      <FilterBox>
        <img src={WatchImg} />
      </FilterBox>
      <TextBox>
        <Text size="16px" weight="semibold" color="white">
          {title}
        </Text>
        <Text my="3" size="10px" color="white">
          {startTime.substr(0, 10)} - {endTime.substr(0, 10)}
        </Text>
      </TextBox>
    </CardBox>
  ) : (
    <CardBox onClick={onClickCard} style={{ backgroundColor: "#F17E7F" }}>
      <ImageBox>
        <img src={imgurl} width="100%" height="100%" />
      </ImageBox>
      <FilterBox />
      <TextBox>
        <Text size="16px" weight="semibold" color="white">
          {title}
        </Text>
        <Text my="3" size="10px" color="white">
          {startTime.substr(0, 10)} - {endTime.substr(0, 10)}
        </Text>
      </TextBox>
    </CardBox>
  );
}
