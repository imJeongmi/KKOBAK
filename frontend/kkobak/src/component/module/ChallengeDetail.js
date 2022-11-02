import { Box } from "@mui/system";
import SideBar from "component/atom/SideBar";
import React from "react";

import Text from "component/atom/Text";
import MainBox from "component/atom/MainBox";

const CardStyle = {
  width: "70%",
  minHeight: "50vh",
  margin: "auto",
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
        <Text size="l" weight="bold" mt="30" my="15">
          경원님의 챌린지리스트
        </Text>
        <MainBox flexDir="col">
          <Box
            sx={{
              width: "100%",
              minHeight: "80vh",
              display: "flex",
              flexWrap: "wrap",
              justifyContent: "center",
            }}
          >
            <Box sx={CardStyle}>
              <Box sx={ImageStyle}>
                <img
                  src="https://t1.daumcdn.net/thumb/R720x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/2xMI/image/eRlaPLwEH3RjexrX_uVmse2g-LU.jpg"
                  alt="img"
                  width="100%"
                  height="100%"
                />
              </Box>
              <Text size="20px" weight="bold" mt="10">
                하루 30분 독서하기 챌린지
              </Text>
              <Box sx={{  }}>
                <Text size="13px" color="grey" my="10">
                  수영은 예로부터 건강에 좋기로 유명하죠. <br />
                  종합운동장 시민 수영장에서 매일 아침 자유수영 하실 분을
                  구합니다. <br />
                  최소 주 5일 이상은 수영을 하시는 분이면 좋겠어요.
                </Text>
              </Box>

              {/* 스크롤 */}
              <Box sx={{ display: "flex" }}>
                <Box
                  sx={{
                    width: "100px",
                    my: "30px",
                    mx: "80px",
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "start",
                  }}
                >
                  <Text size="14px" weight="semibold" my="5">
                    세부 카테고리
                  </Text>
                  <Text size="14px" weight="semibold" my="5">
                    기간
                  </Text>
                  <Text size="14px" weight="semibold" my="5">
                    알림
                  </Text>
                  <Text size="14px" weight="semibold" my="5">
                    워치 사용 여부
                  </Text>
                </Box>

                <Box
                  sx={{
                    width: "400px",
                    my: "30px",
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "start",
                  }}
                >
                  <Text size="14px" color="grey" my="5" mx="10">
                    수영
                  </Text>
                  <Text size="14px" color="grey" my="5" mx="10">
                    2022.10.20 - 2023.10.20
                  </Text>
                  <Text size="14px" color="grey" my="5" mx="10">
                    08.00 AM (매일)
                  </Text>
                  {/* 워치 사용 | 워치 사용 안함*/}
                </Box>
              </Box>
            </Box>
          </Box>
        </MainBox>
      </Box>
      <SideBar />
    </Box>
  );
}
