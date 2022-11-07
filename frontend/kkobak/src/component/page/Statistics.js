import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import Text from "component/atom/Text";
import SideBar from "component/atom/SideBar";
import {
  requestUserInfo,
  fetchMyChallengeCalendarList,
  fetchMyChallengeCalendarPageCnt,
} from "api/userApi";

import moment from "moment";

import HeartRateChart from "component/module/HeartRateChart";

export default function Statistics() {
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
        <Box>
          {MyChallengeList.map((item) => {
            const startTimeCheck = moment(item.startTime).format("YYYY/MM/DD");
            const endTimeCheck = moment(item.endTime).format("YYYY/MM/DD");
            console.log(item);
            return (
              <Box>
                <Box sx={{ flex: 1, display: "flex", marginBottom: "10px" }}>
                  <Box sx={{ float: "left", flex: 1, display: "flex" }}>
                    <Box sx={{ float: "left" }}>
                      <Text size="m" weight="bold" mt="30" my="15" color="red">
                        {item.title}
                      </Text>
                    </Box>
                    <Box>
                      <Text size="m" weight="semibold" mt="30" my="15">
                        챌린지
                      </Text>
                    </Box>
                  </Box>
                </Box>
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
                <Box
                  sx={{
                    width: "60vw",
                    height: "300px",
                    borderRadius: "20px",
                    backgroundColor: "gray",
                    textAlign: "center",
                    display: "flex",
                    justifyContent: "center",
                  }}
                >
                  <Box sx={{ display: "flex", flexDirection: "column" }}>
                    <Box>지도 위치</Box>
                  </Box>
                </Box>
                <Box
                  sx={{
                    marginTop: "10px",
                    width: "60vw",
                    height: "200px",
                    borderRadius: "20px",
                    backgroundColor: "gray",
                    textAlign: "center",
                    display: "flex",
                    justifyContent: "center",
                  }}
                >
                  <Box sx={{ display: "flex", flexDirection: "column" }}>
                    {/* <Box>심박수 위치</Box> */}
                    <HeartRateChart />
                  </Box>
                </Box>
              </Box>
            );
          })}
        </Box>
      </Box>
      <SideBar>
        <Box sx={{ paddingY: "30px", marginLeft: "20px" }}>
          달력 위젯이 들어갈 자리입니다.
        </Box>
      </SideBar>
    </Box>
  );
}
