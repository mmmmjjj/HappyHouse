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
              <h3>아파트API</h3>
            </div>
          </div>
        </div>
      </div>
    </parallax>
    <div class="main main-raised">
      <div class="section section-basic">
        <div class="container">
          <div class="title">
            <h2>Basic Elements</h2>
          </div>
          <basic-elements></basic-elements>
        </div>
        <!-- -->
        <b-container class="bv-example-row mt-3 text-center">
          <h3 class="underline-orange">
            <b-icon icon="house-fill"></b-icon> House Service
          </h3>
          <b-row class="mt-4 mb-4 text-center">
            <b-col class="sm-3">
              <b-form-select
                v-model="sidoCode"
                :options="sidos"
                @change="gugunList"
              ></b-form-select>
            </b-col>
            <b-col class="sm-3">
              <b-form-select
                v-model="gugunCode"
                :options="guguns"
                @change="dongList"
              ></b-form-select>
            </b-col>
            <b-col class="sm-3">
              <!-- <b-form-input
                v-model.trim="dongCode"
                placeholder="동코드 입력...(예 : 11110)"
                @keypress.enter="sendKeyword"
              ></b-form-input> -->
              <b-form-select
                v-model.trim="dongName"
                :options="dongs"
                @change="searchApt"
              ></b-form-select>
            </b-col>
            <b-col class="sm-3" align="left">
              <b-button variant="outline-primary" @click="sendKeyword"
                >검색</b-button
              >
            </b-col>
          </b-row>
          <b-row>
            <!-- <b-col cols="6" align="left">
              <house-list />
            </b-col>
            <b-col cols="6">
              <house-detail />
            </b-col> -->
          </b-row>
        </b-container>
        <!-- -->
      </div>

      <div class="section">
        <div class="container text-center">
          <div class="md-layout">
            <div
              class="md-layout-item md-size-66 md-xsmall-size-100 ml-auto mr-auto text-center"
            >
              <!--이 밑에 카카오 맵 넣자 -->
              <house-map
                id="map"
                class="map"
                :places="newArray"
                :sido-name="sidoName"
                :gugun-name="gugunName"
                :dong-name="dongName"
              ></house-map>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
//import HouseList from "@/views/apt/HouseList.vue";
//import HouseDetail from "@/views/apt/HouseDetail.vue";
import { mapState, mapActions, mapMutations } from "vuex";
import HouseMap from "@/views/kakao/child/HouseMap.vue";
const houseStore = "houseStore";
export default {
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongName: null,
      sidoName: null,
      gugunName: null,
      newArray: [],
      curHouses: [],
    };
  },
  components: {
    //HouseList,
    //HouseDetail,
    HouseMap,
  },
  name: "kakaoapi",
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
  methods: {
    leafActive() {
      if (window.innerWidth < 768) {
        this.leafShow = false;
      } else {
        this.leafShow = true;
      }
    },
    ...mapActions(houseStore, [
      "getSido",
      "getGugun",
      "getDong",
      "getHouseList",
    ]),
    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
    ]),
    gugunList() {
      console.log("시 선택 했따 ~ sidocode=" + this.sidoCode);
      this.sidos.forEach((sido) => {
        if (this.sidoCode == sido.value) {
          this.sidoName = sido.text;
        }
      });
      console.log(this.sidoName);
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    dongList() {
      console.log("군 선택 했다~~ guguncode=" + this.gugunCode);
      this.guguns.forEach((gugun) => {
        if (this.gugunCode == gugun.value) {
          this.gugunName = gugun.text;
        }
      });
      console.log(this.gugunName);
      this.CLEAR_DONG_LIST();
      this.dongCode = null;
      if (this.gugunCode) this.getDong(this.gugunCode);
    },
    searchApt() {
      console.log("dongname=" + this.dongName);
      console.log("guguncode " + this.gugunCode);
      if (this.dongName) this.getHouseList(this.gugunCode);
    },
    sendKeyword() {
      //저장되어있는 houses가져오기
      console.log(this.houses);
      this.curHouses = JSON.parse(JSON.stringify(this.houses));
      this.newArray = JSON.parse(JSON.stringify(this.curHouses));
      console.log(this.curHouses);
      let index = this.newArray.length - 1;
      while (index >= 0) {
        if (" " + this.dongName != this.newArray[index].법정동) {
          this.newArray.splice(index, 1);
        }
        index -= 1;
      }

      console.log(this.newArray);
    },
  },
  computed: {
    ...mapState(houseStore, ["sidos", "guguns", "dongs", "houses"]),
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
    this.CLEAR_SIDO_LIST();
    this.getSido();
  },
};
</script>
<style scoped lang="scss">
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

.map {
  width: 100%;
  height: 500px;
}
</style>
