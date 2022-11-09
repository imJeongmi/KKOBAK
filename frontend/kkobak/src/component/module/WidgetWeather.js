import React, { useState, useEffect } from "react";
import axios from "axios";
import moment from "moment";

import Text from "component/atom/Text";
import SunIcon from "static/weather/sunIcon.png";
import CloudIcon from "static/weather/cloudIcon.png";
import SnowIcon from "static/weather/snowIcon.png";
import RainIcon from "static/weather/rainIcon.png";
import DrizzleIcon from "static/weather/drizzleIcon.png";
import ThunderIcon from "static/weather/thunderIcon.png";

import "./WidgetWeather.css";

export default function WidgetWeather() {
  const apiKey = process.env.REACT_APP_OPENWEATHERMAP_API;

  const dayOfWeek = ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"];
  const [days, setDays] = useState();

  const [location, setLocation] = useState("Seoul");
  // const [location, setLocation] = useState();
  const [currentLat, setCurrentLat] = useState(37.5);
  const [currentLon, setCurrentLon] = useState(127.03);
  const [currentDayIdx, setCurrentDayIdx] = useState(0);
  const [currentTemp, setCurrentTemp] = useState();
  const [currentMaxTemp, setCurrentMaxTemp] = useState();
  const [currentMinTemp, setCurrentMinTemp] = useState();
  const [currentWeather, setCurrentWeather] = useState();
  const [currentWeatherImg, setCurrentWeatherImg] = useState();

  function getCurrentTempSuccess(res) {
    const data = res.data;
    // console.log(data);

    setCurrentTemp(Math.round(data.current.temp));
    setCurrentMaxTemp(Math.round(data.daily[currentDayIdx].temp.max));
    setCurrentMinTemp(Math.round(data.daily[currentDayIdx].temp.min));
    setCurrentWeather(data.daily[currentDayIdx].weather[0].main);
  }

  function getCurrentTemp() {
    axios
      .get(
        `https://api.openweathermap.org/data/2.5/onecall?lat=${currentLat}&lon=${currentLon}&appid=${apiKey}&units=metric`
      )
      .then(getCurrentTempSuccess);
  }

  function getLocationSuccess(res) {
    const data = res.data;
    setCurrentLat(data.coord.lat);
    setCurrentLon(data.coord.lon);
  }

  function getLocationTemp() {
    axios
      .get(
        `https://api.openweathermap.org/data/2.5/weather?q=${location}&appid=${apiKey}&units=metric`
      )
      .then(getLocationSuccess);
  }

  useEffect(() => {
    pushDays();
    console.log("hi");
    // getCurrentTemp();
  }, []);

  useEffect(() => {
    getCurrentTemp();
    // }, [currentLat, currentDayIdx]);
  }, [currentDayIdx]);

  // useEffect(() => {
  //   getLocationTemp();
  // }, [location]);

  useEffect(() => {
    switch (currentWeather) {
      case "Clear":
        setCurrentWeatherImg(SunIcon);
        break;
      case "Clouds":
      case "Haze":
        setCurrentWeatherImg(CloudIcon);
        break;
      case "Snow":
        setCurrentWeatherImg(SnowIcon);
        break;
      case "Rain":
        setCurrentWeatherImg(RainIcon);
        break;
      case "Drizzle":
      case "Mist":
        setCurrentWeatherImg(DrizzleIcon);
        break;
      case "Thunderstorm":
        setCurrentWeatherImg(ThunderIcon);
        break;
      default:
        setCurrentWeatherImg(DrizzleIcon);
    }
  }, [currentWeather]);

  function pushDays() {
    const dayOffset = moment().day();
    const pushedDays = ["TODAY"];
    for (let i = dayOffset + 1; i < dayOffset + 5; i++) {
      if (i > 6) {
        pushedDays.push(dayOfWeek[i - 7]);
      } else {
        pushedDays.push(dayOfWeek[i]);
      }
    }
    setDays(pushedDays);
  }

  function onSubmit(e) {
    e.preventDefault();
    const newLocation = e.target[0].value;
    // setLocation(newLocation);
  }

  return (
    <div className="weather-widget">
      <form onSubmit={onSubmit}>
        <input
          className="location-display"
          defaultValue={location}
          type="text"
        />
      </form>
      <div className="main-display">
        <Text size="xl" weight="medium">{`${currentTemp}°`}</Text>
        <div className="sub-info">
          <div className="weather-display">
            <img src={currentWeatherImg} width="30px" />
            <Text weight="semibold" size="18px" color="white" mx="10px">
              {currentWeather}
            </Text>
          </div>
          <Text
            weight="semibold"
            size="15px"
            mt="3"
          >{`최고: ${currentMaxTemp}°  최저: ${currentMinTemp}°`}</Text>
        </div>
      </div>
      <div className="day-display">
        {days &&
          days.map((item, index) => {
            if (index === currentDayIdx) {
              return (
                <div
                  onClick={() => {
                    setCurrentDayIdx(index);
                  }}
                >
                  <Text weight="bold" mx="4px">
                    {item}
                  </Text>
                </div>
              );
            } else {
              return (
                <div
                  onClick={() => {
                    setCurrentDayIdx(index);
                  }}
                >
                  <Text weight="medium" color="white" mx="4px">
                    {item}
                  </Text>
                </div>
              );
            }
          })}
      </div>
    </div>
  );
}
