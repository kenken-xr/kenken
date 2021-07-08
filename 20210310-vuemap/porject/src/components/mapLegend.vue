<template>
  <div class="mapLegend" id="mapLegend">
    <table class="legendTable">
      <tr height="20px"><th width="30px"></th><th><h2>查询结果</h2></th></tr>
      <tr height="30px"><th> <img src='../assets/images/point-green.png'/></th><th>餐饮</th></tr>
      <tr height="30px"><th> <img src='../assets/images/point-orange.png'/></th><th>娱乐</th></tr>
      <tr height="30px"><th> <img src='../assets/images/point-pink.png'/></th><th>生活服务</th></tr>
      <tr height="30px"><th> <img src='../assets/images/point-purple.png'/></th><th>出行住宿</th></tr>
      <tr height="30px"><th> <img src='../assets/images/point-red.png'/></th><th>户外</th></tr>
      <tr height="30px"><th> <img src='../assets/images/point-white.png'/></th><th>校园</th></tr>
    </table>
  </div>
</template>

<script>
  export default {
    name: "maplegend"
  }
</script>

<style scoped>
  .mapLegend{
    position:absolute;
    bottom: -310px;
    right: 20px;
    width:201px;
    height: 600px;
    background:url("../assets/images/legend-box.png") no-repeat;
    z-index: 99;
  }
  .legendTable{
    position: relative;
    left: 20px;
    top:10px;
    color:white;
    text-align:left;
    z-index: 99;
  }
  .legendTable th{
    font-size: 14px;
    font-weight: normal;
  }
</style>
