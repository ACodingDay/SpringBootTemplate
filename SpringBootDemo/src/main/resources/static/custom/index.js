/**
 * @description：通用基础模板
 * @author：yyt
 * @date：2021-10-21 20:10:06
 **/
require(
    ['/custom/GlobalConfig.js'],
    function () {
        requirejs(
            ['jquery', 'bootstrap', 'custom', 'bootstrap_table', 'bootstrap_table_CN'],
            function ($) {
                // 此处定义自己的脚本

            }
        )
    }
);