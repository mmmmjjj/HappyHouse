<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-1">
      <b-col class="text-left">
        <md-button
          variant="outline-primary"
          class="md-info md-round"
          @click="listArticle"
          >목록</md-button
        >
      </b-col>
      <b-col class="text-right" v-if="isWriter">
        <b-button
          variant="outline-primary"
          class="md-info md-round"
          @click="listArticle"
          >글수정</b-button
        >
        <b-button variant="outline-danger" size="sm" @click="deleteArticle"
          >글삭제</b-button
        >
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col>
        <b-card
          :header-html="
            `<h3>${article.articleno}.
          ${article.subject} [${article.hit}]</h3><div><h6>${article.userid}</div><div>${article.regtime}</h6></div>`
          "
          class="mb-2"
          border-variant="dark"
          no-body
        >
          <b-card-body class="text-left">
            <div v-html="message"></div>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>
    <b-table-simple hover responsive>
      <b-thead head-variant="dark">
        <b-tr>
          <b-th>답변</b-th>
        </b-tr>
      </b-thead>
      <tbody>
        <!-- 하위 component인 ListRow에 데이터 전달(props) -->
        <qna-memo-row
          v-for="(memo, index) in memos"
          :key="index"
          v-bind="memo"
        />
      </tbody>
    </b-table-simple>
    <b-col>
      <!-- <b-table-simple hover responsive>
        <b-thead head-variant="dark">
          <b-tr>
            <b-th>답변작성란</b-th>
          </b-tr>
        </b-thead>
        <tbody> --><!-- </tbody>
      </b-table-simple> -->
      <memo-write-form :articleno="articleno" :userid="userInfo.userid" />
    </b-col>
  </b-container>
</template>

<script>
// import moment from "moment";
import http from "@/util/http-common";
import QnaMemoRow from "@/views/qna/child/QnaMemoRow";
import MemoWriteForm from "./child/MemoWriteForm.vue";
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "qnaview",
  components: {
    QnaMemoRow,
    MemoWriteForm,
  },
  data() {
    return {
      articleno: 0,
      article: {},
      memos: {},
      isWriter: false,
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
    // changeDateFormat() {
    //   return moment(new Date(this.article.regtime)).format(
    //     "YYYY.MM.DD hh:mm:ss"
    //   );
    // },
  },
  created() {
    this.articleno = this.$route.params.articleno;
    this.getArticle();
    console.log(this.$route.params.articleno + "first");
  },
  methods: {
    async getArticle() {
      await http
        .get(`/qna/${this.$route.params.articleno}`)
        .then(({ data }) => {
          this.article = data;
          if (this.article.userid == this.userInfo.userid) this.isWriter = true;
        });
      await http
        .get(`/memo/${this.$route.params.articleno}`)
        .then(({ data }) => {
          this.memos = data;
        });
    },
    listArticle() {
      this.$router.push({ name: "qnalist" });
    },
    moveModifyArticle() {
      this.$router.replace({
        name: "qnaupdate",
        params: { articleno: this.article.articleno },
      });
      //   this.$router.push({ path: `/board/modify/${this.article.articleno}` });
    },
    deleteArticle() {
      if (confirm("정말로 삭제?")) {
        this.$router.replace({
          name: "qnadelete",
          params: { articleno: this.article.articleno },
        });
      }
    },
  },
};
</script>

<style></style>
