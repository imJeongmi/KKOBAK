import React from "react";
import { Routes, Route } from "react-router-dom";
import Main from "../component/page/Main";
import Challenge from "../component/page/Challenge";
import MyChallenge from "../component/page/MyChallenge";

export default function RouterConfiguration() {
  return (
    <Routes>
      <Route path="/main" element={<Main />} />
      <Route path="/challenge" element={<Challenge />} />
      <Route path="/myChallenge" element={<MyChallenge />} />
    </Routes>
  );
}
