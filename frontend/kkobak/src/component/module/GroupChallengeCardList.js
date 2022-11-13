import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import styled from "@emotion/styled";
import Pagination from "@mui/material/Pagination";
import Stack from "@mui/material/Stack";

import GroupChallengeCard from "component/module/GroupChallengeCard";
import Button from "component/atom/TextButton";
import MainBox from "component/atom/MainBox";
import EmptyChallenge from "component/page/EmptyChallenge";

import {
  fetchGroupChallengeList,
  fetchGroupChallengePageCnt,
} from "api/Challenge";
import { useNavigate } from "react-router-dom";

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

export default function GroupChallengeCardList() {
  const navigate = useNavigate();

  const [GroupChallengeList, setGroupChallengeList] = useState([]);
  const [GroupPage, setGroupPage] = useState([]);
  const [page, setPage] = useState(1);

  const handlePage = (event) => {
    const nowPageInt = parseInt(event.target.outerText);
    setPage(nowPageInt);
  };

  function fetchGroupChallengeListSuccess(res) {
    setGroupChallengeList(res.data);
  }

  function fetchGroupChallengeListFail(err) {
    setGroupChallengeList([]);
  }

  function fetchGroupChallengePageCntSuccess(res) {
    setGroupPage(res.data);
  }

  function fetchGroupChallengePageCntFail(res) {
    setGroupPage([]);
  }

  function moveToRegister(e) {
    e.preventDefault();
    navigate("/group/register");
  }

  useEffect(() => {
    fetchGroupChallengePageCnt(
      fetchGroupChallengePageCntSuccess,
      fetchGroupChallengePageCntFail
    );
  }, []);

  useEffect(() => {
    fetchGroupChallengeList(
      page,
      fetchGroupChallengeListSuccess,
      fetchGroupChallengeListFail
    );
  }, [page]);

  return (
    <Box>
      {GroupChallengeList.length === 0 ? (
        <EmptyChallenge />
      ) : (
        <MainBox width={75} justifyContent="space-between">
          <ChallengeListBox>
            {GroupChallengeList.map((item) => {
              return (
                <GroupChallengeCard
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
                ></GroupChallengeCard>
              );
            })}
          </ChallengeListBox>

          <ButtonBox>
            <Button size="ss" my="50px" onClick={moveToRegister}>
              단체 챌린지 생성
            </Button>
          </ButtonBox>

          <PageBox>
            <Stack spacing={2}>
              <Pagination
                count={GroupPage}
                defaultPage={1}
                shape="rounded"
                onChange={(e) => handlePage(e)}
              />
            </Stack>
          </PageBox>
        </MainBox>
      )}
    </Box>
  );
}
