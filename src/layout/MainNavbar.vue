<template>
  <md-toolbar
    id="toolbar"
    md-elevation="0"
    class="md-transparent md-absolute"
    :class="extraNavClasses"
    :color-on-scroll="colorOnScroll"
  >
    <div class="md-toolbar-row md-collapse-lateral">
      <div class="md-toolbar-section-start">
        <img
          src="@/assets/logo2.png"
          @click="moveToHome"
          width="150px"
          height="150px"
        />
        <!-- <h1 class="md-title" @click="moveToHome">Happy house</h1> -->
      </div>
      <div class="md-toolbar-section-end">
        <md-button
          class="md-just-icon md-simple md-toolbar-toggle"
          :class="{ toggled: toggledClass }"
          @click="toggleNavbarMobile()"
        >
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </md-button>

        <div class="md-collapse">
          <div class="md-collapse-wrapper">
            <mobile-menu nav-mobile-section-start="false">
              <!-- Here you can add your items from the section-start of your toolbar -->
            </mobile-menu>
            <md-list>
              <li class="md-list-item" v-if="!showDownload">
                <a
                  href="javascript:void(0)"
                  class="md-list-item-router md-list-item-container md-button-clean dropdown"
                >
                  <div class="md-list-item-content">
                    <drop-down direction="down">
                      <md-button
                        slot="title"
                        class="md-button md-button-link md-white md-simple dropdown-toggle"
                        data-toggle="dropdown"
                      >
                        <i class="material-icons">apps</i>
                        <p>Components</p>
                      </md-button>
                      <ul class="dropdown-menu dropdown-with-icons">
                        <li>
                          <a href="#/">
                            <i class="material-icons">layers</i>
                            <p>All Components</p>
                          </a>
                        </li>
                        <li>
                          <a
                            href="https://demos.creative-tim.com/vue-material-kit/documentation/"
                          >
                            <i class="material-icons">content_paste</i>
                            <p>Documentation</p>
                          </a>
                        </li>
                      </ul>
                    </drop-down>
                  </div>
                </a>
              </li>

              <!-- <md-list-item
                href="https://demos.creative-tim.com/vue-material-kit/documentation/"
                target="_blank"
                v-if="showDownload"
              > -->

              <li class="md-list-item">
                <a
                  href="javascript:void(0)"
                  class="md-list-item-router md-list-item-container md-button-clean dropdown"
                >
                  <div class="md-list-item-content">
                    <drop-down direction="down">
                      <md-button
                        id="a"
                        slot="title"
                        class="md-button md-button-link md-info md-simple md-just-icon"
                        data-toggle="dropdown"
                      >
                        <md-icon>home_work</md-icon>
                      </md-button>
                      <ul class="dropdown-menu dropdown-menu-right">
                        <li class="dropdown-header">apartment info</li>
                        <li>
                          <a class="dropdown-item" @click="goToAptList"
                            >아파트정보</a
                          >
                        </li>
                        <li>
                          <a class="dropdown-item" @click="goToKakaoMap"
                            >아파트맵</a
                          >
                        </li>
                        <li class="dropdown-divider"></li>
                        <li>
                          <a class="dropdown-item" @click="moveToApi"
                            >슬세권찾기</a
                          >
                        </li>
                      </ul>
                    </drop-down>
                  </div>
                </a>
              </li>

              <md-list-item
                href="javascript:void(0)"
                @click="moveToNotice"
                v-if="showDownload"
              >
                <i class="material-icons">content_paste</i>
                <p>공지사항</p>
              </md-list-item>

              <md-list-item
                href="javascript:void(0)"
                @click="moveToQna"
                v-if="showDownload"
              >
                <i class="material-icons">cloud_download</i>
                <p>질문게시판</p>
              </md-list-item>

              <li class="md-list-item" v-else>
                <a
                  href="javascript:void(0)"
                  class="md-list-item-router md-list-item-container md-button-clean dropdown"
                >
                  <div class="md-list-item-content">
                    <drop-down direction="down">
                      <md-button
                        slot="title"
                        class="md-button md-button-link md-white md-simple dropdown-toggle"
                        data-toggle="dropdown"
                      >
                        <i class="material-icons">view_carousel</i>
                        <p>Examples</p>
                      </md-button>
                      <ul class="dropdown-menu dropdown-with-icons">
                        <li>
                          <a href="#/landing">
                            <i class="material-icons">view_day</i>
                            <p>Landing Page</p>
                          </a>
                        </li>
                        <li>
                          <a href="#/login">
                            <i class="material-icons">fingerprint</i>
                            <p>Login Page</p>
                          </a>
                        </li>
                        <li>
                          <a href="#/profile">
                            <i class="material-icons">account_circle</i>
                            <p>Profile Page</p>
                          </a>
                        </li>
                      </ul>
                    </drop-down>
                  </div>
                </a>
              </li>

              <!-- twitter, facebook -->
              <!-- <md-list-item
                href="https://twitter.com/CreativeTim"
                target="_blank"
              >
                <i class="fab fa-twitter"></i>
                <p class="hidden-lg">Twitter</p>
                <md-tooltip md-direction="bottom"
                  >Follow us on Twitter</md-tooltip
                >
              </md-list-item>
              <md-list-item
                href="https://www.facebook.com/CreativeTim"
                target="_blank"
              >
                <i class="fab fa-facebook-square"></i>
                <p class="hidden-lg">Facebook</p>
                <md-tooltip md-direction="bottom"
                  >Like us on Facebook</md-tooltip
                >
              </md-list-item> -->

              <!-- 관리자 -->
              <li class="md-list-item" v-if="user">
                <a
                  href="javascript:void(0)"
                  class="md-list-item-router md-list-item-container md-button-clean dropdown"
                >
                  <div class="md-list-item-content" v-if="user.admin == 1">
                    <drop-down direction="down">
                      <md-button
                        slot="title"
                        class="md-button md-button-link md-info md-simple md-just-icon"
                        data-toggle="dropdown"
                      >
                        <md-icon>face</md-icon>
                      </md-button>
                      <ul class="dropdown-menu dropdown-menu-right">
                        <li>
                          <a class="dropdown-item" @click="moveToAdmin"
                            >관리자모드</a
                          >
                        </li>
                        <li>
                          <a
                            class="dropdown-item"
                            @click.prevent="onClickLogout"
                            >로그아웃</a
                          >
                        </li>
                      </ul>
                    </drop-down>
                  </div>
                </a>
              </li>
              <!-- 일반사용자 -->
              <li class="md-list-item" v-if="user">
                <a
                  href="javascript:void(0)"
                  class="md-list-item-router md-list-item-container md-button-clean dropdown"
                >
                  <div class="md-list-item-content" v-if="user.admin == 0">
                    <drop-down direction="down">
                      <md-button
                        slot="title"
                        class="md-button md-button-link md-info md-simple md-just-icon"
                        data-toggle="dropdown"
                      >
                        <md-icon>face</md-icon>
                      </md-button>
                      <ul class="dropdown-menu dropdown-menu-right">
                        <li>
                          <a class="dropdown-item" @click="moveToMypage"
                            >{{ user.username }}({{ user.userid }})님
                            환영합니다.</a
                          >
                        </li>
                        <li>
                          <a
                            class="dropdown-item"
                            @click.prevent="onClickLogout"
                            >로그아웃</a
                          >
                        </li>
                      </ul>
                    </drop-down>
                  </div>
                </a>
              </li>
              <!-- 로그인 안했을때 -->
              <li class="md-list-item" v-else>
                <a
                  href="javascript:void(0)"
                  class="md-list-item-router md-list-item-container md-button-clean dropdown"
                >
                  <div class="md-list-item-content">
                    <drop-down direction="down">
                      <md-button
                        slot="title"
                        class="md-button md-button-link md-white md-simple md-just-icon"
                        data-toggle="dropdown"
                      >
                        <md-icon>face</md-icon>
                      </md-button>
                      <ul class="dropdown-menu dropdown-menu-right">
                        <li>
                          <a class="dropdown-item" @click="moveToLogin"
                            >로그인</a
                          >
                        </li>
                        <li>
                          <a class="dropdown-item" @click="moveToSignUp"
                            >회원가입</a
                          >
                        </li>
                      </ul>
                    </drop-down>
                  </div>
                </a>
              </li>
              <!-- <md-list-item @click="moveToLogin" v-if="user == null">
                <md-button class="md-rose md-round">
                  <md-list-item @click="moveToLogin">
                  <i class="fab fa-instagram"></i>
                  <p class="hidden-lg">로그인하세요</p>
                  <p>로그인하세요</p>
                  </md-list-item></md-button
                  </md-button
                >
                <md-tooltip md-direction="bottom"
                  >Follow us on Instagram
                </md-tooltip>
              </md-list-item> -->
            </md-list>
          </div>
        </div>
      </div>
    </div>
  </md-toolbar>
</template>

<script>
import { mapActions, mapMutations, mapState } from "vuex";

const memberStore = "memberStore";

let resizeTimeout;
function resizeThrottler(actualResizeHandler) {
  // ignore resize events as long as an actualResizeHandler execution is in the queue
  if (!resizeTimeout) {
    resizeTimeout = setTimeout(() => {
      resizeTimeout = null;
      actualResizeHandler();

      // The actualResizeHandler will execute at a rate of 15fps
    }, 66);
  }
}

import MobileMenu from "@/layout/MobileMenu";
export default {
  components: {
    MobileMenu,
  },
  props: {
    type: {
      type: String,
      default: "white",
      validator(value) {
        return [
          "white",
          "default",
          "primary",
          "danger",
          "success",
          "warning",
          "info",
        ].includes(value);
      },
    },
    colorOnScroll: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      user: {
        userid: "",
        username: "",
        userpwd: "",
        pwdchk: "",
        email: "",
        region: "",
        admin: "",
      },
      extraNavClasses: "",
      toggledClass: false,
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    showDownload() {
      const excludedRoutes = ["login", "landing", "profile"];
      return excludedRoutes.every((r) => r !== this.$route.name);
    },
  },
  methods: {
    ...mapActions(memberStore, ["getUserInfo", "logout"]),
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    moveToLogin() {
      this.$router.push({ name: "loginpage" });
    },
    goToKakaoMap() {
      this.$router.push({ name: "kakaoapi" });
    },
    moveToApi() {
      this.$router.push({ name: "apiset" });
    },
    moveToMypage() {
      this.$router.push({ name: "membermypage" });
    },
    goToAptList() {
      this.$router.push({ name: "house" });
    },
    moveToSignUp() {
      this.$router.push({ name: "signup" });
    },
    moveToAdmin() {
      this.$router.push({ name: "admin" });
    },
    moveToNotice() {
      this.$router.push({ name: "noticeboardlist" });
    },
    moveToHome() {
      this.$router.push({ name: "index" });
    },
    moveToQna() {
      this.$router.push({ name: "qna" });
    },
    bodyClick() {
      let bodyClick = document.getElementById("bodyClick");

      if (bodyClick === null) {
        let body = document.querySelector("body");
        let elem = document.createElement("div");
        elem.setAttribute("id", "bodyClick");
        body.appendChild(elem);

        let bodyClick = document.getElementById("bodyClick");
        bodyClick.addEventListener("click", this.toggleNavbarMobile);
      } else {
        bodyClick.remove();
      }
    },
    toggleNavbarMobile() {
      this.NavbarStore.showNavbar = !this.NavbarStore.showNavbar;
      this.toggledClass = !this.toggledClass;
      this.bodyClick();
    },
    handleScroll() {
      let scrollValue =
        document.body.scrollTop || document.documentElement.scrollTop;
      let navbarColor = document.getElementById("toolbar");
      this.currentScrollValue = scrollValue;
      if (this.colorOnScroll > 0 && scrollValue > this.colorOnScroll) {
        this.extraNavClasses = `md-${this.type}`;
        navbarColor.classList.remove("md-transparent");
      } else {
        if (this.extraNavClasses) {
          this.extraNavClasses = "";
          navbarColor.classList.add("md-transparent");
        }
      }
    },
    scrollListener() {
      resizeThrottler(this.handleScroll);
    },
    scrollToElement() {
      let element_id = document.getElementById("downloadSection");
      if (element_id) {
        element_id.scrollIntoView({ block: "end", behavior: "smooth" });
      }
    },
    onClickLogout() {
      this.logout();
      if (this.$route.path != "/") this.$router.push({ name: "index" });
      location.reload();
    },
  },
  mounted() {
    document.addEventListener("scroll", this.scrollListener);
  },
  beforeDestroy() {
    document.removeEventListener("scroll", this.scrollListener);
  },
  created() {
    this.user = null;
    this.user = this.userInfo;
  },
};
</script>
<style scoped>
a:hover {
  background-color: gold;
}
</style>
