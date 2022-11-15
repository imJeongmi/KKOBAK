import React from "react";

import { Swiper, SwiperSlide } from "swiper/react";
import { Mousewheel, Pagination } from "swiper";

import "swiper/css";
import "swiper/css/pagination";

import "./MainCarousel.css";
import Main from "./Main";
import Challenge from "./ChallengeList";
import GroupChallenge from "./GroupChallenge";

export default function MainCarousel() {
  return (
    <Swiper
      direction={"vertical"}
      slidesPerView={1}
      spaceBetween={30}
      mousewheel={true}
      pagination={{
        clickable: true,
      }}
      modules={[Mousewheel, Pagination]}
      className="mySwiper"
    >
      <SwiperSlide><Main /></SwiperSlide>
      <SwiperSlide><Challenge /></SwiperSlide>
      <SwiperSlide><GroupChallenge /></SwiperSlide>
    </Swiper>
  )
}