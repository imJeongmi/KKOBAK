import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import Main from "../component/page/Main";
import MyChallenge from "../component/page/MyChallenge";
import Statistics from "../component/page/Statistics";
import Setting from "component/page/Setting";
import storage from "../helper/storage";
import LoginModal from "component/module/LoginModal";
import ChallengeDetail from "component/module/ChallengeDetail";

function checkAuth() {
  return !!storage.get("accessToken");
}

function CheckAuth({ children }) {
  if (checkAuth()) return children;
  return <LoginModal />;
}

export default function RouterConfiguration() {
  return (
    <Routes>
      <Route
        path="/"
        element={
          <CheckAuth>
            <Main />
          </CheckAuth>
        }
      />
      <Route
        path="/myChallenge"
        element={
          <CheckAuth>
            <MyChallenge />
          </CheckAuth>
        }
      />
      <Route
        path="/myChallenge/:challengeid"
        element={
          <CheckAuth>
            <ChallengeDetail />
          </CheckAuth>
        }
      />
      <Route
        path="/Statistics"
        element={
          <CheckAuth>
            <Statistics />
          </CheckAuth>
        }
      />
      <Route path="/Setting" element={<Setting />} />
    </Routes>
  );
}
