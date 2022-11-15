import React, { useState } from "react";

import { Swiper, SwiperSlide } from "swiper/react";
import { Mousewheel, Pagination } from "swiper";
import ArrowUpwardIcon from "@mui/icons-material/ArrowUpward";
import SwiperCore from "swiper";
import "swiper/css";
import "swiper/css/pagination";

import "./MainCarousel.css";
import Main from "./Main";
import Challenge from "./ChallengeList";
import GroupChallenge from "./GroupChallenge";
import { Box, Button } from "@mui/material";

export default function MainCarousel() {
  const [swiper, setSwiper] = useState(<SwiperCore />);

  function moveToTop(e) {
    e.preventDefault();
    swiper.slideTo(0, 500, false);
  }

  return (
    <Swiper
      direction={"vertical"}
      slidesPerView={1}
      spaceBetween={30}
      mousewheel={true}
      pagination={{
        clickable: true,
      }}
      touchRatio={0}
      modules={[Mousewheel, Pagination]}
      className="mySwiper"
      onSwiper={(swiper) => setSwiper(swiper)}
    >
      <SwiperSlide>
        <Main />
      </SwiperSlide>
      <SwiperSlide>
        <Challenge />
        <Box sx={{ position: "absolute", right: "9%", bottom: "5%" }}>
          <ArrowUpwardIcon
            fontSize="large"
            color="disabled"
            onClick={moveToTop}
          />
        </Box>
      </SwiperSlide>
      <SwiperSlide>
        <GroupChallenge />
        <Box sx={{ position: "absolute", right: "9%", bottom: "5%" }}>
          <ArrowUpwardIcon
            fontSize="large"
            color="disabled"
            onClick={moveToTop}
          />
        </Box>
      </SwiperSlide>
    </Swiper>
  );
}
