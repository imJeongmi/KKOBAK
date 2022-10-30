import { Modal } from "@mui/material";
import { Box } from "@mui/system";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

import CloseIcon from "@mui/icons-material/Close";
import Logo from "static/Logo.png";
import Input from "component/atom/Input";
import TextButton from "component/atom/TextButton";
import Text from "component/atom/Text";

import storage from "helper/storage";

import {
  requestJoin,
  requestEmailCheck,
  requestNicknameCheck,
  requestAuthNum,
  requestConfirmAuthNum,
  requestPasswdCheck,
} from "api/userApi";

const ModalStyle = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: "50%",
  height: "90%",
  bgcolor: "white",
  borderRadius: "10px",
  border: "none",
};

const LogoStyle = {
  bgcolor: "#E8F2F9",
  width: "100%",
  height: "15%",
  mt: 5,
  textAlign: "center",
  display: "flex",
  justifyContent: "center",
};

export default function SignupModal() {
  const navigate = useNavigate();

  const [open, setOpen] = React.useState(true);
  // const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const [email, setEmail] = useState("");
  const [nickname, setNickname] = useState("");
  const [phoneNum, setPhoneNum] = useState("");
  const [authNum, setAuthNum] = useState("");
  const [passwd, setPasswd] = useState("");
  const [confirmPasswd, setConfirmPasswd] = useState("");
  const [signupMessage, setSignupMessage] =
    useState("여기에 회원가입 메시지 출력!");

  function goToLogin() {
    navigate("/login");
  }

  function onChangeEmail(e) {
    setEmail(e.target.value);
  }

  function onChangeNickname(e) {
    setNickname(e.target.value);
  }

  function onChangePhoneNum(e) {
    setPhoneNum(e.target.value);
  }

  function onChangeAuthNum(e) {
    setAuthNum(e.target.value);
  }

  function onChangePasswd(e) {
    setPasswd(e.target.value);
  }
  
  function onChangeConfirmPasswd(e) {
    setConfirmPasswd(e.target.value);
  }

  // 1) 이메일 중복 확인
  function emailCheckSuccess(res) {
    // console.log(res.data);
    if (res.data) {
      setSignupMessage("등록 가능한 이메일입니다.");
      setTimeout(() => setSignupMessage(""), 1500);
    } else {
      setSignupMessage("다른 이메일을 입력해주세요.");
      setTimeout(() => setSignupMessage(""), 1500);
    }
  }
  function emailCheckFail(res) {
    console.log("Email Check Fail", res);
  }
  function onClickEmailCheck() {
    requestEmailCheck(email, emailCheckSuccess, emailCheckFail);
  }

  // 2) 닉네임 중복 확인
  function nicknameCheckSuccess(res) {
    // console.log(res.data);
    if (res.data) {
      setSignupMessage("등록 가능한 닉네임입니다.");
      setTimeout(() => setSignupMessage(""), 1500);
    } else {
      setSignupMessage("다른 닉네임을 입력해주세요.");
      setTimeout(() => setSignupMessage(""), 1500);
    }
  }
  function nicknameCheckFail(res) {
    console.log("Nickname Check Fail", res);
  }
  function onClickNicknameCheck() {
    requestNicknameCheck(nickname, nicknameCheckSuccess, nicknameCheckFail);
  }

  // 3) 인증번호 요청
  function sendAuthNumSuccess(res) {
    setAuthNum(res.data.authNum);

    setSignupMessage("인증번호 : " + authNum);
    setTimeout(() => setSignupMessage(""), 1500);
  }
  function sendAuthNumFail(res) {
    console.log("Send AuthNum Fail", res);
  }
  function onClickSendAuthNum() {
    requestAuthNum(Number(phoneNum), sendAuthNumSuccess, sendAuthNumFail);
  }

  // 4) 인증번호 확인
  function authNumCheckSuccess(res) {
    // console.log(res.data);
    if (res.data) {
      setSignupMessage("인증되었습니다.");
      setTimeout(() => setSignupMessage(""), 1500);
    } else {
      setSignupMessage("잘못된 인증번호입니다.");
      setTimeout(() => setSignupMessage(""), 1500);
    }
  }
  function authNumCheckFail(res) {
    console.log("AuthNum Check Fail", res);
  }
  function onClickAuthNumCheck() {
    requestConfirmAuthNum(authNum, authNumCheckSuccess, authNumCheckFail);
  }

  // 5) 비밀번호 확인
  function passwdCheckSuccess(res) {
    // console.log(res.data);
    if (res.data) {
      setSignupMessage("사용 가능한 비밀번호입니다.");
      setTimeout(() => setSignupMessage(""), 1500);
    } else {
      setSignupMessage(
        "영문자, 숫자, 특수문자($`~!@$!%*#^?&()_=+)를 포함한 8-20자로 설정해주세요."
      );
      setTimeout(() => setSignupMessage(""), 1500);
    }
  }
  function passwdCheckFail(res) {
    console.log("Password Check Fail", res);
  }
  function onClickPasswdCheck() {
    if (passwd != confirmPasswd) {
      setSignupMessage("비밀번호가 일치하지 않습니다.");
      setTimeout(() => setSignupMessage(""), 1500);
    } else {
      requestPasswdCheck(String(passwd), passwdCheckSuccess, passwdCheckFail);
    }
  }

  // 6) 회원가입
  function joinSuccess(res) {
    const accessToken = res.data.accessToken;
    storage.set("accessToken", accessToken);

    setEmail("");
    setNickname("");
    setPhoneNum("");
    setAuthNum("");
    setPasswd("");
    setConfirmPasswd("");
    setSignupMessage("회원가입 완료");
  }
  function joinFail(res) {
    console.log("Signup Fail", res);
  }
  function onClickSignup(e) {
    // e.preventDefault();
    requestJoin(email, phoneNum, nickname, passwd, joinSuccess, joinFail);
  }

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={ModalStyle}>
        <CloseIcon
          onClick={handleClose}
          sx={{ m: 1, float: "right" }}
        ></CloseIcon>
        <Box sx={LogoStyle}>
          <img alt="logo" src={Logo} height="95%" />
        </Box>

        <Box sx={{ width: "80%", margin: "auto", textAlign: "center", mt: 2 }}>
          <Input
            type="text"
            placeholder="이메일"
            onChange={onChangeEmail}
          ></Input>
          <Box onClick={onClickEmailCheck}>
            <Text size="s">이메일 중복 확인</Text>
            <br />
          </Box>

          <Input
            type="text"
            placeholder="닉네임"
            onChange={onChangeNickname}
          ></Input>
          <Box onClick={onClickNicknameCheck}>
            <Text size="s">닉네임 중복 확인</Text>
          </Box>

          <Input
            type="text"
            placeholder="전화번호"
            onChange={onChangePhoneNum}
          ></Input>
          <Box onClick={onClickSendAuthNum}>
            <Text size="s">인증번호 전송</Text>
          </Box>

          <Input
            type="text"
            placeholder="인증번호"
            onChange={onChangeAuthNum}
          ></Input>
          <Box onClick={onClickAuthNumCheck}>
            <Text size="s">인증번호 확인</Text>
          </Box>

          <Input
            type="password"
            placeholder="비밀번호"
            onChange={onChangePasswd}
          ></Input>
          <Input
            type="password"
            placeholder="비밀번호 확인"
            onChange={onChangeConfirmPasswd}
          ></Input>
          <Box onClick={onClickPasswdCheck}>
            <Text size="s">비밀번호 확인</Text>
          </Box>

          <Box onClick={onClickSignup}>
            <TextButton size="m">회원가입</TextButton>
          </Box>

          <Box onClick={goToLogin}>
            <Text size="s">로그인</Text>
          </Box>

          <br />
          <Text>{signupMessage}</Text>
        </Box>
      </Box>
    </Modal>
  );
}
