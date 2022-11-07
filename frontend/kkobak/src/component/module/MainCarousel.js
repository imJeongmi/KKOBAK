import React, { Component } from "react";
import Slider from "react-slick";

import WidgetTime from "./WidgetTime";
import WidgetWeather from "./WidgetWeather";

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
      vertical: true,
      verticalSwiping: true,
      autoplay: false,
      autospeed: 500,
      arrows: false,
    };

    return (
      <Slider {...settings}>
        <WidgetTime />
        <WidgetWeather />
      </Slider>
    );
  }
}
