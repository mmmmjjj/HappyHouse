<template>
  <div class="wrapper">
    <parallax class="page-header header-filter" :style="headerStyle">
      <div class="md-layout">
        <div class="md-layout-item">
          <div class="image-wrapper">
            <div class="brand">
              <img src="@/assets/aptmap.png" width="300px" height="300px" />
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
                    :tab-name="['설명', '사용법', '유의사항']"
                    :tab-icon="['dashboard', 'list', 'schedule']"
                    plain
                    nav-pills-icons
                    color-button="info"
                  >
                    <!-- here you can add your content for tab-content -->
                    <template slot="tab-pane-1">
                      국토교통부 아파트매매 실거래 상세 자료 api와<br />
                      카카오맵 api를 결합하여<br />
                      사용자가 알고싶은 동네에 거래된 아파트 정보와<br />
                      위치를 제공하는 서비스입니다.
                    </template>
                    <template slot="tab-pane-2">
                      1. 원하시는 시, 군구, 동을 선택한 후, 검색 버튼을
                      누릅니다.<br />
                      2. 좌측 및 지도에 나타나는 정보를 확인하세요.<br />
                      3. 좌측 리스트에서 원하시는 아파트에 커서를 올리거나,<br />
                      지도의 커서를 클릭하여 아파트의 정보를 확인하세요.<br />
                    </template>
                    <template slot="tab-pane-3">
                      1. 원하시는 시, 군구, 동에 아파트 정보가 없을 시<br />지도에
                      마커가 찍히지 않습니다.<br />
                      <br />
                      <br />
                    </template>
                  </tabs>
                </div>
              </b-col>
            </b-row>
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
              <house-map
                id="map"
                class="map"
                :places="newArray"
                :sido-name="sidoName"
                :gugun-name="gugunName"
                :dong-name="dongName"
              ></house-map>
            </b-row>
          </b-container>
          <!-- -->
        </div>
      </div>

      <div class="section">
        <div class="container text-center">
          <div class="md-layout">
            <div
              class="md-layout-item md-size-66 md-xsmall-size-100 ml-auto mr-auto text-center"
            >
              <!--이 밑에 카카오 맵 넣자 -->
              <!-- <house-map
                id="map"
                class="map"
                :places="newArray"
                :sido-name="sidoName"
                :gugun-name="gugunName"
                :dong-name="dongName"
              ></house-map> -->
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
import { Tabs } from "@/components";
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
    Tabs,
  },
  name: "kakaoapi",
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
  methods: {
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
      this.curHouses = JSON.parse(JSON.stringify(this.houses));
      this.newArray = JSON.parse(JSON.stringify(this.curHouses));
      let index = this.newArray.length - 1;
      while (index >= 0) {
        if (" " + this.dongName != this.newArray[index].법정동) {
          this.newArray.splice(index, 1);
        }
        index -= 1;
      }
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
