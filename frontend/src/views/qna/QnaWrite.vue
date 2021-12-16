<template>
  <b-container class="bv-example-row mt-3">
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
