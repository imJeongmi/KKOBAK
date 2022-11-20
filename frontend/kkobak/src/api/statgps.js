import api from "api/api.js";

function requestStatGps(year, month, day, cid, success, fail) {
  api.get(`/gps/list/${year}/${month}/${day}/${cid}`).then(success).catch(fail);
}

export { requestStatGps };
