<template>
  <div class="wrapper">
    <div class="section page-header header-filter" :style="headerStyle">
      <div class="container">
        <div class="md-layout">
          <div
            class="md-layout-item mt-5 md-size-33 md-medium-size-40 md-small-size-50 md-xsmall-size-70 mx-auto text-center"
          >
            <login-card header-color="info">
              <h4 slot="title" class="card-title">MyPage</h4>
              <p slot="description" class="description">내정보 보기</p>
              <md-field :class="idCheck" slot="inputs">
                <md-icon>face</md-icon>
                <label>{{ idtxt }}</label>
                <md-input v-model="user.userid" disabled></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>face</md-icon>
                <label>{{ nametxt }}</label>
                <md-input v-model="user.username" disabled></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>mail</md-icon>
                <label>{{ emailtxt }}</label>
                <md-input v-model="user.email" disabled></md-input>
              </md-field>

              <md-field class="md-form-group" slot="inputs">
                <md-icon>home</md-icon>
                <label>{{ regiontxt }}</label>
                <md-input v-model="user.region" disabled></md-input>
              </md-field>
              <md-button
                slot="footer"
                class="md-simple md-info md-lg"
                @click="gotoupdateanddelete"
              >
                회원정보수정
              </md-button>
            </login-card>
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
import { LoginCard } from "@/components";

import { mapActions, mapMutations, mapState } from "vuex";

const memberStore = "memberStore";
export default {
  components: {
    LoginCard,
  },
  name: "membermypage",
  bodyClass: "index-page",
  props: {
    image: {
      type: String,
      default: require("@/assets/img/vue-mk-header.jpg"),
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
