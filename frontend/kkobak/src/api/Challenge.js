import api from "api/api.js";

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
  categoryId,
  contents,
  detailCategoryId,
  endTime,
  goal,
  imgurl,
  limitpeople,
  roomtype,
  startTime,
  tagList,
  title,
  unit,
  watch,
  password,
  success,
  fail
) {
  api
    .post("challenge/register", {
      alarm: alarm,
      categoryId: categoryId,
      detailCategoryId: detailCategoryId,
      contents: contents,
      endTime: endTime,
      goal: goal,
      imgurl: imgurl,
      limitpeople: limitpeople,
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
};
