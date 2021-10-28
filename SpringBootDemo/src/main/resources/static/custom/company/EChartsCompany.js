/**
 * @description：请描述功能信息
 * @author：yyt
 * @date：2021-10-27 15:10:57
 **/
require(
    ['/custom/GlobalConfig.js'],
    function () {
        requirejs(
            ['jquery', 'bootstrap', 'custom'],
            function ($) {
                // 此处定义自己的脚本
                // 初始化
                var myChartLine = echarts.init(document.getElementById('basicLine'));
                var myChartBar = echarts.init(document.getElementById('basicBar'));
                var myChartArea = echarts.init(document.getElementById('basicArea'));
                var myChartPie = echarts.init(document.getElementById('roundedPie'));
                // 定义每个图表的坐标轴数组
                var LineX = [];
                var LineY = [];
                var BarX = [];
                var BarY = [];
                var AreaX = [];
                var AreaY = [];
                var Pie = [];
                // ECharts 默认有提供了一个简单的加载动画。
                // 只需要调用 showLoading 方法显示。数据加载完成后再调用 hideLoading 方法隐藏加载动画。
                myChartLine.showLoading();
                myChartBar.showLoading();
                myChartArea.showLoading();
                myChartPie.showLoading();
                // 加载数据
                $.ajax({
                    type: 'POST',
                    async: true,
                    url: '/CompanyModule/echarts',
                    dataType: 'json',
                    success: function (result) {
                        // result 为返回的 json数据
                        if (result){
                            var listPerson = result["listPerson"];
                            var listOutput = result["listOutput"];
                            // 组装数据：折线图和柱状图
                            for (var i = 0, num = listPerson.length; i <num; i++) {
                                LineX.push(listPerson[i].name);
                                LineY.push(listPerson[i].value);
                                BarX.push(listPerson[i].name);
                                BarY.push(listPerson[i].value);
                            }
                            // 组装数据：面积图和饼状图
                            for (var j = 0, num = listOutput.length; j < num; j++){
                                AreaX.push(listOutput[j].name);
                                AreaY.push(listOutput[j].value);
                                Pie.push(listOutput[j]);
                            }
                            // 隐藏 loading 动画
                            myChartLine.hideLoading();
                            myChartBar.hideLoading();
                            myChartArea.hideLoading();
                            myChartPie.hideLoading();
                            // 通过 setOption 填入数据和配置项
                            myChartLine.setOption({
                                title: {
                                    text: '各公司员工人数折线图'
                                },
                                tooltip: {
                                    // {a}（系列名称），{b}（类目值），{c}（数值）, {d}（无）
                                    formatter: '公司: {b}<br />人数: {c}'
                                },
                                legend: {
                                    // 图例的数据数组
                                    data: [{
                                        name: '员工数量',
                                        textStyle: {
                                            color: 'red'
                                        }
                                    }]
                                },
                                xAxis: {
                                    data: LineX,
                                    // 坐标轴刻度标签
                                    axisLabel: {
                                        show: true,
                                        // 设置成 0 强制显示所有标签
                                        interval: 0,
                                        // 刻度标签旋转的角度
                                        rotate: 40
                                    }
                                },
                                yAxis: {},
                                series: [
                                    {
                                        name: '员工人数',
                                        type: 'line',
                                        data: LineY
                                    }
                                ]
                            });

                            myChartBar.setOption({
                                title: {
                                    text: '各公司员工人数柱状图'
                                },
                                tooltip: {
                                    formatter: '公司: {b}<br />人数: {c}'
                                },
                                legend: {
                                    data: [{
                                        name: '员工数量',
                                        textStyle: {
                                            color: 'red'
                                        }
                                    }]
                                },
                                xAxis: {
                                    data: BarX
                                },
                                yAxis: {},
                                series: [
                                    {
                                        name: '员工数量',
                                        type: 'bar',
                                        data: BarY
                                    }
                                ]
                            });

                            myChartArea.setOption({
                                title: {
                                    text: '各公司员营业收入面积图'
                                },
                                tooltip: {
                                    formatter: '公司: {b}<br />收入: {c} 万元'
                                },
                                legend: {
                                    data: [{
                                        name: '营业收入',
                                        // 强制设置图形为圆
                                        //icon: 'circle',
                                        // 设置文本为红色
                                        textStyle: {
                                            color: 'red'
                                        }
                                    }]
                                },
                                xAxis: {
                                    data: AreaX,
                                    // 坐标轴两边留白策略
                                    boundaryGap: true
                                },
                                yAxis: {},
                                series: [
                                    {
                                        name: '营业收入',
                                        type: 'line',
                                        data: AreaY,
                                        // 区域填充样式。设置后显示成区域面积图
                                        areaStyle: {
                                            // 支持使用 rgb，rgba，#fff等方式
                                            color: null,
                                            // 图形阴影的模糊大小
                                            shadowBlur: 0
                                        }
                                    }
                                ]
                            });

                            myChartPie.setOption({
                                title: {
                                    text: '各公司营业收入扇形图'
                                },
                                tooltip: {
                                    trigger: "item",
                                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                                },
                                series: [
                                    {
                                        name: '营业收入',
                                        type: 'pie',
                                        // 是否启用图例 hover 时的联动高亮
                                        legendHoverLink: true,
                                        // 可选择两种模式：'radius' 与 'area'
                                        roseType: "area",
                                        // 饼图的中心（圆心）坐标，数组的第一项是横坐标，第二项是纵坐标
                                        center: ['50%', '50%'],
                                        // 饼图的半径
                                        radius: [0, "75%"],
                                        data: Pie
                                    }
                                ]
                            });
                        }
                    }
                });
            }
        )
    }
);