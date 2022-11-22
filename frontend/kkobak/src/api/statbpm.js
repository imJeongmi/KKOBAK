import api from "api/api.js";

function requestStatBpm(year, month, day, cid, success, fail) {
  api.get(`/bpm/list/${year}/${month}/${day}/${cid}`).then(success).catch(fail);
}

export { requestStatBpm };
