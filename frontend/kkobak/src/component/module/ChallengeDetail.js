import { Box } from "@mui/system";
import SideBar from "component/atom/SideBar";
import Text from "component/atom/Text";
import React from "react";

import MainBox from "component/atom/MainBox";

const CardStyle = {
  width: "70%",
  height: "60%",
  margin: "20px auto",
  backgroundColor: "white",
  borderRadius: 2,
};

const ImageStyle = {
  width: "50%",
  height: "25vh",
  margin: "25px auto 15px auto",
  borderRadius: 2,
  background: `url("https://t1.daumcdn.net/thumb/R720x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/2xMI/image/eRlaPLwEH3RjexrX_uVmse2g-LU.jpg") no-repeat center`,
  backgroundSize: "cover",
  overflow: "hidden",
};

export default function ChallengeDetail() {
  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "space-between",
      }}
    >
      <Box sx={{ margin: "0 auto" }}>
        <Box sx={{ mt: 5, mb: 1 }}>
          <Text size="l" weight="bold">
            경원님의 챌린지리스트
          </Text>
        </Box>
        <MainBox>
          <Box sx={CardStyle}>
            <Box sx={ImageStyle}>
              <img
                src="https://t1.daumcdn.net/thumb/R720x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/2xMI/image/eRlaPLwEH3RjexrX_uVmse2g-LU.jpg"
                alt="img"
                width="100%"
                height="100%"
              />
            </Box>
            <Text size="20px" weight="bold">
              하루 30분 독서하기 챌린지
            </Text>
            <Box sx={{ height: "10vh" }}>
              <Text size="13px" color="grey" my="10px">
                수영은 예로부터 건강에 좋기로 유명하죠. <br />
                종합운동장 시민 수영장에서 매일 아침 자유수영 하실 분을
                구합니다. <br />
                최소 주 5일 이상은 수영을 하시는 분이면 좋겠어요.
              </Text>
            </Box>

            {/* 스크롤 */}
            <Box sx={{display: "flex" }}>
              <Box
                sx={{
                  width: "100px",
                  mx: "80px",
                  display: "flex",
                  flexDirection: "column",
                  alignItems: "start",
                }}
              >
                <Text size="14px" weight="semibold" my="5px">
                  세부 카테고리
                </Text>
                <Text size="14px" weight="semibold" my="5px">
                  기간
                </Text>
                <Text size="14px" weight="semibold" my="5px">
                  알림
                </Text>
                <Text size="14px" weight="semibold" my="5px">
                  목표치
                </Text>
                <Text size="14px" weight="semibold" my="5px">
                  워치 사용 여부
                </Text>
                <Text size="14px" weight="semibold" my="5px">
                  공개 여부
                </Text>
                <Text size="14px" weight="semibold" my="5px">
                  참여인원
                </Text>
                <Text size="14px" weight="semibold" my="5px">
                  해시태그
                </Text>
              </Box>

              <Box
                sx={{
                  width: "400px",
                  display: "flex",
                  flexDirection: "column",
                  alignItems: "start",
                }}
              >
                <Text size="14px" color="grey" my="5px" mx="10px">
                  수영
                </Text>
                <Text size="14px" color="grey" my="5px" mx="10px">
                  2022.10.20 - 2023.10.20
                </Text>
                <Text size="14px" color="grey" my="5px" mx="10px">
                  08.00 AM (매일)
                </Text>
                <Text size="14px" color="grey" my="5px" mx="10px">
                  1회
                </Text>
                {/* 워치 사용 | 워치 사용 안함*/}
                {/* 공개 | 비공개 */}
                <Text size="14px" color="grey" my="5px" mx="10px">
                  10명
                </Text>
                {/* 해시태그 */}
              </Box>
            </Box>
          </Box>
        </MainBox>
      </Box>
      <SideBar />
    </Box>
  );
}
