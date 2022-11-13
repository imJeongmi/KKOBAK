import React, { useEffect, useState } from "react";
import { Box, styled } from "@mui/material";


import Text from "component/atom/Text";
import Button from "component/atom/TextButton"
import MainBox from "component/atom/MainBox";
import GroupChallengeForm from "component/module/GroupChallengeForm";
import initial from "static/initial.png";

import { getDetailCategoryList } from "api/Category";
import { registerChallenge } from "api/Challenge";
import axios from "axios";


import { getMyKkobakList } from "api/userApi";

const ButtonBox = styled(Box)(
  () => `
  width: 95%;
  margin-top : 50px;
  `
);

export default function GroupChallengeRegister() {
  const [category, setCategory] = useState(1);
  const [detailCategory, setDetailCategory] = useState(1);
  const [detailCategoryList, setDetailCategoryList] = useState([]);
  // const [imgSrc, setImgSrc] = useState(initial);
  // default imgsrc로 등록해둠!
  const [imgSrc, setImgSrc] = useState(
    "https://initpjtbucket.s3.ap-northeast-2.amazonaws.com/images/95479caa-be3f-4473-95a5-f1d07f2ffe75.png"
  );
  const [watch, setWatch] = useState(true);
  const [kkobak, setKkobak] = useState(0);
  const [startTime, setStartTime] = useState(new Date());
  const [endTime, setEndTime] = useState(new Date());
  const [alarm, setAlarm] = useState("00:00");
  const [title, setTitle] = useState("");
  const [contents, setContents] = useState("");
  const [unit, setUnit] = useState("");
  const [kkobakCount, setKkobakCount] = useState(0)

  function getMyKkobakListSuccess(res) {
    setKkobakCount(res.data.length);
  }

  function getMyKkobakListFail(err) {
  }

  useEffect(() => {
    getMyKkobakList(getMyKkobakListSuccess, getMyKkobakListFail);
  }, []);

  const [goal, setGoal] = useState("");

  function getDetailCategoryListSuccess(res) {
    setDetailCategoryList(res.data);
  }

  function getDetailCategoryListFail(err) {}

  useEffect(() => {
    console.log(category);
    getDetailCategoryList(
      category,
      getDetailCategoryListSuccess,
      getDetailCategoryListFail
    );
  }, [category]);

  function registerSuccess() {
    console.log("성공");
    console.log(unit);
  }

  function registerFail(err) {
    console.log(err);
  }

  function changeAddressToDot(goal) {
    console.log(goal)
    axios
      .get(
        `https://dapi.kakao.com/v2/local/search/address.json?query=${goal}`,
        {
          headers: {
            Authorization: `KakaoAK ${process.env.REACT_APP_KAKAO_REST_API}`,
          },
        }
      )
      .then((res) => {
        const location = res.data.documents[0];
        setUnit(`${location?.address?.x},${location?.address?.y}`);
      });
  }

  function changeUnit(category, detailCategory) {
    if (category === "2" && detailCategory === "7") {
      const add = changeAddressToDot(goal);
      return add
    } else if (category === "2") {
      setUnit("회");
    } else if (category === "1" && detailCategory === "1") {
      setUnit("Km");
    } else if (category === "1" && detailCategory === "2") {
      setUnit("Km");
    } else if (category === "1" && detailCategory === "3") {
      setUnit("분");
    }
  }
  // 유효성 검사
  function checkAll() {
    if (imgSrc === initial) {
      alert("챌린지 이미지를 등록해주세요");
      return;
    }

    if (!category) {
      alert("챌린지 카테고리를 선택해주세요");
      return false
    }
    
    if (!detailCategory) {
      alert("챌린지 상세 카테고리를 선택해주세요");
      return false
    }
    if (!title) {
      alert("챌린지 제목을 입력해주세요");
      return false
    }

    if (!contents) {
      alert("챌린지 상세 설명을 입력해주세요");
      return false
    }
    let check = /^[0-9]+$/;
    if (!goal) {
      alert("챌린지 목표를 입력해주세요");
      return false
    }
    if (detailCategory !== "7" && !check.test(goal)) {
      alert("챌린지 목표는 숫자로 입력해주세요");
      return false;
    } 
    if (kkobak === "1" && kkobakCount >= 3 ) {
      alert("꼬박 챌린지는 최대 3개만 생성 가능합니다");
      return false;
    }
    return true;
  }

  function register(e) {
    e.preventDefault();
    if (!checkAll()) {
      return;
    }
    if (category === "1") {
      changeUnit(category, detailCategory);
      registerChallenge(
        alarm,
        0,
        category,
        contents,
        detailCategory,
        endTime,
        goal,
        imgSrc,
        kkobak,
        "100",
        "",
        "1",
        startTime,
        [],
        title,
        unit,
        watch,
        registerSuccess,
        registerFail
      );
    } else if (category === "2" && detailCategory === "7") {
      registerChallenge(
        alarm,
        0,
        category,
        contents,
        detailCategory,
        endTime,
        1,
        imgSrc,
        kkobak,
        "100",
        "",
        1,
        startTime,
        [],
        title,
        unit,
        watch,
        registerSuccess,
        registerFail
      );
    } else {
      changeUnit(category, detailCategory);
      // console.log(unit)
      registerChallenge(
        alarm,
        0,
        category,
        contents,
        detailCategory,
        endTime,
        1,
        imgSrc,
        kkobak,
        "100",
        "",
        1,
        startTime,
        [],
        title,
        unit,
        watch,
        registerSuccess,
        registerFail
      );
    }
  }

  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "space-between",
      }}
    >
      <Box sx={{ margin: "0 auto" }}>
        <Text size="m" weight="semibold" mt="30" my="15">
          다 같이 챌린지 만들기
        </Text>
        <MainBox width="80" height="80vh" flexDir="col">
          <GroupChallengeForm
            imgSrc={imgSrc}
            category={category}
            detailCategory={detailCategory}
            detailCategoryList={detailCategoryList}
            title={title}
            contents={contents}
            startTime={startTime}
            endTime={endTime}
            alarm={alarm}
            watch={watch}
            goal={goal}
            kkobak={kkobak}
            setImgSrc={setImgSrc}
            setTitle={setTitle}
            setCategory={setCategory}
            setDetailCategory={setDetailCategory}
            setContents={setContents}
            setStartTime={setStartTime}
            setEndTime={setEndTime}
            setAlarm={setAlarm}
            setWatch={setWatch}
            setGoal={setGoal}
            setKkobak={setKkobak}
            register={register}
            setUnit={setUnit}
            changeUnit={changeUnit}
          ></GroupChallengeForm>
          <ButtonBox>
            <Button size="m" my="5" onClick={register}>
              챌린지 등록
            </Button>
          </ButtonBox>
        </MainBox>
      </Box>
    </Box>
  );
}
