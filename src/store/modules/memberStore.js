import jwt_decode from "jwt-decode";
import { login } from "@/api/member.js";
import { findById } from "../../api/member";

const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
  },
  getters: {
    checkUserInfo: function(state) {
      return state.userInfo;
    },
    checkLogin: function(state) {
      return state.isLogin;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
    SET_LOGOUT: (state, isLogin) => {
      state.isLogin = isLogin;
      state.userInfo = null;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        (response) => {
          if (response.data.message === "success") {
            let token = response.data["access-token"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            console.log("login success");
            sessionStorage.setItem("access-token", token);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
          }
        },
        () => {}
      );
    },
    getUserInfo({ commit }, token) {
      let decode_token = jwt_decode(token);
      findById(
        decode_token.userid,
        (response) => {
          if (response.data.message === "success") {
            commit("SET_USER_INFO", response.data.userInfo);
          } else {
            console.log("유저 정보 없음!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    logout({ commit }) {
      commit("SET_LOGOUT", false);
      sessionStorage.removeItem("access-token");
    },
    // checkLogin({ commit }, token) {
    //   let decode_token = jwt_decode(token);
    //   findById(
    //     decode_token.userid,
    //     (response) => {
    //       if (response.data.message === "success") {
    //       } else {
    //         alert("로그인 후 이용 가능합니다.");

    //       }
    //     },
    //     (error) => {
    //       console.log(error);
    //     }
    //   );
    // }
  },
};

export default memberStore;
