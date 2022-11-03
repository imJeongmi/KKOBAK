import apiS3 from "api/apiS3.js";

function uploadPhoto(formdata, success, fail) {
  apiS3.post('/s3/file', formdata).then(success).catch(fail)
}

export {
  uploadPhoto
}