/**
 * @description：请描述功能信息
 * @author：yyt
 * @date：2021-10-29 20:10:01
 **/
require(
    ['/custom/GlobalConfig.js'],
    function () {
        requirejs(
            ['jquery', 'bootstrap3', 'bootstrap_validator', 'bootstrap_validator_CN', 'jqueryform'],
            function ($) {
                // 此处定义自己的脚本
                // 下拉列表的数据
                $.ajax({
                    url: '/security/findAllRoles',
                    type: 'POST',
                    async: true,
                    success: function(data){
                        var options = '';
                        for (var i = 0; i < data.length; i++) {
                            options += '<option value="' + data[i].uuid + '">' + data[i].rolename + '</option>';
                        }
                        $("#sysroleid").html(options);
                    }
                });

                // 验证表单数据
                $(document).ready(function(){
                    $("#SysUserForm").bootstrapValidator({
                        feedbackIcons: {
                            valid: 'fas fa-check',
                            invalid: 'fas fa-times',
                            validating: 'fas fa-hourglass-half'
                        },
                        fields: {
                            username: {
                                message: '请输入账号名称',
                                validators: {
                                    notEmpty: {
                                        message: '账号不能为空'
                                    },
                                    stringLength: {
                                        min: 2,
                                        max: 20,
                                        message: '账号名称长度限制在2—20个汉字范围内'
                                    },
                                    // 有 2 个字符以上才发送 ajax 请求
                                    threshold: 2,
                                    remote: {
                                        url: '/security/validateUsername',
                                        message: '该账号已存在',
                                        // 设置 2秒发送一次ajax
                                        delay: 2000,
                                        type: 'POST'
                                    },
                                    regexp: {
                                        regexp: /^[a-zA-Z0-9_\.]+$/,
                                        message: '账号以字母开头，可包含数字'
                                    }
                                }
                            },
                            password: {
                                message: '请输入账号密码',
                                validators: {
                                    notEmpty: {
                                        message: '密码不能为空'
                                    },
                                    stringLength: {
                                        min: 6,
                                        message: '密码长度不能少于 6 位'
                                    }
                                }
                            },
                            useremail: {
                                validators: {
                                    notEmpty: {
                                        message: '账号邮箱不能为空'
                                    },
                                    // 有 4 个字符以上才发送 ajax 请求
                                    threshold: 4,
                                    remote: {
                                        url: '/security/validateEmail',
                                        message: '该邮箱已存在',
                                        // 设置 2秒发送一次ajax
                                        delay: 2000,
                                        type: 'POST'
                                    },
                                    emailAddress: {
                                        message: '邮箱格式不正确'
                                    }
                                }
                            },
                            usermobile: {
                                message: '请输入账号的手机号',
                                validators: {
                                    notEmpty: {
                                        message: '手机号不能为空'
                                    },
                                    // 有 10 个字符以上才发送 ajax 请求
                                    threshold: 10,
                                    remote: {
                                        url: '/security/validateMobile',
                                        message: '该手机号已存在',
                                        // 设置 2秒发送一次ajax
                                        delay: 2000,
                                        type: 'POST'
                                    },
                                    regexp: {
                                        regexp: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,
                                        message: '手机号格式错误'
                                    }
                                }
                            }
                        }
                    });
                });

                // 异步提交表单数据
                $("#btn_SaveAddSysUser").bind("click", function(){
                    // 获取表单对象
                    var bootstrapValidator = $("#SysUserForm").data('bootstrapValidator');
                    // 手动触发验证
                    bootstrapValidator.validate();
                    // 全部通过验证才能提交
                    if (bootstrapValidator.isValid()) {
                        var options = {
                            complete: function (data) {
                                alert("添加账号信息成功");
                                var mylay = parent.layer.getFrameIndex(window.name);
                                // 关闭当前窗口页面
                                parent.layer.close(mylay);
                            },
                            url: '/security/saveSysUser',
                            dataType: 'json',
                            // 成功提交后，清空原有表单数据
                            resetForm: true
                        };
                        $("#SysUserForm").ajaxSubmit(options);
                    }
                });

                // end
            }
        )
    }
);