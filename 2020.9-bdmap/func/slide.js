/* slider*/
var Months = ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"];
//var Months = ["9月", "10月", "11月", "12月"];
$(function(){createDemos();});
function createDemos(){
    var	date = $("<div id='date' />").appendTo($("#dateSlider"));//渲染日期组件
    var dateSilderObj=date.dateRangeSlider({
        arrows:true,//是否显示左右箭头
        // enabled:true,
        range:{
            min: {days: 6},
            max: {days: 6}
        },
        bounds: {min: new Date(2014, 8, 1), max: new Date(2014, 11, 31)},//最大 最少日期
        defaultValues: {min: new Date(2014, 9, 1), max: new Date(2014, 9, 7)}//默认选中区域
        ,scales:[{
            first: function(value){return value; },
            end: function(value) {return value; },
            next: function(val){
                var next = new Date(val);
                return new Date(next.setMonth(next.getMonth() + 1));
            },
            label: function(val){
                return Months[val.getMonth()];
            },
            format: function(tickContainer, tickStart, tickEnd){
                tickContainer.addClass("myCustomClass");
            }
        }]
    });//日期控件

    //重新赋值（整个时间轴）
    //dateSilderObj.dateRangeSlider("bounds", new Date(2015, 8, 1), new Date(2014, 7, 31, 12, 59, 59));
    //重新赋值（选中区域）
    //dateSilderObj.dateRangeSlider("values", new Date(2015, 4, 23,0,0,0), new Date(2015, 4, 25,0,0,0));

    //拖动完毕后的事件
    dateSilderObj.bind("valuesChanged", function(e, data){
        var val=data.values;
        var sn=val.min.getFullYear();var sy=val.min.getMonth()+1;var sr=val.min.getDate();
        console.log('sn : '+ sn);
        var en=val.max.getFullYear();var ey=val.max.getMonth()+1;var er=val.max.getDate();
        console.log('en : '+ en);
        console.log('ey : '+ ey);
        console.log('er : '+ er);
        if(sy<10){sy='0'+sy} if(sr<10){sr='0'+sr}
        console.log('加0后：');
        console.log('sy : '+ sy);
        console.log('sr : '+ sr);
        if(ey<10){ey='0'+ey} if(er<10){er='0'+er}
        console.log('加0后：');
        console.log('ey : '+ ey);
        console.log('er : '+ er);

        stime=sn+sy+sr+"000000";etime=en+ey+er+"235959";
        st=stime; et=etime;
        console.log('stime : '+ st);
        console.log('etime : '+ et);
        console.log("s:"+st+" "+"e:"+et);
    });  //slider valueChanged


} //function creatDemos