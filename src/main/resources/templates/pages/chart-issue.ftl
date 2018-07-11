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
                                <div style="width:75%;">
                                    <canvas id="canvas"></canvas>
                                </div>
                                <br>
                                <br>
                            <#--<button id="randomizeData">Randomize Data</button>
                            <button id="addDataset">Add Dataset</button>
                            <button id="removeDataset">Remove Dataset</button>-->
                                <a href="javascript:void(0);" id="addData" class="btn btn-primary">增加一天</a>
                                <a href="javascript:void(0);" id="removeData" class="btn btn-info">减少一天</a>
                                <a href="javascript:void(0);" id="nextMonth" class="btn btn-success">下一月</a>
                                <a href="javascript:void(0);" id="upMonth" class="btn btn-purple">上一月</a>
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

    /*var randomScalingFactor = function () {
        return Math.round(Math.random() * 10);
    };*/


    var config = {
        type: 'line',
        data: {
            // labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
            labels: ['1号', '2号', '3号', '4号', '5号', '6号', '7号'],
            // labels:[1,2,3,4,5,6,7],
            datasets: [{
                label: '文章发布量',
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
                text: year+"年"+ month +'月文章发布量'
            },
            tooltips: {
                mode: 'index',
                intersect: false,
            },
            hover: {
                mode: 'nearest',
                intersect: true
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
                        labelString: 'Value'
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


    window.onload = function () {
        var ctx = document.getElementById('canvas').getContext('2d');
        window.myLine = new Chart(ctx, config);
    };

    document.getElementById('upMonth').addEventListener('click', function () {
        if (month > 1) {
            month = month - 1
            location = "/oa/chart/issue.html?month=" + month
        }

    });

    document.getElementById('nextMonth').addEventListener('click', function () {
        if (month < 12) {
            month = month + 1
            location = "/oa/chart/issue.html?month=" + month
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

        // var year = 2018; //表示需要查找的年份
        var curMonthDays = new Date(year, month, 0).getDate(); //0表示3月的第0天，上月的最后一天,月份从0开始记数
        console.log('查找的月份共有' + curMonthDays + "天");

        var day = DAY[config.data.labels.length % DAY.length];
        day = day.split("号");

        if (parseInt(day[0]) <= parseInt(curMonthDays)) {

            config.data.labels.push(day[0]+"号");
            $.post(
                    "/oa/chart/add/day/issue",
                    {
                        day: day[0],
                        month: month,
                        max:max,
                        year:year
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
</script>

<#include "../common/footjs.ftl">
</body>
</html>