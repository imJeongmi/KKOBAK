import React, { useEffect, useState } from "react";
import Box from "@mui/material/Box";
import Text from "component/atom/Text";
import SideBar from "component/atom/SideBar";
import { useParams } from "react-router-dom";

import HeartRateChart from "component/module/HeartRateChart";
import ChallengeMap from "component/atom/ChallengeMap";

import { requestMyChallengeDetail } from "api/userApi";

import CheckDayForm from "component/module/CheckDayForm";

import moment from "moment";

export default function Statistics() {
  const chlId = Number(useParams().chlId);
  const [title, setTitle] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [detailCategoryId, setDetailCategoryId] = useState("");

  const [findTime, setFindTime] = useState(new Date());

  function requestMyChallengeDetailSuccess(res) {
    setTitle(res.data.title);
    setStartTime(res.data.startTime);
    setEndTime(res.data.endTime);
    setDetailCategoryId(res.data.detailCategoryId);
  }

  function requestMyChallengeDetailFail(res) {}

  useEffect(() => {
    requestMyChallengeDetail(
      chlId,
      requestMyChallengeDetailSuccess,
      requestMyChallengeDetailFail
    );
  }, []);

  {
    if (detailCategoryId === 1 || detailCategoryId === 2) {
      return (
        //달리기 걷기 통계페이지입니다.
        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
          }}
        >
          <Box sx={{ margin: "0 auto" }}>
            <Box>
              <Box>
                <Box sx={{ flex: 1, display: "flex", marginBottom: "10px" }}>
                  <Box sx={{ float: "left", flex: 1, display: "flex" }}>
                    <Box sx={{ float: "left" }}>
                      <Text size="m" weight="bold" mt="30" my="15" color="red">
                        {title}
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
                      {moment(startTime.substr(0, 10)).format("YYYY/MM/DD")}
                    </Text>
                  </Box>
                  <Box>
                    <Text weight="semibold" mt="30" my="15">
                      &nbsp;&nbsp;-&nbsp;&nbsp;
                    </Text>
                  </Box>
                  <Box>
                    <Text weight="semibold" mt="30" my="15">
                      {moment(endTime.substr(0, 10)).format("YYYY/MM/DD")}
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
                    <ChallengeMap
                      findTime={findTime}
                      setFindTime={setFindTime}
                    />
                  </Box>
                </Box>
                <Box
                  sx={{
                    marginTop: "10px",
                    width: "60vw",
                    height: "200px",
                    borderRadius: "20px",
                    backgroundColor: "#F7F7F7",
                    textAlign: "center",
                    display: "flex",
                    justifyContent: "center",
                  }}
                >
                  <Box sx={{ display: "flex", flexDirection: "column" }}>
                    {/* <Box>심박수 위치</Box> */}
                    <HeartRateChart
                      findTime={findTime}
                      setFindTime={setFindTime}
                    />
                  </Box>
                </Box>
              </Box>
            </Box>
          </Box>
          <SideBar>
            <Box sx={{ paddingY: "30px", marginLeft: "20px" }}>
              해당 날짜 챌린지 조회하기
              <CheckDayForm
                findTime={findTime}
                setFindTime={setFindTime}
              ></CheckDayForm>
            </Box>
          </SideBar>
        </Box>
      );
    } else if (detailCategoryId === 3) {
      return (
        // 명상챌린지 통계 부분입니다!!

        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
          }}
        >
          <Box sx={{ margin: "0 auto" }}>
            <Box
              sx={{
                marginTop: "250px",
                width: "60vw",
                height: "200px",
                borderRadius: "20px",
                backgroundColor: "#F7F7F7",
                textAlign: "center",
                display: "flex",
                justifyContent: "center",
              }}
            >
              <Box sx={{ display: "flex", flexDirection: "column" }}>
                {/* <Box>심박수 위치</Box> */}
                <HeartRateChart findTime={findTime} setFindTime={setFindTime} />
              </Box>
            </Box>
          </Box>
          <SideBar>
            <Box sx={{ paddingY: "30px", marginLeft: "20px" }}>
              해당 날짜 챌린지 조회하기
              <CheckDayForm
                findTime={findTime}
                setFindTime={setFindTime}
              ></CheckDayForm>
            </Box>
          </SideBar>
        </Box>
      );
    } else {
      // 출석 통계 페이지 입니다.
      return (
        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
          }}
        >
          <Box sx={{ margin: "0 auto" }}>
            <Box>
              <Box>
                <Box sx={{ flex: 1, display: "flex", marginBottom: "10px" }}>
                  <Box sx={{ float: "left", flex: 1, display: "flex" }}>
                    <Box sx={{ float: "left" }}>
                      <Text size="m" weight="bold" mt="30" my="15" color="red">
                        {title}
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
                      {moment(startTime.substr(0, 10)).format("YYYY/MM/DD")}
                    </Text>
                  </Box>
                  <Box>
                    <Text weight="semibold" mt="30" my="15">
                      &nbsp;&nbsp;-&nbsp;&nbsp;
                    </Text>
                  </Box>
                  <Box>
                    <Text weight="semibold" mt="30" my="15">
                      {moment(endTime.substr(0, 10)).format("YYYY/MM/DD")}
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
                    <ChallengeMap
                      findTime={findTime}
                      setFindTime={setFindTime}
                    />
                  </Box>
                </Box>
              </Box>
            </Box>
          </Box>
          <SideBar>
            <Box sx={{ paddingY: "30px", marginLeft: "20px" }}>
              해당 날짜 챌린지 조회하기
              <CheckDayForm
                findTime={findTime}
                setFindTime={setFindTime}
              ></CheckDayForm>
            </Box>
          </SideBar>
        </Box>
      );
    }
  }
}
