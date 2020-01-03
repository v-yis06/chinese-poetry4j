var prefix = ctx + "poetry/article";

$(function() {
    var firstLoad = true;
    var options = {
        code: "id",
        parentCode: "parentId",
        expandColumn: "2",
        uniqueId: "id",
        url: prefix + "/list/tree",
        createUrl: prefix + "/add/{id}",
        updateUrl: prefix + "/edit/{id}",
        removeUrl: prefix + "/remove/{id}",
        exportUrl: prefix + "/export",
        modalName: "诗词文章",
        firstLoad: false,
        columns: [{
            field: 'selectItem',
            radio: true
        },
        {
            field : 'content',
            title : '段落',
            align: 'left'
        },
        {
            field : 'orderNum',
            title : '排序',
            align: 'left'
        },
        {
            field : 'author',
            title : '作者',
            align: 'left'
        },
        {
            field : 'type',
            title : '分类',
            align: 'left'
        },
        {
            field : 'remark',
            title : '备注',
            align: 'left'
        },
        {
            title: '操作',
            align: 'center',
            align: 'left',
            formatter: function(value, row, index) {
                var actions = [];
                actions.push('<a class="btn btn-success btn-xs ' + editFlag + '" href="javascript:void(0)" onclick="$.operate.edit(\'' + row.id + '\')"><i class="fa fa-edit"></i>编辑</a> ');
                actions.push('<a class="btn btn-info btn-xs ' + '' + '" href="javascript:void(0)" onclick="detail(\'' + row.id + '\')"><i class="fa fa-list-ul"></i>列表</a> ');
                actions.push('<a class="btn btn-info  btn-xs ' + addFlag + '" href="javascript:void(0)" onclick="$.operate.add(\'' + row.id + '\')"><i class="fa fa-plus"></i>新增</a> ');
                actions.push('<a class="btn btn-danger btn-xs ' + removeFlag + '" href="javascript:void(0)" onclick="$.operate.remove(\'' + row.id + '\')"><i class="fa fa-remove"></i>删除</a>');
                return actions.join('');
            }
        }],
    };
    $.treeTable.init(options);
});

/*列表-详细*/
function detail(id) {
    var url = prefix + '/detail/' + id;
    $.modal.openTab("列表数据", url);
}

/**
*  layui
**/
layui.use(['element','upload'], function () {
    var element = layui.element
        , upload = layui.upload
        , $ = layui.jquery;

    //上传
    upload.render({
        url: '/poetry/article/articleImport/'
        ,elem: '#articleImport'
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