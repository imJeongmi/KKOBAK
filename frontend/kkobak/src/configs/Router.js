import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import Main from "../component/page/Main";
import MyChallenge from "../component/page/MyChallenge";
import Setting from "component/page/Setting";
import storage from "../helper/storage";
import LoginModal from "component/module/LoginModal";
import SignupModal from "component/module/SignupModal";
import ChallengeDetail from "component/page/ChallengeDetail";
import ChallengeRegister from "component/page/ChallengeRegister";
import Statistics from "component/page/Statistics";
import IntroPage from "component/page/IntroPage";

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
        path="/register"
        element={
          <CheckAuth>
            <ChallengeRegister />
          </CheckAuth>
        }
      />
      <Route
        path="/myChallenge/:chlId"
        element={
          <CheckAuth>
            <ChallengeDetail />
          </CheckAuth>
        }
      />
      <Route
        path="/Statistics/:chlId"
        element={
          <CheckAuth>
            <Statistics />
          </CheckAuth>
        }
      />
      <Route path="/Setting" element={<Setting />} />
      <Route path="/signup" element={<SignupModal />} />
      <Route path="/IntroPage" element={<IntroPage />} />
    </Routes>
  );
}
