<template>
  <div class="smart-cg-box">
    <div class="kpi-box">
      <!--数字统计-->
      <ul class="cg-num-stat">
        <li class="cg-num-box">
          <div class="fkh-left"></div>
          <button id="SelectArea" value="选 择 区 域" class="button" @click="draw()">选 择 区 域</button>
          <div class="fkh-right"></div>
        </li>
        <br><br><br><br>
        <li class="cg-num-box">
          <div class="fkh-left"></div>
          <button id="clearArea" value="清 除 区 域" class="button" @click="clearRec()">清 除 区 域</button>
          <div class="fkh-right"></div>
        </li>
        <br><br><br><br>
        <li class="cg-num-box">
          <div class="fkh-left"></div>
          <button id="sure" value="确 定" class="button" @click="sure()">确 定</button>
          <div class="fkh-right"></div>
        </li>
        <br><br><br><br>
        <li class="cg-num-box">
          <div class="fkh-left"></div>
          <button id="behaviorAnalyse" value="行 为 分 析" class="button" @click="behaviorAnalyse()" >行 为 分 析</button>
          <div class="fkh-right"></div>
        </li>
        <br><br><br><br>
        <li class="cg-num-box">
          <div class="fkh-left"></div>
          <button id="tableToExcel" value="导 出 表 格" class="button" @click="tableToExcel()">导 出 表 格</button>
          <div class="fkh-right"></div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
  import {BaiduMap} from "vue-baidu-map";
  import $ from 'jquery';
  import html2canvas from "html2canvas"
  import { showLoading, hideLoading } from './loading'

  var slng=""; var slat=""; var elng=""; var elat=""; var st = '20140720'; var et = '20140727';
  var lngarr=[];
  var latarr=[]; var behavior=[];
  var ID=[]; var datatext=[]; var datatime=[];
  var point=[]; var marker=[];
  var classFlag = false;
  var hasFlag = false;
  var indexArray=[];
  var cy = 0; var yl = 0; var shfw = 0; var cxzs = 0; var hw = 0; var xy = 0;
  var cyArr = []; var ylArr = []; var shfwArr = []; var cxzsArr = []; var hwArr = []; var xyArr = [];
  var isShow = ''; var isShowLoading = '';

  import dataManage from '../components/dataManage'
  import BMapLib from 'BMapLib'

  let echarts = require('echarts/lib/echarts');
  // 引入饼状图组件
  require('echarts/lib/chart/pie');
  // 引入提示框和title组件
  require('echarts/lib/component/tooltip');
  require('echarts/lib/component/title');
  require("echarts/lib/component/legend");
  require("echarts/lib/component/toolbox");

  import '@/assets/js/DrawingManager_min.js'
  import '@/assets/css/DrawingManager_min.css'

  import submit from './newVue'
  import ePie from './newVue'
  import loadGif from './newVue'
  export default {
    name: "dataManage",

    data() {
      return {
        map: null,
        drawingManager: null,
        isShowLoading:false,
        overlays: [],
        point:marker,
        mapStyle: {
          styleJson: [{
            "featureType": "water",
            "elementType": "all",
            "stylers": {
              "color": "#044161"
            }
          }, {
            "featureType": "land",
            "elementType": "all",
            "stylers": {
              "color": "#091934"
            }
          }, {
            "featureType": "boundary",
            "elementType": "geometry",
            "stylers": {
              "color": "#064f85"
            }
          }, {
            "featureType": "railway",
            "elementType": "all",
            "stylers": {
              "visibility": "off"
            }
          }, {
            "featureType": "highway",
            "elementType": "geometry",
            "stylers": {
              "color": "#004981"
            }
          }, {
            "featureType": "highway",
            "elementType": "geometry.fill",
            "stylers": {
              "color": "#005b96",
              "lightness": 1
            }
          }, {
            "featureType": "highway",
            "elementType": "labels",
            "stylers": {
              "visibility": "on"
            }
          }, {
            "featureType": "arterial",
            "elementType": "geometry",
            "stylers": {
              "color": "#004981",
              "lightness": -39
            }
          }, {
            "featureType": "arterial",
            "elementType": "geometry.fill",
            "stylers": {
              "color": "#00508b"
            }
          }, {
            "featureType": "poi",
            "elementType": "all",
            "stylers": {
              "visibility": "off"
            }
          }, {
            "featureType": "green",
            "elementType": "all",
            "stylers": {
              "color": "#056197",
              "visibility": "off"
            }
          }, {
            "featureType": "subway",
            "elementType": "all",
            "stylers": {
              "visibility": "off"
            }
          }, {
            "featureType": "manmade",
            "elementType": "all",
            "stylers": {
              "visibility": "off"
            }
          }, {
            "featureType": "local",
            "elementType": "all",
            "stylers": {
              "visibility": "off"
            }
          }, {
            "featureType": "arterial",
            "elementType": "labels",
            "stylers": {
              "visibility": "off"
            }
          }, {
            "featureType": "boundary",
            "elementType": "geometry.fill",
            "stylers": {
              "color": "#029fd4"
            }
          }, {
            "featureType": "building",
            "elementType": "all",
            "stylers": {
              "color": "#1a5787"
            }
          }, {
            "featureType": "label",
            "elementType": "all",
            "stylers": {
              "visibility": "off"
            }
          }, {
            "featureType": "poi",
            "elementType": "labels.text.fill",
            "stylers": {
              "color": "#ffffff"
            }
          }, {
            "featureType": "poi",
            "elementType": "labels.text.stroke",
            "stylers": {
              "color": "#1e1c1c"
            }
          }, {
            "featureType": "administrative",
            "elementType": "labels",
            "stylers": {
              "visibility": "on"
            }
          }, {
            "featureType": "road",
            "elementType": "labels",
            "stylers": {
              "visibility": "off"
            }
          }]
        },
        slng:slng,
        slat:slat,
        elng:elng,
        elat:elat,
        st:st,
        et:et,
      }
    },

    components: {
      dataManage,
    },

    mounted(){
      this.map = new BMap.Map("bmap", {
        enableMapClick: false // 禁止底图点击事件
      });
      this.map.centerAndZoom(new BMap.Point(114.3424 ,30.558006), 15);
      this.map.setMapStyle(this.mapStyle);
      this.map.enableScrollWheelZoom();
      this.map.enableMapClick = false;

      var styleOptions = {
        strokeColor: "#F9C126",    //边线颜色。
        fillColor: "#FFFFFF",      //填充颜色。当参数为空时，圆形将没有填充效果。
        strokeWeight: 3,       //边线的宽度，以像素为单位。
        strokeOpacity: 0.8,    //边线透明度，取值范围0 - 1。
        fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
        strokeStyle: 'solid' //边线的样式，solid或dashed。
      };
      //实例化鼠标绘制工具
      this.drawingManager = new BMapLib.DrawingManager(this.map, {
        isOpen: false, //是否开启绘制模式
        // enableDrawingTool: true, //是否显示工具栏
        drawingToolOptions: {
          anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
          offset: new BMap.Size(5, 5), //偏离值
          drawingModes : [ // 可见的操作选项
            BMAP_DRAWING_RECTANGLE
          ]
        },
        rectangleOptions: styleOptions //矩形的样式
      });
      this.drawingManager.addEventListener('overlaycomplete', this.overlaycomplete);

      submit.$on('tL2dM', (...val) => {
        this.st = val[0][0];
        this.et = val[0][1];
      }); //获取时间轴的参数
      submit.$emit('listenShowLoading',[isShowLoading]);
      submit.$emit('loadFunc');

    },

    methods: {
      draw() {
        this.drawingManager.open();
        this.drawingManager.setDrawingMode(BMAP_DRAWING_RECTANGLE);
        this.drawingManager.addEventListener('rectanglecomplete', function (e, p) {
          var point = p.getPath();
          var str = "";
          var lng0="";var lat0="";var lng1="";var lat1="";var lng2="";var lat2="";var lng3="";var lat3="";

          for (var i = 0; i < point.length; i++) {
            str += "(" + point[i].lng + "," + point[i].lat + "),";
            if (i === 0) {lng0 = point[i].lng;lat0 = point[i].lat;}
            if (i === 1) {lng1 = point[i].lng;lat1 = point[i].lat;}
            if (i === 2) {lng2 = point[i].lng;lat2 = point[i].lat;}
            if (i === 3) {lng3 = point[i].lng;lat3 = point[i].lat;}
          }
          //slng=lng0; slat=lat0; elng=lng2; elat=lat2;
          slng = lng3;
          slat = lat3;
          elng = lng1;
          elat = lat1;
          // console.log(str);
          return slng,slat,elng,elat;
        });
      }, //draw

      clearRec() {
        console.log('clearRec');
        this.map.clearOverlays();
        slng = 0;
        marker = []; this.overlays.length=0; lngarr = [];
        cyArr = []; ylArr = []; shfwArr = []; cxzsArr = []; hwArr = []; xyArr = [];
        cy = 0; yl = 0; shfw = 0; cxzs = 0; hw = 0; xy = 0;
        isShow = false;
        ePie.$emit('listenShow',isShow);
      }, //clearRec

      sure() {
          console.log("pl_lon:" + slng + " " + "pl_lat:" + slat + " " + "pr_lon:" + elng + " " + "pr_lat:" + elat );
          console.log("s:" + this.st + " " + "e:" + this.et);
          this.getAjaxData();
          // this.checkData();
          // this.addPointSure();
      }, //sure

      behaviorAnalyse() {
        console.time('行为分析');

        if(!classFlag){
          if (!hasFlag){
            hasFlag = true;
          }
          classFlag = true;
          this.addPointBA();
          ePie.$emit('pieData',[cy, yl, shfw, cxzs, hw, xy]);
          ePie.$emit('echartsMethod');
        }
        else{
          classFlag = false;
          this.addPointBA();
          ePie.$emit('pieData',[cy, yl, shfw, cxzs, hw, xy]);
          ePie.$emit('echartsMethod');
        }
        console.timeEnd('行为分析');
      }, //behaviorAnalyse

      tableToExcel(){
        //列标题，逗号隔开，每一个逗号就是隔开一个单元格
        let str = `lng,lat,activity,time,ID,text\n`;
        //增加\t为了不让表格显示科学计数法或者其他格式
        for(let i = 0 ; i < lngarr.length ; i++ ){
          str+=`${lngarr[i] + '\t'},`;
          str+=`${latarr[i] + '\t'},`;
          str+=`${behavior[i] + '\t'},`;
          str+=`${datatime[i] + '\t'},`;
          str+=`${ID[i] + '\t'},`;
          str+=`${datatext[i] + '\t'},`;
          str+='\n';
        }
        //encodeURIComponent解决中文乱码
        let uri = 'data:text/csv;charset=utf-8,\ufeff' + encodeURIComponent(str);
        //通过创建a标签实现
        var link = document.createElement("a");
        link.href = uri;
        //对下载的文件命名
        link.download = "data.csv";
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        // this.download();
      }, //tableToExcel

      getAjaxData() {
        showLoading();
        console.log('getajaxData');var that = this;
          $.ajax({
            type: "get", //get post
            async: true, //true false
            // url:"http://345ea19162.wicp.vip:31216/demo/blogActivity?pl_lon=114.396600723&pl_lat=30.5176848923&pr_lon=114.4094753265&pr_lat=30.5233042288&start=20150912&end=20150912",
            url:"http://345ea19162.wicp.vip:31216/demo/blogActivity?pl_lon="+slng+"&pl_lat="+slat+"&pr_lon="+elng+"&pr_lat="+elat+"&start="+this.st+"&end="+this.et,
            // url:"http://202.114.196.192:8080/demo/blogContent?pl_lon=114.396600723&pl_lat=30.5176848923&pr_lon=114.4094753265&pr_lat=30.5233042288&start=20150910&end=20150919",
            data: "",
            dataType: "json", //由 JSON 改为 JSONP
            // jsonp: "callback", //传递给请求处理程序或页面的，标识jsonp回调函数名(一般为:callback)
            // jsonpCallback: "getData", //callback的function名称，成功就会直接走 success 方法
            crossDomain: true,
            success: function(data){
              lngarr = [];
              latarr = [];
              $.each(data, function (i, val) {
                lngarr[i] = val['lon'];
                latarr[i] = val['lat'];
                behavior[i]=val['activity'];
                ID[i] = val['id'];
                datatext[i] = val['text'];
                datatime[i] = val['time'];
              });
              console.log('success');
              that.checkData();
              that.addPointSure();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
              console.log("请求对象XMLHttpRequest: " + XMLHttpRequest.status);
              console.log("XMLHttpRequest.readyState: " + XMLHttpRequest.readyState);
              console.log("错误类型textStatus: " + textStatus);
              console.log("异常对象errorThrown: " + errorThrown);
            }
          });
      },  //获取接口数据

      overlaycomplete(e) {
        this.overlays.push(e.overlay);
        this.drawingManager.close();
      },

      addPointSure(){
        console.log("addPoint");
        if (document.createElement('canvas').getContext) {
          for (var i = 0; i < lngarr.length; i++) {// 添加海量点数据
            marker.push(new BMap.Point(lngarr[i], latarr[i]));
          }

          var options = {size:BMAP_POINT_SIZE_SMALL, shape: BMAP_POINT_SHAPE_CIRCLE, color:"yellow",};
          var pointCollection = new BMap.PointCollection(marker, options);
          this.map.addOverlay(pointCollection );

          pointCollection.addEventListener("click", function(e) {
            var id = ""; var text = ""; var time = "";
            for (var i = 0; i < lngarr.length; i++) {
              if (lngarr[i] === e.point.lng && latarr[i] === e.point.lat) {
                id = ID[i]; text = datatext[i]; time = datatime[i]; break;
              }
            }

            var opts = {
              enableMessage: false,//设置允许信息窗发送短息
              backgroundColor:" transparent",
              border:"0",
              color:"#ffffff"
            };
            var infowindow = new BMap.InfoWindow("ID:" + id + "<br/>time:" + time + "<br>text:"+ text , opts);
            var point = new BMap.Point(e.point.lng, e.point.lat);
            this.map.openInfoWindow(infowindow,point);
          });
        }

          // for (var j = 0; j < lngarr.length; j++) {
          //   point[j] = new BMap.Point(lngarr[j], latarr[j]);
          //   var color = "yellow";
          //
          //   marker[j] = new BMap.Marker(point[j], {
          //     // icon: new BMap.Symbol(BMap_Symbol_SHAPE_CIRCLE, {
          //     //   scale: 2,//图标缩放大小
          //     //   fillColor: color,//填充颜色
          //     //   strokeColor:color, //线颜色
          //     //   strokeWeight: 4,
          //     // })
          //   });//创建标注
          //
          //   // this.map.addOverlay(marker[j]);
          //   //Label
          //   var label = new BMap.Label("ID："+ID[j]+"<br>"+"text:"+datatext[j]+"<br>"+"time:"+datatime[j],{ offset: new BMap.Size(30, -10),});
          //   label.setStyle({
          //     backgroundColor:" transparent",
          //     border:"0",
          //     color:"#ffffff"
          //   });
          //   marker[j].setLabel(label); //添加标签/
          //
          //   // 初始化不显示
          //   label.setStyle({
          //     display: "none"
          //   });
          //   // 鼠标经过时
          //   marker[j].addEventListener("mouseover", function(e) {
          //     var label = this.getLabel();
          //     label.setStyle({
          //       display: "block"
          //     });
          //   });
          //   // 鼠标离开时
          //   marker[j].addEventListener("mouseout", function(e) {
          //     var label = this.getLabel();
          //     label.setStyle({
          //       display: "none"
          //     });
          //   });
          // }
      },

      checkData() {
        console.log("checkData");
        hideLoading();
        if(lngarr.length===0){
          alert("该范围内没有数据");
          for(var i = 0; i < this.overlays.length; i++){
            this.map.removeOverlay(this.overlays[i]);
          }
          this.overlays.length = 0;
          classFlag = false;
          hasFlag = false;
        }
        else{
          alert("数据获取成功，共"+lngarr.length+"条数据");
        }
      },  //检查范围内是否有数据

      addPointBA(){
        console.log("addPointBA",lngarr.length);
        cyArr = [];ylArr = [];shfwArr = [];cxzsArr = [];hwArr = [];xyArr = [];
        cy=yl=shfw=cxzs=hw=xy=0;
        if (document.createElement('canvas').getContext) {
          for (var i = 0; i < lngarr.length; i++) {// 添加海量点数据
            // marker.push(new BMap.Point(lngarr[i], latarr[i]));
            if(behavior[i] === "餐饮"){cyArr.push(new BMap.Point(lngarr[i], latarr[i]));cy++;}
            if(behavior[i] === "娱乐"){ylArr.push(new BMap.Point(lngarr[i], latarr[i]));yl++;}
            if(behavior[i] === "生活服务"){shfwArr.push(new BMap.Point(lngarr[i], latarr[i]));shfw++;}
            if(behavior[i] === "出行住宿"){cxzsArr.push(new BMap.Point(lngarr[i], latarr[i]));cxzs++;}
            if(behavior[i] === "户外"){hwArr.push(new BMap.Point(lngarr[i], latarr[i]));hw++;}
            if(behavior[i] === "校园"){xyArr.push(new BMap.Point(lngarr[i], latarr[i]));xy++;}
          }

          var cyOptions = {size:BMAP_POINT_SIZE_SMALL, shape: BMAP_POINT_SHAPE_CIRCLE, color:"#0e932e",};
          var ylOptions = {size:BMAP_POINT_SIZE_SMALL, shape: BMAP_POINT_SHAPE_CIRCLE, color:"#e16531",};
          var shfwOptions = {size:BMAP_POINT_SIZE_SMALL, shape: BMAP_POINT_SHAPE_CIRCLE, color:"#db639b",};
          var cxzsOptions = {size:BMAP_POINT_SIZE_SMALL, shape: BMAP_POINT_SHAPE_CIRCLE, color:"#594d9c",};
          var hwOptions = {size:BMAP_POINT_SIZE_SMALL, shape: BMAP_POINT_SHAPE_CIRCLE, color:"#d81e06",};
          var xyOptions = {size:BMAP_POINT_SIZE_SMALL, shape: BMAP_POINT_SHAPE_CIRCLE, color:"#84E4CE",};

          var cyPointCollection = new BMap.PointCollection(cyArr, cyOptions); this.map.addOverlay(cyPointCollection);
          var ylPointCollection = new BMap.PointCollection(ylArr, ylOptions); this.map.addOverlay(ylPointCollection);
          var shfwPointCollection = new BMap.PointCollection(shfwArr, shfwOptions); this.map.addOverlay(shfwPointCollection);
          var cxzsPointCollection = new BMap.PointCollection(cxzsArr, cxzsOptions); this.map.addOverlay(cxzsPointCollection);
          var hwPointCollection = new BMap.PointCollection(hwArr, hwOptions); this.map.addOverlay(hwPointCollection);
          var xyPointCollection = new BMap.PointCollection(xyArr, xyOptions); this.map.addOverlay(xyPointCollection);

          this.remove0(cy);this.remove0(yl);this.remove0(shfw);this.remove0(cxzs);this.remove0(hw);this.remove0(xy);
          console.log(cy,yl,shfw,cxzs,hw,xy);
        }
      },
      remove0(data){
        if(data===0){
          data = '';
        }
      }
    }, //methods
  } //export default

</script>

<style scoped>
  .button{
    height: 30px;
    width: 180px;
    z-index: 0;
    background-color: #0B1834;
    color: #ffffff;
    border:0;
    font-size: 16px;
    /*border: 2px solid #4CAF50;*/
  }
  #SelectArea{
    top: 15px;
    left:40px;
    position: absolute;
  }
  #clearArea{
    top: 70px;
    left:40px;
    position: absolute;
  }
  #sure{
    top: 125px;
    left:40px;
    position: absolute;
  }
  #behaviorAnalyse{
    top: 180px;
    left:40px;
    position: absolute;
  }
  #tableToExcel{
    top: 235px;
    left:40px;
    position: absolute;
  }
</style>
