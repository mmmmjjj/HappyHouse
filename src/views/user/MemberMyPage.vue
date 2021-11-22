<template>
  <div class="wrapper">
    <parallax class="page-header header-filter" :style="headerStyle">
      <div class="md-layout">
        <div class="md-layout-item">
          <div class="image-wrapper">
            <img :src="leaf4" alt="leaf4" class="leaf4" v-show="leafShow" />
            <img :src="leaf3" alt="leaf3" class="leaf3" v-show="leafShow" />
            <img :src="leaf2" alt="leaf2" class="leaf2" v-show="leafShow" />
            <img :src="leaf1" alt="leaf1" class="leaf1" v-show="leafShow" />
            <div class="brand">
              <h1>Vue Material Kit</h1>
              <h3>본인정보확인</h3>
            </div>
          </div>
        </div>
      </div>
    </parallax>
    <div class="main main-raised">
      <div class="section section-basic">
        <b-container class="mt-4" v-if="userInfo">
          <b-row>
            <b-col>
              <b-alert variant="secondary" show><h3>내정보</h3></b-alert>
            </b-col>
          </b-row>
          <b-row>
            <b-col></b-col>
            <b-col cols="8">
              <b-jumbotron>
                <template #header>My Page</template>

                <template #lead> 내 정보 확인페이지입니다. </template>

                <hr class="my-4" />

                <b-container class="mb-1">
                  <b-row>
                    <b-col></b-col>
                    <b-col cols="8">
                      <b-card
                        class="text-center mt-3"
                        style="max-width: 40rem"
                        align="left"
                      >
                        <b-form @submit="update" class="text-left">
                          <b-form-group label="아이디:" label-for="userid">
                            <b-form-input
                              id="userid"
                              v-model="user.userid"
                              required
                              placeholder="이름 입력...."
                              readonly
                            ></b-form-input>
                          </b-form-group>
                          <b-form-group
                            id="username-group"
                            label="이름:"
                            label-for="username"
                          >
                            <b-form-input
                              id="username"
                              v-model="user.username"
                              required
                              placeholder="이름 입력...."
                              readonly
                            ></b-form-input>
                          </b-form-group>

                          <b-form-group
                            id="email-group"
                            label="이메일:"
                            label-for="email"
                          >
                            <b-form-input
                              id="email"
                              v-model="user.email"
                              required
                              readonly
                            ></b-form-input>
                          </b-form-group>
                          <b-form-group
                            id="region-group"
                            label="거주지역:"
                            label-for="region"
                          >
                            <b-form-input
                              id="region"
                              v-model="user.region"
                              required
                              readonly
                            ></b-form-input>
                          </b-form-group>
                          <b-button
                            variant="primary"
                            class="m-1"
                            @click="gotoupdateanddelete"
                            >회원정보수정및탈퇴</b-button
                          >
                          <!-- <b-button variant="danger" @click="withdraw"
                            >회원탈퇴</b-button
                          > -->
                        </b-form>
                      </b-card>
                    </b-col>
                    <b-col></b-col>
                  </b-row>
                </b-container>
                <hr class="my-4" />
              </b-jumbotron>
            </b-col>
            <b-col></b-col>
          </b-row>
        </b-container>
      </div>
    </div>
  </div>
</template>

<script>
// import BasicElements from "./components/BasicElementsSection";
// import Navigation from "./components/NavigationSection";
// import SmallNavigation from "./components/SmallNavigationSection";
// import Tabs from "./components/TabsSection";
// import NavPills from "./components/NavPillsSection";
// import Notifications from "./components/NotificationsSection";
// import TypographyImages from "./components/TypographyImagesSection";
// import JavascriptComponents from "./components/JavascriptComponentsSection";
//import { LoginCard } from "@/components";
import http from "@/util/http-common";

import { mapActions, mapMutations, mapState } from "vuex";

const memberStore = "memberStore";
export default {
  components: {
    // BasicElements,
    // Navigation,
    // SmallNavigation,
    // Tabs,
    // NavPills,
    // Notifications,
    // TypographyImages,
    // JavascriptComponents,
    //LoginCard,
  },
  name: "membermypage",
  bodyClass: "index-page",
  props: {
    image: {
      type: String,
      default: require("@/assets/img/vue-mk-header.jpg"),
    },
    leaf4: {
      type: String,
      default: require("@/assets/img/leaf4.png"),
    },
    leaf3: {
      type: String,
      default: require("@/assets/img/leaf3.png"),
    },
    leaf2: {
      type: String,
      default: require("@/assets/img/leaf2.png"),
    },
    leaf1: {
      type: String,
      default: require("@/assets/img/leaf1.png"),
    },
    signup: {
      type: String,
      default: require("@/assets/img/city.jpg"),
    },
    landing: {
      type: String,
      default: require("@/assets/img/landing.jpg"),
    },
    profile: {
      type: String,
      default: require("@/assets/img/profile.jpg"),
    },
  },
  data() {
    return {
      firstname: null,
      email: null,
      password: null,
      leafShow: false,
      user: {
        userid: "",
        username: "",
        userpwd: "",
        pwdchk: "",
        email: "",
        region: "",
      },
      isCheckedPwd: false,
    };
  },
  methods: {
    leafActive() {
      if (window.innerWidth < 768) {
        this.leafShow = false;
      } else {
        this.leafShow = true;
      }
    },
    ...mapActions(memberStore, ["getUserInfo"]),
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    withdraw() {
      if (confirm("정말로 탈퇴하시겠습니까?")) {
        http
          .put(`/user/withdraw`, {
            userid: this.userInfo.userid,
          })
          .then(({ data }) => {
            let msg = "탈퇴 처리시 문제가 발생했습니다.";

            if (data == "success") {
              msg = "탈퇴가 완료되었습니다.";
            }
            alert(msg);
            this.moveList();
          });
      }
    },
    async update(event) {
      event.preventDefault();

      let err = true;
      let msg = "";

      !this.user.username &&
        ((msg = "이름 입력해주세요"),
        (err = false),
        this.$refs.username.focus());
      err &&
        !this.user.userpwd &&
        ((msg = "비밀번호 입력해주세요"),
        (err = false),
        this.$refs.content.focus());
      err &&
        !this.user.email &&
        ((msg = "이메일 입력해주세요"),
        (err = false),
        this.$refs.email.focus());
      err &&
        !this.user.region &&
        ((msg = "거주지역 입력해주세요"),
        (err = false),
        this.$refs.region.focus());
      err &&
        this.isCheckedPwd == false &&
        ((msg = "비밀번호를 확인해주세요"),
        (err = false),
        this.$refs.pwdchk.focus());
      if (!err) alert(msg);
      else
        await http
          .put(`/user/update`, {
            userid: this.user.userid,
            username: this.user.username,
            userpwd: this.user.userpwd,
            email: this.user.email,
            region: this.user.region,
          })
          .then(({ data }) => {
            let msg = "수정 처리시 문제가 발생했습니다.";
            console.log(data);
            if (data == "success") {
              msg = "수정이 완료되었습니다.";
            }
            alert(msg);
            let token = sessionStorage.getItem("access-token");
            this.getUserInfo(token);
            this.$router.push({ name: "Home" });
          });
    },
    moveList() {
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      sessionStorage.removeItem("access-token");
      this.$router.push({
        name: "index",
      });
    },
    gotoupdateanddelete() {
      this.$router.push({
        name: "updateanddelete",
      });
    },
    PasswordCheck() {
      if (this.user.userpwd == this.user.pwdchk) {
        alert("비밀번호 확인완료");
        this.isCheckedPwd = true;
      } else {
        alert("비밀번호를 확인해주세요");
        this.$refs.pwdchk.focus();
      }
    },
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    headerStyle() {
      return {
        backgroundImage: `url(${this.image})`,
      };
    },
    signupImage() {
      return {
        backgroundImage: `url(${this.signup})`,
      };
    },
  },
  mounted() {
    this.leafActive();
    window.addEventListener("resize", this.leafActive);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.leafActive);
  },
  created() {
    this.user = null;
    this.user = this.userInfo;
  },
};
</script>
<style lang="scss">
.section-download {
  .md-button + .md-button {
    margin-left: 5px;
  }
}

@media all and (min-width: 991px) {
  .btn-container {
    display: flex;
  }
}
</style>
