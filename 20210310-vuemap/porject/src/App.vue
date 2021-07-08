<template>
  <div id="app">
    <BaiduMap></BaiduMap>
    <logotitle></logotitle>
    <dataManage></dataManage>
    <timeLine></timeLine>
    <echartsPie></echartsPie>
    <router-view/>

  </div>
</template>

<script>
  import BaiduMap from './components/bmap';
  import logotitle from './components/title';
  import dataManage from './components/dataManage';
  import mapLegend from './components/mapLegend';
  import rangeSelector from './components/rangeSelector';
  import timeLine from './components/timeLine'
  import echartsPie from './components/echartsPie'
export default {
  // name: 'App'

  components:{
    BaiduMap,
    logotitle,
    dataManage,
    mapLegend,
    rangeSelector,
    timeLine,
    echartsPie,
  },
}
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

</style>
