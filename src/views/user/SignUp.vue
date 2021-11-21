<template>
  <div class="wrapper">
    <div class="section page-header header-filter" :style="headerStyle">
      <div class="container">
        <div class="md-layout">
          <div
            class="md-layout-item mt-5 md-size-33 md-medium-size-40 md-small-size-50 md-xsmall-size-70 mx-auto text-center"
          >
            <login-card header-color="green">
              <h4 slot="title" class="card-title">SignUp</h4>
              <p slot="description" class="description">Welcome!</p>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>face</md-icon>
                <label>아이디를 입력하세요...</label>
                <md-input ref="userid" v-model="user.userid"></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>face</md-icon>
                <label>이름을 입력하세요...</label>
                <md-input ref="username" v-model="user.username"></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>lock_outline</md-icon>
                <label>비밀번호를 입력하세요...</label>
                <md-input
                  ref="userpwd"
                  v-model="user.userpwd"
                  type="password"
                ></md-input>
              </md-field>
              <md-field :class="pwdCheck" slot="inputs">
                <md-icon>lock_outline</md-icon>
                <label>다시 한번 입력하세요...</label>
                <md-input
                  ref="pwdchk"
                  v-model="user.pwdchk"
                  type="password"
                ></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>mail</md-icon>
                <label>이메일을 입력하세요...</label>
                <md-input ref="email" v-model="user.email"></md-input>
              </md-field>

              <md-field class="md-form-group" slot="inputs">
                <md-icon>home</md-icon>
                <label>거주지를 입력하세요...</label>
                <md-input ref="region" v-model="user.region"></md-input>
              </md-field>
              <md-button
                slot="footer"
                class="md-simple md-success md-lg"
                @click="confirm"
              >
                Get Started
              </md-button>
            </login-card>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!--
                  <b-button type="submit" variant="primary" class="m-1"
                    >회원가입</b-button
                  >
                  <b-button type="reset" variant="success" class="m-1"
                    >초기화</b-button
                  >
                </b-form>
              </b-card>
            </b-col>
            <b-col></b-col>
          </b-row>
        </b-container> -->
  <!-- -->
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import http from "@/util/http-common";
import { LoginCard } from "@/components";
// import BasicElements from "./components/BasicElementsSection";
// import Navigation from "./components/NavigationSection";
// import SmallNavigation from "./components/SmallNavigationSection";
// import Tabs from "./components/TabsSection";
// import NavPills from "./components/NavPillsSection";
// import Notifications from "./components/NotificationsSection";
// import TypographyImages from "./components/TypographyImagesSection";
// import JavascriptComponents from "./components/JavascriptComponentsSection";
// import { LoginCard } from "@/components";

const houseStore = "houseStore";

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
    LoginCard,
  },
  name: "signup",
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
      user: {
        userid: "",
        username: "",
        userpwd: "",
        pwdchk: "",
        email: "",
        region: "",
      },
      sidoCode: null,
      gugunCode: null,
      isCheckedPwd: null,
    };
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.getSido();

    console.log(this.sidos);
  },
  methods: {
    ...mapActions(houseStore, ["getSido", "getGugun", "getHouseList"]),
    ...mapMutations(houseStore, ["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST"]),
    leafActive() {
      if (window.innerWidth < 768) {
        this.leafShow = false;
      } else {
        this.leafShow = true;
      }
    },
    confirm() {
      let err = true;
      let msg = "";
      !this.user.userid &&
        ((msg = "아이디 입력해주세요"),
        (err = false),
        this.$refs.userid.$el.focus());
      err &&
        !this.user.username &&
        ((msg = "이름 입력해주세요"),
        (err = false),
        this.$refs.username.$el.focus());
      err &&
        !this.user.userpwd &&
        ((msg = "비밀번호 입력해주세요"),
        (err = false),
        this.$refs.userpwd.$el.focus());
      err &&
        !this.user.email &&
        ((msg = "이메일 입력해주세요"),
        (err = false),
        this.$refs.email.$el.focus());
      err &&
        !this.user.region &&
        ((msg = "거주지역 입력해주세요"),
        (err = false),
        this.$refs.region.$el.focus());
      err &&
        this.isCheckedPwd == false &&
        ((msg = "비밀번호를 확인해주세요"),
        (err = false),
        this.$refs.pwdchk.$el.focus());
      if (!err) alert(msg);
      else this.registMember();
    },
    PasswordCheck() {
      if (this.user.userpwd == this.user.pwdchk) {
        alert("비밀번호 확인완료");
        this.isCheckedPwd = true;
      } else {
        alert("비밀번호를 확인해주세요");
        this.$refs.pwdchk.$el.focus();
      }
    },
    registMember() {
      http
        .post(`/user/register`, {
          userid: this.user.userid,
          username: this.user.username,
          userpwd: this.user.userpwd,
          email: this.user.email,
          region: this.user.region,
        })
        .then(({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.$router.push({ name: "loginpage" });
        });
    },
    gugunList() {
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
  },
  watch: {
    userpwd: {},
  },
  computed: {
    ...mapState(houseStore, ["sidos", "guguns"]),
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
    pwdCheck: function() {
      return {
        "md-form-group": this.isCheckedPwd == null,
        "md-error": this.isCheckedPwd == false,
        "md-valid": this.isCheckedPwd,
      };
    },
    // isCheckedPwd: function() {
    //   if(this.userpwd == this.pwdchk) return
    // },
  },
  mounted() {
    this.leafActive();
    window.addEventListener("resize", this.leafActive);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.leafActive);
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
