import React from "react";
import { useNavigate } from "react-router-dom";
import Box from "@mui/material/Box";
import { styled } from "@mui/system";

import Text from "component/atom/Text";
// import WatchImg from "static/watch.svg";
import WatchImg from "static/watch.svg";

const CardBox = styled(Box)(
  () => `
  width: 230px;
  height: 210px;
  margin: 10px 20px;
  background-color: white;
  border-radius: 10px;
  `
);

const ImageBox = styled(Box)(
  () => `
  width: 210px;
  height: 130px;
  margin: 10px 10px 5px 10px;
  border-radius: 8px;
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
    <CardBox onClick={onClickCard}>
      <ImageBox>
        <img src={imgurl} width="100%" height="100%" />
      </ImageBox>
      <FilterBox>
        <img src={WatchImg}/>
      </FilterBox>
      <TextBox>
        <Text size="16px" weight="semibold">
          {title}
        </Text>
        <Text my="3" size="10px" color="grey">
          {startTime.substr(0, 10)} - {endTime.substr(0, 10)}
        </Text>
      </TextBox>
    </CardBox>
  ) : (
    <CardBox onClick={onClickCard}>
      <ImageBox>
        <img src={imgurl} width="100%" height="100%" />
      </ImageBox>
      <FilterBox />
      <TextBox>
        <Text size="16px" weight="semibold">
          {title}
        </Text>
        <Text my="3" size="10px" color="grey">
          {startTime.substr(0, 10)} - {endTime.substr(0, 10)}
        </Text>
      </TextBox>
    </CardBox>
  );
}
