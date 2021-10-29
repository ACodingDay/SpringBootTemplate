/**
 * @description：RequireJS全局配置
 * @author：yyt
 * @date：2021-10-21 18:10:15
 **/
require.config({
    waitSeconds: 200,
    // 配置 JS 的别名和路径，别加 .js 后缀
    paths:{
        jquery:['https://cdn.bootcdn.net/ajax/libs/jquery/3.6.0/jquery',
            'https://cdn.bootcdn.net/ajax/libs/jquery/3.6.0/jquery.min',
            'https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery'],
        bootstrap:['https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/4.6.0/js/bootstrap.bundle.min'],
        bootstrap3:['https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.4.1/js/bootstrap.min',
            'https://cdn.staticfile.org/twitter-bootstrap/3.4.1/js/bootstrap.min'],
        custom:['/libs/GA/custom.min'],
        bootstrap_table:['https://cdn.bootcdn.net/ajax/libs/bootstrap-table/1.18.3/bootstrap-table.min',
            'https://cdn.staticfile.org/bootstrap-table/1.18.3/bootstrap-table.min',
            'https://unpkg.com/bootstrap-table@1.18.3/dist/bootstrap-table.min'],
        bootstrap_table_CN:['https://cdn.bootcdn.net/ajax/libs/bootstrap-table/1.18.3/locale/bootstrap-table-zh-CN.min',
            'https://cdn.staticfile.org/bootstrap-table/1.18.3/locale/bootstrap-table-zh-CN.min'],
        TableExport:['https://unpkg.com/tableexport.jquery.plugin/tableExport.min'],
        bootstrap_table_export:['https://unpkg.com/bootstrap-table@1.18.3/dist/extensions/export/bootstrap-table-export'],
        layer:['https://cdn.bootcdn.net/ajax/libs/layer/3.5.1/layer','https://cdn.staticfile.org/layer/3.5.1/layer'],
        bootstrap_validator:['https://cdn.bootcdn.net/ajax/libs/bootstrap-validator/0.5.3/js/bootstrapValidator.min'],
        bootstrap_validator_CN:['https://cdn.bootcdn.net/ajax/libs/bootstrap-validator/0.5.3/js/language/zh_CN.min'],
        jqueryform:['https://cdn.bootcdn.net/ajax/libs/jquery.form/4.3.0/jquery.form.min'],
        jqueryupload:['/libs/jquery.upload/jQuery.upload.min'],
        zTree:['/libs/zTreeV3/js/jquery.ztree.all','https://cdn.bootcdn.net/ajax/libs/zTree.v3/3.5.42/js/jquery.ztree.all']
    },
    // 配置 JS 组件的版本
    map:{
        '*':{
            css: ['https://cdn.bootcdn.net/ajax/libs/require-css/0.1.10/css.min.js']
        }
    },
    // 用于解决非 AMD 标准模块的注入
    shim: {
        bootstrap:{
          // 依赖
          deps:['jquery',
              'css!https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/4.6.0/css/bootstrap.min.css',
              'css!https://cdn.bootcdn.net/ajax/libs/font-awesome/5.15.3/css/all.min.css']
        },
        custom: {
            deps:['jquery','bootstrap','css!/libs/GA/custom.min.css']
        },
        bootstrap_table: {
            // 别忘了引入 bootstrap_table 的 CSS 样式
            deps:['jquery','bootstrap','css!https://cdn.bootcdn.net/ajax/libs/bootstrap-table/1.18.3/bootstrap-table.min.css']
        },
        bootstrap_table_CN: {
            deps:['jquery','bootstrap','bootstrap_table']
        },
        layer: {
            deps:['css!https://cdn.bootcdn.net/ajax/libs/layer/3.5.1/theme/default/layer.css']
        },
        bootstrap_validator: {
            deps:['jquery','bootstrap','css!https://cdn.bootcdn.net/ajax/libs/bootstrap-validator/0.5.3/css/bootstrapValidator.min.css']
        },
        bootstrap_validator_CN: {
            deps:['jquery','bootstrap3','bootstrap_validator']
        },
        jqueryform:{
            deps:['jquery']
        },
        bootstrap3: {
            deps:['jquery','css!https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.4.1/css/bootstrap.min.css',
                'css!https://cdn.bootcdn.net/ajax/libs/font-awesome/5.15.3/css/all.min.css']
        },
        jqueryupload: {
            deps:['jquery','css!/libs/jquery.upload/upload.css']
        },
        zTree: {
            deps:['jquery','css!/libs/zTreeV3/css/metroStyle/metroStyle.css']
        }
    }
})