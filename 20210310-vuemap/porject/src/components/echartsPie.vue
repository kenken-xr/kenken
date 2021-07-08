<template>
  <div class="main" id="main" v-show="isShow">
  </div>
</template>

<script>
  import echartsPie from '../components/echartsPie'
  import ePie from './newVue'

  let echarts = require('echarts/lib/echarts');
  // 引入饼状图组件
  require('echarts/lib/chart/pie');
  // 引入提示框和title组件
  require('echarts/lib/component/tooltip');
  require('echarts/lib/component/title');
  require("echarts/lib/component/legend");
  require("echarts/lib/component/toolbox");
    export default {
      name: "echartsPie",
      data(){
        return{
          isShow:false,
          cy:'',
          yl:'',
          shfw:'',
          cxzs:'',
          hw:'',
          xy:'',
        }
      },
      components: {
        echartsPie,
      },
      mounted(){
        ePie.$on('pieData', (...val) => {
          this.cy = val[0][0];
          this.yl = val[0][1];
          this.shfw = val[0][2];
          this.cxzs = val[0][3];
          this.hw = val[0][4];
          this.xy = val[0][5];
        });
        var that = this;
        ePie.$on('echartsMethod', function() {
          that.myEcharts()
        });
        ePie.$on('listenShow', (...val) => {
          this.isShow = val[0][0];
        });
      },
      methods:{
        myEcharts(){
          this.isShow = true;
          console.log('餐饮:',this.cy,'娱乐:',this.yl,'生活服务:',this.shfw,'出行住宿:',this.cxzs,'户外:',this.hw,'校园:',this.xy);
          // 基于准备好的dom，初始化echarts实例
          var myChart = echarts.init(document.getElementById('main'));
          // 绘制图表
          myChart.setOption({
            title : {
              text: '用户活动类型',//主标题
              textStyle: {color: 'white'},
              x:'center',//x轴方向对齐方式
              y:20,
            },
            tooltip : {
              trigger: 'item',
              formatter: "{b} : {c} ({d}%)",
              confine:true//将此限制打开后tooltip将不再溢出
            },
            legend: [
              {
                data: ['餐饮','娱乐','生活服务'],
                orient: 'vertical',
                bottom: '15',//图例位置
                left:'20',
                textStyle: { color: "#FFFDFE" },//字体颜色
                icon: "circle"//图例形状设置
              },
              {
                data: ['出行住宿','户外','校园'],
                orient: 'vertical',
                bottom: '15',//图例位置
                left:'160',
                textStyle: { color: "#FFFDFE" },//字体颜色
                icon: "circle"//图例形状设置
              },
            ],
            series : [{
                type: 'pie',
                radius : '34%',
                center: ['50%', '45%'],
                data:[
                  {value:this.cy, name:'餐饮', itemStyle:{normal:{color:"#0e932e", label: { show: true }, labelLine: { show: true }}}},
                  {value:this.yl, name:'娱乐',itemStyle:{color:"#e16531"},label: { show: true },labelLine: { show: true }},
                  {value:this.shfw, name:'生活服务',itemStyle:{color:"#db639b"},label: { show: true },labelLine: { show: true }},
                  {value:this.cxzs, name:'出行住宿',itemStyle:{color:"#594d9c"},label: { show: true },labelLine: { show: true }},
                  {value:this.hw, name:'户外',itemStyle:{color:"#d81e06"},label: { show: true },labelLine: { show: true }},
                  {value:this.xy, name:'校园',itemStyle:{color:"#84E4CE"},label: { show: true },labelLine: { show: true }},
                ],
                itemStyle: {
                  // emphasis: {
                  //   shadowBlur: 10,
                  //   shadowOffsetX: 0,
                  //   shadowColor: "black"
                  // },
                  normal:{
                    label:{
                      textStyle: {color:'white',},
                      show: true,
                      // formatter: '{b} : {c} ({d}%)',
                      formatter:'{b} : \n{c} ({d}%)',
                    },
                    labelLine :{show:true,},
                  },
                },
                labelLine :{
                  show:true,
                  normal:{
                    length:2
                  }
                },
              }],
            toolbox: {
              left: '20',//距左
              top: '20',//距上
              show:true,
              feature: {
                // dataView: {
                //   readOnly: false,
                //   emphasis: {
                //     iconStyle: {
                //       textFill:'#ffffff',
                //     }
                //   }
                // },
                saveAsImage: {
                  show:true,
                  backgroundColor:"#004981",
                  emphasis: {
                    iconStyle: {
                      textFill:'#ffffff',
                    }
                  }
                },//保存为图片
              },
              iconStyle:{
                color:'white',//设置颜色
              }
            },
          });
        }  //画echarts图
      }
    }


</script>

<style scoped>
  .main{
    position:absolute;
    bottom: 150px;
    right: 40px;
    width:330px;
    height: 345px;
    background:url("../assets/images/legend-box.png") no-repeat;
    z-index: 99;
  }
</style>
