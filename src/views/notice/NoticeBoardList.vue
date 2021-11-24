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
              <h3>공지사항을 확인하세요</h3>
            </div>
          </div>
        </div>
      </div>
    </parallax>
    <div class="main main-raised">
      <div class="section section-basic">
        <!-- -->
        <b-container class="bv-example-row mt-3">
          <b-row class="mb-1">
            <b-col class="text-right">
              <b-button
                variant="outline-primary"
                @click="moveWrite()"
                v-if="userInfo.admin == 1"
                >글쓰기</b-button
              >
              <!-- <b-button variant="outline-primary" @click="check">체크</b-button> -->
            </b-col>
          </b-row>
          <b-row>
            <b-col v-if="articles.length">
              <paginated-list :list-array="articles" />
            </b-col>
            <b-col v-else class="text-center"
              >작성된 공지사항이 없습니다.</b-col
            >
          </b-row>
        </b-container>
        <!-- -->
      </div>
      <div class="section section-navbars">
        <div class="container">
          <small-navigation></small-navigation>
        </div>
        <navigation></navigation>
      </div>
    </div>
  </div>
</template>

<script>
import { listArticle } from "@/api/board.js";
import PaginatedList from "@/views/notice/child/PaginatedList";
import { mapActions, mapMutations, mapState } from "vuex";

const memberStore = "memberStore";
export default {
  components: {
    PaginatedList,
  },
  name: "noticeboardlist",
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
      firstname: null,
      email: null,
      password: null,
      leafShow: false,
      articles: [],
    };
  },
  methods: {
    ...mapActions(memberStore, ["getUserInfo"]),
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    leafActive() {
      if (window.innerWidth < 768) {
        this.leafShow = false;
      } else {
        this.leafShow = true;
      }
    },
    check() {
      let token = sessionStorage.getItem("access-token");
      if (this.getUserInfo(token)) {
        console.log("sucess");
        console.log(this.user.admin);
      }
    },
    moveWrite() {
      this.$router.push({ name: "noticeboardwrite" });
    },
  },
  created() {
    this.user = null;
    this.user = this.userInfo;
    let param = {
      pg: 1,
      spp: 20,
      key: null,
      word: null,
    };
    listArticle(
      param,
      (response) => {
        this.articles = response.data;
      },
      (error) => {
        console.log(error);
      }
    );
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
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
