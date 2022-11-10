import React, { Component } from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import WidgetStatisticsIng from "component/module/WidgetStatisticsIng";
import WidgetStatisticsRlt from "component/module/WidgetStatisticsRlt";

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
            <WidgetStatisticsIng />
            <WidgetStatisticsRlt />
        </Slider>
    );
  }
}
