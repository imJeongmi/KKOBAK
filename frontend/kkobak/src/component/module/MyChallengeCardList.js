import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import Box from "@mui/material/Box";
import styled from "@emotion/styled";
import Pagination from "@mui/material/Pagination";
import Stack from "@mui/material/Stack";
import { ToggleButton, ToggleButtonGroup } from "@mui/material";

import Button from "component/atom/TextButton";
import MainBox from "component/atom/MainBox";
import ChallengeCard from "component/module/ChallengeCard";
import EmptyChallenge from "component/module/EmptyChallenge";

import WatchImage from "static/watch.png";
import NoWatchImage from "static/noWatch.png";

import { fetchMyChallengeList, fetchMyChallengePageCnt } from "api/userApi";

import {
  requestChallengeUseWatch,
  requestChallengeNoUseWatch,
  fetchWatchMyChallengePageCnt,
  fetchNoWatchMyChallengePageCnt,
} from "api/Challenge";

const ToggleBox = styled(ToggleButtonGroup)(
  () => `
width: 130px;
height: 30px;
background-color : white;
    `
);

const WatchToggleBox = styled(Box)(
  () => `
  width: 90%;
  margin: 20px 10px 0 10px;
  display: flex;
  justify-content: start
  `
);

const ChallengeListBox = styled(Box)(
  () => `
  width: 1000px;
  height: 60vh;
  display: flex;
  flex-wrap: wrap;
  // align-items: center;
  // justify-content: center;
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
  height: 35px;
  margin: 10px auto;
  `
);

export default function MyChallengeCardList() {
  const navigate = useNavigate();

  const [MyChallengeList, setMyChallengeList] = useState([]);
  const [TotalMyPage, setMyPageNation] = useState([]);
  const [WatchTotalMyPage, setWatchTotalMyPageNation] = useState([]);
  const [WatchNoTotalMyPage, setWatchNoTotalMyPageNation] = useState([]);
  const [page, setPage] = useState(1);
  const [filter, setFilter] = useState("all");
  const [MyChallengeFilterList, setMyChallengeFilterList] = useState([]);
  const [MyChallengeNoFilterList, setMyChallengeNoFilterList] = useState([]);

  const handlePage = (event) => {
    const nowPageInt = parseInt(event.target.outerText);
    setPage(nowPageInt);
  };

  const handleFilter = (event, newFilter) => {
    setFilter(newFilter);
  };

  function fetchMyChallengeListSuccess(res) {
    setMyChallengeList(res.data);
  }

  function fetchMyChallengeListFail(err) {
    setMyChallengeList([]);
  }

  function fetchChallengePageCntSuccess(res) {
    setMyPageNation(res.data);
  }

  function fetchChallengePageCntFail(res) {
    setMyPageNation([]);
  }

  function fetchWatchChallengePageCntSuccess(res) {
    setWatchTotalMyPageNation(res.data);
  }

  function fetchWatchChallengePageCntFail(res) {
    setWatchTotalMyPageNation([]);
  }

  function fetchNoWatchChallengePageCntSuccess(res) {
    setWatchNoTotalMyPageNation(res.data);
  }

  function fetchNoWatchChallengePageCntFail(res) {
    setWatchNoTotalMyPageNation([]);
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

  function moveToRegister(e) {
    e.preventDefault();
    navigate("/register");
  }

  useEffect(() => {
    fetchMyChallengePageCnt(
      fetchChallengePageCntSuccess,
      fetchChallengePageCntFail
    );
    fetchNoWatchMyChallengePageCnt(
      fetchNoWatchChallengePageCntSuccess,
      fetchNoWatchChallengePageCntFail
    );
    fetchWatchMyChallengePageCnt(
      fetchWatchChallengePageCntSuccess,
      fetchWatchChallengePageCntFail
    );
  }, []);

  useEffect(() => {
    fetchMyChallengeList(
      page,
      fetchMyChallengeListSuccess,
      fetchMyChallengeListFail
    );
    requestChallengeNoUseWatch(
      page,
      requestChallengeNoUseWatchSuccess,
      requestChallengeNoUseWatchFail
    );
    requestChallengeUseWatch(
      page,
      requestChallengeUseWatchSuccess,
      requestChallengeUseWatchFail
    );
  }, [page]);

  return (
    <Box>
      {MyChallengeList.length === 0 ? (
        <EmptyChallenge />
      ) : (
        <MainBox width="75" justifyContent="space-between">
          <WatchToggleBox>
            {filter === "all" ? (
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
            ) : filter === "no" ? (
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
            ) : (
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
            )}
          </WatchToggleBox>

          <ChallengeListBox>
            {filter === "all"
              ? MyChallengeList.map((item) => {
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
                })
              : filter === "no"
              ? MyChallengeNoFilterList.map((item) => {
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
                })
              : MyChallengeFilterList.map((item) => {
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
            <Button size="ss" my="50px" onClick={moveToRegister}>
              챌린지 생성
            </Button>
          </ButtonBox>

          <PageBox>
            {filter === "all" ? (
              <Stack spacing={2}>
                <Pagination
                  count={TotalMyPage}
                  defaultPage={1}
                  shape="rounded"
                  onChange={(e) => handlePage(e)}
                />
              </Stack>
            ) : filter === "no" ? (
              <Stack spacing={2}>
                <Pagination
                  count={WatchNoTotalMyPage}
                  defaultPage={1}
                  shape="rounded"
                  onChange={(e) => handlePage(e)}
                />
              </Stack>
            ) : (
              <Stack spacing={2}>
                <Pagination
                  count={WatchTotalMyPage}
                  defaultPage={1}
                  shape="rounded"
                  onChange={(e) => handlePage(e)}
                />
              </Stack>
            )}
          </PageBox>
        </MainBox>
      )}
    </Box>
  );
}
