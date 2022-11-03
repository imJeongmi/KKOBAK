import React from "react";
import Box from "@mui/material/Box";
import { styled } from "@mui/system";

import Text from "component/atom/Text";
import TagLabel from "component/atom/TagLabel";
import WatchImg from "static/watch.svg";
import { useNavigate } from "react-router-dom";

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

export default function ChallengeCard({
  id,
  imgurl,
  tagList,
  title,
  startTime,
  endTime,
  watch,
}) {
  const navigate = useNavigate();

  function onClickCard() {
    navigate(`/myChallenge/${id}`);
  }

  return (
    <CardBox onClick={onClickCard}>
      <ImageBox>
        {/* <img src={props.src} width="100%" height="100%" /> */}
        <img src={imgurl} width="100%" height="100%" />
      </ImageBox>
      <FilterBox>
        <TagLabel>{tagList[0]}</TagLabel>
        <img src={WatchImg} width="25px" />
      </FilterBox>
      <TextBox>
        <Text size="m" weight="medium">
          {title}
        </Text>
        <Text my="5" size="12px" color="grey">
          {startTime.substr(0, 10)} - {endTime.substr(0, 10)}
        </Text>
      </TextBox>
    </CardBox>
  );
}
