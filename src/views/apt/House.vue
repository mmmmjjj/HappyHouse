<template>
  <div class="wrapper">
    <parallax class="page-header header-filter" :style="headerStyle">
      <div class="md-layout">
        <div class="md-layout-item">
          <div class="image-wrapper">
            <div class="brand">
              <img src="@/assets/aptinfo.png" width="300px" height="300px" />
            </div>
          </div>
        </div>
      </div>
    </parallax>
    <div class="main main-raised">
      <div class="section section-basic">
        <div class="md-layout">
          <!-- -->
          <b-container class="bv-example-row mt-3">
            <b-row>
              <b-col>
                <div class="md-layout-item md-size-50 md-small-size-100 ">
                  <tabs
                    :tab-name="['설명', '사용법']"
                    :tab-icon="['dashboard', 'list']"
                    plain
                    nav-pills-icons
                    color-button="info"
                  >
                    <!-- here you can add your content for tab-content -->
                    <template slot="tab-pane-1">
                      국토교통부 아파트매매 실거래 상세 자료 api를 활용한
                      <br />아파트 정보 확인 서비스입니다. <br /><br />
                    </template>
                    <template slot="tab-pane-2">
                      1. 원하시는 시, 군구를 선택합니다.<br />
                      2. 좌측 하단에 실시간으로 보여지는 아파트들을
                      확인하세요.<br />
                      3. 원하시는 아파트를 클릭하여 우측에서 상세 정보를
                      확인하세요.<br />
                    </template>
                  </tabs>
                </div>
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <house-search-bar></house-search-bar>
              </b-col>
            </b-row>
            <b-row>
              <b-col cols="6" align="left">
                <house-list />
              </b-col>
              <b-col cols="6">
                <house-detail />
              </b-col>
            </b-row>
          </b-container>
          <!-- -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HouseSearchBar from "@/views/apt/HouseSearchBar.vue";
import HouseList from "@/views/apt/HouseList.vue";
import HouseDetail from "@/views/apt/HouseDetail.vue";
import { Tabs } from "@/components";
export default {
  components: {
    HouseSearchBar,
    HouseList,
    HouseDetail,
    Tabs,
  },
  name: "house",
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
