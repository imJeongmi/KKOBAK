import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import moment from "moment";

import Stack from "@mui/material/Stack";
import Pagination from "@mui/material/Pagination";

import MainBox from "component/atom/MainBox";
import SideBar from "component/atom/SideBar";
import Text from "component/atom/Text";
import MainCalendar from "component/atom/MainCalendar";
import Todolist from "component/module/Todolist";
import WidgetCarousel from "component/module/WidgetCarousel";
import EmptyChallenge from "component/page/EmptyChallenge";

import {
  requestUserInfo,
  fetchMyChallengeCalendarPageCnt,
  getMyKkobakList,
} from "api/userApi";

export default function Main() {
  const [user, setUser] = useState([]);
  const [MyChallengeList, setMyChallengeList] = useState([]);
  const [TotalMyPage, setMyPageNation] = useState([]);
  const [page, setPage] = useState(1);

  const handlePage = (event) => {
    const nowPageInt = parseInt(event.target.outerText);
    setPage(nowPageInt);
  };

  function getMyKkobakListSuccess(res) {
    setMyChallengeList(res.data.reverse());
  }

  function getMyKkobakListFail(err) {
    setMyChallengeList([]);
  }

  useEffect(() => {
    getMyKkobakList(getMyKkobakListSuccess, getMyKkobakListFail);
  }, []);

  function fetchChallengePageCntSuccess(res) {
    setMyPageNation(res.data);
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
  }

  function requestUserInfoFail(res) {
    setUser([]);
  }

  useEffect(() => {
    requestUserInfo(requestUserInfoSuccess, requestUserInfoFail);
  }, []);

  return MyChallengeList.length === 0 ? (
    <Box
      sx={{
        display: "flex",
        justifyContent: "space-between",
      }}
    >
      <Box sx={{ margin: "0 auto" }}>
        <Box
          sx={{
            flex: 1,
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            marginTop: "200px",
          }}
        >
          <EmptyChallenge />
        </Box>
      </Box>
    </Box>
  ) : (
    <Box
      sx={{
        display: "flex",
      }}
    >
      <Box sx={{ margin: "0 auto", display: "flex", flexDirection: "column" }}>
        <Box sx={{ display: "flex" }}>
          <Text size="m" weight="semibold" mt="30" my="15">
            {"안녕하세요\u00A0"}
          </Text>
          <Text size="m" weight="bold" mt="30" my="15" color="blue">
            {` ${user.nickName}`}
          </Text>
          <Text size="m" weight="semibold" mt="30" my="15">
            님
          </Text>
        </Box>
        <MainBox height="622px">
          <Box sx={{ display: "flex", flexDirection: "column" }}>
            {MyChallengeList.slice(page - 1, page).map((item) => {
              const startTimeCheck = moment(item.startTime).format(
                "YYYY/MM/DD"
              );
              const endTimeCheck = moment(item.endTime).format("YYYY/MM/DD");
              return (
                <Box>
                  <Box
                    sx={{
                      height: "45px",
                      margin: "30px 45px 1px 45px",
                      display: "flex",
                      alignItems: "end",
                      justifyContent: "space-between",
                    }}
                  >
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
                    <Text size="20px" weight="bold" my="25">
                      {item.title}
                    </Text>
                    <Text size="14px">{`${startTimeCheck}-${endTimeCheck}`}</Text>
                  </Box>
                  <Box>
                    <MainCalendar
                      title={item.title}
                      chlId={item.chlId}
                      key={item.id}
                      startTime={item.startTime}
                      endTime={item.endTime}
                    />
                  </Box>
                </Box>
              );
            })}
          </Box>
        </MainBox>
      </Box>
      <Box sx={{ margin: "0 auto", display: "flex", flexDirection: "column" }}>
        <Box sx={{ width: "250px", margin: "70px 30px 50px 30px" }}>
          <WidgetCarousel />
          <Todolist />
        </Box>
      </Box>
      {/* <Box sx={{ width: "150px", height: "100vh", backgroundColor: "#f8f8f8" }} /> */}
      <Box sx={{ width: "150px", height: "100vh", backgroundColor: "white" }} />
    </Box>
  );
}
