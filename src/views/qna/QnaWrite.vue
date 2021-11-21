<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert show><h3>질문작성</h3></b-alert>
      </b-col>
    </b-row>
    <board-write-form type="register" />
  </b-container>
</template>

<script>
import BoardWriteForm from "./child/QnaWriteForm.vue";

export default {
  name: "qnawrite",
  components: {
    BoardWriteForm,
  },
};
</script>

<style></style>
