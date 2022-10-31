import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Box } from "@mui/system";
import { Modal } from "@mui/material";

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
  my: 5,
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
};

export default function SignupModal() {
  const navigate = useNavigate();

  const [open, setOpen] = React.useState(true);
  const handleClose = () => setOpen(false);

  const [email, setEmail] = useState("");
  const [passwd, setPasswd] = useState("");
  const [passwdCheck, setPasswdCheck] = useState("");
  const [nickname, setNickname] = useState("");
  const [phoneNum, setPhoneNum] = useState("");
  const [authNum, setAuthNum] = useState("");
  const [confirmEmail, setConfirmEmail] = useState("");
  const [confirmPasswd, setConfirmPasswd] = useState("");
  const [confirmNickname, setConfirmNickname] = useState("");
  const [confirmPhoneNum, setConfirmPhoneNum] = useState("");
  const [confirmAuthNum, setConfirmAuthNum] = useState("");
  const [signupMessage, setSignupMessage] = useState("");

  function goToLogin() {
    navigate("/login");
  }

  function onChangeEmail(e) {
    setEmail(e.target.value);
    setConfirmEmail("");
  }

  function onChangePasswd(e) {
    setPasswd(e.target.value);
    setConfirmPasswd("");
  }

  function onChangePasswdCheck(e) {
    setPasswdCheck(e.target.value);
    setConfirmPasswd("");
  }

  function onChangeNickname(e) {
    setNickname(e.target.value);
    setConfirmNickname("");
  }

  function onChangePhoneNum(e) {
    setPhoneNum(e.target.value);
    setConfirmPhoneNum("");
  }

  function onChangeAuthNum(e) {
    setAuthNum(e.target.value);
  }

  // 회원가입
  function joinSuccess(res) {
    const accessToken = res.data.accessToken;
    storage.set("accessToken", accessToken);

    setEmail("");
    setPasswd("");
    setPasswdCheck("");
    setNickname("");
    setPhoneNum("");
    setAuthNum("");
    setConfirmEmail("");
    setConfirmPasswd("");
    setConfirmNickname("");
    setConfirmPhoneNum("");
    setConfirmAuthNum("");

    setSignupMessage("회원가입 완료");
  }
  function joinFail(res) {
    console.log("Signup Fail", res);
  }
  function onClickSignup() {
    if (!email) {
      setSignupMessage("이메일을 입력해주세요.");
      setTimeout(() => setSignupMessage(""), 1500);
      return;
    }

    if (!confirmEmail) {
      setSignupMessage("이메일 중복 인증이 필요해요.");
      setTimeout(() => setSignupMessage(""), 1500);
      return;
    }

    if (!passwd || !passwdCheck) {
      setSignupMessage("비밀번호를 입력해주세요.");
      setTimeout(() => setSignupMessage(""), 1500);
      return;
    }

    if (!nickname) {
      setSignupMessage("닉네임을 입력해주세요.");
      setTimeout(() => setSignupMessage(""), 1500);
      return;
    }

    if (!confirmNickname) {
      setSignupMessage("닉네임 중복 인증이 필요해요.");
      setTimeout(() => setSignupMessage(""), 1500);
      return;
    }

    if (!phoneNum) {
      setSignupMessage("전화번호를 입력해주세요.");
      setTimeout(() => setSignupMessage(""), 1500);
      return;
    }

    if (!authNum) {
      setSignupMessage("인증번호를 입력해주세요.");
      setTimeout(() => setSignupMessage(""), 1500);
      return;
    }

    onClickPasswdCheck();

    if (confirmPasswd && onClickAuthNumCheck()) {
      requestJoin(
        confirmEmail,
        confirmPhoneNum,
        confirmNickname,
        passwdCheck,
        joinSuccess,
        joinFail
      );
    }
  }

  // 이메일 중복 확인
  function emailCheckSuccess(res) {
    if (res.data) {
      setConfirmEmail(email);
      setSignupMessage("사용 가능한 이메일입니다.");
      setTimeout(() => setSignupMessage(""), 1500);
    }
  }
  function emailCheckFail(res) {
    setConfirmEmail("");
    setSignupMessage("다른 이메일을 입력해주세요.");
    setTimeout(() => setSignupMessage(""), 1500);
    // console.log("Email Check Fail", res);
  }
  function onClickEmailCheck() {
    requestEmailCheck(email, emailCheckSuccess, emailCheckFail);
  }

  // 비밀번호 확인
  function passwdCheckSuccess(res) {
    if (res.data) {
      setConfirmPasswd(passwd);
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
    if (passwd !== passwdCheck) {
      setSignupMessage("비밀번호가 일치하지 않습니다.");
      setTimeout(() => setSignupMessage(""), 1500);
    } else {
      requestPasswdCheck(passwd, passwdCheckSuccess, passwdCheckFail);
    }
  }

  // 닉네임 중복 확인
  function nicknameCheckSuccess(res) {
    if (res.data) {
      setConfirmNickname(nickname);
      setSignupMessage("사용 가능한 닉네임입니다.");
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

  // 인증번호 요청
  function sendAuthNumSuccess(res) {
    setConfirmPhoneNum(phoneNum);
    setConfirmAuthNum(res.data.authNum);
    console.log("인증번호 : " + res.data.authNum);
  }
  function sendAuthNumFail(res) {
    console.log("Send AuthNum Fail", res);
  }
  function onClickSendAuthNum() {
    requestAuthNum(Number(phoneNum), sendAuthNumSuccess, sendAuthNumFail);
  }

  // 인증번호 확인
  function onClickAuthNumCheck() {
    if (authNum === confirmAuthNum) {
      return true;
    } else {
      setSignupMessage("잘못된 인증번호입니다.");
      setTimeout(() => setSignupMessage(""), 1500);
    }
  }

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={ModalStyle}>
        <CloseIcon
          onClick={handleClose}
          sx={{ m: 1, float: "right" }}
        ></CloseIcon>
        <Box sx={LogoStyle}>
          <img alt="logo" src={Logo} height="80%" />
        </Box>

        <Box sx={{ width: "80%", margin: "auto", textAlign: "center", mt: 2 }}>
          <Box
            sx={{ display: "flex", flexDirection: "col", alignItems: "center" }}
          >
            <Input
              type="text"
              placeholder="이메일"
              onChange={onChangeEmail}
            ></Input>
            <Box onClick={onClickEmailCheck} sx={{ width: "20%" }}>
              <Text size="13px" weight="medium" color="grey">
                중복 확인
              </Text>
            </Box>
          </Box>

          <Box
            sx={{ display: "flex", flexDirection: "col", alignItems: "center" }}
          >
            <Input
              type="password"
              placeholder="비밀번호"
              onChange={onChangePasswd}
            ></Input>
            <Box sx={{ width: "20%" }} />
          </Box>

          <Box
            sx={{ display: "flex", flexDirection: "col", alignItems: "center" }}
          >
            <Input
              type="password"
              placeholder="비밀번호 재확인"
              onChange={onChangePasswdCheck}
            ></Input>
            <Box sx={{ width: "20%" }} />
          </Box>

          <Box
            sx={{ display: "flex", flexDirection: "col", alignItems: "center" }}
          >
            <Input
              type="text"
              placeholder="닉네임"
              onChange={onChangeNickname}
            ></Input>
            <Box onClick={onClickNicknameCheck} sx={{ width: "20%" }}>
              <Text size="13px" weight="medium" color="grey">중복 확인</Text>
            </Box>
          </Box>

          <Box
            sx={{ display: "flex", flexDirection: "col", alignItems: "center" }}
          >
            <Input
              type="text"
              placeholder="전화번호"
              onChange={onChangePhoneNum}
            ></Input>
            <Box onClick={onClickSendAuthNum} sx={{ width: "20%" }}>
              <Text size="13px" weight="medium" color="grey">인증번호 받기</Text>
            </Box>
          </Box>

          <Box
            sx={{ display: "flex", flexDirection: "col", alignItems: "center" }}
          >
            <Input
              type="text"
              placeholder="인증번호"
              onChange={onChangeAuthNum}
            ></Input>
            <Box sx={{ width: "20%" }}></Box>
          </Box>

          <Box onClick={onClickSignup} sx={{ mt: "20px"}}>
            <TextButton size="l" my="15px">회원가입</TextButton>
          </Box>
          <Box onClick={goToLogin}>
            {/* <Text size="s">로그인</Text> */}
            <TextButton size="l" color="secondary" my="5px">로그인</TextButton>
          </Box>

          <Text>{signupMessage}</Text>
        </Box>
      </Box>
    </Modal>
  );
}
