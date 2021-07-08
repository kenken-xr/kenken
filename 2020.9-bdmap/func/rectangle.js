// 百度地图API功能
var map = new BMap.Map('map');
//var poi = new BMap.Point(114.3424,30.558006);
map.centerAndZoom("武汉", 15); //whu
map.enableScrollWheelZoom();
// map.setMapStyle({style:'dark'});

var overlays = [];
var styleOptions = {
    strokeColor: "#F9C126",    //边线颜色。
    fillColor: "#FFFFFF",      //填充颜色。当参数为空时，圆形将没有填充效果。
    strokeWeight: 3,       //边线的宽度，以像素为单位。
    strokeOpacity: 0.8,    //边线透明度，取值范围0 - 1。
    fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
    strokeStyle: 'solid' //边线的样式，solid或dashed。
};
//实例化鼠标绘制工具
var drawingManager = new BMapLib.DrawingManager(map, {
    isOpen: false, //是否开启绘制模式
    //enableDrawingTool: true, //是否显示工具栏
    drawingToolOptions: {
        anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
        offset: new BMap.Size(5, 5), //偏离值
    },
    rectangleOptions: styleOptions //矩形的样式
});
//添加鼠标绘制工具监听事件，用于获取绘制结果
var overlaycomplete = function (e) {
    overlays.push(e.overlay);
    drawingManager.close();
    console.log(drawingManager.getDrawingMode())
};
drawingManager.addEventListener('overlaycomplete', overlaycomplete);
//矩形
function draw(type) {
    drawingManager.open();
    drawingManager.setDrawingMode(type);
    drawingManager.addEventListener('rectanglecomplete', function(e,p){
        var point = p.getPath();
        var str="";
        for(var i = 0;i<point.length;i++){
            //console.log("i="+i+"  point="+point[i].lng+","+point[i].lat);
            str += "("+point[i].lng+","+point[i].lat+"),";
            if(i===0){lng0 = point[i].lng;lat0=point[i].lat;}
            if(i===1){lng1 = point[i].lng;lat1=point[i].lat;}
            if(i===2){lng2 = point[i].lng;lat2=point[i].lat;}
            if(i===3){lng3 = point[i].lng;lat3=point[i].lat;}
        }
        //slng=lng0; slat=lat0; elng=lng2; elat=lat2;
        slng=lng3; slat=lat3; elng=lng1; elat=lat1;
        console.log(str);
    });
}


// 地图样式
map.setMapStyle({
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
});

