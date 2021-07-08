<template>
  <baidu-map class="bmap" id="bmap"
             :center="{lng: 114.3424, lat: 30.558006}"
             :zoom="15"
             :mapStyle="mapStyle"
             :scroll-wheel-zoom="true"
             :style="bstyle">
  </baidu-map>
</template>
<script>
  import {BaiduMap} from "vue-baidu-map";
  export default {
    name: 'bmap',
    data () {
      return {
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
        bstyle:{width:'100%', height:''},
      }
    },
    methods:{
      getWidth(){
        //获取窗口的高度
        this.bstyle.height=(window.innerHeight)+'px';
      }
    },
    mounted(){
      this.getWidth()
    },
    components: {
      BaiduMap,
    },
  }
</script>
<style >
  .BMap_cpyCtrl {
    display: none;
  }
  .anchorBL {
    display: none;
  }
  /*.BMap_bubble_content{*/
    /*background: transparent;*/
  /*}*/
  .BMap_bottom {
    display: none;
  }
  .BMap_top {
    display: none;
  }
  .BMap_center {
    display: none;
  }
  .BMap_pop > div{
    background: transparent;
    color: #ffffff;
  }
  /* 隐藏边角 */
  .BMap_pop div:nth-child(1) div {
    display:none;
  }
  .BMap_pop div:nth-child(3) {
    display:none;
  }
  .BMap_pop div:nth-child(5) {
    display:none;
  }
  .BMap_pop div:nth-child(6) {
    display:none;
  }
  .BMap_pop div:nth-child(7) {
    display:none;
  }
  .BMap_pop div:nth-child(8) {
    display:none;
  }
  /*.BMap_pop img {*/
    /*top:43px !important;*/
    /*left:215px !important;*/
    /*!*color: white;*!*/
  /*}*/
</style>
