<template>
  <b-row class="mb-1">
    <b-col>
      <b-card
        :header-html="`<h3>${comment}</h3>`"
        class="mb-1"
        border-variant="dark"
        no-body
      >
        <b-card-body class="text-left">
          <b-col class="text-left">
            작성자:{{ userid }}&nbsp;&nbsp;&nbsp;작성일:{{ memotime }}
          </b-col>
          <b-col class="text-right" v-if="isWriter">
            <b-button
              variant="outline-info"
              size="sm"
              @click="moveModifyArticle"
              class="mr-2"
              >글수정</b-button
            >
            <b-button variant="outline-danger" size="sm" @click="deleteArticle"
              >글삭제</b-button
            >
          </b-col>
        </b-card-body>
      </b-card>
    </b-col>
  </b-row>
</template>

<script>
// import moment from "moment";
import http from "@/util/http-common";
import { mapState } from "vuex";

const memberStore = "memberStore";
export default {
  name: "qnamemorow",
  props: {
    memono: Number,
    userid: String,
    comment: String,
    memotime: String,
    articleno: Number,
  },
  data() {
    return {
      isWriter: false,
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),

    // changeDateFormat() {
    //   return moment(new Date(this.regtime)).format("YY.MM.DD hh:mm:ss");
    // },
  },
  created() {
    if (this.userid == this.userInfo.userid) this.isWriter = true;
  },
  methods: {
    moveModifyArticle() {
      console.log(this.memono);
      this.$router.replace({
        name: "memomodifyform",
        params: { memono: this.memono },
      });
    },
    deleteArticle() {
      if (confirm("정말로 삭제?")) {
        http.delete(`/memo/${this.memono}`).then(({ data }) => {
          let msg = "삭제 처리시 문제가 발생했습니다.";
          if (data == "success") {
            msg = "삭제가 완료되었습니다.";
          }
          alert(msg);
          // 현재 route를 /list로 변경.
          this.$router.go(this.$router.currentRoute);
        });
      }
    },
  },
};
</script>

<style></style>
