<template>
  <div class="wrapper">
    <parallax class="page-header header-filter" :style="headerStyle">
      <div class="md-layout">
        <div class="md-layout-item">
          <div class="brand">
            <!-- <h1>Happy House</h1> -->
            <img src="@/assets/logo2.png" width="300px" height="300px" />
          </div>
        </div>
      </div>
    </parallax>
    <div class="main main-raised">
      <div class="section section-basic">
        <div class="md-layout">
          <div class="md-layout-item md-size-66 mx-auto md-small-size-100">
            <md-card>
              <carousel
                :per-page="1"
                loop
                :speed="700"
                autoplay
                :autoplay-timeout="5000"
                :mouse-drag="false"
                navigationEnabled
                navigationNextLabel="<i class='material-icons'>keyboard_arrow_right</i>"
                navigationPrevLabel="<i class='material-icons'>keyboard_arrow_left</i>"
              >
                <slide>
                  <div class="carousel-caption">
                    <h4 id="carousel" style="color:white;">
                      <md-icon>location_on</md-icon>
                      여기 귀찮은 것이 싫은 개발자가 있습니다
                    </h4>
                  </div>
                  <img :src="carousel1" alt="carousel1" />
                </slide>
                <slide>
                  <div class="carousel-caption">
                    <h4 id="carousel" style="color:white;">
                      <md-icon>location_on</md-icon>
                      슬세권-프리미엄을 만족시킬 수 있는 서비스를 제공합니다
                    </h4>
                  </div>
                  <img :src="carousel2" alt="carousel2" />
                </slide>
                <slide>
                  <div class="carousel-caption">
                    <h4 id="carousel" style="color:white;">
                      <md-icon>location_on</md-icon>
                      최대한 많은 인프라가 모여있는 집에서 개발을 해보세요
                    </h4>
                  </div>
                  <img :src="carousel3" alt="carousel3" />
                </slide>
              </carousel>
            </md-card>
          </div>
        </div>
      </div>
      <div class="section">
        <div class="container text-center">
          <div class="md-layout">
            <div
              class="md-layout-item md-size-66 md-xsmall-size-100 ml-auto mr-auto text-center"
            >
              <h2>Why we made this?</h2>
              <h4>
                귀찮은걸 매우 싫어하는 개발자들이 모였습니다. <br />
                이 세상 모든 귀찮은걸 싫어하는 개발자들을 위한 <br />good-infra
                서비스를 제공합니다.<br />
                최대한 많은 infra가 모여있는 집에서 <br />개발을 할 수있도록
                서비스를 개발하였습니다.
              </h4>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  name: "index",
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
      carousel1: require("@/assets/img/nature-2.jpg"),
      carousel2: require("@/assets/img/nature.jpg"),
      carousel3: require("@/assets/img/nature-3.jpg"),
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
  created() {},
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
#carousel {
  color: rgb(0, 0, 0);
}
</style>
