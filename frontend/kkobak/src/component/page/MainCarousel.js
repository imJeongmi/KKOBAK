import React from "react";

import { Swiper, SwiperSlide } from "swiper/react";
import { Mousewheel, Pagination } from "swiper";

import "swiper/css";
import "swiper/css/pagination";

import "./MainCarousel.css";
import Main from "./Main";
import ChallengeCardList from "component/module/ChallengeCardList";
import GroupChallengeCardList from "component/module/GroupChallengeCardList";

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
      <SwiperSlide><ChallengeCardList /></SwiperSlide>
      <SwiperSlide><GroupChallengeCardList /></SwiperSlide>
    </Swiper>
  )
}