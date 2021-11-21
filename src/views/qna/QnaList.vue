<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>질문 목록</h3></b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-right">
        <b-button variant="outline-primary" @click="moveWrite()"
          >질문 작성</b-button
        >
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <!-- <b-table
          striped
          hover
          :items="articles"
          :fields="fields"
          @row-clicked="viewArticle"
        >
          <template #cell(subject)="data">
            <router-link
              :to="{
                name: 'QnaView',
                params: { articleno: data.item.articleno },
              }"
              >{{ data.item.subject }}</router-link
            >
          </template>
        </b-table> -->
      </b-col>
    </b-row>
    <b-row>
      <b-col v-if="articles.length">
        <qna-page-list :list-array="articles" />
      </b-col>
      <b-col v-else class="text-center">질문 목록이 없습니다.</b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/util/http-common";
// import BoardListRow from "@/components/board/BoardListRow";
import QnaPageList from "@/views/qna/child/QnaPageList";
export default {
  name: "qnalist",
  components: {
    // BoardListRow,
    QnaPageList,
  },
  data() {
    return {
      articles: [],
      fields: [
        { key: "articleno", label: "질문번호", tdClass: "tdClass" },
        { key: "subject", label: "제목", tdClass: "tdSubject" },
        { key: "userid", label: "작성자", tdClass: "tdClass" },
        { key: "regtime", label: "작성일", tdClass: "tdClass" },
        { key: "hit", label: "조회수", tdClass: "tdClass" },
      ],
    };
  },
  created() {
    http.get(`/qna`).then(({ data }) => {
      this.articles = data;
    });
  },
  methods: {
    moveWrite() {
      this.$router.push({ name: "qnawrite" });
    },
    viewArticle(article) {
      this.$router.push({
        name: "QnaView",
        params: { articleno: article.articleno },
      });
    },
  },
};
</script>

<style scope>
.tdClass {
  width: 50px;
  text-align: center;
}
.tdSubject {
  width: 300px;
  text-align: left;
}
</style>
