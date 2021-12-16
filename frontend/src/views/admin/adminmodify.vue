<template>
  <b-container class="mb-1">
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
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
              ></b-form-input>
            </b-form-group>

            <b-form-group id="email-group" label="이메일:" label-for="email">
              <b-form-input
                id="email"
                v-model="user.email"
                required
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
              ></b-form-input>
            </b-form-group>
            <b-button variant="primary" class="m-1" @click="update"
              >회원정보수정</b-button
            >
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/util/http-common";

export default {
  components: {},
  name: "adminmodify",
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
    async update(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
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
            if (data == "success") {
              msg = "수정이 완료되었습니다.";
            }
            alert(msg);
            this.$router.push({ name: "AdminList" });
          });
    },
  },
  computed: {
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
    this.user = this.$route.params.user;
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
