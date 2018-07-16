<!DOCTYPE html>
<html lang="en">
<!-- header -->
<#include "../common/header.ftl">

<style>
    canvas {
        -moz-user-select: none;
        -webkit-user-select: none;
        -ms-user-select: none;
    }
</style>
<body>
<!-- 导航 -->

<#include "../common/nav.ftl">

<!-- 主体内容 -->
<div class="main-container container-fluid">
    <!-- 页面内容 -->
    <div class="page-container">
        <!-- 侧边栏导航 -->
       <#include "../common/sider.ftl">
        <!-- /侧边栏导航 -->
        <!-- Page Content -->
        <div class="page-content">
            <!-- 页面面包屑 -->
            <div class="page-breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="fa fa-home"></i>
                        <a href="#">主页</a>
                    </li>
                    <li class="active">${pageTitle}</li>
                </ul>
            </div>
            <!-- /页面面包屑 -->
            <!-- Page Header -->
            <div class="page-header position-relative">
                <div class="header-title">
                    <h1>
                    ${pageTitle}
                    </h1>
                </div>
                <!--Header Buttons-->
                <div class="header-buttons">
                    <a class="sidebar-toggler" href="#">
                        <i class="fa fa-arrows-h"></i>
                    </a>
                    <a class="refresh" id="refresh-toggler" href="">
                        <i class="glyphicon glyphicon-refresh"></i>
                    </a>
                    <a class="fullscreen" id="fullscreen-toggler" href="#">
                        <i class="glyphicon glyphicon-fullscreen"></i>
                    </a>
                </div>
                <!--Header Buttons End-->
            </div>
            <!-- /Page Header -->
            <!-- Page Body -->
            <div class="page-body">
                <div class="row">
                    <div class="col-lg-12 col-sm-6 col-xs-12">
                        <div class="widget flat radius-bordered">
                            <div class="widget-header bg-blue">
                                <span class="widget-caption">${pageTitle}</span>
                            </div>
                            <div class="widget-body">
                                <div style="margin-bottom: 50px">
                                    <div style="width: 30%;display: inline-block">
                                        <canvas id="chart-area"></canvas>
                                    </div>
                                    <div style="width: 30%;display: inline-block">
                                        <canvas id="chart-area1"></canvas>
                                    </div>
                                    <div style="width: 30%;display: inline-block">
                                        <canvas id="chart-area2"></canvas>
                                    </div>
                                </div>
                                <div>
                                    <div style="width:60%; display: flex;align-items: center;justify-content: center;margin-left: 22%;">
                                        <canvas id="canvas"></canvas>
                                    </div>
                                    <#--<div style="width:40%;display: inline-block;margin-left: 50px;margin-top: 65px">
                                        <div class="table-scrollable">
                                            <table class="table table-bordered table-hover">
                                                <thead>
                                                <tr>
                                                    <th>ip</th>
                                                    <th>时间</th>
                                                    <th>浏览器</th>
                                                    <th>地区</th>
                                                    <th>访问工具</th>
                                                </tr>
                                                </thead>
                                                <tbody id="pageData">
                                                      <#list pageContent.getPageContent() as ipData>
                                                          <tr id="${ipData.getId()}">
                                                              <td>${ipData.getIp()}</td>
                                                              <td>${ipData.getTime()}</td>
                                                              <td>${ipData.getBrowser()}</td>
                                                              <td>${ipData.getAddress()}</td>
                                                              <td>${ipData.getUtil()}</td>
                                                          </tr>
                                                      </#list>
                                                    </div>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="margin-top-30 text-align-right">
                                            <div class="next">
                                                <ul class="pagination">
                                                    <li><a href="javascript:void(0);" onclick="(1,${size})">首页</a></li>
                                            <#if currentPage lte 1>
                                                <li class="disabled"><a>上一页</a></li>
                                            <#else>
                                                <li><a href="javascript:void(0);" onclick="page(${currentPage-1},${size})">上一页</a></li>

                                            </#if>

                                               <#if pageContent.getTotalPages() lte 5>
                                                   <#list 1..pageContent.getTotalPages() as index>
                                                       <#if currentPage == index >
                         <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                        <li><a href="javascript:void(0);" onclick="page(${index},${size})">${index}</a></li>
                                                       </#if>
                                                   </#list>
                                               <#elseif currentPage lte 3>
                                                   <#list 1..5 as index>
                                                       <#if currentPage == index >
                         <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                        <li><a href="javascript:void(0);" onclick="page(${index},${size})">${index}</a></li>
                                                       </#if>
                                                   </#list>
                                               <#elseif  currentPage gt 3 && currentPage lte pageContent.getTotalPages()-2>
                                                   <#list currentPage-2..currentPage+2 as index>
                                                       <#if currentPage == index >
                                <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                                <li><a href="javascript:void(0);" onclick="page(${index},${size})">${index}</a></li>
                                                       </#if>
                                                   </#list>
                                               <#elseif  currentPage gt pageContent.getTotalPages()-2>
                                                   <#list pageContent.getTotalPages()-4..pageContent.getTotalPages() as index>
                                                       <#if currentPage == index >
                                <li class="active"><a href="#">${index}</a></li>
                                                       <#else>
                                <li><a href="javascript:void(0);" onclick="page(${index},${size})">${index}</a></li>
                                                       </#if>
                                                   </#list>
                                               </#if>


                                                <#if currentPage gte pageContent.getTotalPages()>
                                                    <li class="disabled"><a>下一页</a></li>
                                                <#else>
                                                    <li><a href="javascript:void(0);" onclick="page(${currentPage+1},${size})">下一页</a></li>
                                                </#if>
                                                    <li>
                                                        <a href="javascript:void(0);" onclick="page(${pageContent.getTotalPages()},${size})">尾页</a>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>-->
                                    <br>
                                    <br>
                                <#--<button id="randomizeData">Randomize Data</button>
                                <button id="addDataset">Add Dataset</button>
                                <button id="removeDataset">Remove Dataset</button>-->
                                    <div style="display: flex;align-items: center;justify-content: center;">
                                        <a href="javascript:void(0);" id="addData" class="btn btn-primary">增加一天</a>
                                        <a href="javascript:void(0);" id="removeData" class="btn btn-info">减少一天</a>
                                        <a href="javascript:void(0);" id="nextMonth" class="btn btn-success">下一月</a>
                                        <a href="javascript:void(0);" id="upMonth" class="btn btn-purple">上一月</a>
                                    </div>

                                </div>

                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <!-- /Page Body -->
        </div>
    </div>

</div>

<script src="/assets/js/charts/chartjs/Chart.bundle.js"></script>
<script src="/assets/js/charts/chartjs/utils.js"></script>
<script>
    // var MONTHS = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    var DAY = ['1号', '2号', '3号', '4号', '5号', '6号', '7号', '8号', '9号', '10号', '11号', '12号', '13号', '14号', '15号', '16号', '17号', '18号', '19号',
        '20号', '21号', '22号', '23号', '24号', '25号', '26号', '27号', '28号', '29号', '30号', '31号', '32号'];

    var year = ${year}

    var month = ${month}

    var max = ${max}

    var nowDay = ${nowDay}

    /*var randomScalingFactor = function () {
        return Math.round(Math.random() * 10);
    };*/

    var config = {
        type: 'line',
        data: {
            // labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
            // labels: ['1号', '2号', '3号', '4号', '5号', '6号', '7号'],
            labels: ${dayList},
            // labels:[1,2,3,4,5,6,7],
            datasets: [{
                label: '访问量',
                backgroundColor: window.chartColors.red,
                borderColor: window.chartColors.red,
                data: ${list},
                fill: false,
            }]
        },
        options: {
            responsive: true,
            title: {
                display: true,
                text: year + "年" + month + '月访问量'
            },
            tooltips: {
                mode: 'index',
                intersect: false,
            },
            hover: {
                mode: 'nearest',
                intersect: true
            },
            onClick: function(e){
                var element = this.getElementAtEvent(e);
                if(element.length!=0){
                    var clickDay = (element[0]._index)+1;
                    // page(1,5,clickDay);
                    address(clickDay);
                    browser(clickDay);
                    util(clickDay);
                }

            },
            scales: {
                xAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'Day'
                    }
                }],
                yAxes: [{
                    display: true,
                    scaleLabel: {
                        display: true,
                        labelString: 'ip访问量'
                    },
                    ticks: {
                        min: 0,
                        max: max,

                        // forces step size to be 5 units
                        stepSize: 500
                    }
                }]
            }
        }
    };
    var addressConfig = {
        type: 'pie',
        data: {
            datasets: [{
                data: ${addressCount},
                backgroundColor: [
                    window.chartColors.red,
                    window.chartColors.orange
                ],
                label: '地址访问'
            }],
            labels: ${addressKey}
        },
        options: {
            responsive: true,
            title: {
                display: true,
                text: year + "年" + month + '月'+nowDay+'地区访问量'
            }
        }
    };
    var browserConfig = {
        type: 'pie',
        data: {
            datasets: [{
                data: ${browserCount},
                backgroundColor: [
                    window.chartColors.red,
                    window.chartColors.orange,
                    window.chartColors.yellow,
                    window.chartColors.green,
                    window.chartColors.blue,
                ],
                label: 'Dataset 1'
            }],
            labels: ${browserKey}
        },
        options: {
            responsive: true,
            title: {
                display: true,
                text: year + "年" + month + '月'+nowDay+'浏览器访问量'
            }
        }
    };
    var utilConfig = {
        type: 'pie',
        data: {
            datasets: [{
                data: ${utilCount},
                backgroundColor: [
                    window.chartColors.red,
                    window.chartColors.orange,
                    window.chartColors.yellow
                ],
                label: 'Dataset 1'
            }],
            labels: ${utilKey}
        },
        options: {
            responsive: true,
            title: {
                display: true,
                text: year + "年" + month + '月'+nowDay+'工具访问量'
            }
        }
    };


    window.onload = function () {
        var ctx = document.getElementById('canvas').getContext('2d');
        window.myLine = new Chart(ctx, config);
        var ctx1 = document.getElementById('chart-area').getContext('2d');
        window.myPie = new Chart(ctx1, addressConfig);
        var ctx2 = document.getElementById('chart-area1').getContext('2d');
        window.myBrowser = new Chart(ctx2, browserConfig);
        var ctx3 = document.getElementById('chart-area2').getContext('2d');
        window.myUtil = new Chart(ctx3, utilConfig);
    };

    document.getElementById('upMonth').addEventListener('click', function () {
        if (month > 1) {
            month = month - 1
            location = "/oa/chart/ip.html?month=" + month
        }
    });

    document.getElementById('nextMonth').addEventListener('click', function () {
        if (month < 12) {
            month = month + 1
            location = "/oa/chart/ip.html?month=" + month
        }

    });

    /*document.getElementById('randomizeData').addEventListener('click', function () {
        config.data.datasets.forEach(function (dataset) {
            dataset.data = dataset.data.map(function () {
                return randomScalingFactor();
            });
        });

        window.myLine.update();
    });*/

    var colorNames = Object.keys(window.chartColors);
    /*document.getElementById('addDataset').addEventListener('click', function () {
        var colorName = colorNames[config.data.datasets.length % colorNames.length];
        var newColor = window.chartColors[colorName];
        var newDataset = {
            label: 'Dataset ' + config.data.datasets.length,
            backgroundColor: newColor,
            borderColor: newColor,
            data: [],
            fill: false
        };

        for (var index = 0; index < config.data.labels.length; ++index) {
            newDataset.data.push(randomScalingFactor());
        }

        config.data.datasets.push(newDataset);
        window.myLine.update();
    });*/

    document.getElementById('addData').addEventListener('click', function () {

        // var year = year; //表示需要查找的年份
        var curMonthDays = new Date(year, month, 0).getDate(); //0表示3月的第0天，上月的最后一天,月份从0开始记数
        // console.log('查找的月份共有' + curMonthDays + "天");

        var day = DAY[config.data.labels.length % DAY.length];
        day = day.split("号");

        if (parseInt(day[0]) <= parseInt(curMonthDays)) {

            config.data.labels.push(day[0] + "号");
            $.post(
                    "/oa/chart/add/day",
                    {
                        day: day[0],
                        month: month,
                        max: max,
                        year: year
                    },
                    function (data) {
                        if (data.code == 0) {
                            if (config.data.datasets.length > 0) {
                                config.data.datasets.forEach(function (dataset) {
                                    dataset.data.push(data.data.dayValue);
                                });
                            }
                            max = data.data.max;
                            config.options.scales.yAxes[0].ticks.max = max
                            window.myLine.update();
                        }
                        if (data.code > 0) {
                            layer.msg(data.message);
                        }
                    }
            )
        }


    });

    /*document.getElementById('removeDataset').addEventListener('click', function () {
        config.data.datasets.splice(0, 1);
        window.myLine.update();
    });*/

    document.getElementById('removeData').addEventListener('click', function () {
        config.data.labels.splice(-1, 1); // remove the label first

        config.data.datasets.forEach(function (dataset) {
            dataset.data.pop();
        });

        window.myLine.update();
    });


    function page(page,size,day){
        var content = $("#pageData").find("tr")
        if (page==0){
            page = 1
        }
        $.post(
                "/oa/chart/ip/list",
                {
                    day: day,
                    month: month,
                    max: max,
                    year: year,
                    page:page,
                    size:size
                },
                function (data) {
                    if (data.code == 0) {
                        for(var j=0;j<content.length;j++){
                            content.eq(j).remove()
                        }
                        for (var i=0;i<data.data.ipTime.length;i++){
                            var dataLine ='<tr>'+
                                                '<td>'+data.data.ipTime[i].ip+'</td>'+
                                                '<td>'+data.data.ipTime[i].time+'</td>'+
                                                '<td>'+data.data.ipTime[i].browser+'</td>'+
                                                '<td>'+data.data.ipTime[i].address+'</td>'+
                                                '<td>'+data.data.ipTime[i].util+'</td>'+
                                            '</tr>'
                            $("#pageData").append(dataLine);
                        }

                    }
                    if (data.code > 0) {
                        layer.msg(data.message);
                    }
                }
        )
    }

    function address(day) {
        var newDataset = {
            label: 'Dataset ',
            backgroundColor: [],
            data:[]
        };
        $.post(
                "/oa/chart/address",
                {
                    day: day,
                    month: month,
                    year: year
                },
                function (data) {
                    if (data.code == 0) {
                        labelLength = addressConfig.data.labels.length;
                        for (var p=0;p<labelLength;p++){
                            addressConfig.data.labels.splice(0,1);
                        }
                        addressConfig.data.datasets.splice(0,1);
                        for (var i=0;i<data.data.addressCount.length;++i){
                            newDataset.data.push(data.data.addressCount[i]);
                            var colorName = colorNames[i];
                            var newColor = window.chartColors[colorName];
                            newDataset.backgroundColor.push(newColor);
                        }
                        for (var j=0;j<data.data.addressKey.length;++j){
                            addressConfig.data.labels.push(data.data.addressKey[j])
                        }
                        addressConfig.options.title.text = year + "年" + month + '月'+data.data.nowDay+'地区访问量'
                        addressConfig.data.datasets.push(newDataset);
                        window.myPie.update()
                    }
                    if (data.code > 0) {
                        layer.msg(data.message);
                    }
                }
        )
    }

    function browser(day) {
        var newDataset = {
            label: 'Dataset ',
            backgroundColor: [
                window.chartColors.red,
                window.chartColors.orange,
                window.chartColors.yellow
            ],
            data:[]
        };
        $.post(
                "/oa/chart/browser",
                {
                    day: day,
                    month: month,
                    year: year
                },
                function (data) {
                    if (data.code == 0) {
                        labelLength = browserConfig.data.labels.length;
                        for (var p=0;p<labelLength;p++){
                            browserConfig.data.labels.splice(0,1);
                        }
                        browserConfig.data.datasets.splice(0,1);
                        for (var i=0;i<data.data.browserCount.length;++i){
                            newDataset.data.push(data.data.browserCount[i]);
                            var colorName = colorNames[i];
                            var newColor = window.chartColors[colorName];
                            newDataset.backgroundColor.push(newColor);
                        }
                        for (var j=0;j<data.data.browserKey.length;++j){
                            browserConfig.data.labels.push(data.data.browserKey[j])
                        }
                        browserConfig.options.title.text = year + "年" + month + '月'+data.data.nowDay+'浏览器访问量'
                        browserConfig.data.datasets.push(newDataset);
                        window.myBrowser.update()
                    }
                    if (data.code > 0) {
                        layer.msg(data.message);
                    }
                }
        )
    }

    function util(day) {
        var newDataset = {
            label: 'Dataset ',
            backgroundColor: [
                window.chartColors.red,
                window.chartColors.orange,
                window.chartColors.yellow
            ],
            data:[]
        };


        labelLength = utilConfig.data.labels.length;
        for (var p=0;p<labelLength;p++){
            utilConfig.data.labels.splice(0,1);
        }



        $.post(
                "/oa/chart/util",
                {
                    day: day,
                    month: month,
                    year: year
                },
                function (data) {
                    if (data.code == 0) {
                        utilConfig.data.datasets.splice(0,1);
                        for (var i=0;i<data.data.utilCount.length;++i){
                            newDataset.data.push(data.data.utilCount[i]);
                            var colorName = colorNames[i];
                            var newColor = window.chartColors[colorName];
                            newDataset.backgroundColor.push(newColor);
                        }
                        for (var j=0;j<data.data.utilKey.length;++j){
                            utilConfig.data.labels.push(data.data.utilKey[j])
                        }
                        utilConfig.options.title.text = year + "年" + month + '月'+data.data.nowDay+'工具访问量'
                        utilConfig.data.datasets.push(newDataset);
                        window.myUtil.update()
                    }
                    if (data.code > 0) {
                        layer.msg(data.message);
                    }
                }
        )
    }


</script>

<#include "../common/footjs.ftl">
</body>
</html>