<template>
  <el-container style="height: 100vh; border: 1px solid #eee">
    <NavMenu></NavMenu>
      <el-header>
          <span style="font-size: 17px;text-align: center" >遥感数据模块</span>
      </el-header>

      <el-main>
        <Table :msg="tableData" ></Table>
        <el-upload
          id="upload"
          class="upload-demo"
          action="http://345ea19162.wicp.vip:31216/Manage-0.0.1-SNAPSHOT/file/img"
          :on-success="handle_success"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
          multiple
          :limit="10"
          :on-exceed="handleExceed"
          :file-list="fileList"
        >
          <el-button size="small" type="primary" id="uploadBto"><i class="el-icon-upload el-icon--left"></i>上传</el-button>
        </el-upload>
        <el-upload
          id="analyse"
          class="upload-demo"
          action="http://345ea19162.wicp.vip:31216/Manage-0.0.1-SNAPSHOT/file/analysis"
          :on-success="handle_success"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
          multiple
          :limit="10"
          :on-exceed="handleExceed"
          :file-list="fileList"
        >
          <el-button size="small" type="primary" id="analyseBto" icon="el-icon-edit">分析</el-button>
        </el-upload>
        <div id="text">只能上传jpg/png/tif文件，且不超过500kb</div>
      </el-main>

    </el-container>
</template>

<script>
import Table from "./Table";
import NavMenu from '../common/NavMenu'
export default {
  name: "upload-image",
  index: "",
  model: {
    prop: "imageUrl",
    event: "change",
  },
  props: {
    imageUrl: {
      type: String,
      default: "",
      index: "",
    },
    fileSize: {
      type: Number,
      default: 2048, // 2M 超出后执行压缩
    },
    maxWH: {
      type: Number,
      default: 1920, // 图片长宽上限
    },
  },
  data() {
    return {
      // path: path,
      center: { lng: 109.45744048529967, lat: 36.49771311230842 },
      zoom: 13,
      tableData: [],
      fileList: [],
      imageUrl: "",
      file: "",
    };
  },

  methods: {
    beforeImageUpload(file) {
      let isRightSize = file.size / 1024 < this.fileSize;
      if (!isRightSize) {
        // 压缩
        let compress = new ImageCompress(file, this.fileSize, this.maxWH);
        return compress.compress();
      }
      return isRightSize;
    },
    handleImageSuccess(res) {
      // this.imageUrl = URL.createObjectURL(file.raw);
      const { data } = res;
      if (data.file) {
        this.$emit("change", data.file.url);
      }
    },
    handler({ BMap, map }) {
      var point = new BMap.Point(109.49926175379778, 36.60449676862417);
      map.centerAndZoom(point, 13);
      var marker = new BMap.Marker(point); // 创建标注
      map.addOverlay(marker); // 将标注添加到地图中
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
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      );
    },
    handle_success(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      console.log(res);
      this.tableData = res;
      this.$message.success("图片上传成功");
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
  },
  components: {
    Table,
    NavMenu,
  },
};
</script>

<style>
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

#upload{
  position: absolute;
  top: 200px;
  right: 250px;
}
#analyse{
  position: absolute;
  top:200px ;
  right: 40px;
}
#text{
  position: absolute;
  top: 200px;
  right: 125px;
}
#uploadBto {
  position: absolute;
  top: -50px;
  right: 60px;
   color: #FFF;
   background-color: #519FFF;
   border-color: #519FFF;
 }
#analyseBto {
  position: absolute;
  top: -50px;
  right: 40px;
  color: #FFF;
  background-color: #20B2AA;
  border-color: #20B2AA;
}

/*.upload-demo{*/
  /*display: inline;*/
 /*}*/
</style>
