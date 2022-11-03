import api from "api/api.js";

function uploadPhoto(formdata, success, fail) {
  api.post('/s3/file', formdata).then(success).catch(fail)
}

export {
  uploadPhoto
}