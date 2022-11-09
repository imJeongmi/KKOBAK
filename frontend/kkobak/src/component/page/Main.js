import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import moment from "moment";

import Stack from "@mui/material/Stack";
import Pagination from "@mui/material/Pagination";

import MainBox from "component/atom/MainBox";
import SideBar from "component/atom/SideBar";
import Text from "component/atom/Text";
import Todolist from "component/module/Todolist";
import MainCarousel from "component/module/MainCarousel";
import MainCalendar from "component/atom/MainCalendar";
import EmptyChallenge from "component/page/EmptyChallenge";

import {
  requestUserInfo,
  fetchMyChallengeCalendarList,
  fetchMyChallengeCalendarPageCnt,
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
      <SideBar>
        <Box sx={{ margin: "40px 30px 50px 30px" }}>
          <MainCarousel />
        </Box>

        <Box sx={{ marginTop: "30px", marginLeft: "30px" }}>
          <Todolist />
        </Box>
      </SideBar>
    </Box>
  ) : (
    <Box
      sx={{
        display: "flex",
        justifyContent: "space-between",
      }}
    >
      <Box sx={{ margin: "0 auto" }}>
        <Box sx={{ flex: 1, display: "flex", marginBottom: "10px" }}>
          <Box sx={{ float: "left", flex: 1, display: "flex" }}>
            <Box sx={{ float: "left" }}>
              <Text size="m" weight="semibold" mt="30" my="15">
                {"안녕하세요," + "\u00A0"}
              </Text>
            </Box>
            <Box>
              <Text size="m" weight="bold" mt="30" my="15" color="blue">
                {` ${user.nickName}`}
              </Text>
            </Box>
            <Box>
              <Text size="m" weight="semibold" mt="30" my="15">
                님
              </Text>
            </Box>
          </Box>
        </Box>
        <MainBox>
          <Box sx={{ display: "flex", flexDirection: "column" }}>
            {MyChallengeList.map((item) => {
              const startTimeCheck = moment(item.startTime).format(
                "YYYY/MM/DD"
              );
              const endTimeCheck = moment(item.endTime).format("YYYY/MM/DD");
              // console.log(item);
              return (
                <Box>
                  <Box sx={{ display: "flex" }}>
                    <Box
                      sx={{
                        float: "left",
                        marginTop: "20px",
                        marginLeft: "40px",
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
                    </Box>
                    <Box sx={{ flex: 1 }}></Box>
                    <Box
                      sx={{
                        float: "right",
                        marginLeft: "50px",
                        marginTop: "20px",
                      }}
                    >
                      <Text size="l" weight="bold">
                        {item.title}
                      </Text>
                    </Box>
                    <Box sx={{ flex: 1 }}></Box>
                    <Box sx={{ float: "right", marginRight: "45px" }}>
                      <Box sx={{ flex: 1, display: "flex" }}>
                        <Box sx={{ float: "left" }}>
                          <Text weight="semibold" mt="30" my="15">
                            {startTimeCheck}
                          </Text>
                        </Box>
                        <Box>
                          <Text weight="semibold" mt="30" my="15">
                            &nbsp;&nbsp;-&nbsp;&nbsp;
                          </Text>
                        </Box>
                        <Box>
                          <Text weight="semibold" mt="30" my="15">
                            {endTimeCheck}
                          </Text>
                        </Box>
                      </Box>
                    </Box>
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
      <SideBar>
        <Box sx={{ margin: "40px 30px 50px 30px" }}>
          <MainCarousel />
        </Box>

        <Box sx={{ marginTop: "30px", marginLeft: "30px" }}>
          <Todolist />
        </Box>
      </SideBar>
    </Box>
  );
}
