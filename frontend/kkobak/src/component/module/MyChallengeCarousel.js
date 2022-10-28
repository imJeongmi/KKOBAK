import React, { Component } from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Box from "@mui/material/Box";

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
    };
    return (
      <div style={{ width: "290px" }}>
        <Slider {...settings}>
          <div>
            <Box
              sx={{
                width: "280px",
                height: "130px",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                border: "solid",
                borderRadius: 2,
              }}
            >
              완료한 챌린지
              <br />
              성공한 챌린지
              <br />
              실패한 챌린지
            </Box>
          </div>
          <div>
            <Box
              sx={{
                width: "280px",
                height: "130px",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                border: "solid",
                borderRadius: 2,
              }}
            >
              통계 정보
            </Box>
          </div>
        </Slider>
      </div>
    );
  }
}
