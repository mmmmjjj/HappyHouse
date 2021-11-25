<template>
  <b-row class="mb-1">
    <modal v-if="classicModal" @close="classicModalHide">
      <template slot="body">
        <p>
          글 작성이 완료되었습니다.
        </p>
      </template>

      <template slot="footer">
        <md-button class="md-success md-simple" @click="moveList"
          >확인</md-button
        >
      </template>
    </modal>

    <b-col style="text-align: left">
      <b-form @submit="onSubmit" @reset="onReset">
        <b-form-group
          id="userid-group"
          label="작성자:"
          label-for="userid"
          description="작성자를 입력하세요."
        >
          <b-form-input
            id="userid"
            :disabled="isUserid"
            v-model="article.userid"
            type="text"
            required
            readonly
            placeholder="작성자 입력..."
          ></b-form-input>
        </b-form-group>

        <b-form-group
          id="subject-group"
          label="제목:"
          label-for="subject"
          description="제목을 입력하세요."
        >
          <b-form-input
            id="subject"
            v-model="article.subject"
            type="text"
            required
            placeholder="제목 입력..."
          ></b-form-input>
        </b-form-group>

        <b-form-group id="content-group" label="내용:" label-for="content">
          <b-form-textarea
            id="content"
            v-model="article.content"
            placeholder="내용 입력..."
            rows="10"
            max-rows="15"
          ></b-form-textarea>
        </b-form-group>

        <md-button
          type="submit"
          variant="primary"
          class="md-info md-round"
          v-if="this.type === 'register'"
          >글작성</md-button
        >
        <md-button
          type="submit"
          variant="primary"
          class="md-info md-round"
          v-else
          >글수정</md-button
        >
        <md-button type="reset" variant="danger" class="md-simple md-round"
          >초기화</md-button
        >
      </b-form>
    </b-col>
  </b-row>
</template>

<script>
import { writeArticle, getArticle, modifyArticle } from "@/api/board";

import { mapActions, mapMutations, mapState } from "vuex";
import { Modal } from "@/components";

const memberStore = "memberStore";

export default {
  name: "noticeboardwriteform",
  data() {
    return {
      article: {
        articleno: 0,
        userid: "",
        subject: "",
        content: "",
      },
      isUserid: false,
      classicModal: false,
    };
  },
  components: { Modal },

  props: {
    type: { type: String },
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  created() {
    this.article.userid = this.userInfo.userid;
    if (this.type === "modify") {
      getArticle(
        this.$route.params.articleno,
        ({ data }) => {
          // this.article.articleno = data.article.articleno;
          // this.article.userid = data.article.userid;
          // this.article.subject = data.article.subject;
          // this.article.content = data.article.content;
          this.article = data;
        },
        (error) => {
          console.log(error);
        }
      );
      this.isUserid = true;
    }
  },
  methods: {
    ...mapActions(memberStore, ["getUserInfo"]),
    ...mapMutations(memberStore, ["SET_USER_INFO"]),
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      !this.article.userid &&
        ((msg = "작성자 입력해주세요"),
        (err = false),
        this.$refs.userid.focus());
      err &&
        !this.article.subject &&
        ((msg = "제목 입력해주세요"),
        (err = false),
        this.$refs.subject.focus());
      err &&
        !this.article.content &&
        ((msg = "내용 입력해주세요"),
        (err = false),
        this.$refs.content.focus());

      if (!err) alert(msg);
      else
        this.type === "register" ? this.registArticle() : this.updateArticle();
    },
    onReset(event) {
      event.preventDefault();
      this.article.articleno = 0;
      this.article.subject = "";
      this.article.content = "";
      this.$router.push({ name: "BoardList" });
    },
    registArticle() {
      writeArticle(
        {
          userid: this.article.userid,
          subject: this.article.subject,
          content: this.article.content,
        },
        ({ data }) => {
          if (data != "success") {
            let msg = "등록 처리시 문제가 발생했습니다.";
            alert(msg);
            this.moveList();
          } else this.classicModal = true;
        },
        (error) => {
          console.log(error);
        }
      );
    },
    updateArticle() {
      modifyArticle(
        {
          articleno: this.article.articleno,
          userid: this.article.userid,
          subject: this.article.subject,
          content: this.article.content,
        },
        ({ data }) => {
          if (data != "success") {
            let msg = "수정 처리시 문제가 발생했습니다.";
            alert(msg);
            this.moveList();
          } else this.classicModal = true;
        },
        (error) => {
          console.log(error);
        }
      );
    },
    moveList() {
      this.type === "register"
        ? this.$router.push({ name: "noticeboardlist" })
        : this.$router.push({
            name: "noticeboardview",
            params: { articleno: this.article.articleno },
          });
    },
    classicModalHide() {
      this.classicModal = false;
    },
  },
};
</script>

<style></style>
