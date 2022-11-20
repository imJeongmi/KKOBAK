import api from "api/api.js";

function registerTodolist(contents, date, success, fail) {
  api
    .post("/todolist/register", {
      contents: contents,
      date: date,
    })
    .then(success)
    .catch(fail);
}

function getTodolist(date, success, fail) {
  const year = date.substr(0, 4);
  const month = date.substr(5, 2);
  const day = date.substr(8, 2);
  api.get(`/todolist/${year}/${month}/${day}`).then(success).catch(fail);
}

function deleteTodolist(todoId, success, fail) {
  api.delete(`/todolist/remove/${todoId}`).then(success).catch(fail);
}

function updateTodolistStatus(todoId, success, fail) {
  api.post(`/todolist/change/status/${todoId}`).then(success).catch(fail);
}

export { registerTodolist, getTodolist, deleteTodolist, updateTodolistStatus };