import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import styled from "@emotion/styled";
import Pagination from "@mui/material/Pagination";
import Stack from "@mui/material/Stack";

import ChallengeCard from "component/module/ChallengeCard";
import Button from "component/atom/TextButton";
import MainBox from "component/atom/MainBox";
import EmptyChallenge from "component/page/EmptyChallenge";

import { fetchMyChallengeList, fetchMyChallengePageCnt } from "api/userApi";
import { useNavigate } from "react-router-dom";

import { ToggleButton, ToggleButtonGroup } from "@mui/material";

import {
  requestChallengeUseWatch,
  requestChallengeNoUseWatch,
  fetchWatchMyChallengePageCnt,
  fetchNoWatchMyChallengePageCnt,
} from "api/Challenge";

import WatchImage from "static/watch.png";
import NoWatchImage from "static/noWatch.png";

const ToggleBox = styled(ToggleButtonGroup)(
  () => `
width: 130px;
height: 32px;
background-color : white;
    `
);

const WatchToggleBox = styled(Box)(
  () => `
  width: 90%;
  margin: 20px 20px 0 20px;
  display: flex;
  justify-content: start
  `
);

const ChallengeListBox = styled(Box)(
  () => `
  width: 100%;
  height: 60vh;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;
  margin-top: 30px;
  `
);

const ButtonBox = styled(Box)(
  () => `
  width: 95%;
  height: 35px;
  display: flex;
  justify-content: end;
  marginTop: 15px;
  `
);

const PageBox = styled(Box)(
  () => `
  margin: 5px auto;
  `
);

export default function MyChallengeCardList() {
  const [MyChallengeList, setMyChallengeList] = useState([]);
  const [TotalMyPage, setMyPageNation] = useState([]);
  const [WatchTotalMyPage, setWatchTotalMyPageNation] = useState([]);
  const [WatchNoTotalMyPage, setWatchNoTotalMyPageNation] = useState([]);
  const [page, setPage] = useState(1);
  const navigate = useNavigate();
  const handlePage = (event) => {
    const nowPageInt = parseInt(event.target.outerText);
    setPage(nowPageInt);
  };

  const [MyChallengeFilterList, setMyChallengeFilterList] = useState([]);
  const [MyChallengeNoFilterList, setMyChallengeNoFilterList] = useState([]);

  // console.log(MyChallengeFilterList);

  const [filter, setFilter] = useState("all");
  const handleFilter = (event, newFilter) => {
    setFilter(newFilter);
  };

  function fetchMyChallengeListSuccess(res) {
    setMyChallengeList(res.data);
  }

  function fetchMyChallengeListFail(err) {
    setMyChallengeList([]);
  }

  useEffect(() => {
    fetchMyChallengeList(
      page,
      fetchMyChallengeListSuccess,
      fetchMyChallengeListFail
    );
  }, [page]);

  function fetchChallengePageCntSuccess(res) {
    setMyPageNation(res.data);
  }

  function fetchChallengePageCntFail(res) {
    setMyPageNation([]);
  }

  useEffect(() => {
    fetchMyChallengePageCnt(
      fetchChallengePageCntSuccess,
      fetchChallengePageCntFail
    );
  }, []);

  function fetchWatchChallengePageCntSuccess(res) {
    setWatchTotalMyPageNation(res.data);
  }

  function fetchWatchChallengePageCntFail(res) {
    setWatchTotalMyPageNation([]);
  }

  useEffect(() => {
    fetchWatchMyChallengePageCnt(
      fetchWatchChallengePageCntSuccess,
      fetchWatchChallengePageCntFail
    );
  }, []);

  function fetchNoWatchChallengePageCntSuccess(res) {
    setWatchNoTotalMyPageNation(res.data);
  }

  function fetchNoWatchChallengePageCntFail(res) {
    setWatchNoTotalMyPageNation([]);
  }

  useEffect(() => {
    fetchNoWatchMyChallengePageCnt(
      fetchNoWatchChallengePageCntSuccess,
      fetchNoWatchChallengePageCntFail
    );
  }, []);

  function moveToRegister(e) {
    e.preventDefault();
    navigate("/register");
  }

  function requestChallengeUseWatchSuccess(res) {
    setMyChallengeFilterList(res.data);
  }

  function requestChallengeUseWatchFail(res) {
    setMyChallengeFilterList([]);
  }

  function requestChallengeNoUseWatchSuccess(res) {
    setMyChallengeNoFilterList(res.data);
  }

  function requestChallengeNoUseWatchFail(res) {
    setMyChallengeNoFilterList([]);
  }

  useEffect(() => {
    requestChallengeNoUseWatch(
      page,
      requestChallengeNoUseWatchSuccess,
      requestChallengeNoUseWatchFail
    );
  }, [page]);

  useEffect(() => {
    requestChallengeUseWatch(
      page,
      requestChallengeUseWatchSuccess,
      requestChallengeUseWatchFail
    );
  }, [page]);

  {
    if (MyChallengeList.length === 0) {
      return (
        <Box>
          <EmptyChallenge />
        </Box>
      );
    } else if (filter === "all") {
      return (
        <Box
          sx={{
            width: "80vw",
            backgroundColor: "#F7F7F7",
            borderRadius: "20px",
            textAlign: "center",
            display: "flex",
            flexDirection: "column",
          }}
        >
          <WatchToggleBox>
            <ToggleBox
              color="primary"
              value={filter}
              exclusive
              onChange={handleFilter}
              aria-label="watch filter"
            >
              <ToggleButton value="yes" aria-label="yes">
                <img src={WatchImage} width="20px" />
              </ToggleButton>
              <ToggleButton value="all" aria-label="all" disabled>
                <Box sx={{ width: "20px" }} />
              </ToggleButton>
              <ToggleButton value="no" aria-label="no">
                <img src={NoWatchImage} width="20px" />
              </ToggleButton>
            </ToggleBox>
          </WatchToggleBox>

          <ChallengeListBox>
            {/* 받아온 챌린지리스트 순회하기 */}

            {MyChallengeList.map((item) => {
              return (
                <ChallengeCard
                  key={item.chlId}
                  chlId={item.chlId}
                  imgurl={item.imgurl}
                  tagList={item.tagList}
                  title={item.title}
                  contents={item.contents}
                  startTime={item.startTime}
                  endTime={item.endTime}
                  categoryId={item.categoryId}
                  alarm={item.alarm}
                  watch={item.watch}
                ></ChallengeCard>
              );
            })}
          </ChallengeListBox>

          <ButtonBox>
            <Button size="ss" my="0" onClick={moveToRegister}>
              챌린지 생성
            </Button>
          </ButtonBox>

          <PageBox>
            <Stack spacing={2}>
              <Pagination
                count={TotalMyPage}
                defaultPage={1}
                shape="rounded"
                onChange={(e) => handlePage(e)}
              />
            </Stack>
          </PageBox>
        </Box>
      );
    } else if (filter === "no") {
      return (
        <MainBox flexDir="col">
          <WatchToggleBox>
            <ToggleBox
              color="primary"
              value={filter}
              exclusive
              onChange={handleFilter}
              aria-label="watch filter"
            >
              <ToggleButton value="yes" aria-label="yes">
                <img src={WatchImage} width="20px" />
              </ToggleButton>
              <ToggleButton value="all" aria-label="all">
                <Box sx={{ width: "20px" }} />
              </ToggleButton>
              <ToggleButton value="no" aria-label="no" disabled>
                <img src={NoWatchImage} width="20px" />
              </ToggleButton>
            </ToggleBox>
          </WatchToggleBox>

          <ChallengeListBox>
            {/* 받아온 챌린지리스트 순회하기 */}

            {MyChallengeNoFilterList.map((item) => {
              return (
                <ChallengeCard
                  key={item.id}
                  chlId={item.id}
                  imgurl={item.imgurl}
                  tagList={item.tagList}
                  title={item.title}
                  contents={item.contents}
                  startTime={item.startTime}
                  endTime={item.endTime}
                  categoryId={item.categoryId}
                  alarm={item.alarm}
                  watch={item.watch}
                ></ChallengeCard>
              );
            })}
          </ChallengeListBox>

          <ButtonBox>
            <Button size="ss" my="0" onClick={moveToRegister}>
              챌린지 생성
            </Button>
          </ButtonBox>

          <PageBox>
            <Stack spacing={2}>
              <Pagination
                count={WatchNoTotalMyPage}
                defaultPage={1}
                shape="rounded"
                onChange={(e) => handlePage(e)}
              />
            </Stack>
          </PageBox>
        </MainBox>
      );
    } else if (filter === "yes") {
      return (
        <MainBox flexDir="col">
          <WatchToggleBox>
            <ToggleBox
              color="primary"
              value={filter}
              exclusive
              onChange={handleFilter}
              aria-label="watch filter"
            >
              <ToggleButton value="yes" aria-label="yes" disabled>
                <img src={WatchImage} width="20px" />
              </ToggleButton>
              <ToggleButton value="all" aria-label="all">
                <Box sx={{ width: "20px" }} />
              </ToggleButton>
              <ToggleButton value="no" aria-label="no">
                <img src={NoWatchImage} width="20px" />
              </ToggleButton>
            </ToggleBox>
          </WatchToggleBox>

          <ChallengeListBox>
            {/* 받아온 챌린지리스트 순회하기 */}

            {MyChallengeFilterList.map((item) => {
              return (
                <ChallengeCard
                  key={item.id}
                  chlId={item.id}
                  imgurl={item.imgurl}
                  tagList={item.tagList}
                  title={item.title}
                  contents={item.contents}
                  startTime={item.startTime}
                  endTime={item.endTime}
                  categoryId={item.categoryId}
                  alarm={item.alarm}
                  watch={true}
                ></ChallengeCard>
              );
            })}
          </ChallengeListBox>

          <ButtonBox>
            <Button size="ss" my="0" onClick={moveToRegister}>
              챌린지 생성
            </Button>
          </ButtonBox>

          <PageBox>
            <Stack spacing={2}>
              <Pagination
                count={WatchTotalMyPage}
                defaultPage={1}
                shape="rounded"
                onChange={(e) => handlePage(e)}
              />
            </Stack>
          </PageBox>
        </MainBox>
      );
    }
  }
}
