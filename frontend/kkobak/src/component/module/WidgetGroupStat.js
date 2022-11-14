import React, { Component, useState } from "react";
import Slider from "react-slick";

import GroupStatistics from "component/module/GroupStatistics";
import GroupStatRank from "component/module/GroupStatRank";

import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
export default class VerticalMode extends Component {
  render() {
    const settings = {
      dots: true,
      infinite: true,
      speed: 500,
      slidesToShow: 1,
      slidesToScroll: 1,
      vertical: false,
      verticalSwiping: false,
      autoplay: false,
      autospeed: 500,
      arrows: false,
    };

    return (
      <Slider {...settings}>
        <GroupStatistics />
        <GroupStatRank />
      </Slider>
    );
  }
}
