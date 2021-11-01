/**
 * @description：请描述功能信息
 * @author：yyt
 * @date：2021-11-01 18:11:55
 **/
require(
    ['/custom/GlobalConfig.js'],
    function () {
        requirejs(
            ['jquery', 'bootstrap', 'custom', 'bootstrap_table', 'bootstrap_table_CN'],
            function ($) {
                // 此处定义自己的脚本
                // 操作日志表格配置
                $('#tb_log').bootstrapTable({
                    // 请求后台的 URL 路径
                    url: '/LogModule/queryDynamically',
                    // 请求方式
                    method: 'post',
                    // 表的 class属性
                    classes: "table table-bordered table-hover table-striped",
                    // 列的 class 属性
                    theadClasses: "thead-dark",
                    // 工具按钮的容器，例如：#toolbar，.toolbar，或者是DOM 结点
                    //toolbar: '#toolbar',
                    // 默认缓存 ajax请求，设为 false 则禁用缓存
                    cache: false,
                    // 默认为false隐藏刷新按钮，设为true显示
                    showRefresh: false,
                    // 默认为 false，表格的底部工具栏不会显示分页条
                    pagination: true,
                    // 设置在哪进行分页，默认"client"，可选"server"，如果设置 "server"，则必须设置url或者重写ajax方法
                    sidePagination: "server",
                    showExtendedPagination: true,
                    //showExport: true,
                    // 默认 true，设为 false禁用所有的行排列
                    sortable: true,
                    // 默认的排序方式为"asc（升序）"，也可以设为"desc（降序）"。
                    sortOrder: "asc",
                    // 前提：pagination设为true，启用了分页功能。默认第1页，用于设置初始的页数
                    pageNumber: 1,
                    // 前提：pagination设为true，启用了分页功能。默认每页显示10条记录，用于设置每页初始显示的条数
                    pageSize: 10,
                    //  默认为[10, 25, 50, 100]，即可以选择"每页显示10/25/50/100条记录"，用于设置选择每页显示的条数
                    pageList: [10, 25, 50, 100],
                    // 最小显示的列数
                    minimumCountColumns: 10,
                    // 表明每行唯一的标识符
                    uniqueId: "uuid",
                    // 默认为true显示表头，设为false隐藏
                    showHeader: true,
                    // 开启精确搜索
                    strictSearch: true,
                    // 默认为false不显示所有的列，设为true显示
                    showColumns: false,
                    // 默认为false 隐藏视图切换按钮，设为true显示
                    showToggle: false,
                    // 显示全屏按钮
                    showFullscreen: false,
                    // 默认false，设为 true显示card view（卡片视图）
                    cardView: false,
                    clickToSelect: true,
                    // 默认false，设为 true显示detail view（细节视图）
                    detailView: true,
                    detailViewIcon: false,
                    detailViewByClick: true,
                    detailFormatter: showOperateDetail,
                    // 当请求数据时，可以通过修改 queryParams向服务器发送其余的参数。
                    queryParams: function (params) {
                        // 重写参数，方便后期增加查询条件
                        var param = {
                            size: params.limit,
                            page: (params.offset / params.limit),
                            // 页面搜索框的条件
                            username: $("#username").val(),
                            operateDesc: $("#operateDesc").val(),
                            operateType: $("#operateType").val()
                        };
                        return param;
                    },
                    // 默认空数组，在JS里面定义，field即data-field，title就是每列表头名等等。
                    columns: [
                        {
                            title: '编号',
                            formatter:function (value, row, index) {
                                var pageSize = $('#tb_log').bootstrapTable('getOptions').pageSize;
                                var pageNumber = $('#tb_log').bootstrapTable('getOptions').pageNumber;
                                return pageSize * (pageNumber - 1) + index + 1;
                            },
                            width: 60
                        },{
                            field: 'username',
                            title: '账号名称'
                        },{
                            field: 'operateTime',
                            title: '操作时间'
                        },{
                            field: 'operateType',
                            title: '操作类型',
                            width: 60
                        },{
                            field: 'operateDesc',
                            title: '操作说明'
                        },{
                            field: 'operateDetail',
                            title: '操作详情',
                            visible: false
                        },{
                            field: 'osType',
                            title: '操作系统类型'
                        },{
                            field: 'browserType',
                            title: '浏览器类型'
                        },{
                            field: 'ipAddr',
                            title: 'IP 地址'
                        }
                    ]
                });

                // 多条件查询按钮
                $('#searchLogBtn').click(function () {
                    // 执行刷新就是带参数查询
                    $('#tb_log').bootstrapTable('refresh');
                });

                // 显示操作详情
                function showOperateDetail(index, row) {
                    var html = []
                    $.each(row, function (key, value) {
                        html.push('<p><b>' + key + ':</b> ' + value + '</p>')
                    })
                    return html.join('')
                }
            }
        )
    }
);