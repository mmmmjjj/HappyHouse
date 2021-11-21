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

          <b-button type="submit" variant="primary" class="m-1"
            >답변작성</b-button
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
    //this.memo.memono = this.memono;
    console.log(this.$route.params.memono + "dddd");
    http.get(`/memo/single/${this.$route.params.memono}`).then(({ data }) => {
      console.log(data);
      this.memo.memono = this.$route.params.memono;
      this.memo.articleno = data.articleno;
      this.memo.userid = data.userid;
      //this.memo.subject = data.article.subject;
      this.memo.comment = data.comment;
      //this.article = data;
    });
    console.log(this.memo.memono + "update1");
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
          console.log(this.articleno);
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data == "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        });
    },
    moveList() {
      // this.$router.push({
      //   name: "QnaList",
      // });
      this.$router.push({
        name: "qnaview",
        params: { articleno: this.memo.articleno },
      });
    },
  },
};
</script>

<style></style>
