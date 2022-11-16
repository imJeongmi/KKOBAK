import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import moment from "moment";

import Stack from "@mui/material/Stack";
import Pagination from "@mui/material/Pagination";

import MainBox from "component/atom/MainBox";
import Text from "component/atom/Text";
import MainCalendar from "component/atom/MainCalendar";
import Todolist from "component/module/Todolist";
import WidgetCarousel from "component/module/WidgetCarousel";
import EmptyChallenge from "component/module/EmptyChallenge";

import {
  requestUserInfo,
  fetchMyChallengeCalendarPageCnt,
  getMyKkobakList,
} from "api/userApi";
import ProfileMenu from "component/atom/ProfileMenu";

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

  const checkPage = MyChallengeList.length;
  const [checkNum, setCheckNum] = useState(1);

  function CheckNumPage(checkPage) {
    if (checkPage === 1) {
      setCheckNum(1);
    } else if (checkPage === 2) {
      setCheckNum(2);
    } else {
      setCheckNum(3);
    }
  }

  useEffect(() => {
    CheckNumPage(checkPage);
  });

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

        <ProfileMenu nickName={user?.nickName} imgurl={user?.imgurl}></ProfileMenu>
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
                        count={checkNum}
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
        <Box sx={{ width: "250px", margin: "30px 0 30px 60px" }}>
          <WidgetCarousel />
          <Todolist />
        </Box>
      </Box>
    </Box>
  );
}
