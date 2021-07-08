<template>
  <div id="app">
    <div>
      <span class="demonstration">位置</span>
      <el-cascader
        size="large"
        :options="options"
        v-model="selectedOptions"
        @change="handleChange"
      >
      </el-cascader>
      <el-button @click="citytoParent">确定</el-button>
    </div>
  </div>
</template>

<script>
import { provinceAndCityData, CodeToText } from "element-china-area-data";

export default {
  name: "app",
  data() {
    return {
      options: provinceAndCityData,
      selectedOptions: [],
      province: "",
      city: "",
    };
  },

  methods: {
    handleChange() {
      var loc = "";
      for (let i = 0; i < this.selectedOptions.length; i++) {
        loc += CodeToText[this.selectedOptions[i]];
      }
      this.province = CodeToText[this.selectedOptions[0]];
      this.city = CodeToText[this.selectedOptions[1]];
    },
    citytoParent: function () {
      this.$emit("event2", this.province, this.city);
    },
  },
};
</script>
