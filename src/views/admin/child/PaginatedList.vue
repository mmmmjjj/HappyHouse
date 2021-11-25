<template>
  <div>
    <b-table-simple hover responsive style=" text-align: center;">
      <colgroup>
        <col style="width:10%;" />
        <col style="width:15%;" />
        <col style="width:20%;" />
        <col style="width:10%;" />
        <col style="width:10%;" />
        <col style="width:15%;" />
        <col style="width:20%;" />
      </colgroup>
      <b-thead>
        <b-tr>
          <b-th>아이디</b-th>
          <b-th>이름</b-th>
          <b-th>이메일</b-th>
          <b-th>가입일</b-th>
          <b-th>거주지</b-th>
          <b-th>탈퇴여부</b-th>
          <b-th>수정/삭제</b-th>
        </b-tr>
      </b-thead>
      <b-tr v-for="p in paginatedData" :key="p.userid">
        <b-td>{{ p.userid }}</b-td>
        <b-td>{{ p.username }}</b-td>
        <b-td> {{ p.email }}</b-td>
        <b-td>{{ p.joindate }}</b-td>
        <b-td>{{ p.region }}</b-td>
        <b-td>{{ p.delflag ? "O" : "X" }}</b-td>
        <b-td
          ><md-button
            class="md-info md-round md-sm"
            id="searchBtn"
            @click="update(p)"
          >
            수정
          </md-button>
          <md-button
            class="md-simple md-round md-sm"
            variant="outline-primary"
            @click="delUser(p.userid)"
          >
            삭제
          </md-button></b-td
        >
        <b-td></b-td>
      </b-tr>
    </b-table-simple>
    <div class="btn-cover">
      <button :disabled="pageNum === 0" @click="prevPage" class="page-btn">
        이전
      </button>
      <span class="page-count">{{ pageNum + 1 }} / {{ pageCount }} 페이지</span>
      <button
        :disabled="pageNum >= pageCount - 1"
        @click="nextPage"
        class="page-btn"
      >
        다음
      </button>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
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
    delUser(id) {
      if (confirm("정말로 탈퇴하시겠습니까?")) {
        http
          .put(`/user/withdraw`, {
            userid: id,
          })
          .then(({ data }) => {
            let msg = "탈퇴 처리시 문제가 발생했습니다.";

            if (data == "success") {
              msg = "탈퇴가 완료되었습니다.";
            }
            alert(msg);
            this.moveList();
          });
      }
    },
    moveList() {
      this.$router.go(this.$router.currentRoute);
    },
    update(person) {
      this.$router.push({
        name: "adminmodify",
        params: { user: person },
      });
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
