import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import Main from "../component/page/Main";
import Challenge from "../component/page/Challenge";
import MyChallenge from "../component/page/MyChallenge";
import Setting from "component/page/Setting";
import storage from "../helper/storage";
import LoginModal from "component/module/LoginModal";

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
        path="/challenge"
        element={
          <CheckAuth>
            <Challenge />
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
        path="/Setting"
        element={
          <CheckAuth>
            <Setting />
          </CheckAuth>
        }
      />
    </Routes>
  );
}
