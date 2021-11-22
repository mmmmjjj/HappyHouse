<template>
  <b-container>
    <div id="map">map</div>
  </b-container>
</template>

<script>
export default {
  name: "HouseMap",
  data() {
    return {
      map: null, //지도 객체
      ps: null, //장소 검색 객체
      markers: [], // 마커를 담을 배열
      infoWindow: null, //검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우
      customOverlays: [], // 커스텀 오버레이
      overlayIdx: 0, // 오버레이 인덱스
      geocoder: null, // 주소-좌표 변환 객체
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
        // console.log(this.dongName + "바뀜");
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
      let mapContainer = document.getElementById("map"), // 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(37.564343, 126.947613), // 지도의 중심좌표
          level: 3, // 지도의 확대 레벨
        };
      mapContainer.style.height = "600px";

      this.map = new kakao.maps.Map(mapContainer, mapOption);

      this.ps = new kakao.maps.services.Places();
      this.infoWindow = new kakao.maps.InfoWindow({ zIndex: 1 });
      this.geocoder = new kakao.maps.services.Geocoder();
    },
    addScript() {
      const script = document.createElement("script");
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=d8401d94ff64689778b1711fbfb96f64&libraries=services";
      document.head.appendChild(script);
    },
    displayMarkers(places) {
      let fragment = document.createDocumentFragment();
      let bounds = new kakao.maps.LatLngBounds();

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker();

      // 검색을 해야한다.
      places.forEach((place, index, arr) => {
        console.log(index);
        console.log(place);
        var address =
          this.sidoName +
          " " +
          this.gugunName +
          " " +
          place.법정동 +
          " " +
          place.지번;

        var itemEl = this.getListItem(index, place);

        console.log(this.map);

        var position = new kakao.maps.LatLng(0, 0);

        this.geocoder.addressSearch(address, function(result, status) {
          console.log(address);
          let mm = map[0].__vue__.map;
          console.log(map);
          if (status === kakao.maps.services.Status.OK) {
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            var placePosition = new kakao.maps.LatLng(result[0].y, result[0].x);
            position = placePosition;
            // console.log(result);
            console.log(result[0].y, result[0].x);
            // 결과값으로 받은 위치를 마커로 표시합니다
            var marker = new kakao.maps.Marker({
              map: mm,
              position: coords,
            });
            // marker.setMap(mm);
            // 인포윈도우로 장소에 대한 설명을 표시합니다
            var infowindow = new kakao.maps.InfoWindow({
              content:
                '<div style="width:150px;text-align:center;padding:6px 0;">우리회사</div>',
            });
            infowindow.open(mm, marker);
            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
            mm.setCenter(coords);
          }
        });
        var marker = this.addMarker(position, index);
        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(position);
        position = new kakao.maps.LatLng(0, 0);
        // 마커와 검색결과 항목에 mouseover 했을때
        // 해당 장소에 인포윈도우에 장소명을 표시합니다
        // mouseout 했을 때는 인포윈도우를 닫습니다
        (function(marker, title, code, place) {
          let mm = map[0].__vue__.map;
          let txt = map[0].__vue__;

          kakao.maps.event.addListener(marker, "click", function() {
            displayInfowindow(marker, title, place);
            console.log(title + " " + code);
          });

          kakao.maps.event.addListener(mm, "click", function() {
            txt.removeInfowindow();
          });

          itemEl.onmouseover = function() {
            this.displayInfowindow(marker, title);
          };

          itemEl.onmouseout = function() {
            this.removeInfowindow();
          };
        })(marker, place.아파트, place.지번, place);

        fragment.appendChild(itemEl);
      });

      // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
      this.map.setBounds(bounds);
    },
    addMarker(position, idx) {
      //마커 생성하고 지도 위에 마커를 표시
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
      let el = document.createElement("li");
      let itemStr =
        ` 
      <span class="markerbg marker_` +
        (index + 1) +
        `></span>
      <div class="info"><h5>` +
        place.아파트 +
        `</h5> <button>관심등록</button>
      <span> ` +
        this.sidoName +
        this.gugunName +
        this.dongName +
        place.지번 +
        ` </span>
          `;
      // let itemStr = `<span > hi </span>`;
      el.innerHTML = itemStr;
      el.className = "item";

      return el;
    },
    displayInfowindow(marker, title, place) {
      //   console.log(title);
      var content =
        `
		<div class="overlaybox">
			<div class="boxtitle">` +
        place.아파트 +
        `</div>
			<div class="first"><img src="@/assets/apt.png" style="width:247px; height:136px;" alt=""></div>
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
        place.거래금액`</span>
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
      //	console.log("인덱스 증가 : "+overlayIdx);
    },
    removeInfowindow() {
      if (this.overlayIdx > 0) {
        this.customOverlays[--this.overlayIdx].setMap(null);
      }
      //	console.log("인덱스 감소 : "+overlayIdx);
    },
  },
};
</script>

<style></style>
