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
                        skin: 'layui-layer-molv',
                        shadeClose: true,
                        shade: 0.6,
                        maxmin: false,
                        area: ['800px', '450px'],
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
                        skin: 'layui-layer-molv',
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
                        if (tempName.trim() === newRoleName) {
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
                            }, function () {
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
                    }, function () {
                        layer.closeAll();
                    }, function () {
                        $.ajax({
                            url: '/security/deleteRole',
                            type: 'POST',
                            data: {
                                uuid: deluuid
                            },
                            // 异步
                            async: true,
                            complete: function () {
                                // 重新加载页面
                                window.location.reload();
                            }
                        });
                    });
                });

                // 新增一级权限
                $("#btn_addMainAuth").bind('click', function () {
                    // 父节点的 id 设置为 0
                    $("#nowid").val(0);
                    layer.open({
                        type: 1,
                        title: '新增一级权限',
                        anim: 2,
                        skin: 'layui-layer-molv',
                        shadeClose: true,
                        shade: 0.4,
                        maxmin: false,
                        area: ['500px', '140px'],
                        // 请求路径，获取页面内容
                        content: $('#addAuthDiv')
                    });
                });

                // 新增子权限
                $("#btn_addAuth").bind('click', function () {
                    var authName = $("#nowname").val();
                    if (authName === "无") {
                        alert("请先选中对应的节点，再操作");
                        return;
                    }

                    layer.open({
                        type: 1,
                        title: '新增【' + authName + '】的子节点',
                        anim: 2,
                        skin: 'layui-layer-molv',
                        shadeClose: true,
                        shade: 0.4,
                        maxmin: false,
                        area: ['500px', '140px'],
                        // 请求路径，获取页面内容
                        content: $('#addAuthDiv')
                    });
                })

                // 保存新增的权限
                $("#saveAuthBtn").bind('click', function () {
                    var newAuthName = $("#authName").val();
                    var newAuthId = $("#nowid").val();

                    $.ajax({
                        url: '/security/saveChildAuth',
                        type: 'POST',
                        data: {
                            "id": newAuthId,
                            "name": newAuthName
                        },
                        // 异步
                        async: true,
                        success: function (data) {
                            var msg = data.msg;
                            // 判断是否重名了
                            if (msg === "exist") {
                                alert("节点名称已存在，请重新输入");
                            } else {
                                alert("节点添加成功！");
                            }

                            $("#nowid").val("无");
                            $("#nowpid").val("无");
                            $("#nowname").val("无");

                            window.location.reload();
                        }
                    });
                });

                // 删除当前的权限节点
                $("#btn_delAuth").bind('click', function () {
                    var authName = $("#nowname").val();
                    var id = $("#nowid").val();
                    if (authName === "无") {
                        alert("请先点击对应的节点，再操作");
                        return;
                    }

                    layer.confirm("您确定要删除权限【" + authName + "】吗？", {
                        icon: 0,
                        btn: ['取 消', '确 定']
                    }, function (index) {
                        parent.layer.close(index);
                    }, function (index) {
                        $.ajax({
                            url: '/security/deleteByChild',
                            type: 'POST',
                            data: {
                                id: id
                            },
                            async: true,
                            success: function () {
                                $("#nowid").val("无");
                                $("#nowpid").val("无");
                                $("#nowname").val("无");

                                window.location.reload();
                            }
                        });
                    });
                });

                // 保存修改的权限
                $("#btn_assignRole").bind("click", function () {
                    var seluuid = $("input:radio[name='radioRole']:checked").val();
                    if (seluuid == null) {
                        alert("请选择对应的角色");
                        return;
                    }

                    var arrAuths = new Array();
                    var zTreeObj = $.fn.zTree.getZTreeObj("AuthTree");
                    var checkedNodes = zTreeObj.getCheckedNodes();
                    if (checkedNodes.length < 1) {
                        alert("还未选择权限！");
                        return;
                    }
                    for (var i = 0; i < checkedNodes.length; i++) {
                        arrAuths[i] = checkedNodes[i].id;
                    }
                    // 转换成以 ‘$’ 分割的字符串
                    var strAuths = arrAuths.join('$');

                    // 保存
                    $.ajax({
                        url: '/security/editRole',
                        type: 'POST',
                        data: {
                            uuid: seluuid,
                            authinfo: strAuths
                        },
                        async: true,
                        success: function () {
                            alert("角色分配权限成功！")
                        }
                    });
                });

                // 角色-权限有变化后，重新加载权限树
                $("input:radio[name='radioRole']").change(function () {
                    showAuthTree();
                });

                // 绑定表格中的按钮事件
                window.operateEvents = {
                    'click .delete': function (e, value, row, index) {
                        // 删除记录
                        layer.confirm('您确定要删除该记录吗？', {
                            icon: 0,
                            btn: ['取 消', '确 定']
                        }, function () {
                            layer.closeAll();
                        }, function () {
                            $.ajax({
                                url: '/security/deleteRole',
                                type: 'POST',
                                data: {
                                    uuid: row.uuid
                                },
                                async: true,
                                complete: function () {
                                    $('#tb_SysUser').bootstrapTable('refresh');
                                }
                            });
                        });
                    },
                    'click .modify': function (e, value, row, index) {
                        layer.alert("您点击了账号：【" + row.username + "】", {
                            skin: 'layui-layer-molv',
                            anim: 1,
                            closeBtn: 1,
                            btn: ['知道','关闭'],
                            icon: 6
                        });
                    }
                };

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
                            formatter: function (value, row, index) {
                                var pageSize = $('#tb_SysUser').bootstrapTable('getOptions').pageSize;
                                var pageNumber = $('#tb_SysUser').bootstrapTable('getOptions').pageNumber;
                                return pageSize * (pageNumber - 1) + index + 1;
                            }
                        }, {
                            field: 'uuid',
                            title: 'UUID'
                        }, {
                            field: 'username',
                            title: '账号名称'
                        }, {
                            field: 'useremail',
                            title: '邮箱'
                        }, {
                            field: 'usermobile',
                            title: '手机号'
                        }, {
                            field: 'sysrolename',
                            title: '角色名称'
                        }, {
                            field: 'sysroleid',
                            title: '角色UUID',
                            visible: false
                        }, {
                            field: '',
                            title: '操 作',
                            align: 'center',
                            clickToSelect: false,
                            events: window.operateEvents,
                            formatter: function (value, row, index) {
                                return ['<button style="margin-right: 10px;" type="button" class="modify btn btn-info btn-sm">',
                                    '<i class="fas fa-edit"></i> 编辑</button>',
                                    '<button type="button" class="delete btn btn-danger btn-sm">',
                                    '<i class="fas fa-trash"></i> 删除</button>'
                                ].join('')
                            }
                        }
                    ]
                });

                // 权限树加载配置
                function showAuthTree() {
                    // 清空页面内容
                    $("#AuthTree").html("");

                    // 获取对应的角色
                    var seluuid = $("input:radio[name='radioRole']:checked").val();
                    if (seluuid == null) {
                        seluuid = "nouuid";
                    }

                    // ztree 参数设置
                    var setting = {
                        check: {
                            enable: true,
                            chkboxType: {
                                "Y" : "ps",
                                "N" : "ps"
                            }
                        },
                        data: {
                            simpleData: {
                                enable: true
                            }
                        },
                        callback: {
                            onClick: onClick
                        }
                    };

                    // 节点单击事件
                    function onClick(event, treeId, treeNode, clickFlag) {
                        $("#nowid").val(treeNode.id);
                        $("#nowpid").val(treeNode.pId);
                        $("#nowname").val(treeNode.name);
                    }

                    // 加载权限明细树
                    $.ajax({
                        url: '/security/listauth',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            roleid: seluuid
                        },
                        // 异步
                        async: true,
                        success: function (auths) {
                            $.fn.zTree.init($("#AuthTree"), setting, auths);
                        }
                    });
                }

                // 加载权限树
                showAuthTree();
            }
        )
    }
);