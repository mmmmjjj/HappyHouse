<template>
  <div class="wrapper">
    <parallax class="page-header header-filter" :style="headerStyle">
      <div class="md-layout">
        <div class="md-layout-item">
          <div class="image-wrapper">
            <div class="brand">
              <img src="@/assets/notice.png" width="300px" height="300px" />
            </div>
          </div>
        </div>
      </div>
    </parallax>
    <div class="main main-raised">
      <div class="section section-basic">
        <div class="container">
          <basic-elements></basic-elements>
        </div>
        <b-container class="bv-example-row mt-3">
          <b-row class="mb-1">
            <b-col class="text-left">
              <md-button
                variant="outline-primary"
                class="md-info md-round"
                @click="listArticle()"
                >목록</md-button
              >
            </b-col>
            <b-col class="text-right" v-if="isWriter">
              <b-button
                variant="outline-info"
                size="sm"
                @click="moveModifyArticle"
                class="mr-2"
                >글수정</b-button
              >
              <b-button
                variant="outline-danger"
                size="sm"
                @click="removeArticle"
                >글삭제</b-button
              >
            </b-col>
          </b-row>
          <b-row class="mb-1">
            <b-col>
              <b-card
                :header-html="
                  `<h3>${article.articleno}.
          ${article.subject} [${article.hit}]</h3><div><h6>${article.userid}</div><div>${article.regtime}</h6></div>`
                "
                class="mb-2"
                border-variant="dark"
                no-body
              >
                <b-card-body class="text-left">
                  <div v-html="message"></div>
                </b-card-body>
              </b-card>
            </b-col>
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
// import { LoginCard } from "@/components";
import { getArticle, deleteArticle } from "@/api/board";
import { mapState } from "vuex";

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
    // LoginCard,
  },
  name: "noticeboardview",
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
      article: {},
      isWriter: false,
    };
  },
  methods: {
    listArticle() {
      this.$router.push({ name: "noticeboardlist" });
    },
    moveModifyArticle() {
      this.$router.replace({
        name: "noticeboardupdate",
        params: { articleno: this.article.articleno },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    removeArticle() {
      if (confirm("정말로 삭제?")) {
        deleteArticle(this.article.articleno, () => {
          this.$router.push({ name: "noticeboardlist" });
        });
      }
    },
    leafActive() {
      if (window.innerWidth < 768) {
        this.leafShow = false;
      } else {
        this.leafShow = true;
      }
    },
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
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
    getArticle(
      this.$route.params.articleno,
      (response) => {
        this.article = response.data;
        if (this.article.userid == this.userInfo.userid) this.isWriter = true;
      },
      (error) => {
        console.log("삭제시 에러발생!!", error);
      }
    );
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
