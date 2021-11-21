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
              <md-field :class="idCheck" slot="inputs">
                <md-icon>face</md-icon>
                <label>{{ idtxt }}</label>
                <md-input
                  ref="userid"
                  v-model="user.userid"
                  @keyup="idPress"
                ></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>face</md-icon>
                <label>{{ nametxt }}</label>
                <md-input
                  ref="username"
                  v-model="user.username"
                  @keyup="Press"
                ></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>lock_outline</md-icon>
                <label>{{ pwdtxt }}</label>
                <md-input
                  ref="userpwd"
                  v-model="user.userpwd"
                  type="password"
                  @keyup="keyPress"
                ></md-input>
              </md-field>
              <md-field :class="pwdCheck" slot="inputs">
                <md-icon>lock_outline</md-icon>
                <label>{{ pwdchktxt }}</label>
                <md-input
                  ref="pwdchk"
                  v-model="user.pwdchk"
                  type="password"
                  @keyup="keyPress"
                ></md-input>
              </md-field>
              <md-field class="md-form-group" slot="inputs">
                <md-icon>mail</md-icon>
                <label>{{ emailtxt }}</label>
                <md-input
                  ref="email"
                  v-model="user.email"
                  @keyup="Press"
                ></md-input>
              </md-field>

              <md-field class="md-form-group" slot="inputs">
                <md-icon>home</md-icon>
                <label>{{ regiontxt }}</label>
                <md-input
                  ref="region"
                  v-model="user.region"
                  @keyup="Press"
                ></md-input>
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
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import http from "@/util/http-common";
import { LoginCard } from "@/components";

const houseStore = "houseStore";

export default {
  components: {
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
      isCheckedPwd: null,
      idchk: null,
      idtxt: "아이디를 입력하세요.",
      nametxt: "이름을 입력하세요.",
      emailtxt: "이메일을 입력하세요.",
      pwdtxt: "비밀번호를 입력하세요.",
      pwdchktxt: "다시 한번 입력해주세요.",
      regiontxt: "거주지를 입력하세요.",
    };
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.getSido();
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
        this.idchk == false &&
        ((msg = "아이디를 확인해주세요"),
        (err = false),
        this.$refs.userid.$el.focus());
      err &&
        this.isCheckedPwd == false &&
        ((msg = "비밀번호를 확인해주세요"),
        (err = false),
        this.$refs.pwdchk.$el.focus());

      if (!err) alert(msg);
      else this.registMember();
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
    keyPress() {
      let pwdchk = this.$refs.pwdchk.$options.propsData.value;
      let pwd = this.$refs.userpwd.$options.propsData.value;
      if (pwd != null) this.pwdtxt = null;
      if (pwdchk == null) this.isCheckedPwd = null;
      else if (pwd == pwdchk) {
        this.isCheckedPwd = true;
        this.pwdchktxt = "사용 가능합니다.";
      }
      if (pwd != pwdchk && pwdchk != null) {
        this.isCheckedPwd = false;
        this.pwdchktxt = "비밀번호가 올바르지 않습니다.";
      }
    },
    idPress() {
      let id = this.$refs.userid.$options.propsData.value;
      if (id == "") {
        this.idtxt = "아이디를 입력하세요.";
        return;
      }
      http
        .get(`/user/idcheck/${this.$refs.userid.$options.propsData.value}`)
        .then(({ data }) => {
          if (data == 0) {
            this.idchk = true;
            this.idtxt = "이 아이디는 사용 가능합니다.";
          } else if (data == 1) {
            this.idchk = false;
            this.idtxt = "이미 사용 중인 아이디입니다.";
          }
        });
    },
    Press() {
      let name = this.$refs.username.$options.propsData.value;
      let email = this.$refs.email.$options.propsData.value;
      let region = this.$refs.region.$options.propsData.value;

      if (name != "") this.nametxt = null;
      else this.nametxt = "이름을 입력하세요.";
      if (email != "") this.emailtxt = null;
      else this.emailtxt = "이메일을 입력하세요.";
      if (region != "") this.regiontxt = null;
      else this.regiontxt = "거주지를 입력하세요.";
    },
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
        "md-valid": this.isCheckedPwd == true,
      };
    },
    idCheck: function() {
      return {
        "md-form-group": this.idchk == null,
        "md-error": this.idchk == false,
        "md-valid": this.idchk == true,
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
