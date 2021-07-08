<template>
  <div>
    <DxRangeSelector
      id="range-selector"
      v-model:value="range"
    >
      <!--<DxMargin :top="50"/>-->
      <DxScale
        :start-value="startValue"
        :end-value="endValue"
      >
        <DxMinorTick :visible="false"/>
        <DxMinorTickInterval :days="1"/>
        <DxTickInterval :weeks="1"/>
        <DxMinRange :weeks="1"/>
        <DxMaxRange :months="1"/>
      </DxScale>
      <DxSliderMarker format="monthAndDay"/>
    </DxRangeSelector>
    <button id="run" value="选 择 区 域" class="button" @click="run()">选 择 区 域</button>
  </div>

</template>
<script>

  import {
    DxRangeSelector,
    DxMargin,
    DxScale,
    DxMinorTick,
    DxMinorTickInterval,
    DxTickInterval,
    DxMinRange,
    DxMaxRange,
    DxSliderMarker
  } from 'devextreme-vue/range-selector';

  // import 'src/assets/css/dx.light.css'

  export default {
    components: {
      DxRangeSelector,
      DxMargin,
      DxScale,
      DxMinorTick,
      DxMinorTickInterval,
      DxTickInterval,
      DxMinRange,
      DxMaxRange,
      DxSliderMarker
    },
    data() {
      return {
        startValue: new Date(2014, 6, 1),
        endValue: new Date(2014, 8, 31),
        range: {
          from: new Date(2014,7,1),
          to:new Date(2014,7,14),
          min:{days:7}
        }
      };
    },
 
    methods:{
      run(){
        console.log(this.range)
      }
    }

  };

</script>
<style scoped>
  #range-selector {
    height: 250px;
    width: 1200px;
    position: absolute;
    bottom: -50px;
    left:50px;
  }
  .button{
    height: 30px;
    width: 270px;
    z-index: 999;
    background-color: #0000ff;
    color: #ffffff;
    border:0;
    font-size: 16px;
    /*border: 2px solid #4CAF50;*/

  }
  #run{
    top: 500px;
    left:100px;
    position: absolute;
  }

</style>
