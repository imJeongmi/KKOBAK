import React, { Component } from "react";
import ReactDOM from "react-dom";
import axios from "axios";

import Text from "component/atom/Text";
import SunIcon from "static/weather/sunIcon.png";
import CloudIcon from "static/weather/cloudIcon.png";
import SnowIcon from "static/weather/snowIcon.png";
import RainIcon from "static/weather/rainIcon.png";
import DrizzleIcon from "static/weather/drizzleIcon.png";
import ThunderIcon from "static/weather/thunderIcon.png";

import "./WidgetWeather.css";
export default class WidgetWeather extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: {},
      location: "Seoul",
      days: [],
      daysFull: [],
      temps: [],
      minTemps: [],
      maxTemps: [],
      weather: [],
      icons: [],
      displayIndex: 0,
    };
  }

  fetchData = () => {
    const url = this.buildUrlApi();

    axios.get(url).then((response) => {
      this.setState({
        data: response.data,
      });

      const currentData = this.currentData();
      const dayOfWeek = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
      const dayOfWeekFull = [
        "Sunday",
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
      ];
      const currentDay = "Today";
      const currentDayFull =
        dayOfWeekFull[new Date(currentData.dt_txt).getDay()];
      const currentTemp = Math.round(currentData.main.temp);
      const currentMinTemp = Math.round(currentData.main.temp_min);
      const currentMaxTemp = Math.round(currentData.main.temp_max);
      const currentWeather =
        currentData.weather[0].main === "Clouds"
          ? "Cloudy"
          : currentData.weather[0].main;
      const currentIcon = this.convertWeatherIcons(currentData.weather[0].main);

      const days = [];
      const daysFull = [];
      const temps = [];
      const minTemps = [];
      const maxTemps = [];
      const weather = [];
      const icons = [];

      for (let i = 0; i < this.state.data.list.length; i = i + 8) {
        let date = new Date(this.state.data.list[i].dt_txt);
        let day = dayOfWeek[date.getDay()];
        let dayFull = dayOfWeekFull[date.getDay()];
        days.push(day);
        daysFull.push(dayFull);
        temps.push(Math.round(this.state.data.list[i].main.temp));
        minTemps.push(Math.round(this.state.data.list[i].main.temp_min));
        maxTemps.push(Math.round(this.state.data.list[i].main.temp_max));

        if (this.state.data.list[i].weather[0].main === "Clouds") {
          weather.push("Cloudy");
        } else {
          weather.push(this.state.data.list[i].weather[0].main);
        }

        icons.push(
          this.convertWeatherIcons(this.state.data.list[i].weather[0].main)
        );
      }

      this.setState({
        days: [currentDay, ...days.slice(1)],
        daysFull: [currentDayFull, ...daysFull.slice(1)],
        temps: [currentTemp, ...temps.slice(1)],
        minTemps: [currentMinTemp, ...minTemps.slice(1)],
        maxTemps: [currentMaxTemp, ...maxTemps.slice(1)],
        weather: [currentWeather, ...weather.slice(1)],
        icons: [currentIcon, ...icons.slice(1)],
      });
    });
  };

  buildUrlApi = () => {
    const location = encodeURIComponent(this.state.location);
    const urlPrefix = "https://api.openweathermap.org/data/2.5/forecast?q=";
    const urlSuffix = "&APPID=fb1158dc7dfef5f0967ceac8f71ee3a6&units=metric";

    return [urlPrefix, location, urlSuffix].join("");
  };

  currentData = () => {
    const list = this.state.data.list;
    const nearestHr = this.computeNearestHr();

    return list.find((e) => new Date(e.dt_txt).getHours() === nearestHr);
  };

  computeNearestHr = () => {
    const currentTimeInHrs = new Date().getHours();
    const constHrs = [0, 3, 6, 9, 12, 15, 18, 21];
    const differences = constHrs.map((e) => Math.abs(e - currentTimeInHrs));
    const indexofLowestDiff = differences.indexOf(Math.min(...differences));

    return constHrs[indexofLowestDiff];
  };

  convertWeatherIcons = (weather) => {
    switch (weather) {
      case "Clear":
        return SunIcon;
      case "Clouds":
        return CloudIcon;
      case "Snow":
        return SnowIcon;
      case "Rain":
        return RainIcon;
      case "Drizzle":
        return DrizzleIcon;
      case "Thunderstorm":
        return ThunderIcon;
      default:
        return SunIcon;
    }
  };

  componentDidMount() {
    this.fetchData();
  }

  handleFocus = (e) => {
    e.target.select();
  };

  changeLocation = (e) => {
    e.preventDefault();
    const inputLocation = this.locationInput.value;
    this.setState(
      {
        location: inputLocation,
      },
      () => {
        this.fetchData();
      }
    );
  };

  setIndex = (index) => {
    this.setState({
      displayIndex: index,
    });
  };

  render() {
    const {
      location,
      days,
      daysFull,
      temps,
      maxTemps,
      minTemps,
      weather,
      icons,
      displayIndex,
    } = this.state;

    let imgSrc = "";
    switch (weather[displayIndex]) {
      case "Clear":
        imgSrc = SunIcon;
        break;
      case "Cloudy":
        imgSrc = CloudIcon;
        break;
      case "Snow":
        imgSrc = SnowIcon;
        break;
      case "Rain":
        imgSrc = RainIcon;
        break;
      case "Drizzle":
        imgSrc = DrizzleIcon;
        break;
      case "Thunderstorm":
        imgSrc = ThunderIcon;
        break;
      default:
        imgSrc = SunIcon;
    }

    return (
      <div className="weather-widget">
        <form onSubmit={this.changeLocation}>
          <input
            className="location-display"
            defaultValue={location}
            type="text"
            onFocus={this.handleFocus}
            ref={(input) => (this.locationInput = input)}
          />
        </form>

        <div className="main-display">
          <Text size="xl" weight="medium">{`${temps[displayIndex]}°`}</Text>
          <div className="sub-info">
            <div className="weather-display">
              {/* 날씨 이모지 추가하기*/}
              <img src={imgSrc} width="30px" />                               
              <Text
                weight="semibold"
                size="18px"
                color="white"
                mx="10px"
              >{`${weather[displayIndex]}`}</Text>
            </div>
            <Text
              weight="semibold"
              size="15px"
              mt="3"
            >{`최고: ${maxTemps[displayIndex]}°  최저: ${minTemps[displayIndex]}°`}</Text>
          </div>
        </div>

        <div className="day-display">
          {days.map((item, index) => {
            if (displayIndex === index) {
              return (
                <div
                  className="selection-days selected"
                  key={index + 1}
                  onClick={() => this.setIndex(index)}
                >
                  {item}
                </div>
              );
            } else {
              return (
                <div
                  className="selection-days"
                  key={index + 1}
                  onClick={() => this.setIndex(index)}
                >
                  {item}
                </div>
              );
            }
          })}
        </div>
      </div>
    );
  }
}

ReactDOM.render(<WidgetWeather />, document.getElementById("root"));
