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
                            description="이름을 입력하세요."
                          >
                            <b-form-input
                              id="username"
                              v-model="user.username"
                              required
                              placeholder="이름 입력...."
                            ></b-form-input>
                          </b-form-group>
                          <b-form-group label="비밀번호:" label-for="userpwd">
                            <b-form-input
                              type="password"
                              id="userpwd"
                              v-model="user.userpwd"
                              required
                              placeholder="비밀번호 입력...."
                            ></b-form-input>
                          </b-form-group>
                          <b-form-group
                            id="pwdchk-group"
                            label="비밀번호 확인:"
                            label-for="pwdchk"
                            description="비밀번호를 다시 입력해주세요."
                          >
                            <b-form-input
                              type="password"
                              id="pwdchk"
                              ref="pwdchk"
                              v-model="user.pwdchk"
                              required
                              placeholder="비밀번호 재입력...."
                            >
                            </b-form-input>
                            <b-button
                              type="button"
                              variant="primary"
                              @click="PasswordCheck"
                              >비밀번호 확인</b-button
                            >
                          </b-form-group>
                          <b-form-group
                            id="email-group"
                            label="이메일:"
                            label-for="email"
                            description="이메일을 입력하세요."
                          >
                            <b-form-input
                              id="email"
                              v-model="user.email"
                              required
                              placeholder="이메일 입력...."
                            ></b-form-input>
                          </b-form-group>
                          <b-form-group
                            id="region-group"
                            label="거주지역:"
                            label-for="region"
                            description="거주지를 입력하세요"
                          >
                            <b-form-input
                              id="region"
                              v-model="user.region"
                              required
                              placeholder="거주지역 입력...."
                            ></b-form-input>
                          </b-form-group>
                          <b-button type="submit" variant="primary" class="m-1"
                            >회원정보수정</b-button
                          ><b-button variant="danger" @click="withdraw"
                            >회원탈퇴</b-button
                          >
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
        <div class="container">
          <div class="title">
            <h2>Basic Elements</h2>
          </div>
          <basic-elements></basic-elements>
        </div>
      </div>
      <div class="section section-navbars">
        <div class="container">
          <small-navigation></small-navigation>
        </div>
        <navigation></navigation>
      </div>
      <div class="section section-tabs">
        <div class="container">
          <tabs></tabs>
        </div>
      </div>
      <div class="section section-white">
        <div class="container">
          <nav-pills></nav-pills>
        </div>
      </div>
      <div class="section section-notifications">
        <div class="container">
          <div class="title">
            <h3>Notifications</h3>
          </div>
        </div>
        <notifications></notifications>
      </div>
      <div class="section">
        <div class="container">
          <typography-images></typography-images>
        </div>
      </div>
      <div class="section section-javascript">
        <div class="container">
          <javascript-components></javascript-components>
        </div>
      </div>
      <div class="section">
        <div class="container text-center">
          <div class="md-layout">
            <div
              class="md-layout-item md-size-66 md-xsmall-size-100 ml-auto mr-auto text-center"
            >
              <h2>Completed with examples</h2>
              <h4>
                The kit comes with three pre-built pages to help you get started
                faster. You can change the text and images and you're good to
                go. More importantly, looking at them will give you a picture of
                what you can built with this powerful kit.
              </h4>
            </div>
          </div>
        </div>
      </div>
      <div class="section section-signup page-header" :style="signupImage">
        <div class="container">
          <div class="md-layout">
            <div
              class="md-layout-item md-size-33 md-medium-size-40 md-small-size-50 md-xsmall-size-70 mx-auto text-center"
            >
              <login-card header-color="green">
                <h4 slot="title" class="card-title">Login</h4>
                <md-button
                  slot="buttons"
                  href="javascript:void(0)"
                  class="md-just-icon md-simple md-white"
                >
                  <i class="fab fa-facebook-square"></i>
                </md-button>
                <md-button
                  slot="buttons"
                  href="javascript:void(0)"
                  class="md-just-icon md-simple md-white"
                >
                  <i class="fab fa-twitter"></i>
                </md-button>
                <md-button
                  slot="buttons"
                  href="javascript:void(0)"
                  class="md-just-icon md-simple md-white"
                >
                  <i class="fab fa-google-plus-g"></i>
                </md-button>
                <p slot="description" class="description">Or Be Classical</p>
                <md-field class="md-form-group" slot="inputs">
                  <md-icon>face</md-icon>
                  <label>First Name...</label>
                  <md-input v-model="firstname"></md-input>
                </md-field>
                <md-field class="md-form-group" slot="inputs">
                  <md-icon>email</md-icon>
                  <label>Email...</label>
                  <md-input v-model="email" type="email"></md-input>
                </md-field>
                <md-field class="md-form-group" slot="inputs">
                  <md-icon>lock_outline</md-icon>
                  <label>Password...</label>
                  <md-input v-model="password"></md-input>
                </md-field>
                <md-button slot="footer" class="md-simple md-success md-lg">
                  Get Started
                </md-button>
              </login-card>
            </div>
          </div>
        </div>
      </div>
      <div class="md-layout">
        <div class="md-layout-item text-center">
          <md-button href="#/login" class="md-simple md-success md-lg"
            >View Login Page</md-button
          >
        </div>
      </div>
      <div class="section section-examples">
        <div class="container-fluid text-center">
          <div class="md-layout">
            <div class="md-layout-item">
              <a href="#/landing" target="_blank">
                <img
                  :src="landing"
                  alt="Rounded Image"
                  class="img-raised rounded img-fluid"
                />
              </a>
              <md-button href="#/landing" class="md-simple md-success md-lg"
                >View Landing Page</md-button
              >
            </div>
            <div class="md-layout-item">
              <a href="#/profile" target="_blank">
                <img
                  :src="profile"
                  alt="Rounded Image"
                  class="img-raised rounded img-fluid"
                />
              </a>
              <md-button href="#/profile" class="md-simple md-success md-lg"
                >View Profile Page</md-button
              >
            </div>
          </div>
        </div>
      </div>
      <div class="section section-download" id="downloadSection">
        <div class="container">
          <div class="md-layout text-center">
            <div class="md-layout-item md-size-66 md-small-size-100 mx-auto">
              <h2>Do you love this UI Kit?</h2>
              <h4>
                Cause if you do, it can be yours for FREE. Hit the buttons below
                to navigate to our website where you can find the kit. Our
                friends from
                <a
                  href="https://themeisle.com/?utm_campaign=mkfree-hestia&amp;utm_source=creativetim&amp;utm_medium=website"
                  target="_blank"
                  >ThemeIsle</a
                >
                created a Wordpress Theme which can be also downloaded for free.
                Start a new project or give an old Bootstrap project a new look!
              </h4>
            </div>
            <div class="md-layout-item md-size-50 md-small-size-100 mx-auto">
              <md-button
                href="https://www.creative-tim.com/product/material-kit"
                target="_blank"
                class="md-success md-lg"
                ><i class="fab fa-html5"></i> Free HTML Download</md-button
              >
              <md-button
                href="https://themeisle.com/themes/hestia/?utm_campaign=mkfree-hestia&amp;utm_source=creativetim&amp;utm_medium=website"
                class="md-success md-lg"
                target="_blank"
                ><i class="fab fa-wordpress"></i> Wordpress Theme</md-button
              >
            </div>
          </div>
          <br /><br />

          <div class="md-layout text-center">
            <div class="md-layout-item md-size-66 md-small-size-100 mx-auto">
              <h2>Want more?</h2>
              <h4>
                We've just launched
                <a
                  href="https://www.creative-tim.com/product/vue-material-dashboard-pro?ref=utp-mk-demos"
                  target="_blank"
                  >Vue Material Dashboard PRO</a
                >
                and
                <a
                  href="https://www.creative-tim.com/product/vue-material-kit-pro?ref=utp-vmk-demos"
                  target="_blank"
                  >Vue Material Kit PRO</a
                >. Every product has a huge number of components, sections and
                example pages. Start Your Development With A Badass Bootstrap UI
                Kit inspired by Material Design.
              </h4>
            </div>
            <div class="md-layout-item md-size-50 md-small-size-100 mx-auto">
              <div class="btn-container">
                <md-button
                  href="https://www.creative-tim.com/product/vue-material-kit-pro?ref=utp-vmk-demos"
                  class="md-button md-success md-lg md-upgrade"
                  target="_blank"
                  ><md-icon>unarchive</md-icon>Vue Material Kit PRO</md-button
                >
                <md-button
                  href="https://www.creative-tim.com/product/vue-material-dashboard-pro?ref=utp-vmd-demos"
                  class="md-button md-success md-lg md-upgrade"
                  target="_blank"
                  ><md-icon>unarchive</md-icon>Vue Material Dashboard
                  PRO</md-button
                >
              </div>
            </div>
          </div>

          <div class="sharing-area text-center">
            <div class="md-layout">
              <div class="md-layout-item">
                <h3>Thank you for supporting us!</h3>
              </div>
            </div>

            <md-button class="md-twitter"
              ><i class="fab fa-twitter"></i>Tweet</md-button
            >
            <md-button class="md-facebook"
              ><i class="fab fa-facebook-square"></i> Share</md-button
            >
            <md-button class="md-google"
              ><i class="fab fa-google-plus"></i> Share</md-button
            >
            <md-button class="md-github"
              ><i class="fab fa-github"></i> Star</md-button
            >
          </div>
        </div>
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
