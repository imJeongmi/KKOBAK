import React, { useEffect, useState } from "react";
import ChallengeCard from "./ChallengeCard";
import Box from "@mui/material/Box";
import Pagination from "@mui/material/Pagination";
import Stack from "@mui/material/Stack";
import Button from "component/atom/TextButton";
import WatchToggle from "component/atom/WatchToggle";
import { fetchChallengeList } from "api/Challenge";
import { fetchChallengePageCnt } from "api/Challenge";

export default function ChallengeCardList() {
  const [ChallengeList, setChallengeList] = useState([]);
  const [TotalPage, setPageNation] = useState([]);
  const [page, setPage] = useState(1);

  const handlePage = (event) => {
    const nowPageInt = parseInt(event.target.outerText);
    setPage(nowPageInt);
  };

  function fetchChallengeListSuccess(res) {
    setChallengeList(res.data);
  }

  function fetchChallengeListFail(err) {
    setChallengeList([]);
  }

  useEffect(() => {
    fetchChallengeList(page, fetchChallengeListSuccess, fetchChallengeListFail);
  }, [page]);

  function fetchChallengePageCntSuccess(res) {
    setPageNation(res.data);
    console.log(res.data);
  }

  function fetchChallengePageCntFail(res) {
    setPageNation([]);
  }

  useEffect(() => {
    fetchChallengePageCnt(
      fetchChallengePageCntSuccess,
      fetchChallengePageCntFail
    );
  }, []);

  // 현재 챌린지리스트 길이로 했는데 전체 길이로 수정해야 함
  return ChallengeList.length === 0 ? (
    <Box> 챌린지 리스트가 없음. </Box>
  ) : (
    <Box>
      <Box
        sx={{
          width: "924px",
          backgroundColor: "#F5F5F5",
          verticalAlign: "middle",
          display: "flex",
          flexWrap: "wrap",
          borderRadius: 5,
        }}
      >
        {/* 받아온 챌린지리스트 순회하기 */}
        <Box
          sx={{
            display: "flex",
            flexWrap: "wrap",
            marginTop: "40px",
            width: "924px",
            height: "560px",
          }}
        >
          {ChallengeList.map((item) => {
            return (
              <ChallengeCard
                key={item.id}
                imgurl={item.imgurl}
                tagList={item.tagList}
                title={item.title}
                startTime={item.startTime}
                endTime={item.endTime}
              ></ChallengeCard>
            );
          })}
        </Box>

        <Box sx={{ marginLeft: "275px", marginTop: "45px" }}>
          <Stack spacing={2}>
            {/* 해당 챌린지리스트 말고 전체 몇 개 오면 그거 6 나눠서 몇 페이지 까지 있는지 표시해주기 */}
            <Pagination
              count={TotalPage}
              defaultPage={1}
              shape="rounded"
              onChange={(e) => handlePage(e)}
            />
          </Stack>
        </Box>
        <Box sx={{ marginLeft: "735px", marginTop: "-80px" }}>
          <Button size="ss">챌린지 생성</Button>
        </Box>
        <Box sx={{ marginLeft: "750px", marginTop: "-670px" }}>
          <WatchToggle></WatchToggle>
        </Box>
      </Box>
    </Box>
  );
}
