<template>
  <b-container class="bv-example-row mt-3">
    <b-row class="mb-1">
      <b-col style="text-align: left">
        <b-form @submit="onSubmit">
          <b-form-group
            id="userid-group"
            label="작성자:"
            label-for="userid"
            description="작성자를 입력하세요."
          >
            <b-form-input
              id="userid"
              v-model="memo.userid"
              type="text"
              required
              readonly
              placeholder="작성자 입력..."
            ></b-form-input>
          </b-form-group>

          <b-form-group id="content-group" label="내용:" label-for="content">
            <b-form-textarea
              id="content"
              v-model="memo.comment"
              placeholder="내용 입력..."
              rows="10"
              max-rows="15"
            ></b-form-textarea>
          </b-form-group>

          <md-button
            type="submit"
            variant="outline-primary"
            class="md-info md-round"
            >답변작성</md-button
          >
        </b-form>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import http from "@/util/http-common";

export default {
  name: "memomodifyform",
  data() {
    return {
      memo: {
        memono: "",
        articleno: "",
        userid: "",
        comment: "",
      },
    };
  },

  created() {
    http.get(`/memo/single/${this.$route.params.memono}`).then(({ data }) => {
      this.memo.memono = this.$route.params.memono;
      this.memo.articleno = data.articleno;
      this.memo.userid = data.userid;
      this.memo.comment = data.comment;
    });
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();
      this.registArticle();
    },
    registArticle() {
      http
        .put(`/memo`, {
          memono: this.memo.memono,
          userid: this.memo.userid,
          articleno: this.memo.articleno,
          comment: this.memo.comment,
        })
        .then(({ data }) => {
          let msg = "답변 수정 시 문제가 발생했습니다.";
          if (data == "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        });
    },
    moveList() {
      this.$router.push({
        name: "qnaview",
        params: { articleno: this.memo.articleno },
      });
    },
  },
};
</script>

<style></style>
