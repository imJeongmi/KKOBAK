import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import moment from "moment";

import Stack from "@mui/material/Stack";
import Pagination from "@mui/material/Pagination";

import MainBox from "component/atom/MainBox";
import Text from "component/atom/Text";
import MainCalendar from "component/atom/MainCalendar";
import ProfileMenu from "component/atom/ProfileMenu";
import Todolist from "component/module/Todolist";
import WidgetCarousel from "component/module/WidgetCarousel";
import EmptyChallenge from "component/module/EmptyChallenge";

// import { getChallengeDetail } from "api/Challenge";
import {
  requestUserInfo,
  fetchMyChallengeCalendarPageCnt,
  getMyKkobakList,
} from "api/userApi";


export default function Main() {
  const [user, setUser] = useState([]);
  // const detailCategoryId = [];
  // const [detailCategoryId, setDetailCategoryId] = useState([]);
  const [MyChallengeList, setMyChallengeList] = useState([]);
  const checkPage = MyChallengeList.length;
  const [TotalMyPage, setMyPageNation] = useState([]);
  const [page, setPage] = useState(1);
  const [checkNum, setCheckNum] = useState(1);

  const handlePage = (event) => {
    const nowPageInt = parseInt(event.target.outerText);
    setPage(nowPageInt);
  };

  function getMyKkobakListSuccess(res) {
    setMyChallengeList(res.data.reverse());

    // for (let i=0; i<3; i++) {
    //   getChallengeDetail(res.data[i].chlId, getChallengeDetailSuccess, getChallengeDetailFail)
    // }
  }

  function getMyKkobakListFail(err) {
    setMyChallengeList([]);
  }

  function fetchChallengePageCntSuccess(res) {
    setMyPageNation(res.data);
  }

  function fetchChallengePageCntFail(res) {
    setMyPageNation([]);
  }

  function requestUserInfoSuccess(res) {
    setUser(res.data);
  }

  function requestUserInfoFail(res) {
    setUser([]);
  }

  // function getChallengeDetailSuccess(res) {
  //   detailCategoryId.push(res.data.detailCategoryId);
  // }

  // function getChallengeDetailFail(err) {}

  function CheckNumPage(checkPage) {
    if (checkPage === 1) {
      setCheckNum(1);
    } else if (checkPage === 2) {
      setCheckNum(2);
    } else {
      setCheckNum(3);
    }
  }

  function getEmoji(detailCategoryId) {
    if (detailCategoryId < 3) return "🏃🏻‍♂️";
    else if (detailCategoryId === 3) return "🧘🏼";
    else if (detailCategoryId === 4) return "🥛";
    else if (detailCategoryId === 5) return "💊";
    else if (detailCategoryId === 6) return "🧍🏻";
    else if (detailCategoryId === 7) return "💁🏻";
    else return "📌";
  }

  useEffect(() => {
    CheckNumPage(checkPage);
    fetchMyChallengeCalendarPageCnt(
      fetchChallengePageCntSuccess,
      fetchChallengePageCntFail
    );
    requestUserInfo(requestUserInfoSuccess, requestUserInfoFail);
    getMyKkobakList(getMyKkobakListSuccess, getMyKkobakListFail);
  }, []);

  return MyChallengeList.length === 0 ? (
    <Box
      sx={{
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
      }}
    >
      <EmptyChallenge />
    </Box>
  ) : (
    <Box
      sx={{
        display: "flex",
      }}
    >
      <Box sx={{ margin: "0 auto", display: "flex", flexDirection: "column" }}>
        <ProfileMenu
          nickName={user?.nickName}
          imgurl={user?.imgurl}
        ></ProfileMenu>
        <MainBox height="622px">
          <Box sx={{ display: "flex", flexDirection: "column" }}>
            {MyChallengeList.slice(page - 1, page).map((item, index) => {
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
                    <Stack spacing={2} sx={{ width: "200px" }}>
                      <Pagination
                        count={checkNum}
                        defaultPage={1}
                        shape="rounded"
                        onChange={(e) => handlePage(e)}
                        hidePrevButton
                        hideNextButton
                      />
                    </Stack>
                    <Box sx={{ width: "350px" }}>
                      <Text size="20px" weight="bold" my="25">
                        {/* {console.log(detailCategoryId[index])} */}
                        {item.watch === true
                          ? `⌚️ ${item.title}`
                          // : `${getEmoji(detailCategoryId[index])} ${item.title}`}
                          : `📌 ${item.title}`}
                      </Text>
                    </Box>
                    <Box
                      sx={{
                        width: "200px",
                        display: "flex",
                        justifyContent: "end",
                      }}
                    >
                      <Text size="14px">{`${startTimeCheck}-${endTimeCheck}`}</Text>
                    </Box>
                  </Box>
                  <Box>
                    <MainCalendar
                      chlId={item.chlId}
                      key={item.id}
                      startTime={item.startTime}
                      endTime={item.endTime}
                      watch={item.watch}
                      title={item.title}
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
