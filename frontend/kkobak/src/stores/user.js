// 액션의 타입 정의 Action Type
const SET_USER = "user/SET_USER";

export const setUserInfoToStore = (info) => ({ type: SET_USER, info });

// 초기 상태 정의 Initial State
const initialState = {
  info: null,
};

// 리듀서 Reducer
export default function user(state = initialState, action) {
  switch (action.type) {
    case SET_USER:
      return {
        ...state,
        info: action.info,
      };
    default:
      return state;
  }
}
