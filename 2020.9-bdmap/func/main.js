// function $(id) {
//     return document.getElementById(id);
// }
var slng=""; var slat=""; var elng=""; var elat="";
var st="20141001"; var et="20141007";  var stime=""; var etime="";
var lng0="";var lat0="";var lng1="";var lat1="";var lng2="";var lat2="";var lng3="";var lat3="";
var lngarrAll=[]; var latarrAll=[]; var behaviorAll=[]; var IDAll=[]; var datatextAll=[]; var datatimeAll=[];
var lngarr=[];  var latarr=[]; var behavior=[];
var lngarrCopy=[];  var latarrCopy=[]; var behaviorCopy=[]; var ID=[]; var datatext=[]; var datatime=[];
var point=[];var marker=[];
var indexArray=[];
var classFlag = false;
var hasFlag = false;

function startTime()
{
    //获取当前系统日期
    var today=new Date();
    var h=today.getHours();
    var m=today.getMinutes();
    //调用checkTime（）函数，小于十的数字前加0
    h=checkTime(h);m=checkTime(m);
    //s设置层txt的内容
    document.getElementById('lblCHours').innerHTML=h+":"+m;
    //过500毫秒再调用一次
    t=setTimeout('startTime()',500);
    //小于10，加0
    function checkTime(i)
    {
        if(i<10)
        {i="0"+i}
        return i
    }
}

//清除所有
function clearRec() {
    map.clearOverlays();
    classFlag = false;
    hasFlag = false;
    lngarrAll=[]; latarrAll=[]; IDAll=[]; datatextAll=[]; datatimeAll=[];
    lngarr=[]; latarr=[]; behavior=[];
    lngarrCopy=[]; latarrCopy=[]; behaviorCopy=[]; ID=[]; datatext=[]; datatime=[];
    indexArray=[];
}

// 确认按钮响应
function sure(){
    function getAjaxData() {
        classFlag = false;
        hasFlag = false;
        $.ajax({
            type: "get", //get post
            async: false, //true false
            //url:"http://345ea19162.wicp.vip:19326/test3.1?pl_lon="+slng+"&pl_lat="+slat+"&pr_lon="+elng+"&pr_lat="+elat+"&start="+st+"&end="+et,
            //url:"http://345ea19162.wicp.vip:19326/test5.1?pl_lon="+slng+"&pl_lat="+slat+"&pr_lon="+elng+"&pr_lat="+elat+"&start="+st+"&end="+et,
            //url:"http://345ea19162.wicp.vip:19326/test5.1?pl_lon=114.041298&pl_lat=30.841398&pr_lon=114.801239&pr_lat=30.3258&start=20140910200000&end=20140910210000",
            //url:"http://339kr74718.zicp.vip:37996/test5.1?pl_lon=114.041298&pl_lat=30.841398&pr_lon=114.801239&pr_lat=30.3258&start=20150910200000&end=20150910210000",
            //url:"/demo/blogContent?pl_lon="+slng+"&pl_lat="+slat+"&pr_lon="+elng+"&pr_lat="+elat+"&start="+st+"&end="+et,
            url:"http://339kr74718.zicp.vip:37996/blogContent?pl_lon="+slng+"&pl_lat="+slat+"&pr_lon="+elng+"&pr_lat="+elat+"&start="+st+"&end="+et,
            data:"",
            dataType: "json", //由 JSON 改为 JSONP
            crossDomain: true,
            success: function(data) {
                lngarr=[];latarr=[];
                $.each(data, function(i, val){
                    lngarr[i]=val['lon'];        lngarrAll[i]=val['lon'];
                    latarr[i]=val['lat'];        latarrAll[i]=val['lat'];
                    ID[i]=val['id'];             IDAll[i]=val['id'];
                    datatext[i]=val['text'];     datatextAll[i]=val['text'];
                    datatime[i]=val['time'];     datatimeAll[i]=val['time'];
                });
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
                console.log("请求对象XMLHttpRequest: "+XMLHttpRequest.status);
                console.log("XMLHttpRequest.readyState: "+XMLHttpRequest.readyState);
                console.log("错误类型textStatus: "+textStatus);
                console.log("异常对象errorThrown: "+errorThrown);
            }
        });
    }
    getAjaxData();

    function checkData() {
        if(lngarr.length===0){
            alert("该范围内没有数据");
            map.clearOverlays();
            classFlag = false;
            hasFlag = false;
        }
    }
    checkData();

    // 抽稀
    function selectRandom(num, from, to) {
        let arr = [];
        let json = {};
        let needNum;

        if (from - to >= 0) {
            console.log("111");
            return '起始值要小于末尾值'
        }

        if (to - from === to) {
            needNum = parseInt(to) + 1;
        } else {
            needNum = to - from;
        }
        if (num > needNum) {
            return
        } else {
            while (arr.length < num) {
                let ranNum = Math.ceil(Math.random() * needNum);
                if (!json[ranNum]) {
                    json[ranNum] = 1;
                    arr.push(ranNum);
                }
            }
            return arr;
        }
    }
    var randomSize = 120;
    indexArray = selectRandom(randomSize,0,lngarr.length);

    //从数组随机选取n个元素
    function getArrayByMultipleIndex(old, indexArray) {
        var result = [];
        for (var i = 0; i < old.length; i++) {
            if (indexArray.indexOf(i) !== -1){
                result.push(old[i]);
            }
        }
        return result;
    }

    if(lngarr.length > randomSize){
        lngarrCopy = lngarr;
        latarrCopy = latarr;
        behaviorCopy = behavior;
        lngarr = getArrayByMultipleIndex(lngarr, indexArray);
        latarr = getArrayByMultipleIndex(latarr, indexArray);
        behavior = getArrayByMultipleIndex(behavior, indexArray);
        ID = getArrayByMultipleIndex(ID, indexArray);
        datatime = getArrayByMultipleIndex(datatime, indexArray);
        datatext = getArrayByMultipleIndex(datatext, indexArray);
    }

    function removePoint(){
        console.log("removePoint:"+marker.length);
        for (var i = 0; i < marker.length -1; i++){
            map.removeOverlay(marker[i]);
        }
    }
    function addPoint(){
        console.log("addPoint");
        point=[];marker=[];
        for (var j = 0; j < lngarr.length; j++) {
            point[j] = new BMap.Point(lngarr[j], latarr[j]);
            var color = "yellow";
            marker[j] = new BMap.Marker(point[j], {
                icon: new BMap.Symbol(BMap_Symbol_SHAPE_CIRCLE, {
                    scale: 2,//图标缩放大小
                    fillColor: color,//填充颜色
                    strokeColor:color, //线颜色
                    strokeWeight: 4,
                })
            });//创建标注
            map.addOverlay(marker[j]);

            //Label
            var label = new BMap.Label("ID："+ID[j]+"<br>"+"text:"+datatext[j]+"<br>"+"time:"+datatime[j],{ offset: new BMap.Size(30, -10),});
            label.setStyle({

                backgroundColor:" transparent",
                border:"0",
                color:"#ffffff"
        });
            marker[j].setLabel(label); //添加标签/
            // / 初始化不显示
            label.setStyle({
                display: "none"
            });

            // 鼠标经过时
            marker[j].addEventListener("mouseover", function(e) {
                var label = this.getLabel();
                label.setStyle({
                    display: "block"
                });
            });
            // 鼠标离开时
            marker[j].addEventListener("mouseout", function(e) {
                var label = this.getLabel();
                label.setStyle({
                    display: "none"
                });
            });

        }

    }
    removePoint();
    addPoint();
}



//behaviorAnalyse
function behaviorAnalyse() {
    function checkData() {
        if(lngarr.length===0){
            alert("尚未选择数据区域！");
            classFlag = false;
            hasFlag = false;
            return;
        }
    }
    checkData();

    function getAjaxData() {
        var lon = "";
        var lat = "";
        var time = "";
        for(var i=0;i<latarr.length;i++){
            lon += lngarr[i] + ",";
            lat += latarr[i] + ",";
            time += datatime[i] + ",";
        }
        $.ajax({
            type: "post", //get post
            async: false, //true false
            //url:"http://345ea19162.wicp.vip:19326/test3.1?pl_lon="+slng+"&pl_lat="+slat+"&pr_lon="+elng+"&pr_lat="+elat+"&start="+st+"&end="+et,
            //url:"http://345ea19162.wicp.vip:19326/test5.1?pl_lon="+slng+"&pl_lat="+slat+"&pr_lon="+elng+"&pr_lat="+elat+"&start="+st+"&end="+et,
            //url:"http://345ea19162.wicp.vip:19326/test5.1?pl_lon=114.041298&pl_lat=30.841398&pr_lon=114.801239&pr_lat=30.3258&start=20140910200000&end=20140910210000",
            //url:"http://339kr74718.zicp.vip:37996/test5.1?pl_lon=114.041298&pl_lat=30.841398&pr_lon=114.801239&pr_lat=30.3258&start=20150910200000&end=20150910210000",
            // url:"http://339kr74718.zicp.vip:37996/blogActivity?pl_lon="+slng+"&pl_lat="+slat+"&pr_lon="+elng+"&pr_lat="+elat+"&start="+st+"&end="+et,
            url:"/demo/activity",
            data:{
                "lon":lon.substring(0,lon.length-1),
                "lat":lat.substring(0,lat.length-1),
                "time":time.substring(0,time.length-1),
            },
            dataType: "json", //由 JSON 改为 JSONP
            crossDomain: true,
            success: function(data) {
                $.each(data, function(i){
                    behavior[i]=data[i];
                });
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
                console.log("请求对象XMLHttpRequest: "+XMLHttpRequest.status);
                console.log("XMLHttpRequest.readyState: "+XMLHttpRequest.readyState);
                console.log("错误类型textStatus: "+textStatus);
                console.log("异常对象errorThrown: "+errorThrown);
            }
        });
    }
    if(!classFlag){
        if (!hasFlag){
            getAjaxData();
            hasFlag = true;
        }
        classFlag = true;
        function removePoint(){
            for (var i = 0; i < lngarr.length -1; i++){
                map.removeOverlay(lngarr[i],latarr[i]);
            }
        }
        function addPoint(){
            point=[];marker=[];
            for (var j = 0; j < lngarr.length; j++) {
                point[j] = new BMap.Point(lngarr[j], latarr[j]);
                var color = "yellow";
                if(behavior[j] === "餐饮"){
                    color="#0e932e"
                }
                if(behavior[j] === "娱乐"){
                    color="#e16531"
                }
                if(behavior[j] === "生活服务"){
                    color="#db639b"
                }
                if(behavior[j] === "出行住宿"){
                    color="#594d9c"
                }
                if(behavior[j] === "户外"){
                    color="#d81e06"
                }
                if(behavior[j] === "校园"){
                    color="#ffffff"
                }

                marker[j] = new BMap.Marker(point[j], {
                    icon: new BMap.Symbol(BMap_Symbol_SHAPE_CIRCLE, {
                        scale: 2,//图标缩放大小
                        fillColor: color,//填充颜色
                        strokeColor:color, //线颜色
                        strokeWeight: 4,
                    })
                });//创建标注
                map.addOverlay(marker[j]);

                //Label
                var label = new BMap.Label("ID："+ID[j]+"<br>"+"text:"+datatext[j]+"<br>"+"time:"+datatime[j],{ offset: new BMap.Size(30, -10),});
                label.setStyle({
                    backgroundColor:" transparent",
                    border:"0",
                    color:"#ffffff"
                });
                marker[j].setLabel(label); //添加标签/
                // / 初始化不显示
                label.setStyle({
                    display: "none"
                });

                // 鼠标经过时
                marker[j].addEventListener("mouseover", function(e) {
                    var label = this.getLabel();
                    label.setStyle({
                        display: "block"
                    });
                });
                // 鼠标离开时
                marker[j].addEventListener("mouseout", function(e) {
                    var label = this.getLabel();
                    label.setStyle({
                        display: "none"
                    });
                });


            }
        }
        removePoint();
        addPoint();
    }
    else{
        classFlag = false;
        function removePoint(){
            console.log("removePoint:"+marker.length);
            for (var i = 0; i < marker.length -1; i++){
                map.removeOverlay(marker[i]);
            }
        }
        function addPoint(){
            console.log("addPoint");
            point=[];marker=[];
            for (var j = 0; j < lngarr.length; j++) {
                point[j] = new BMap.Point(lngarr[j], latarr[j]);
                var color = "yellow";
                marker[j] = new BMap.Marker(point[j], {
                    icon: new BMap.Symbol(BMap_Symbol_SHAPE_CIRCLE, {
                        scale: 2,//图标缩放大小
                        fillColor: color,//填充颜色
                        strokeColor:color, //线颜色
                        strokeWeight: 4,
                    })
                });//创建标注
                map.addOverlay(marker[j]);

                //Label
                var label = new BMap.Label("ID："+ID[j]+"<br>"+"text:"+datatext[j]+"<br>"+"time:"+datatime[j],{ offset: new BMap.Size(30, -10),});
                label.setStyle({

                    backgroundColor:" transparent",
                    border:"0",
                    color:"#ffffff"
                });
                marker[j].setLabel(label); //添加标签/
                // / 初始化不显示
                label.setStyle({
                    display: "none"
                });

                // 鼠标经过时
                marker[j].addEventListener("mouseover", function(e) {
                    var label = this.getLabel();
                    label.setStyle({
                        display: "block"
                    });
                });
                // 鼠标离开时
                marker[j].addEventListener("mouseout", function(e) {
                    var label = this.getLabel();
                    label.setStyle({
                        display: "none"
                    });
                });

            }

        }
        removePoint();
        addPoint();
    }
}

//导出表格
function tableToExcel(){
//列标题，逗号隔开，每一个逗号就是隔开一个单元格
    let str = `ID,lng,lat,time,text\n`;
//增加\t为了不让表格显示科学计数法或者其他格式
    for(let i = 0 ; i < lngarrAll.length ; i++ ){
        str+=`${IDAll[i] + '\t'},`;
        str+=`${lngarrAll[i] + '\t'},`;
        str+=`${latarrAll[i] + '\t'},`;
        str+=`${datatimeAll[i] + '\t'},`;
        str+=`${datatextAll[i] + '\t'},`;
        str+='\n';
    }
//encodeURIComponent解决中文乱码
    let uri = 'data:text/csv;charset=utf-8,\ufeff' + encodeURIComponent(str);
//通过创建a标签实现
    var link = document.createElement("a");
    link.href = uri;
//对下载的文件命名
    link.download = "data.csv";
    //link.download = slng+","+slat+"to"+elng+","+elat+"&"+st+"to"+et+".csv";
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}