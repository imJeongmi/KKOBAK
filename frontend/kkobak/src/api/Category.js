import api from "api/api.js";

function getCategoryList(success, fail) {
  api.get('/category/list').then(success).catch(fail)
}

function getDetailCategoryList(category, success, fail) {
  api.get(`/detail-category/list/${category}`).then(success).catch(fail)
}

export {
  getCategoryList,
  getDetailCategoryList
}