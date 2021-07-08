<template>
  <div class="logotitle" id="logotitle">
    <!--logo-->
    <div class="logo"></div>
    <!--天气-->
    <div class="weatherbox">
      <div class="weather-prlt-box">
        <!--日期-->
        <div class="daybox">
          <span id="day">{{currentDay}}</span>
        </div>
        <div class="tempBox">
          <div id="he-plugin-simple"></div>
          <!--<div id="weather-v2-plugin-simple"></div>-->
        </div>
        <!--时钟-->
        <div class="timebox">
          <span id="nowTime">{{nowTime}}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  var padaDate = function(value){
    return value<10 ? '0'+value : value;
  };

  import logotitle from '../components/title';
  export default {
    name: "logotitle",
    data(){
      return {
        currentDay:'',
        nowTime:'',
      }
    }, //data

    mounted() {
      // window.WIDGET = {
      //   CONFIG: {
      //     "modules": "02",
      //     "background": 5,
      //     "tmpColor": "#FFFFFF",
      //     "tmpSize": 22,
      //     "cityColor": "#FFFFFF",
      //     "citySize": 16,
      //     "aqiSize": 16,
      //     "weatherIconSize": "90",
      //     "alertIconSize": 18,
      //     "padding": "10px 10px 10px 10px",
      //     "shadow": "0",
      //     "language": "auto",
      //     "borderRadius": 5,
      //     "fixed": "false",
      //     "vertical": "middle",
      //     "horizontal": "center",
      //     "key": "bb0bd610ef8e4dd0b28f6920d11d41a8"
      //   }
      // };

      // var script = document.createElement('script');
      // script.type = 'text/javascript';
      // // script.src="https://widget.qweather.net/simple/static/js/he-simple-common.js?v=2.0";
      // script.src="https://apip.weatherdt.com/simple/static/js/weather-simple-common.js?v=2.0";
      // document.getElementsByTagName('head')[0].appendChild(script);

      // window.WIDGET = {
      //   "CONFIG": {
      //     "modules": "20134",
      //     "background": "5",
      //     "tmpColor": "17E6F5",
      //     "tmpSize": "30",
      //     "cityColor": "17E6F5",
      //     "citySize": "30",
      //     "aqiColor": "17E6F5",
      //     "aqiSize": "30",
      //     "weatherIconSize": "30",
      //     "alertIconSize": "18",
      //     "padding": "10px 10px 10px 10px",
      //     "shadow": "0",
      //     "language": "zh",
      //     "fixed": "false",
      //     "vertical": "center",
      //     "horizontal": "center",
      //     "key": "bb0bd610ef8e4dd0b28f6920d11d41a8"
      //   }
      // };
      // (function (d) {
      //   var c = d.createElement('link');
      //   c.rel = 'stylesheet';
      //   // c.href = '../static/css/he-simple.css';
      //   c.href = 'https://widget.heweather.net/simple/static/css/he-simple.css?v=1.4.0';
      //   var s = d.createElement('script');
      //   // s.src = '../static/js/he-simple.js';
      //   s.src = 'https://widget.heweather.net/simple/static/js/he-simple.js?v=1.4.0';
      //   var sn = d.getElementsByTagName('script')[0];
      //   sn.parentNode.insertBefore(c, sn);
      //   sn.parentNode.insertBefore(s, sn);
      // })(document);

      this.currentDay= new Date().getFullYear() + "年" + (new Date().getMonth() + 1) + "月" + new Date().getDate() + "日" + " 星期" + "日一二三四五六".charAt(new Date().getDay());

      let _this = this; // 声明一个变量指向Vue实例this，保证作用域一致
      this.timer = setInterval(() => {
        _this.nowTime =padaDate(new Date().getHours())+':'+padaDate(new Date().getMinutes()); // 修改数据date
      }, 1000)
    },//mounted

    created(){
      //和风天气插件调用
      window.WIDGET = {
        "CONFIG": {
          "modules": "02",
          "background": "5",
          "tmpColor": "#FFFFFF",
          "tmpSize": "22",
          "aqiColor": "#FFFFFF",
          "aqiSize": "30",
          "weatherIconSize": "90",
          "alertIconSize": "18",
          "padding": "10px 10px 10px 10px",
          "shadow": "0",
          "language": "auto",
          "fixed": "false",
          "vertical": "center",
          "horizontal": "center",
          "key": "bb0bd610ef8e4dd0b28f6920d11d41a8"
        }
      };
      (function (d) {
        var c = d.createElement('link');
        c.rel = 'stylesheet';
        c.href = '../static/css/he-simple.css';
        // c.href = 'https://widget.heweather.net/simple/static/css/he-simple.css?v=1.4.0';
        var s = d.createElement('script');
        s.src = '../static/js/he-simple.js';
        // s.src = 'https://widget.heweather.net/simple/static/js/he-simple.js?v=1.4.0';
        var sn = d.getElementsByTagName('script')[0];
        sn.parentNode.insertBefore(c, sn);
        sn.parentNode.insertBefore(s, sn);
      })(document);
    },

    beforeDestroy() {
      if (this.timer) {
        clearInterval(this.timer); // 在Vue实例销毁前，清除我们的定时器
      }
    },//befoteDestroy

    components:{
      logotitle,
    },
  }



</script>


<style scoped>

</style>
