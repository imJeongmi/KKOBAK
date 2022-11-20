import api from "api/api.js";

function logController(chlId, day, success, fail) {
  api
    .post("/log/change-status", {
      chlId: chlId,
      day: day,
    })
    .then(success)
    .catch(fail);
}

export { logController };
