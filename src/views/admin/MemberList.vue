<template>
  <!-- -->
  <div class="col-lg-12 mx-auto">
    <div class="p-3 mb-3 text-left">
      <form id="searchform" action="">
        검색 :
        <select id="select" v-model="value">
          <option value="0">선택</option>
          <option value="1">이름</option>
          <option value="2">아이디</option>
        </select>

        <input type="text" id="keyword" v-model="keyword" />

        <button
          type="button"
          id="searchBtn"
          class="btn btn-outline-primary"
          @click="searchUser"
        >
          검색
        </button>
      </form>
    </div>
    <b-row>
      <b-col v-if="members.length">
        <paginated-list :list-array="members" />
      </b-col>
      <b-col v-else class="text-center">회원이 없습니다.</b-col>
    </b-row>
  </div>
  <!-- -->
</template>

<script>
import { listMember } from "@/api/member.js";
import PaginatedList from "@/views/admin/child/PaginatedList";
import http from "@/util/http-common";
export default {
  components: {
    PaginatedList,
  },
  name: "adminlist",
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
      value: "0",
      firstname: null,
      email: null,
      password: null,
      leafShow: false,
      members: [],
      keyword: null,
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
    searchUser() {
      console.log(this.value);
      console.log(this.keyword);
      let params = { select: this.value, keyword: this.keyword };
      http.get(`/admin/search`, { params }).then(({ data }) => {
        this.members = data;
      });
    },
  },
  created() {
    let param = {
      pg: 1,
      spp: 20,
      key: null,
      word: null,
    };
    listMember(
      param,
      (response) => {
        this.members = response.data;
      },
      (error) => {
        console.log(error);
      }
    );
  },
  computed: {
    // ...mapState(memberStore, ["isLogin", "userInfo"]),
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
