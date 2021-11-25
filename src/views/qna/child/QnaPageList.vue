<template>
  <div>
    <b-table-simple hover responsive style=" text-align: center;">
      <colgroup>
        <col style="width:10%;" />
        <col style="width:40%;" />
        <col style="width:10%;" />
        <col style="width:20%;" />
        <col style="width:20%;" />
      </colgroup>
      <b-thead>
        <b-tr>
          <b-th>글번호</b-th>
          <b-th>제목</b-th>
          <b-th>조회수</b-th>
          <b-th>작성자</b-th>
          <b-th>작성일</b-th>
        </b-tr>
      </b-thead>
      <b-tr v-for="p in paginatedData" :key="p.no">
        <b-td>{{ p.articleno }}</b-td>
        <b-th class="text-center">
          <router-link
            :to="{ name: 'qnaview', params: { articleno: p.articleno } }"
            >{{ p.subject }}</router-link
          ></b-th
        >
        <b-td>{{ p.hit }}</b-td>
        <b-td>{{ p.userid }}</b-td>
        <b-td>{{ p.regtime }}</b-td>
      </b-tr>
    </b-table-simple>
    <div class="btn-cover">
      <!-- <button :disabled="pageNum === 0" @click="prevPage" class="page-btn">
        이전
      </button> -->
      <md-button
        variant="outline-primary"
        class="md-default md-round md-sm"
        :disabled="pageNum === 0"
        @click="prevPage"
        >이전</md-button
      >
      <span class="page-count">{{ pageNum + 1 }} / {{ pageCount }} 페이지</span>
      <!-- <button
        :disabled="pageNum >= pageCount - 1"
        @click="nextPage"
        class="page-btn"
      >
        다음
      </button> -->
      <md-button
        variant="outline-primary"
        class="md-default md-round md-sm"
        :disabled="pageNum >= pageCount - 1"
        @click="nextPage"
        >다음</md-button
      >
    </div>
  </div>
</template>

<script>
export default {
  name: "paginated-list",
  data() {
    return {
      pageNum: 0,
    };
  },
  props: {
    listArray: {
      type: Array,
      required: true,
    },
    pageSize: {
      type: Number,
      required: false,
      default: 10,
    },
  },
  methods: {
    nextPage() {
      this.pageNum += 1;
    },
    prevPage() {
      this.pageNum -= 1;
    },
  },
  computed: {
    pageCount() {
      let listLeng = this.listArray.length,
        listSize = this.pageSize,
        page = Math.floor(listLeng / listSize);
      if (listLeng % listSize > 0) page += 1;

      /*
      아니면 page = Math.floor((listLeng - 1) / listSize) + 1;
      이런식으로 if 문 없이 고칠 수도 있다!
      */
      return page;
    },
    paginatedData() {
      const start = this.pageNum * this.pageSize,
        end = start + this.pageSize;
      return this.listArray.slice(start, end);
    },
  },
};
</script>
<style>
.btn-cover {
  margin-top: 1.5rem;
  text-align: center;
}
.btn-cover .page-btn {
  width: 5rem;
  height: 2rem;
  letter-spacing: 0.5px;
}
.btn-cover .page-count {
  padding: 0 1rem;
}
</style>
