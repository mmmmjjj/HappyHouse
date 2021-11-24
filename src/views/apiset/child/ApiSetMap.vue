<template>
  <b-container>
    <b-row>
      <b-col cols="12" align="left">
        <div id="kakaomap">map</div>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="4" align="left">
        <div>
          <table id="placeslist"></table>
        </div>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: "apisetmap",
  data() {
    return {
      map: null, //지도 객체
      ps: null, //장소 검색 객체
      markers: [], // 마커를 담을 배열
      infoWindow: null, //검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우
      contentNode: null, // 커스텀 오버레이의 컨텐츠 엘리먼트
      customOverlays: [], // 커스텀 오버레이
      overlayIdx: 0, // 오버레이 인덱스
      geocoder: null, // 주소-좌표 변환 객체
      result: [],
      positions: [],
      placeOverlay: null,
      markers2: [],
      currCategory: null,
    };
  },
  props: {
    places: {
      type: Array,
      required: true,
    },
    sidoName: {
      type: String,
      required: true,
    },
    gugunName: {
      type: String,
      required: true,
    },
    dongName: {
      type: String,
      required: true,
    },
  },
  watch: {
    places: {
      handler: function() {
        this.positions = [];
        this.displayMarkers(this.places);
      },
      deep: true,
    },
  },
  mounted() {
    window.kakao && window.kakao.maps ? this.initMap() : this.addScript();
  },
  methods: {
    initMap() {
      //기존의 맵-------------------------------
      let mapContainer = document.getElementById("kakaomap"), // 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(37.564343, 126.947613), // 지도의 중심좌표
          level: 3, // 지도의 확대 레벨
        };
      ///as
      mapContainer.style.height = "500px";
      mapContainer.style.width = "95%";

      this.map = new kakao.maps.Map(mapContainer, mapOption);

      this.ps = new kakao.maps.services.Places(this.map);
      this.infoWindow = new kakao.maps.InfoWindow({ zIndex: 1 });
      this.contentNode = document.createElement("div");
      // this.customOverlays = new kakao.maps.customOverlay({zIndex:1});
      this.geocoder = new kakao.maps.services.Geocoder();

      // 마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이입니다
      this.placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });
      // var contentNode = document.createElement("div"); // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다
      this.markers2 = []; // 마커를 담을 배열입니다
      this.currCategory = ""; // 현재 선택된 카테고리를 가지고 있을 변수입니다
      // 지도에 idle 이벤트를 등록합니다
      //console.log(this.searchPlaces);
      kakao.maps.event.addListener(this.map, "idle", this.searchPlaces);
      // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다
      this.contentNode.className = "placeinfo_wrap";
      // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
      // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
      this.addEventHandle(
        this.contentNode,
        "mousedown",
        kakao.maps.event.preventMap
      );
      this.addEventHandle(
        this.contentNode,
        "touchstart",
        kakao.maps.event.preventMap
      );
      // 커스텀 오버레이 컨텐츠를 설정합니다
      this.placeOverlay.setContent(this.contentNode);

      // 각 카테고리에 클릭 이벤트를 등록합니다
      this.addCategoryClickEvent();
    },
    addScript() {
      const script = document.createElement("script");
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=d8401d94ff64689778b1711fbfb96f64&libraries=services";
      document.head.appendChild(script);
    },
    async displayMarkers(places) {
      let listEl = document.getElementById("placeslist");
      console.log("asdas" + listEl);
      this.removeAllChildNods(listEl);
      // while (listEl.hasChildNodes()) {
      //   listEl.removeChild(listEl.lastChild);
      // }
      let fragment = document.createDocumentFragment();
      let bounds = new kakao.maps.LatLngBounds();
      // 지도에 표시되고 있는 마커를 제거합니다
      // this.removeMarker();

      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
        console.log("dd");
        this.removeInfowindow();
      }
      this.markers = [];

      // 검색을 해야한다.
      places.forEach((place, index, arr) => {
        var address =
          this.sidoName +
          " " +
          this.gugunName +
          " " +
          place.법정동 +
          " " +
          place.지번;

        var itemEl = this.getListItem(index, place);

        const addressSearch = (address) => {
          return new Promise((resolve, reject) => {
            this.geocoder.addressSearch(address, (result, status) => {
              if (status === kakao.maps.services.Status.OK) {
                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                this.result[1] = result[0].x;
                this.result[0] = result[0].y;
                // 결과값으로 받은 위치를 마커로 표시합니다
                // var marker = new kakao.maps.Marker({
                //   map: this.map,
                //   position: coords,
                // });
                // marker.setMap(this.map);
                // 인포윈도우로 장소에 대한 설명을 표시합니다
                // var infowindow = new kakao.maps.InfoWindow({
                //   content:
                //     '<div style="width:150px;text-align:center;padding:6px 0;">' +
                //     place.아파트 +
                //     "</div>",
                // });
                // infowindow.open(this.map, marker);
                // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                this.map.setCenter(coords);
                resolve("success");
              }
            });
          });
        };

        (async () => {
          try {
            const result = await addressSearch(address);
            if (result) {
              console.log("뙜다!");

              var position = new kakao.maps.LatLng(
                this.result[0],
                this.result[1]
              );

              let p = {
                x: result[0].x,
                y: result[0].y,
                아파트: place.아파트,
              };

              this.positions.push(p);

              console.log(position);
              //마커 생성하고 지도에 표시.
              var marker = this.addMarker(position, index);
              // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
              // LatLngBounds 객체에 좌표를 추가합니다
              bounds.extend(position);
              this.map.setCenter(position);

              ((marker, title, code, place) => {
                kakao.maps.event.addListener(marker, "click", () => {
                  this.displayInfowindow(marker, title, place);
                });

                kakao.maps.event.addListener(this.map, "click", () => {
                  this.removeInfowindow();
                });

                itemEl.onmouseover = () => {
                  this.displayInfowindow(marker, title, place);
                };

                itemEl.onmouseout = () => {
                  this.removeInfowindow();
                };
              })(marker, place.아파트, place.지번, place);

              fragment.appendChild(itemEl);

              document.getElementById("placeslist").appendChild(fragment);

              this.map.setBounds(bounds);
            }
          } catch (e) {
            console.log(e);
          }
        })();
      });

      // map.js에서는 이부분에 마커를 생성하고 지도에 표시합니다. 라는 주석이 있는데 코드는 없음..

      // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    },
    //마커 생성하고 지도 위에 마커를 표시
    addMarker(position, idx) {
      var imageSrc =
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imgOptions
        ),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.markers.push(marker); // 배열에 생성된 마커를 추가합니다

      console.log(this.markers);
      return marker;
    },
    removeMarker() {
      //지도 위에 표시되고 있는 모든 마커 제거
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    },
    getListItem(index, place) {
      //검색결과 항목을 Element로 반환
      let el = document.createElement("tr");
      let itemStr =
        `
      <tr class="markerbg marker_` +
        (index + 1) +
        `></span>
      <tr class="info"><h5>` +
        (index + 1) +
        " " +
        place.아파트 +
        `</h5>
      </tr> ` +
        this.sidoName +
        " " +
        this.gugunName +
        " " +
        this.dongName +
        " " +
        place.지번 +
        `<div class="infos"><h5>` +
        " " +
        `</h5> </tr>
          `;
      // let itemStr = `<span > hi </span>`;
      el.innerHTML = itemStr;
      el.className = "item";

      return el;
    },
    displayInfowindow(marker, title, place) {
      console.log("displayInfoWindow called");
      var content =
        `
		<div class="overlaybox">
			<div class="boxtitle">` +
        place.아파트 +
        `</div>
			<ul>
				<li class="up">
					<span class="title">건축년도</span>
					<span class="count"> ` +
        place.건축년도 +
        `</span>
				</li>
				<li>
					<span class="title">주소</span>
					<span class="count"> ` +
        this.sidoName +
        this.gugunName +
        this.dongName +
        place.지번 +
        `</span>
				</li>
				<li>
					<span class="title">최신거래금액</span>
					<span class="count"> ` +
        place.거래금액 +
        `</span>
				</li>
				<li>
					<span class="last" id="recenthistor" data-toggle="modal" data-target="#myModal">아파트정보 update</span>
				</li>
			</ul>
		</div>
	`;
      let position = new kakao.maps.LatLng(
        marker.getPosition().getLat() + 0.00033,
        marker.getPosition().getLng() - 0.00003
      );
      let customOverlay = new kakao.maps.CustomOverlay({
        position: position,
        content: content,
        xAnchor: 0.3,
        yAnchor: 0.91,
      });

      this.customOverlays[this.overlayIdx] = customOverlay;
      this.customOverlays[this.overlayIdx++].setMap(this.map);
      console.log("인덱스 증가 : " + this.overlayIdx);
    },
    removeInfowindow() {
      console.log("Remove");
      if (this.overlayIdx > 0) {
        this.customOverlays[--this.overlayIdx].setMap(null);
      }
      console.log("인덱스 감소 : " + this.overlayIdx);
    },
    removeAllChildNods(el) {
      while (el.hasChildNodes()) {
        el.removeChild(el.lastChild);
      }
    },
    // 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
    addEventHandle(target, type, callback) {
      if (target.addEventListener) {
        target.addEventListener(type, callback);
      } else {
        target.attachEvent("on" + type, callback);
      }
    },
    // 카테고리 검색을 요청하는 함수입니다
    searchPlaces() {
      if (!this.currCategory) {
        return;
      }

      // 커스텀 오버레이를 숨깁니다
      this.placeOverlay.setMap(null);

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker2();

      this.ps.categorySearch(this.currCategory, this.placesSearchCB, {
        useMapBounds: true,
      });
    },
    // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
    placesSearchCB(data, status, pagination) {
      if (status === kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
        this.displayPlaces(data);
        console.log(this.currCategory + "의 마커 표출");
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
        console.log("검색결과 없");
      } else if (status === kakao.maps.services.Status.ERROR) {
        // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
      }
    },
    // 지도에 마커를 표출하는 함수입니다
    displayPlaces(places) {
      // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
      // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다

      console.log(places);
      var order = document
        .getElementById(this.currCategory)
        .getAttribute("data-order");

      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        var marker = this.addMarker2(
          new kakao.maps.LatLng(places[i].y, places[i].x),
          order
        );

        // 마커와 검색결과 항목을 클릭 했을 때
        // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
        ((marker, place) => {
          kakao.maps.event.addListener(marker, "click", () => {
            this.displayPlaceInfo(place);
          });
        })(marker, places[i]);
      }
    },
    // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    addMarker2(position, order) {
      var imageSrc =
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(27, 28), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(46, order * 36), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(11, 28), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imgOptions
        ),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.markers2.push(marker); // 배열에 생성된 마커를 추가합니다
      //console.log(marker);
      //console.log(markers2);
      return marker;
    },
    // 지도 위에 표시되고 있는 마커를 모두 제거합니다
    removeMarker2() {
      for (var i = 0; i < this.markers2.length; i++) {
        this.markers2[i].setMap(null);
      }
      this.markers2 = [];
    },
    // 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
    displayPlaceInfo(place) {
      console.log("display");
      console.log(place.place_name);
      var content =
        '<div class="placeinfo">' +
        '   <a class="title" href="' +
        place.place_url +
        '" target="_blank" title="' +
        place.place_name +
        '">' +
        place.place_name +
        "</a>";

      if (place.road_address_name) {
        content +=
          '    <span title="' +
          place.road_address_name +
          '">' +
          place.road_address_name +
          "</span>" +
          '  <span class="jibun" title="' +
          place.address_name +
          '">(지번 : ' +
          place.address_name +
          ")</span>";
      } else {
        content +=
          '    <span title="' +
          place.address_name +
          '">' +
          place.address_name +
          "</span>";
      }

      content +=
        '    <span class="tel">' +
        place.phone +
        "</span>" +
        "</div>" +
        '<div class="after"></div>';

      this.contentNode.innerHTML = content;
      this.placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
      this.placeOverlay.setMap(this.map);
    },
    // 각 카테고리에 클릭 이벤트를 등록합니다
    addCategoryClickEvent() {
      var category = document.getElementById("category"),
        children = category.children;
      // console.log(children[0]);
      for (var i = 0; i < children.length; i++) {
        // children[i].onclick = this.onClickCategory(children[i]);
        ((children) => {
          children.addEventListener("click", () => {
            this.onClickCategory(children);
          });
        })(children[i]);
      }
    },

    // 카테고리를 클릭했을 때 호출되는 함수입니다
    onClickCategory(el) {
      console.log("onclick");
      var id = el.id;
      var className = el.className;

      this.placeOverlay.setMap(null);
      // console.log(id);
      console.log("classname : " + className);

      if (className === "on") {
        this.currCategory = "";
        console.log("on -> off");
        this.changeCategoryClass();
        this.removeMarker2();
      } else {
        this.currCategory = id;
        console.log("off->on");
        console.log(this.currCategory);
        this.changeCategoryClass(el);
        this.searchPlaces();
      }
    },
    // 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수입니다
    changeCategoryClass(el) {
      var category = document.getElementById("category"),
        children = category.children,
        i;

      for (i = 0; i < children.length; i++) {
        children[i].className = "";
      }

      if (el) {
        el.className = "on";
      }
    },
  },
};
</script>

<style scoped>
.overlaybox {
  position: relative;
  width: 360px;
  height: 350px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/box_movie.png")
    no-repeat;
  padding: 15px 10px;
}
.overlaybox div,
ul {
  overflow: hidden;
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
  color: #aaabaf;
  line-height: 1;
}
.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
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
</style>
