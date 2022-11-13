import React from "react";
import Box from "@mui/material/Box";
import { TypeAnimation } from "react-type-animation";
import introImage from "../../static/introImage.jpg";
import IntroCarousel from "../atom/IntroCarousel";

export default function IntroPage() {
  return (
    <Box
      sx={{
        backgroundColor: "white",
        width: "100vw",
        height: "100vh",
      }}
    >
      <Box
        sx={{
          display: "flex",
          flexDirection: "row",
          flex: 1,
        }}
      >
        <Box sx={{ flex: 4, marginTop: "50px", marginLeft: "40px" }}>
          <Box
            sx={{
              backgroundColor: "black",
              width: "80px",
              height: "10px",
            }}
          />

          <Box sx={{ marginY: "50px" }}>
            안녕하세요. 저는 인트로 페이지입니다.
          </Box>
          <Box sx={{ marginTop: "100px", height: "130px" }}>
            <TypeAnimation
              speed={1}
              cursor={false}
              sequence={["꼬박꼬박", 2000, "꼬박꼬박"]}
              wrapper="Box"
              repeat={1}
              style={{ fontSize: "90px", fontWeight: "bold" }}
            />
          </Box>
          <Box
            sx={{
              marginTop: "10px",
              fontSize: "40px",
              fontWeight: "bold",
              float: "left",
            }}
          >
            나의 매일
          </Box>
          <Box
            sx={{
              marginTop: "10px",
              width: "340px",
              height: "340px",
              fontSize: "40px",
              fontWeight: "bold",
            }}
          >
            <IntroCarousel />
          </Box>
        </Box>
        <Box sx={{ flex: 3 }}>
          <Box
            sx={{
              height: "100vh",
            }}
          >
            <Box
              sx={{ backgroundColor: "black", width: "100%", height: "100%" }}
            >
              <Box
                sx={{
                  backgroundImage: `url(${introImage})`,
                  width: "100%",
                  height: "100%",
                  backgroundSize: "cover",
                  opacity: 0.7,
                }}
              ></Box>
            </Box>
          </Box>
          {/* <img src={introImage} width="100%" height="100%" /> */}
          {/* </Box> */}
        </Box>
      </Box>
    </Box>
  );
}
