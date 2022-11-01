import api from "api/api.js";

function getChallengeDetail(challengeId, success, fail) {
  api.get(`/challenge/${challengeId}`).then(success).catch(fail)
}

function checkChallengePassword(challengeId, password, success, fail) {
  api.post('/challenge/chk-pw', { id: challengeId, password: password }).then(success).catch(fail)
}

function getChallengeList(page, success, fail) {
  api.get(`/challenge/list/${page}`).then(success).catch(fail)
}

function getChallengeListWithCategory(categoryId, page, success, fail) {
  api.get(`/challenge/list/category/${categoryId}/${page}`).then(success).catch(fail)
}

function getChallengeListWithDetailCategory(detailCategoryId, page, success, fail) {
  api.get(`/challenge/list/detail/${detailCategoryId}/${page}`).then(success).catch(fail)
}

function searchChallengeListWithNickname(nickname, page, success, fail) {
  api.get(`/challenge/list/search/nickname/${nickname}/${page}`).then(success).catch(fail)
}

function searchChallengeListWithTag(tag, page, success, fail) {
  api.get(`/challenge/list/search/tag/${tag}/${page}`).then(success).catch(fail)
}

function searchChallengeListWithTitle(word, page, success, fail) {
  api.get(`/challenge/list/search/title/${word}/${page}`).then(success).catch(fail)
}

function registerChallenge() {
  
}
export {
  getChallengeDetail,
  checkChallengePassword,
  getChallengeList,
  getChallengeListWithCategory,
  getChallengeListWithDetailCategory,
  searchChallengeListWithNickname,
  searchChallengeListWithTag,
  searchChallengeListWithTitle
}