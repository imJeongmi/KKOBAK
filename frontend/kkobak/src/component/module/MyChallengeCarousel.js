import React, { Component } from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Box from "@mui/material/Box";
import Text from "component/atom/Text";

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
      <div style={{ width: "280px" }}>
        <Slider {...settings}>
          <div>
            <Box
              sx={{
                width: "280px",
                height: "130px",
                display: "flex",
                alignItems: "start",
                justifyContent: "center",
                backgroundColor: "#FBFBFB",
                borderRadius: 2,
                flexDirection: "column",
              }}
            >
              <Box sx={{ flex: 1, marginTop: "25px", marginLeft: "20px" }}>
                <Box>
                  <Text>진행 중인 챌린지</Text>
                </Box>
              </Box>
              <Box sx={{ flex: 1, marginLeft: "20px" }}>
                <Text>지금까지 참여한 챌린지</Text>
              </Box>
            </Box>
          </div>
          <div>
            <Box
              sx={{
                width: "280px",
                height: "130px",
                display: "flex",
                alignItems: "start",
                justifyContent: "center",
                backgroundColor: "#FBFBFB",
                borderRadius: 2,
                flexDirection: "column",
              }}
            >
              <Box sx={{ flex: 1, marginTop: "15px", marginLeft: "20px" }}>
                <Box>
                  <Text>완료한 챌린지</Text>
                </Box>
              </Box>
              <Box sx={{ flex: 1, marginLeft: "20px" }}>
                <Text>성공한 챌린지</Text>
              </Box>
              <Box sx={{ flex: 1, marginLeft: "20px" }}>
                <Text>실패한 챌린지</Text>
              </Box>
            </Box>
          </div>
        </Slider>
      </div>
    );
  }
}
