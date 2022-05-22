// 声明函数显示模态框
function showConfirmModal(roleArray) {

    $("#roleNameDiv").empty();
    $("#confirmModal").modal("show");
    window.roleIdArray = [];
    for (var i = 0; i < roleArray.length; i++) {

        var role = roleArray[i];
        var roleName = role.roleName;
        $("#roleNameDiv").append(roleName + "<br/>");
        var roleId = role.roleId;

        // 调用数组的push()来存入id
        window.roleIdArray.push(roleId);

    }

}


//执行分页，生成页面效果，任何时候都会调用这个函数都会重新加载页面
function generatePage() {

    //获取分页的数据
    var pageInfo = getPageInfoRemote();

    //填充表格
    fillTableBody(pageInfo);
}

// 远程访问服务器端程序获取pageInfo数据
function getPageInfoRemote() {

    var ajaxResult = $.ajax({
        url: "role/get/page/info.json",
        type: "post",
        data: {
            pageNum: window.pageNum,
            pageSize: window.pageSize,
            keyword: window.keyword
        },
        async: false,
        dataType: "json"

    });
    console.log(ajaxResult);

    //判断当前的响应码是不是200
    var statusCode = ajaxResult.status;
    if (statusCode != 200) {
        layer.msg("服务器端调用失败！" + " 说明信息为" + ajaxResult.statusText);
        return null;
    }

    var resultEntity = ajaxResult.responseJSON;
    var result = resultEntity.result;

    if (result == "FAILED") {
        layer.msg(resultEntity.message);
        return null;
    }

    //确认成功之后获取pageInfo的值
    var pageInfo = resultEntity.data;

    return pageInfo;


}

// 填充表格
function fillTableBody(pageInfo) {

    // 清除表格的数据
    $("#rolePageBody").empty();
    $("#Pagination").empty();
    //pageInfo是否为空
    if (pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>抱歉！没有查询到您搜索的数据！</td></tr>");
        return;
    }
    // 使用pageInfo的list属性来填充body
    for (var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;

        var numberTd = "<td>" + (i + 1) + "</td>";
        var checkboxTd = "<td><input id='" + roleId + "' class='itemBox' type='checkbox'></td>";
        var roleNameTd = "<td>" + roleName + "</td>";

        var checkBtn = "<button id='" + roleId + "' type='button' class='btn btn-success btn-xs checkBtn'><i class='" +
            " glyphicon" +
            " glyphicon-check'></i></button>";

        // 通过button标签的id属性（别的属性其实也可以）把roleId值传递到button按钮的单击响应函数中，在单击响应函数中使用this.id
        var pencilBtn = "<button id='" + roleId + "' type='button' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";

        // 通过button标签的id属性（别的属性其实也可以）把roleId值传递到button按钮的单击响应函数中，在单击响应函数中使用this.id
        var removeBtn = "<button id='" + roleId + "' type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";

        var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>";

        $("#rolePageBody").append(tr);
    }
    generateNavigator(pageInfo)
}

// 生成分页页码导航条
function generateNavigator(pageInfo) {
    // 获取总记录数
    var totalRecord = pageInfo.total;

    // 声明相关属性
    var properties = {
        "num_edge_entries": 3,
        "num_display_entries": 5,
        "callback": paginationCallBack,
        "items_per_page": pageInfo.pageSize,
        "current_page": pageInfo.pageNum - 1,
        "prev_text": "上一页",
        "next_text": "下一页"
    }
    // 调用pagination的初始化函数
    $("#Pagination").pagination(totalRecord, properties);

}

function paginationCallBack(pageIndex, jQuery) {
    // 修改window对象pageNum 的属性
    window.pageNum = pageIndex + 1;
    // 调用分页的函数
    generatePage();

    // 取消超链接的默认行为
    return false;


}


function filAuthTree() {

    // 发送ajax的请求查询auth的数据
    var ajaxReturn = $.ajax({
        url: "assign/get/all/auth.json",
        type: "post",
        dataType: "json",
        async: false/*改成同步的ajax请求可以从上往下依次执行 */
        //<editor-fold desc="Description">
        // success: function (response) {
        //     var result = response.result;
        //     if (result == "SUCCESS") {
        //         // 从响应结构中获取Auth的JSON数据
        //         // zTree自动组装数据
        //         var authList = response.data;
        //
        //         // 准备对zTree进行设置的JSON对象
        //         var setting = {
        //             data: {
        //                 simpleData: {
        //                     enable: true
        //                 }
        //             }
        //         };
        //
        //         // 生成树形结构
        //         $.fn.zTree.init($("#authTreeDemo"),setting,authList)
        //     }
        //     if (result == "FAILED") {
        //         layer.msg("操作失败" + response.message)
        //
        //     }
        // },
        // error: function (response) {
        //     layer.msg(response.status+" "+response.statusText)
        // }
        //</editor-fold>
    });
    if (ajaxReturn.status != 200) {
        layer.msg("请求处理出错！响应状态码是：" + ajaxReturn.status + " 说明是：" + ajaxReturn.statusText);
        return;
    }
    console.log(ajaxReturn);
    // 2.从响应结果中获取Auth的JSON数据
    // 从服务器端查询到的list不需要组装成树形结构，这里我们交给zTree去组装
    var authList = ajaxReturn.responseJSON.data;

    // 准备对ztree的进行设置的JSON对象
    // 3.准备对zTree进行设置的JSON对象
    var setting = {
        data: {
            simpleData: {
                // 开启简单JSON功能
                enable: true,
                // 使用categoryId属性关联父节点，不用默认的pId了
                pIdKey: "categoryId"
            },
            key: {
                // 使用title属性显示节点名称，不用默认的name作为属性名了
                name: "title"
            }
        },
        check: {
            enable: true
        }
    };

    // 生成树形结构
    // $.fn.zTree.init($("#authTreeDemo"), setting, authList);
    $.fn.zTree.init($("#authTreeDemo"), setting, authList);
    // 获取Ztreeobj对象
    var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
    // 调用zTreeObj的方法来实现默认展开树形节点
    zTreeObj.expandAll(true);
    //  查询已分配的Auth的id的组合
    ajaxReturn = $.ajax({
        url: "assign/get/assigned/auth/id/by/role/id.json",
        type: "post",
        data: {
            roleId: window.roleId
        },
        dataType: "json",
        async: false
    });
    if (ajaxReturn.status !=200){
        layer.msg("请求处理处理出错！响应码是："+ajaxReturn.status+"说明是："+ajaxReturn.statusText);
        return;
    }
    // 从响应结果中获取authIdArray
    var authIdArray = ajaxReturn.responseJSON.data;

        // 根据authIdArray把树形结构中对应的节点勾选上
    // 遍历authIdArray
    for (var i = 0; i < authIdArray.length; i++) {
        var authId = authIdArray[i];

        // 根据id查询树形结构中对应的节点
        var treeNode = zTreeObj.getNodeByParam("id",authId);

        // 将treeNode设置为被勾选
        // checked设置为true表示节点勾选
        var checked = true;

        // 设置为false表示为不联动，不联动是为了避免把不该勾选的给勾上
        var checkTypeFlag = false;

        zTreeObj.checkNode(treeNode,checked,checkTypeFlag);
    }


 }