<template>
  <b-container>
    <b-row>
      <b-col cols="12" align="left">
        <div id="kakaomap">map</div>
      </b-col>
    </b-row>
    <div class="form-row float-right">
      <b-button variant="outline-primary" @click="removeCircles()"
        >모두 지우기</b-button
      >
    </div>
    <b-row>
      <b-col cols="4" align="left">
        <div hidden>
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
      drawingFlag: null, // 원이 그려지고 있는 상태를 가지고 있을 변수입니다
      centerPosition: null, // 원의 중심좌표 입니다
      drawingCircle: null, // 그려지고 있는 원을 표시할 원 객체입니다
      drawingLine: null, // 그려지고 있는 원의 반지름을 표시할 선 객체입니다
      drawingOverlay: null, // 그려지고 있는 원의 반경을 표시할 커스텀오버레이 입니다
      drawingDot: null, // 그려지고 있는 원의 중심점을 표시할 커스텀오버레이 입니다
      circles: [], // 클릭으로 그려진 원과 반경 정보를 표시하는 선과 커스텀오버레이를 가지고 있을 배열입니다
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
      mapContainer.style.height = "550px";
      mapContainer.style.width = "120%";

      this.map = new kakao.maps.Map(mapContainer, mapOption);

      this.ps = new kakao.maps.services.Places(this.map);
      this.infoWindow = new kakao.maps.InfoWindow({ zIndex: 1 });
      this.contentNode = document.createElement("div");
      this.geocoder = new kakao.maps.services.Geocoder();

      // 마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이입니다
      this.placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });
      // var contentNode = document.createElement("div"); // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다
      this.markers2 = []; // 마커를 담을 배열입니다
      this.currCategory = ""; // 현재 선택된 카테고리를 가지고 있을 변수입니다
      // 지도에 idle 이벤트를 등록합니다
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

      //원의반경계산하기
      // 지도에 마우스 오른쪽 클릭이벤트를 등록합니다
      // 원을 그리고있는 상태에서 마우스 오른쪽 클릭 이벤트가 발생하면
      // 마우스 오른쪽 클릭한 위치를 기준으로 원과 원의 반경정보를 표시하는 선과 커스텀 오버레이를 표시하고 그리기를 종료합니다
      kakao.maps.event.addListener(this.map, "rightclick", (mouseEvent) => {
        if (this.drawingFlag) {
          // 마우스로 오른쪽 클릭한 위치입니다
          var rClickPosition = mouseEvent.latLng;

          // 원의 반경을 표시할 선 객체를 생성합니다
          var polyline = new kakao.maps.Polyline({
            path: [this.centerPosition, rClickPosition], // 선을 구성하는 좌표 배열입니다. 원의 중심좌표와 클릭한 위치로 설정합니다
            strokeWeight: 3, // 선의 두께 입니다
            strokeColor: "#00a0e9", // 선의 색깔입니다
            strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
            strokeStyle: "solid", // 선의 스타일입니다
          });

          // 원 객체를 생성합니다
          var circle = new kakao.maps.Circle({
            center: this.centerPosition, // 원의 중심좌표입니다
            radius: polyline.getLength(), // 원의 반지름입니다 m 단위 이며 선 객체를 이용해서 얻어옵니다
            strokeWeight: 1, // 선의 두께입니다
            strokeColor: "#00a0e9", // 선의 색깔입니다
            strokeOpacity: 0.1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
            strokeStyle: "solid", // 선의 스타일입니다
            fillColor: "#00a0e9", // 채우기 색깔입니다
            fillOpacity: 0.2, // 채우기 불투명도입니다
          });

          var radius = Math.round(circle.getRadius()), // 원의 반경 정보를 얻어옵니다
            content = this.getTimeHTML(radius); // 커스텀 오버레이에 표시할 반경 정보입니다

          // 반경정보를 표시할 커스텀 오버레이를 생성합니다
          var radiusOverlay = new kakao.maps.CustomOverlay({
            content: content, // 표시할 내용입니다
            position: rClickPosition, // 표시할 위치입니다. 클릭한 위치로 설정합니다
            xAnchor: 0,
            yAnchor: 0,
            zIndex: 1,
          });

          // 원을 지도에 표시합니다
          circle.setMap(this.map);

          // 선을 지도에 표시합니다
          polyline.setMap(this.map);

          // 반경 정보 커스텀 오버레이를 지도에 표시합니다
          radiusOverlay.setMap(this.map);

          // 배열에 담을 객체입니다. 원, 선, 커스텀오버레이 객체를 가지고 있습니다
          var radiusObj = {
            polyline: polyline,
            circle: circle,
            overlay: radiusOverlay,
          };

          // 배열에 추가합니다
          // 이 배열을 이용해서 "모두 지우기" 버튼을 클릭했을 때 지도에 그려진 원, 선, 커스텀오버레이들을 지웁니다
          this.circles.push(radiusObj);

          // 그리기 상태를 그리고 있지 않는 상태로 바꿉니다
          this.drawingFlag = false;

          // 중심 좌표를 초기화 합니다
          this.centerPosition = null;

          // 그려지고 있는 원, 선, 커스텀오버레이를 지도에서 제거합니다
          this.drawingCircle.setMap(null);
          this.drawingLine.setMap(null);
          this.drawingOverlay.setMap(null);
        } else if (!this.drawingFlag) {
          // 상태를 그리고있는 상태로 변경합니다
          this.drawingFlag = true;

          // 원이 그려질 중심좌표를 클릭한 위치로 설정합니다
          this.centerPosition = mouseEvent.latLng;

          // 그려지고 있는 원의 반경을 표시할 선 객체를 생성합니다
          if (!this.drawingLine) {
            this.drawingLine = new kakao.maps.Polyline({
              strokeWeight: 3, // 선의 두께입니다
              strokeColor: "#00a0e9", // 선의 색깔입니다
              strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
              strokeStyle: "solid", // 선의 스타일입니다
            });
          }

          // 그려지고 있는 원을 표시할 원 객체를 생성합니다
          if (!this.drawingCircle) {
            this.drawingCircle = new kakao.maps.Circle({
              strokeWeight: 1, // 선의 두께입니다
              strokeColor: "#00a0e9", // 선의 색깔입니다
              strokeOpacity: 0.1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
              strokeStyle: "solid", // 선의 스타일입니다
              fillColor: "#00a0e9", // 채우기 색깔입니다
              fillOpacity: 0.2, // 채우기 불투명도입니다
            });
          }

          // 그려지고 있는 원의 반경 정보를 표시할 커스텀오버레이를 생성합니다
          if (!this.drawingOverlay) {
            this.drawingOverlay = new kakao.maps.CustomOverlay({
              xAnchor: 0,
              yAnchor: 0,
              zIndex: 1,
            });
          }
        }
      });
      // 지도에 마우스무브 이벤트를 등록합니다
      // 원을 그리고있는 상태에서 마우스무브 이벤트가 발생하면 그려질 원의 위치와 반경정보를 동적으로 보여주도록 합니다
      kakao.maps.event.addListener(this.map, "mousemove", (mouseEvent) => {
        // 마우스무브 이벤트가 발생했을 때 원을 그리고있는 상태이면
        if (this.drawingFlag) {
          // 마우스 커서의 현재 위치를 얻어옵니다
          var mousePosition = mouseEvent.latLng;

          // 그려지고 있는 선을 표시할 좌표 배열입니다. 클릭한 중심좌표와 마우스커서의 위치로 설정합니다
          var linePath = [this.centerPosition, mousePosition];

          // 그려지고 있는 선을 표시할 선 객체에 좌표 배열을 설정합니다
          this.drawingLine.setPath(linePath);

          // 원의 반지름을 선 객체를 이용해서 얻어옵니다
          var length = this.drawingLine.getLength();

          if (length > 0) {
            // 그려지고 있는 원의 중심좌표와 반지름입니다
            var circleOptions = {
              center: this.centerPosition,
              radius: length,
            };

            // 그려지고 있는 원의 옵션을 설정합니다
            this.drawingCircle.setOptions(circleOptions);

            // 반경 정보를 표시할 커스텀오버레이의 내용입니다
            var radius = Math.round(this.drawingCircle.getRadius()),
              content =
                '<div class="info">반경 <span class="number">' +
                radius +
                "</span>m</div>";

            // 반경 정보를 표시할 커스텀 오버레이의 좌표를 마우스커서 위치로 설정합니다
            this.drawingOverlay.setPosition(mousePosition);

            // 반경 정보를 표시할 커스텀 오버레이의 표시할 내용을 설정합니다
            this.drawingOverlay.setContent(content);

            // 그려지고 있는 원을 지도에 표시합니다
            this.drawingCircle.setMap(this.map);

            // 그려지고 있는 선을 지도에 표시합니다
            this.drawingLine.setMap(this.map);

            // 그려지고 있는 원의 반경정보 커스텀 오버레이를 지도에 표시합니다
            this.drawingOverlay.setMap(this.map);
          } else {
            this.drawingCircle.setMap(null);
            this.drawingLine.setMap(null);
            this.drawingOverlay.setMap(null);
          }
        }
      });
    },
    removeCircles() {
      for (var i = 0; i < this.circles.length; i++) {
        this.circles[i].circle.setMap(null);
        this.circles[i].polyline.setMap(null);
        this.circles[i].overlay.setMap(null);
      }
      this.circles = [];
    },
    // 마우스 우클릭 하여 원 그리기가 종료됐을 때 호출하여
    // 그려진 원의 반경 정보와 반경에 대한 도보, 자전거 시간을 계산하여
    // HTML Content를 만들어 리턴하는 함수입니다
    getTimeHTML(distance) {
      // 도보의 시속은 평균 4km/h 이고 도보의 분속은 67m/min입니다
      var walkkTime = (distance / 67) | 0;
      var walkHour = "",
        walkMin = "";

      // 계산한 도보 시간이 60분 보다 크면 시간으로 표시합니다
      if (walkkTime > 60) {
        walkHour =
          '<span class="number">' + Math.floor(walkkTime / 60) + "</span>시간 ";
      }
      walkMin = '<span class="number">' + (walkkTime % 60) + "</span>분";

      // 자전거의 평균 시속은 16km/h 이고 이것을 기준으로 자전거의 분속은 267m/min입니다
      var bycicleTime = (distance / 227) | 0;
      var bycicleHour = "",
        bycicleMin = "";

      // 계산한 자전거 시간이 60분 보다 크면 시간으로 표출합니다
      if (bycicleTime > 60) {
        bycicleHour =
          '<span class="number">' +
          Math.floor(bycicleTime / 60) +
          "</span>시간 ";
      }
      bycicleMin = '<span class="number">' + (bycicleTime % 60) + "</span>분";

      // 거리와 도보 시간, 자전거 시간을 가지고 HTML Content를 만들어 리턴합니다
      var content = '<ul class="info">';
      content += "    <li>";
      content +=
        '        <span class="label">총거리</span><span class="number">' +
        distance +
        "</span>m";
      content += "    </li>";
      content += "    <li>";
      content += '        <span class="label">도보</span>' + walkHour + walkMin;
      content += "    </li>";
      content += "    <li>";
      content +=
        '        <span class="label">자전거</span>' + bycicleHour + bycicleMin;
      content += "    </li>";
      content += "</ul>";

      return content;
    },
    //원의 반경 구하기 끝
    addScript() {
      const script = document.createElement("script");
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=d8401d94ff64689778b1711fbfb96f64&libraries=services";
      document.head.appendChild(script);
    },
    async displayMarkers(places) {
      let listEl = document.getElementById("placeslist");
      this.removeAllChildNods(listEl);
      let fragment = document.createDocumentFragment();
      let bounds = new kakao.maps.LatLngBounds();
      // 지도에 표시되고 있는 마커를 제거합니다

      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
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
      el.innerHTML = itemStr;
      el.className = "item";

      return el;
    },
    displayInfowindow(marker, title, place) {
      var content =
        `
		<div class="overlaybox">
			<div class="boxtitle">` +
        place.아파트 +
        `</div>
			<ul>
				<li class="up">
					<span class="title"> 건축년도 </span>
					<span class="count"> ` +
        place.건축년도 +
        `</span>
				</li>
				<li>
					<span class="title"> 주소 </span>
					<span class="count"> ` +
        this.sidoName +
        " " +
        this.gugunName +
        " " +
        this.dongName +
        " " +
        place.지번 +
        `</span>
				</li>
				<li>
					<span class="title"> 최신거래금액 </span>
					<span class="count"> ` +
        place.거래금액 +
        `</span>
				</li>
				<li>
        <span class="title"> 전용면적 </span>
					<span class="count"> ` +
        Math.round(place.전용면적 / 3.3) +
        " 평 " +
        `</span>
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
        yAnchor: 0.41,
      });

      this.customOverlays[this.overlayIdx] = customOverlay;
      this.customOverlays[this.overlayIdx++].setMap(this.map);
    },
    removeInfowindow() {
      if (this.overlayIdx > 0) {
        this.customOverlays[--this.overlayIdx].setMap(null);
      }
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
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
      } else if (status === kakao.maps.services.Status.ERROR) {
        // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
      }
    },
    // 지도에 마커를 표출하는 함수입니다
    displayPlaces(places) {
      // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
      // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다

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
      for (var i = 0; i < children.length; i++) {
        ((children) => {
          children.addEventListener("click", () => {
            this.onClickCategory(children);
          });
        })(children[i]);
      }
    },

    // 카테고리를 클릭했을 때 호출되는 함수입니다
    onClickCategory(el) {
      var id = el.id;
      var className = el.className;

      this.placeOverlay.setMap(null);

      if (className === "on") {
        this.currCategory = "";
        this.changeCategoryClass();
        this.removeMarker2();
      } else {
        this.currCategory = id;
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

<style>
.info {
  position: relative;
  top: 5px;
  left: 5px;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  font-size: 12px;
  padding: 5px;
  background: #fff;
  list-style: none;
  margin: 0;
}
.info:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.info .label {
  display: inline-block;
  width: 50px;
}
.number {
  font-weight: bold;
  color: #00a0e9;
}
</style>
