<template>
  <div class="wrapper">
    <parallax class="page-header header-filter" :style="headerStyle">
      <div class="md-layout">
        <div class="md-layout-item">
          <div class="image-wrapper">
            <div class="brand">
              <img src="@/assets/seul.png" width="300px" height="300px" />
            </div>
          </div>
        </div>
      </div>
    </parallax>
    <div class="main main-raised">
      <div class="section section-basic">
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
                    사용자가 알고싶은 동네의 슬세권을 확인할 수 있는
                    서비스입니다.<br />
                    <br />
                  </template>
                  <template slot="tab-pane-2">
                    1. 원하시는 시, 군구, 동을 선택한 후, 검색 버튼을
                    누릅니다.<br />
                    2. 좌측 상단의 리스트에서 원하는 카테고리를 선택합니다.<br />
                    3. 우클릭을 활용하여 반경구하기를 시작하고,<br />
                    다시 우클릭으로 반경구하기를 종료합니다.(좌측 하단의
                    지우기로 원 지우기)<br />
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
          <b-row class="map_wrap">
            <house-map
              id="map"
              class="map"
              style="width:100%;height:100%;position:relative;overflow:hidden;"
              :places="newArray"
              :sido-name="sidoName"
              :gugun-name="gugunName"
              :dong-name="dongName"
            ></house-map>
            <ul id="category">
              <li id="BK9" data-order="0">
                <span class="category_bg bank"></span>
                은행
              </li>
              <li id="MT1" data-order="1">
                <span class="category_bg mart"></span>
                마트
              </li>
              <li id="PM9" data-order="2">
                <span class="category_bg pharmacy"></span>
                약국
              </li>
              <li id="PK6" data-order="3">
                <span class="category_bg oil"></span>
                주차장
              </li>
              <li id="CE7" data-order="4">
                <span class="category_bg cafe"></span>
                카페
              </li>
              <li id="CS2" data-order="5">
                <span class="category_bg store"></span>
                편의점
              </li>
            </ul>
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
import HouseMap from "@/views/apiset/child/ApiSetMap.vue";
import { Tabs } from "@/components";
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
    Tabs,
  },
  name: "apiset",
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
      this.sidos.forEach((sido) => {
        if (this.sidoCode == sido.value) {
          this.sidoName = sido.text;
        }
      });
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
      this.CLEAR_DONG_LIST();
      this.dongCode = null;
      if (this.gugunCode) this.getDong(this.gugunCode);
    },
    searchApt() {
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

.map {
  width: 100%;
  height: 500px;
}

.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 600px;
}
#category {
  position: absolute;
  top: 10px;
  left: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
}
#category li {
  float: left;
  list-style: none;
  width: 50px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: #eee;
}
#category li:hover {
  background: #ffe6e6;
  border-left: 1px solid #acacac;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
#category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}
#category li .category_bg {
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png)
    no-repeat;
}
#category li .bank {
  background-position: -10px 0;
}
#category li .mart {
  background-position: -10px -36px;
}
#category li .pharmacy {
  background-position: -10px -72px;
}
#category li .oil {
  background-position: -10px -108px;
}
#category li .cafe {
  background-position: -10px -144px;
}
#category li .store {
  background-position: -10px -180px;
}
#category li.on .category_bg {
  background-position-x: -46px;
}
.overlaybox {
  position: relative;
  width: 360px;
  height: 350px;
  background: url("https://user-images.githubusercontent.com/67090601/143394235-bb0b1743-5f9d-44ab-ab4e-18e2fff9e9ac.png")
    no-repeat;
  padding: 15px 10px;
}
.overlaybox div,
ul {
  margin: 0;
  padding: 0;
}
.overlaybox li {
  list-style: none;
}
.overlaybox .boxtitle {
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  background: no-repeat right 120px center;
  margin-bottom: 8px;
}
.overlaybox .first {
  position: relative;
  width: 247px;
  height: 136px;

  margin-bottom: 8px;
}
.first .text {
  color: #fff;
  font-weight: bold;
}
.first .triangle {
  position: absolute;
  width: 48px;
  height: 48px;
  top: 0;
  left: 0;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/triangle.png")
    no-repeat;
  padding: 6px;
  font-size: 18px;
}
.first .movietitle {
  position: absolute;
  width: 100%;
  bottom: 0;
  background: rgba(0, 0, 0, 0.4);
  padding: 7px 15px;
  font-size: 14px;
}
.overlaybox ul {
  width: 247px;
}
.overlaybox li {
  position: relative;
  margin-bottom: 2px;
  background: #2b2d36;
  padding: 5px 10px;
  color: #fff;
  line-height: 1;
}
.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.placeinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
.placeinfo a,
.placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  background: #d95050
    url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
.placeinfo .tel {
  color: #0f7833;
}
.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}
.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
</style>
