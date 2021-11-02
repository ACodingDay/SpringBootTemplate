/**
 * @description：请描述功能信息
 * @author：yyt
 * @date：2021-11-02 21:11:57
 **/
require(
    ['/custom/GlobalConfig.js'],
    function () {
        requirejs(
            ['jquery', 'bootstrap', 'custom'],
            function ($) {
                // 此处定义自己的脚本
                $.ajax({
                    url: '/CompanyReport/html',
                    type: 'POST',
                    async: true,
                    success: function (data) {
                        $("#myReport").html(data);
                    }
                });

                // 导出为 Excel
                $("#btn_exportExcel").click(function () {
                   window.location = "/CompanyReport/excel";
                });

                // 导出为 Excel
                $("#btn_exportPDF").click(function () {
                    window.location = "/CompanyReport/pdf";
                });
            }
        )
    }
);