import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
// import MainCarousel from "component/module/MainCarousel";
import Todolist from "component/module/Todolist";
import MainCalendar from "component/atom/MainCalendar";
import Text from "component/atom/Text";
import SideBar from "component/atom/SideBar";
import MainBox from "component/atom/MainBox";
import {
  requestUserInfo,
  fetchMyChallengeCalendarList,
  fetchMyChallengeCalendarPageCnt,
} from "api/userApi";

import Pagination from "@mui/material/Pagination";
import Stack from "@mui/material/Stack";

export default function Main() {
  const [user, setUser] = useState([]);
  const [MyChallengeList, setMyChallengeList] = useState([]);
  const [TotalMyPage, setMyPageNation] = useState([]);
  const [page, setPage] = useState(1);

  const handlePage = (event) => {
    const nowPageInt = parseInt(event.target.outerText);
    setPage(nowPageInt);
  };

  function fetchMyChallengeListSuccess(res) {
    setMyChallengeList(res.data);
    // console.log(res.data);
  }

  function fetchMyChallengeListFail(err) {
    setMyChallengeList([]);
  }

  useEffect(() => {
    fetchMyChallengeCalendarList(
      page,
      fetchMyChallengeListSuccess,
      fetchMyChallengeListFail
    );
  }, [page]);

  function fetchChallengePageCntSuccess(res) {
    setMyPageNation(res.data);
    // console.log(res.data);
  }

  function fetchChallengePageCntFail(res) {
    setMyPageNation([]);
  }

  useEffect(() => {
    fetchMyChallengeCalendarPageCnt(
      fetchChallengePageCntSuccess,
      fetchChallengePageCntFail
    );
  }, []);

  function requestUserInfoSuccess(res) {
    setUser(res.data);
    // console.log(res.data);
    // console.log(user.nickName);
  }

  function requestUserInfoFail(res) {
    setUser([]);
  }

  useEffect(() => {
    requestUserInfo(requestUserInfoSuccess, requestUserInfoFail);
  }, []);

  return MyChallengeList.length === 0 ? (
    <Box>
      <Text> 생성된 챌린지가 없어요</Text>
    </Box>
  ) : (
    <Box
      sx={{
        display: "flex",
        justifyContent: "space-between",
      }}
    >
      <Box sx={{ margin: "0 auto" }}>
        <Text size="l" weight="bold" mt="30" my="15">
          안녕하세요,
          {user.nickName} 님
        </Text>
        <Box sx={{ margin: "10px auto" }}>
          <Stack spacing={2}>
            <Pagination
              count={3}
              defaultPage={1}
              shape="rounded"
              onChange={(e) => handlePage(e)}
              hidePrevButton
              hideNextButton
            />
          </Stack>
        </Box>
        <MainBox>
          {MyChallengeList.map((item) => {
            return (
              <Box>
                <Box>{item.title}</Box>
                <MainCalendar
                  key={item.id}
                  startTime={item.startTime}
                  endTime={item.endTime}
                ></MainCalendar>
              </Box>
            );
          })}
        </MainBox>
      </Box>
      <SideBar>
        <Box sx={{ paddingY: "30px", marginLeft: "20px" }}>
          {/* <MainCarousel /> */}
        </Box>

        <Box sx={{ marginTop: "30px", marginLeft: "30px" }}>
          <Todolist />
        </Box>
      </SideBar>
    </Box>
  );
}
