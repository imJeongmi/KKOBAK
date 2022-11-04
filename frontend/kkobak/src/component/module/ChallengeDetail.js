import React from "react";
import { useLocation } from "react-router-dom";

import { Box } from "@mui/system";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormControl from "@mui/material/FormControl";

import MainBox from "component/atom/MainBox";
import SideBar from "component/atom/SideBar";
import Text from "component/atom/Text";

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
  backgroundSize: "cover",
  backgroundColor: "grey",
  overflow: "hidden",
};

export default function ChallengeDetail() {
  const location = useLocation();

  const imgurl = location?.state?.imgurl;
  const title = location?.state?.title;
  const contents = location?.state?.contents;
  const startTime = location?.state?.startTime;
  const endTime = location?.state?.endTime;
  const categoryId = location?.state?.categoryId;
  const alarm = location?.state?.alarm;
  const watch = location?.state?.watch;
  // const watch = true;

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
                {<img src={imgurl} alt="img" width="100%" height="100%" />}
              </Box>
              <Text size="20px" weight="bold" mt="10">
                {title}
              </Text>
              <Box sx={{}}>
                <Text size="13px" color="grey" my="10">
                  {contents}
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
                  <Text size="14px" weight="semibold" my="10">
                    카테고리
                  </Text>
                  <Text size="14px" weight="semibold" my="10">
                    기간
                  </Text>
                  <Text size="14px" weight="semibold" my="10">
                    알림
                  </Text>
                  <Text size="14px" weight="semibold" my="10">
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
                  <Text size="14px" color="grey" my="10" mx="10">
                    {categoryId}
                  </Text>
                  <Text size="14px" color="grey" my="10" mx="10">
                    {startTime.substr(0, 10)} - {endTime.substr(0, 10)}
                  </Text>
                  <Text size="14px" color="grey" my="10" mx="10">
                    {alarm}
                  </Text>
                  <Box sx={{ mx: "8px" }}>
                    <RadioGroup
                      row
                      aria-labelledby="demo-row-controlled-radio-buttons-group"
                      name="controlled-radio-buttons-group"
                      value={watch}
                    >
                      <FormControlLabel
                        value="true"
                        control={<Radio />}
                        label="사용"
                      />
                      <FormControlLabel
                        value="false"
                        control={<Radio />}
                        label="사용 안함"
                      />
                    </RadioGroup>
                  </Box>
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