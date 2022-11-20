import React, { Component } from "react";
import Slider from "react-slick";
import Box from "@mui/material/Box";

import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
export default class VerticalMode extends Component {
  render() {
    const settings = {
      dots: false,
      infinite: true,
      speed: 300,
      slidesToShow: 1,
      slidesToScroll: 1,
      vertical: true,
      verticalSwiping: true,
      autoplay: true,
      autospeed: 100,
      arrows: false,
    };

    return (
      <Slider {...settings}>
        <Box sx={{ width: "280px", height: "140px" }}>습관</Box>
        <Box sx={{ width: "280px", height: "140px" }}>실천</Box>
        <Box sx={{ width: "280px", height: "140px" }}>도전</Box>
      </Slider>
    );
  }
}
