/**
 * @description：请描述功能信息
 * @author：yyt
 * @date：2021-10-29 20:10:59
 **/
require(
    ['/custom/GlobalConfig.js'],
    function () {
        requirejs(
            ['jquery', 'bootstrap', 'custom', 'bootstrap_table', 'bootstrap_table_CN', 'layer', 'zTree', 'jqueryform'],
            function ($) {
                // 此处定义自己的脚本
                // 弹窗
                $("#btn_addUser").bind('click', function () {
                   layer.open({
                       type: 2,
                       title: '新增账号',
                       anim: 2,
                       skin: 'layui-layer-lan',
                       shadeClose: true,
                       shade: 0.6,
                       maxmin: false,
                       area: ['800px', '400px'],
                       // 请求路径，获取页面内容
                       content: '/security/toAddSysUser',
                       // 销毁后回调，不论是确认还是取消
                       end: function () {
                           $('#tb_SysUser').bootstrapTable('refresh');
                       }
                   });
                });

                // 弹窗
                $("#btn_addRole").bind('click', function () {
                    layer.open({
                        type: 1,
                        title: '新增角色',
                        anim: 2,
                        skin: 'layui-layer-lan',
                        shadeClose: true,
                        shade: 0.4,
                        maxmin: false,
                        area: ['600px', '480px'],
                        // 请求路径，获取页面内容
                        content: $('#addRoleDiv')
                    });
                });

                // 保存新增角色
                $("#saveRoleBtn").bind('click', function () {
                    var newRoleName = $("#rolename").val();
                    // 遍历判断是否输入了已有的
                    var items = $("input[name='radioRole']");
                    for (var i = 0; i < items.length; i++) {
                        var tempName = items[i].nextSibling.nodeValue;
                        // 去除空格
                        if (tempName.trim() === newRoleName){
                            alert("请勿输入重复的角色名称");
                            return false;
                        }
                    }

                    // 保存数据到数据库
                    var options = {
                        complete: function (data) {
                            layer.alert("新增角色【" + newRoleName + "】成功！", {
                                icon: 1,
                                closeBtn: 0,
                            },function () {
                                // 重新加载页面
                                window.location.reload();
                            });
                        },
                        url: "/security/saveRole",
                        type: 'POST',
                        dataType: "json",
                        resetForm: true
                    };

                    // 异步提交
                    $("#SysRoleForm").ajaxSubmit(options);
                });

                // 删除角色
                $("#btn_delRole").bind('click', function () {
                    var deluuid = $("input:radio[name='radioRole']:checked").val();
                    layer.confirm("您确定要删除选定的角色吗？", {
                        icon: 0,
                        closeBtn: 0,
                        btn: ['取 消', '确 定']
                    },function () {
                        layer.closeAll();
                    },function () {
                        $.ajax({
                            url: '/security/deleteRole',
                            type: 'POST',
                            data: {
                                uuid: deluuid
                            },
                            // 异步
                            async: true,
                            complete: function (){
                                // 重新加载页面
                                window.location.reload();
                            }
                        });
                    });
                });

                // 新增一级权限
                $("#btn_addMainAuth").bind('click', function () {

                });

                // 新增子权限
                $("#btn_addAuth").bind('click', function () {

                })

                // 保存新增的权限
                $("#saveAuthBtn").bind('click', function () {

                });

                // 删除当前的权限节点
                $("#btn_delAuth").bind('click', function () {

                });

                // 数据表格配置
                $('#tb_SysUser').bootstrapTable({
                    url: '/security/listuser',
                    method: 'post',
                    classes: "table table-bordered table-hover table-striped",
                    theadClasses: "thead-dark",
                    cache: false,
                    pagination: true,
                    sidePagination: "server",
                    sortable: true,
                    sortOrder: "asc",
                    pageNumber: 1,
                    pageSize: 10,
                    pageList: [5, 10, 20, 50],
                    minimumCountColumns: 3,
                    uniqueId: "uuid",
                    height: 500,
                    showHeader: true,
                    showExtendedPagination: true,
                    clickToSelect: true,
                    showToggle: false,
                    cardView: false,
                    detailView: false,
                    // 行单击事件，改变颜色
                    onClickRow: function (row, $element) {
                       $('.info').removeClass("info");
                       $($element).addClass("info");
                    },
                    // 当请求数据时，可以通过修改 queryParams向服务器发送其余的参数。
                    queryParams: function (params) {
                        // 重写参数，方便后期增加查询条件
                        var param = {
                            size: params.limit,
                            page: (params.offset / params.limit)
                        };
                        return param;
                    },
                    // 默认空数组，在JS里面定义，field即data-field，title就是每列表头名等等。
                    columns: [
                        {
                            title: '编号',
                            formatter:function (value, row, index) {
                                var pageSize = $('#tb_SysUser').bootstrapTable('getOptions').pageSize;
                                var pageNumber = $('#tb_SysUser').bootstrapTable('getOptions').pageNumber;
                                return pageSize * (pageNumber - 1) + index + 1;
                            },
                            width: 60
                        },{
                            field: 'uuid',
                            title: 'UUID'
                        },{
                            field: 'username',
                            title: '账号名称'
                        },{
                            field: 'useremail',
                            title: '邮箱'
                        },{
                            field: 'usermobile',
                            title: '手机号'
                        },{
                            field: 'sysrolename',
                            title: '角色名称'
                        },{
                            field: 'sysroleid',
                            title: '角色UUID',
                            visible: false
                        },{
                            field: '',
                            title: '操 作',
                            align: 'center',
                            clickToSelect: false,
                            events: window.operateEvents,
                            formatter: function(value, row, index){
                                return ['<button style="margin-right: 10px;" type="button" class="btn btn-info">',
                                    '<i class="modify fas fa-edit fa-lg"></i> 编辑</button>',
                                    '<button type="button" class="btn btn-danger">',
                                    '<i class="delete fas fa-trash fa-lg"></i> 删除</button>'
                                ].join('')
                            }
                        }
                    ]
                });

                // 权限树加载

            }
        )
    }
);