
var prefix = ctx + "poetry/author";
$(function() {
    var options = {
        url: prefix + "/list",
        createUrl: prefix + "/add",
        updateUrl: prefix + "/edit/{id}",
        removeUrl: prefix + "/remove",
        exportUrl: prefix + "/export",
        modalName: "作者",
        columns: [{
            checkbox: true
        },
        {
            field : 'id',
            title : 'ID',
            visible: false
        },
        {
            field : 'name',
            title : '名称'
        },
        {
            field : 'description',
            title : '描述'
        },
        {
            title: '操作',
            align: 'center',
            formatter: function(value, row, index) {
                var actions = [];
                actions.push('<a class="btn btn-success btn-xs ' + editFlag + '" href="javascript:void(0)" onclick="$.operate.edit(\'' + row.id + '\')"><i class="fa fa-edit"></i>编辑</a> ');
                actions.push('<a class="btn btn-danger btn-xs ' + removeFlag + '" href="javascript:void(0)" onclick="$.operate.remove(\'' + row.id + '\')"><i class="fa fa-remove"></i>删除</a>');
                return actions.join('');
            }
        }]
    };
    $.table.init(options);
});

/**
*  layui
**/
layui.use(['element','upload'], function () {
    var element = layui.element
        , upload = layui.upload
        , $ = layui.jquery;

    //上传
    upload.render({
        url: '/poetry/author/authorImport/'
        ,elem: '#authorImport'
        ,accept: 'file'
        ,drag: true
        ,done: function(res){
            if(res.status == 0){
                layer.msg("上传数据成功");
            } else {
                layer.msg(res.msg, {icon: 5});
            }
        }
    });
});