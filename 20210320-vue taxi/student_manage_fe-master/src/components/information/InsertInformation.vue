<template>
  <div>
    <NavMenu></NavMenu>
    <el-row :gutter="20">
      <el-col :span="12"><DatePicker @event1="change(arguments)" /></el-col>
      <el-col :span="6"><CityPicker @event2="changeCity(arguments)" /></el-col>
      <el-button @click="getResult">显示结果</el-button>
    </el-row>
    <baidu-map
      :center="center"
      :zoom="zoom"
      @ready="handler"
      style="height: 1080px"
      @click="getClickInfo"
      :scroll-wheel-zoom="true"
    >
      <!--缩放控件-->
      <bm-navigation anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-navigation>
      <bm-scale anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-scale>
      <!-- <bm-geolocation
        anchor="BMAP_ANCHOR_TOP_LEFT"
        :showAddressBar="true"
        :autoLocation="true"
      ></bm-geolocation> -->
      <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT"></bm-city-list>
      <!--定位-->
      <bm-geolocation
        anchor="BMAP_ANCHOR_BOTTOM_RIGHT"
        :showAddressBar="true"
        :autoLocation="true"
      ></bm-geolocation>
      <div v-for="(point, index) in resultData" :key="point.num">
        <bm-marker
          :position="{ lng: point.lon, lat: point.lat }"
          :dragging="true"
        >
          <bm-label
            :content="index + 1"
            :labelStyle="{ color: 'red', fontSize: '10px' }"
            :offset="{ width: -35, height: 20 }"
          />
        </bm-marker>
      </div>
    </baidu-map>
  </div>
</template>

<script>
import DatePicker from "./DatePicker";
import CityPicker from "./CityPicker";
import NavMenu from '../common/NavMenu'
export default {
  name: "InsertInformation",
  data() {
    return {
      resultData: [],
      center: { lng: 0, lat: 0 },
      zoom: 3,
      starttime: 0,
      endtime: 0,
      province: "",
      city: "",
    };
  },
  methods: {
    handler({ BMap, map }) {
      var point = new BMap.Point(114.40888375359529, 30.51208882068532);
      map.centerAndZoom(point, 13);
      //var marker = new BMap.Marker(point); // 创建标注
      //map.addOverlay(marker); // 将标注添加到地图中
      var circle = new BMap.Circle(point, 6, {
        strokeColor: "Red",
        strokeWeight: 6,
        strokeOpacity: 1,
        Color: "Red",
        fillColor: "#f03",
      });
      map.addOverlay(circle);
    },
    getClickInfo(e) {
      console.log(e.point.lng);
      console.log(e.point.lat);
      this.center.lng = e.point.lng;
      this.center.lat = e.point.lat;
    },
    change(data) {
      this.starttime = data[0];
      this.endtime = data[1];
      console.log(data);
      console.log(data[0]);
      console.log(data[1]);
    },
    changeCity(data) {
      this.province = data[0];
      this.city = data[1];
      console.log(data);
    },
    getResult() {
      // var data = {
      //   type: "SYNC",
      //   s: this.starttime,
      //   e: this.endtime,
      //   province: decodeURIComponent(this.province),
      //   city: encodeURIComponent(this.city),
      // };
      // console.log(this.$qs.stringify(data));
      this.$axios
        .get(
          "http://345ea19162.wicp.vip:19326/analyzeResult?" +
            "type=SYNC" +
            "&s=" +
            this.starttime +
            "&e=" +
            this.endtime +
            "&province=" +
            this.province +
            "&city=" +
            this.city
          //"http://345ea19162.wicp.vip:19326/analyzeResult?type=SYNC&s=20141001&e=20141007&province=%E6%B9%96%E5%8C%97%E7%9C%81&city=%E6%AD%A6%E6%B1%89%E5%B8%82"
        )
        .then((response) => {
          this.resultData = response.data.result;
          console.log(this.resultData);
          //response.data.status
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
  components: {
    DatePicker,
    CityPicker,
    NavMenu,
  },
};
</script>

<style scoped>
html,
body {
  padding: 0;
  margin: 0;
}

.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  background-color: #003366;
  color: #333;
}
</style>
