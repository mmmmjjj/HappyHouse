<template>
  <b-container v-if="houses && houses.length != 0" class="bv-example-row mt-3">
    <house-list-row
      v-for="(house, index) in houses"
      :key="index"
      :house="house"
    />
    <!-- <apt-pagination no-arrows v-model="defaultPagination" :page-count="5">
    </apt-pagination>

    <props-table component-name="aptpagination"></props-table> -->
    <b-row>
      <b-col v-if="houses.length">
        <house-page-list :list-array="house" />
      </b-col>
      <b-col v-else class="text-center">주택 목록이 없습니다.</b-col>
    </b-row>
  </b-container>
  <b-container v-else class="bv-example-row mt-3">
    <b-row>
      <b-col><b-alert show>주택 목록이 없습니다.</b-alert></b-col>
    </b-row>
  </b-container>
</template>

<script>
import HouseListRow from "@/views/apt/HouseListRow.vue";
import { mapState } from "vuex";
import HousePageList from "@/views/apt/child/HousePageList";
//import AptPagination from "@/views/apt/child/AptPagination";

const houseStore = "houseStore";

export default {
  name: "HouseList",
  components: {
    HouseListRow,
    HousePageList,
    //AptPagination,
  },
  data() {
    return {
      perPage: 10,
      currentPage: 1,
      defaultPagination: 1,
    };
  },
  computed: {
    ...mapState(houseStore, ["houses"]),
    // houses() {
    //   return this.$store.state.houses;
    // },
  },
};
</script>

<style></style>
