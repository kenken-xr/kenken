<template>
  <div>
    <NavMenu></NavMenu>
    <iframe
      src="http://202.114.196.192:8080/taxi_traces-master/index.html"
      id="mobsf"
      scrolling="no"
      frameborder="0"
      style="
        position: absolute;
        top: 60px;
        left: 10px;
        height: 670px;
        width: 1500px;
      "
    ></iframe>
  </div>
</template>
 
 
<script>
  import NavMenu from '../common/NavMenu'
export default {
  data() {
    return {};
  },
  mounted() {
    /**
     * iframe-宽高自适应显示
     */
    function changeMobsfIframe() {
      const mobsf = document.getElementById("mobsf");
      const deviceWidth = document.body.clientWidth;
      const deviceHeight = document.body.clientHeight;
      mobsf.style.width = Number(deviceWidth) - 120 + "px"; //数字是页面布局宽度差值
      mobsf.style.height = Number(deviceHeight) - 80 + "px"; //数字是页面布局高度差
    }

    changeMobsfIframe();

    window.onresize = function () {
      changeMobsfIframe();
    };
  },
  components:{
    NavMenu,
  }
};
</script>
