<template>
  <b-row class="mb-1">
    <b-col style="text-align: left">
      <b-form @submit="onSubmit">
        <b-form-group id="userid-group" label="작성자:" label-for="userid">
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
</template>

<script>
import http from "@/util/http-common";

export default {
  name: "memowriteform",
  data() {
    return {
      memo: {
        articleno: "",
        userid: "",
        comment: "",
      },
    };
  },
  props: {
    articleno: Number,
    userid: String,
  },
  created() {
    this.memo.articleno = this.articleno;
    this.memo.userid = this.userid;
    console.log(this.memo.articleno + "update");
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();
      this.registArticle();
    },
    registArticle() {
      http
        .post(`/memo`, {
          articleno: this.memo.articleno,
          userid: this.memo.userid,
          comment: this.memo.comment,
        })
        .then(({ data }) => {
          console.log(this.articleno);
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data != "success") {
            alert(msg);
          }
          this.moveList();
        });
    },
    moveList() {
      this.$router.go(this.$router.currentRoute);
    },
  },
};
</script>

<style></style>
