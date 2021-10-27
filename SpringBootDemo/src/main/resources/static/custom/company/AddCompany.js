/**
 * @description：新增公司信息表单
 * @author：yyt
 * @date：2021-10-22 18:10:37
 **/
require(
    ['/custom/GlobalConfig.js'],
    function () {
        requirejs(
            ['jquery', 'bootstrap3', 'bootstrap_validator', 'bootstrap_validator_CN', 'jqueryform','jqueryupload'],
            function ($) {
                // 此处定义自己的脚本
                // 表单验证
                $('#form_company').bootstrapValidator({
                    feedbackIcons: {
                        valid: 'fas fa-check',
                        invalid: 'fas fa-times',
                        validating: 'fas fa-hourglass-half'
                    },
                    fields: {
                        inputComName: {
                            message: '请输入公司名称',
                            validators: {
                                notEmpty: {
                                    message: '公司名称不能为空'
                                },
                                stringLength: {
                                    min: 6,
                                    max: 30,
                                    message: '公司名称长度限制在6—30个汉字范围内'
                                }
                            }
                        },
                        inputConPhone: {
                            message: '请输入联系人的手机号',
                            validators: {
                                notEmpty: {
                                    message: '手机号不能为空'
                                },
                                regexp: {
                                    regexp: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,
                                    message: '手机号格式错误'
                                }
                            }
                        },
                        inputConEmail: {
                            validators: {
                                notEmpty: {
                                    message: '联系人邮箱不能为空'
                                },
                                // 有 4 个字符以上才发送 ajax 请求
                                threshold: 4,
                                remote: {
                                    url: '/CompanyModule/validateEmail',
                                    message: '该邮箱已存在',
                                    // 设置 2秒发送一次ajax
                                    delay: 2000,
                                    type: 'GET'
                                },
                                emailAddress: {
                                    message: '邮箱格式不正确'
                                }
                            }
                        },
                        inputComOut: {
                            notEmpty: {
                                message: '总产值不能为空'
                            },
                            number: {
                                message: '只能是数字，可以有小数点'
                            }
                        },
                        inputComUrl: {
                            notEmpty: {
                                message: '公司官网不能为空'
                            },
                            uri: {
                                message: '网址格式错误'
                            }
                        }
                    }
                });

                // 异步提交表单
                $('#btn_save').bind('click', function(){
                    $(".filename").each(function(){
                        $("[name=upload]").val($("[name=upload]").val()+','+$(this).html());
                    });

                    // 获取表单对象
                    var bootstrapValidator = $('#form_company').data('bootstrapValidator');
                    // 手动触发验证
                    bootstrapValidator.validate();
                    // 全部通过验证才能提交
                    if (bootstrapValidator.isValid()) {
                        var options = {
                            complete: function (data) {
                                alert("添加公司信息成功");
                                var mylay = parent.layer.getFrameIndex(window.name);
                                // 关闭当前窗口页面
                                parent.layer.close(mylay);
                            },
                            url: '/CompanyModule/save',
                            dataType: 'json',
                            // 成功提交后，清空原有表单数据
                            resetForm: true
                        };
                        $('#form_company').ajaxSubmit(options);
                    }
                });

                // 上传附件
                $('#inputConFile').upload();
            }
        )
    }
);