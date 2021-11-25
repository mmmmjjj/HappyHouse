<template>
  <div class="wrapper">
    <div class="section page-header header-filter" :style="headerStyle">
      <div class="container">
        <div class="md-layout">
          <div
            class="md-layout-item mt-5 md-size-33 md-medium-size-40 md-small-size-50 md-xsmall-size-70 mx-auto text-center"
          >
            <login-card header-color="info">
              <h4 slot="title" class="card-title">Edit</h4>
              <p slot="description" class="description">내 정보 수정</p>
              <md-field :class="idCheck" slot="inputs">
                <md-icon>face</md-icon>
                <label>{{ idtxt }}</label>
                <md-input
                  ref="userid"
                  v-model="user.userid"
                  disabled
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
                class="md-simple md-info md-lg"
                @click="update"
              >
                정보 수정하기
              </md-button>
            </login-card>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import { LoginCard } from "@/components";

import { mapActions, mapMutations, mapState } from "vuex";

const memberStore = "memberStore";
export default {
  components: { LoginCard },
  name: "updateanddelete",
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
      isCheckedPwd: null,
      nametxt: null,
      emailtxt: null,
      pwdtxt: "비밀번호를 입력하세요.",
      pwdchktxt: "다시 한번 입력해주세요.",
      regiontxt: null,
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
    keyPress() {
      let pwdchk = this.$refs.pwdchk.$options.propsData.value;
      let pwd = this.$refs.userpwd.$options.propsData.value;
      if (pwd != null) this.pwdtxt = null;
      if (pwdchk == "") this.isCheckedPwd = null;
      else if (pwd == pwdchk) {
        this.isCheckedPwd = true;
        this.pwdchktxt = "사용 가능합니다.";
      }
      if (pwd != pwdchk && pwdchk != null) {
        this.isCheckedPwd = false;
        this.pwdchktxt = "비밀번호가 올바르지 않습니다.";
      }
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
    async update(event) {
      event.preventDefault();

      let err = true;
      let msg = "";

      !this.user.username &&
        ((msg = "이름을 입력해주세요"),
        (err = false),
        this.$refs.username.$el.focus());
      err &&
        !this.user.userpwd &&
        ((msg = "비밀번호를 입력해주세요"),
        (err = false),
        this.$refs.userpwd.$el.focus());
      err &&
        !this.user.email &&
        ((msg = "이메일을 입력해주세요"),
        (err = false),
        this.$refs.email.$el.focus());
      err &&
        !this.user.region &&
        ((msg = "거주지역을 입력해주세요"),
        (err = false),
        this.$refs.region.$el.focus());
      err &&
        this.isCheckedPwd == false &&
        ((msg = "회원정보 수정 및 탈퇴를 위해 비밀번호를 확인해주세요"),
        (err = false),
        this.$refs.pwdchk.$el.ocus());
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
            this.$router.push({ name: "membermypage" });
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
    pwdCheck: function() {
      return {
        "md-form-group": this.isCheckedPwd == null,
        "md-error": this.isCheckedPwd == false,
        "md-valid": this.isCheckedPwd == true,
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
