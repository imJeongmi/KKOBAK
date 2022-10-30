import api from "api/api.js";

function requestJoin(email, hp, nickname, password, success, fail) {
  api
    .post("member/join", {
      email: email,
      hp: hp,
      nickname: nickname,
      password: password,
    })
    .then(success)
    .catch(fail);
}

function requestEmailCheck(email, success, fail) {
    api.post("/member/chk-email", {email})
    .then(success)
    .catch(fail)
}

function requestNicknameCheck(nickname, success, fail) {
    api.post("member/chk-name", {nickname})
    .then(success)
    .catch(fail)
}

function requestAuthNum(phoneNumber, success, fail) {
  api
    .post("member/register/phone", {
      phoneNumber: phoneNumber,
    })
    .then(success)
    .catch(fail)
}

function requestConfirmAuthNum() {

}

function requestPasswdCheck(passwd, success, fail) {
    api.post("member/chk-pw", {passwd})
    .then(success)
    .catch(fail)
}

export {
  requestJoin,
  requestEmailCheck,
  requestNicknameCheck,
  requestAuthNum,
  requestConfirmAuthNum,
  requestPasswdCheck,
};
