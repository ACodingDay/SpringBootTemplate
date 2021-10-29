/**
 * @description：请描述功能信息
 * @author：yyt
 * @date：2021-10-20 02:10:33
 **/
require(
    ['/custom/GlobalConfig.js'],
    function () {
        requirejs(
            ['jquery', 'bootstrap', 'bootstrap_table', 'bootstrap_table_CN', 'custom','layer'],
            function ($) {
                // 此处定义自己的脚本
                // 绑定操作事件按钮
                window.operateEvents = {
                    'click .like': function (e, value, row, index) {
                        alert('你点击了喜欢按钮, row: ' + JSON.stringify(row))
                    },
                    'click .modify': function (e, value, row, index) {
                        alert('你点击了修改按钮, row: ' + JSON.stringify(row))
                    },
                    'click .delete': function (e, value, row, index) {
                        $('#tb_company').bootstrapTable('remove', {
                            field: 'uuid',
                            values: [row.uuid]
                        })
                    }
                };
                // 数据表格配置
                $('#tb_company').bootstrapTable({
                    // 请求后台的 URL 路径，之前简单查询就用 findAllSimplePageMap
                    url: '/CompanyModule/queryDynamically',
                    // 请求方式
                    method: 'post',
                    // 每行表格的背景是否显示灰白相间
                    //striped: true,
                    // 表的 class属性
                    classes: "table table-bordered table-hover table-striped",
                    // 列的 class 属性
                    theadClasses: "thead-dark",
                    // 工具按钮的容器，例如：#toolbar，.toolbar，或者是DOM 结点
                    toolbar: '#toolbar',
                    // 默认缓存 ajax请求，设为 false 则禁用缓存
                    cache: false,
                    // 显示分页按钮
                    showPaginationSwitch: true,
                    // 默认为false隐藏刷新按钮，设为true显示
                    showRefresh: true,
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
                    pageList: [5, 10, 20, 50],
                    // 默认false不显示表格右上方搜索框 ，可设为true，在搜索框内只要输入内容即开始搜索
                    search: true,
                    // 搜索框居左
                    //searchAlign: 'left',
                    // 颜色高亮
                    searchHighlight: true,
                    // 显示搜索按钮
                    showSearchButton: true,
                    // 显示清除搜索内容按钮
                    showSearchClearButton: true,
                    // 最小显示的列数
                    minimumCountColumns: 5,
                    // 表明每行唯一的标识符
                    uniqueId: "uuid",
                    // 默认为true显示表头，设为false隐藏
                    showHeader: true,
                    // 默认为false隐藏表尾，设为true显示
                    showFooter: true,
                    // 默认为false不显示所有的列，设为true显示
                    showColumns: true,
                    // 默认为false 隐藏视图切换按钮，设为true显示
                    showToggle: true,
                    // 显示全屏按钮
                    showFullscreen: true,
                    // 按钮的颜色样式
                    //buttonsClass: 'primary',
                    // 默认false，设为 true显示card view（卡片视图）
                    cardView: false,
                    // clickToSelect: true,
                    // 默认false，设为 true显示detail view（细节视图）
                    detailView: false,
                    // 行单击事件
                    onClickRow: function (row, $element) {
                        var detail = "<h2>" + row.comname + "<small class='text-muted'> - 详细信息</small></h2>";
                        detail += "<p class='card-text'>UUID：<b>" + row.uuid + "</b></p>";
                        detail += "<p class='card-text'>公司名称：<b>" + row.comname + "</b></p>";
                        detail += "<p class='card-text'>公司地址：<b>" + row.comaddress + "</b></p>";
                        detail += "<p class='card-text'>公司官网：<b>" + row.comurl + "</b></p>";
                        detail += "<p class='card-text'>公司座机：<b>" + row.comtelephone + "</b></p>";
                        detail += "<p class='card-text'>成立日期：<b>" + row.establishdate + "</b></p>";
                        detail += "<p class='card-text'>员工人数：<b>" + row.employeenumber + "</b></p>";
                        detail += "<p class='card-text'>总产值：<b>" + row.totaloutput + "</b></p>";
                        detail += "<p class='card-text'>公司简介：<b>" + row.comdesc + "</b></p>";
                        detail += "<p class='card-text'>营运状态：<b>" + row.comstatus + "</b></p>";
                        detail += "<p class='card-text'>联系人姓名：<b>" + row.contactname + "</b></p>";
                        detail += "<p class='card-text'>联系人电话：<b>" + row.contactmobile + "</b></p>";
                        detail += "<p class='card-text'>联系人邮箱：<b>" + row.contactemail + "</b></p>";
                        // 赋值给页面元素
                        $("#detail_company").html(detail);
                    },
                    // 当请求数据时，可以通过修改 queryParams向服务器发送其余的参数。
                    queryParams: function (params) {
                        // 重写参数，方便后期增加查询条件
                        var param = {
                            size: params.limit,
                            page: (params.offset / params.limit),
                            // 页面搜索框的条件
                            comname: $("#searchComName").val(),
                            employeenumber: $("#searchComNum").val(),
                            comstatus: $("#searchComStatus").val()
                        };
                        return param;
                    },
                    // 默认空数组，在JS里面定义，field即data-field，title就是每列表头名等等。
                    columns: [
                        {
                            title: '编号',
                            formatter:function (value, row, index) {
                                var pageSize = $('#tb_company').bootstrapTable('getOptions').pageSize;
                                var pageNumber = $('#tb_company').bootstrapTable('getOptions').pageNumber;
                                return pageSize * (pageNumber - 1) + index + 1;
                            },
                            width: 60
                        },{
                            field: 'uuid',
                            title: 'UUID',
                            visible: false
                        },{
                            field: 'comname',
                            title: '公司名称'
                        },{
                            field: 'comaddress',
                            title: '公司地址'
                        },{
                            field: 'comurl',
                            title: '公司官网'
                        },{
                            field: 'comtelephone',
                            title: '公司座机'
                        },{
                            field: 'establishdate',
                            title: '成立日期'
                        },{
                            field: 'employeenumber',
                            title: '员工人数'
                        },{
                            field: 'totaloutput',
                            title: '总产值(万元)',
                            visible: false
                        },{
                            field: 'comdesc',
                            title: '公司简介',
                            visible: false
                        },{
                            field: 'comstatus',
                            title: '营运状态',
                            formatter: function indexFormatter(value, row, index) {
                                if (value === "正常运营"){
                                    newValue = '<span class="badge badge-success">' + value + '</span>';
                                }else if (value === "正在注销"){
                                    newValue = '<span class="badge badge-warning">' + value + '</span>';
                                }else {
                                    newValue = '<span class="badge badge-danger">' + value + '</span>';
                                }
                                return newValue;
                            }
                        },{
                            field: 'contactname',
                            title: '联系人姓名',
                            visible: false
                        },{
                            field: 'contactmobile',
                            title: '联系人手机号',
                            visible: false
                        },{
                            field: 'contactemail',
                            title: '联系人邮箱',
                            visible: false
                        },{
                            field: '',
                            title: '操 作',
                            align: 'center',
                            clickToSelect: false,
                            events: window.operateEvents,
                            formatter: function(value, row, index){
                                return ['<a style="margin-right: 10px;" href="javascript:void(0)" title="Like">',
                                    '<i class="like text-primary fa fa-heart fa-lg"></i></a>',
                                    '<a style="margin-right: 10px;" href="javascript:void(0)" title="Edit">',
                                    '<i class="modify text-warning fas fa-edit fa-lg"></i></a>',
                                    '<a href="javascript:void(0)" title="Remove">',
                                    '<i class="delete text-danger fas fa-trash fa-lg"></i></a>'
                                ].join('')
                            }
                        }
                    ]
                });
                // 多条件查询按钮
                $('#searchComBtn').click(function () {
                    // 执行刷新就是带参数查询
                    $('#tb_company').bootstrapTable('refresh');
                });
                // layer 弹出层
                $('#btn_add').on('click', function(){
                   layer.open({
                       // 0：信息框，默认；1：页面层；2：iframe层；3：加载层；4：tips层
                       type: 2,
                       title: '录入公司的信息',
                       // 弹出动画，值：0~6
                       anim: 2,
                       skin: 'layui-layer-lan',
                       // 点击阴影部分是否关闭
                       shadeClose: true,
                       shade: 0.6,
                       // 是否有最大化最小化按钮
                       maxmin: false,
                       area: ['800px', '800px'],
                       // 请求路径，获取页面内容
                       content: '/CompanyModule/addCompany',
                       // 销毁后回调，不论是确认还是取消
                       end: function () {
                           $('#tb_company').bootstrapTable('refresh');
                       }
                   });
                });
                // end
            }
        )
    }
);