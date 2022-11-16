import api from "api/api.js";
import apiS3 from "./apiS3";

function getChallengeDetail(challengeId, success, fail) {
  api.get(`/challenge/${challengeId}`).then(success).catch(fail);
}

function checkChallengePassword(challengeId, password, success, fail) {
  api
    .post("/challenge/chk-pw", { id: challengeId, password: password })
    .then(success)
    .catch(fail);
}

function fetchChallengeList(page, success, fail) {
  api.get(`/challenge/list/${page}`).then(success).catch(fail);
}

function fetchChallengePageCnt(success, fail) {
  api
    .get(`challenge/list/page-cnt?size=6&&sort=id,DESC`)
    .then(success)
    .catch(fail);
}

function getChallengeListWithCategory(categoryId, page, success, fail) {
  api
    .get(`/challenge/list/category/${categoryId}/${page}`)
    .then(success)
    .catch(fail);
}

function getChallengeListWithDetailCategory(
  detailCategoryId,
  page,
  success,
  fail
) {
  api
    .get(`/challenge/list/detail/${detailCategoryId}/${page}`)
    .then(success)
    .catch(fail);
}

function searchChallengeListWithNickname(nickname, page, success, fail) {
  api
    .get(`/challenge/list/search/nickname/${nickname}/${page}`)
    .then(success)
    .catch(fail);
}

function searchChallengeListWithTag(tag, page, success, fail) {
  api
    .get(`/challenge/list/search/tag/${tag}/${page}`)
    .then(success)
    .catch(fail);
}

function searchChallengeListWithTitle(word, page, success, fail) {
  api
    .get(`/challenge/list/search/title/${word}/${page}`)
    .then(success)
    .catch(fail);
}

function registerChallenge(
  alarm,
  alramDir,
  categoryId,
  contents,
  detailCategoryId,
  endTime,
  goal,
  imgurl,
  kkobak,
  limitPeople,
  password,
  roomtype,
  startTime,
  tagList,
  title,
  unit,
  watch,
  success,
  fail
) {
  api
    .post("challenge/register", {
      alarm: alarm,
      alramDir: alramDir,
      categoryId: categoryId,
      contents: contents,
      detailCategoryId: detailCategoryId,
      endTime: endTime,
      goal: goal,
      imgurl: imgurl,
      kkobak: kkobak,
      limitPeople: limitPeople,
      password: password,
      roomtype: roomtype,
      startTime: startTime,
      tagList: tagList,
      title: title,
      unit: unit,
      watch: watch,
    })
    .then(success)
    .catch(fail);
}

function requestCalendarCheckChallenge(chlId, year, month, success, fail) {
  api
    .get(`/challenge/check-done-date/${chlId}/${year}/${month}`)
    .then(success)
    .catch(fail);
}

function requestChallengeUseWatch(page, success, fail) {
  api
    .get(`/challenge/watch/true?page=${page - 1}&size=8&sort=id,DESC`)
    .then(success)
    .catch(fail);
}

function requestChallengeNoUseWatch(page, success, fail) {
  api
    .get(`/challenge/watch/false?page=${page - 1}&size=8&sort=id,DESC`)
    .then(success)
    .catch(fail);
}

function fetchWatchMyChallengePageCnt(success, fail) {
  api.get(`/challenge/watch-cnt/true?size=8`).then(success).catch(fail);
}

function fetchNoWatchMyChallengePageCnt(success, fail) {
  api.get(`/challenge/watch-cnt/false?size=8`).then(success).catch(fail);
}

function fetchGroupChallengeList(page, success, fail) {
  api
    .get(`/challenge/list/group-chl?page=${page - 1}&size=8&sort=id,DESC`)
    .then(success)
    .catch(fail);
}

function fetchGroupChallengePageCnt(success, fail) {
  api
    .get(`/challenge/list/group-chl/cnt?size=8&sort=id,DESC`)
    .then(success)
    .catch(fail);
}

function requestChallengeParticipate(chlId, success, fail) {
  api.get(`/challenge/participate/${chlId}/1`).then(success).catch(fail);
}

function requestChallengeUserList(chlId, success, fail) {
  api.get(`/challenge/user-list/stat/${chlId}`).then(success).catch(fail);
}

function requestParticipateCheck(chlId, success, fail) {
  api.get(`/challenge/member/${chlId}`).then(success).catch(fail);
}

function requestChallengeCount(cid, type, success, fail) {
  api.get(`challenge/state/${cid}/${type}`).then(success).catch(fail);
}

function requestTotalRunStat(cid, success, fail) {
  api.get(`/challenge/run-stat/${cid}`).then(success).catch(fail);
}

function requestTotalMedStat(cid, success, fail) {
  api.get(`/challenge/med-stat/${cid}`).then(success).catch(fail);
}

function requestTotalHabitStat(cid, success, fail) {
  api.get(`/challenge/habit-cnt/${cid}`).then(success).catch(fail);
}


function requestRanking(cid, success, fail) {
  api.get(`/challenge/ranking/${cid}`).then(success).catch(fail);
}

function requestAppearStat(cid, success, fail) {
  api.get(`/challenge/get-logs/${cid}`).then(success).catch(fail);
}

export {
  getChallengeDetail,
  checkChallengePassword,
  fetchChallengeList,
  fetchChallengePageCnt,
  getChallengeListWithCategory,
  getChallengeListWithDetailCategory,
  searchChallengeListWithNickname,
  searchChallengeListWithTag,
  searchChallengeListWithTitle,
  registerChallenge,
  requestCalendarCheckChallenge,
  requestChallengeUseWatch,
  requestChallengeNoUseWatch,
  fetchWatchMyChallengePageCnt,
  fetchNoWatchMyChallengePageCnt,
  fetchGroupChallengeList,
  fetchGroupChallengePageCnt,
  requestChallengeParticipate,
  requestChallengeUserList,
  requestParticipateCheck,
  requestChallengeCount,
  requestTotalRunStat,
  requestTotalMedStat,
  requestTotalHabitStat,
  requestRanking,
  requestAppearStat,
};
