<template>
    <div id="mainDiv">
      <button class="button" id="wbData" @click="goWbData">微博签到数据模块</button>
      <button class="button" id="rsData" @click="goRsData">遥感数据模块</button>
      <button class="button" id="trailData" @click="goTrailData">轨迹数据模块</button>
    </div>
</template>

<script>
  export default {
    name: 'mainPage',
    methods:{
      goWbData(){
        this.$router.push("../information/insert");
      },
      goRsData(){
        this.$router.push("../information/select");
      },
      goTrailData(){
        this.$router.push("../information/update");
      }
    },
    beforeRouteEnter(to, from, next) {
      // 添加背景色 margin:0;padding:0是为了解决vue四周有白边的问题
      document.querySelector('body').setAttribute('style', 'margin:0;padding:0')
      next()
    },
    beforeRouteLeave(to, from, next) {
      // 去除背景色
      document.querySelector('body').setAttribute('style', '')
      next()
    },
  }
</script>

<style scoped>
  *{
    margin:0;
    padding:0;
    border:0;
  }
  #mainDiv{
    height: 100%;
    width: 100%;
    background: url("../../assets/mainPage-background.gif") no-repeat center;
    /*background: url("../../assets/mainPage.jpg") no-repeat center;*/
    background-size: cover;
    position: fixed;
  }
  .button{
    position: relative;
    top: 400px;
  }
  button{
    position: relative;
    padding: 10px 30px;
    margin: 0 45px;
    color: #21ebff;
    text-decoration: none;
    font-size: 20px;
    text-transform: uppercase;
    transition: 0.5s;
    overflow: hidden;
    background-color: Transparent;
    border-style :none;
    -webkit-box-reflect: below 1px -webkit-linear-gradient(transparent,transparent 50%,rgba(0,0,0,.8));
  }
  button:hover {
    background: #21ebff;
    color: #111;
    box-shadow: 0 0 50px #21ebff;
    transition-delay: 0.5s;
  }

  button:nth-child(1) {
    filter: hue-rotate(115deg);
  }

  button:nth-child(3) {
    filter: hue-rotate(270deg);
  }

  button::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 10px;
    height: 10px;
    border-top: 2px solid #21ebff;
    border-left: 2px solid #21ebff;
    transition: 0.5s;
    transition-delay: 0.5s;
  }

  button:hover::before {
    width: 100%;
    height: 100%;
    transition-delay: 0s;
  }

  button::after {
    content: '';
    position: absolute;
    right: 0;
    bottom: 0;
    width: 10px;
    height: 10px;
    border-bottom: 2px solid #21ebff;
    border-right: 2px solid #21ebff;
    transition: 0.5s;
    transition-delay: 0.5s;
  }

 button:hover::after {
    width: 100%;
    height: 100%;
    transition-delay: 0s;
  }
</style>
