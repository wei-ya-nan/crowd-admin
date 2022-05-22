<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%--<%@include file="/WEB-INF/include-head.jsp"%>--%>
<jsp:include page="include-head.jsp"/>
<link rel="stylesheet" href="css/pagination.css"/>
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<%--<link rel="stylesheet" href="ztree/zTreeStyle.css">--%>
<%--<script type="text/javascript" href="ztree/jquery.ztree.all-3.5.min.js"></script>--%>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-role.js"></script>
<%-- 移入ztree的时候需要在pagination的下面--%>
<script type="text/javascript">
    $(function () {

        //分页操作准备初始化数据
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";

        generatePage();

        //给查询按钮绑定单击事件
        $("#searchBtn").click(function () {
            window.keyword = $("#keywordInput").val();

            // 调用分页刷新的页面
            generatePage();

        });

        // 打开模态框
        $("#showAddModalBtn").click(function () {
            $("#addModal").modal("show");
        });

        // 给新增的模态框的保存按钮天剑响应事件
        $("#saveRoleBtn").click(function () {
            // 获取用户在文本框输入的角色名称
           var roleName =  $.trim($("#addModal [name=roleName]").val());
            // console.log(roleName);
            $.ajax({
               url:"role/save.json",
                type:"post",
                data:{
                   name:roleName
                },
                dataType:"json",
                success:function (response) {
                  var result = response.result;
                  if(result == "SUCCESS"){
                      layer.msg("操作成功");
                      // 重新加载分页的函数
                      window.pageNum = 9999999;
                      generatePage();
                  }
                  if(result == "FAILED"){
                      layer.msg("操作失败"+response.message);
                  }
                },
                error:function (response) {
                    layer.msg("操作失败,哈哈");
                }
            });
            // 关闭模态框
            $("#addModal").modal("hide");

            // 清理输入框的值
            $("#addModal [name=roleName]").val("");
        });

        // $("button.pencilBtn").click(function () {
        //     alert("1231");
        // })

        // 使用jQuery对象的on函数
        // ① 找到动态生成的元素所附着的静态元素
        // 使用jQuery对象的on()函数可以解决上面问题
        // ①首先找到所有“动态生成”的元素所附着的“静态”元素
        // ②on()函数的第一个参数是事件类型
        // ③on()函数的第二个参数是找到真正要绑定事件的元素的选择器
        // ③on()函数的第三个参数是事件的响应函数
        $("#rolePageBody").on("click",".pencilBtn",function () {
            // alert("aaaa")
            $("#editModal").modal("show");

            // 获取当前行中角色的名称
            var roleName = $(this).parent().prev().text();
            // alert(roleName);
            // window.roleId = $(this).id;
            window.roleId = this.id;
            $("#editModal [name=roleName]").val(roleName);
        });
        // 给更新按钮绑定单击函数
        $("#updateRoleBtn").click(function () {

            var roleName = $("#editModal [name=roleName]").val();
            $.ajax({
                url:"role/update.json",
                type:"post",
                data:{
                    id:window.roleId,
                    name:roleName
                },
                dataType:"json",
                success:function (response) {
                    var result = response.result;

                    if(result == "SUCCESS") {
                        layer.msg("操作成功！");

                        // 重新加载分页数据
                        generatePage();
                    }

                    if(result == "FAILED") {
                        layer.msg("操作失败！"+response.message);
                    }
                },
                error:function (response) {
                    layer.msg(response.status+" "+response.statusText);
                }
            });
            $("#editModal").modal("hide");
        });

        // 点击确认模态框中按钮确认删除
        $("#removeRoleBtn").click(function () {
            // 全局变量中获取roleIdArray 的值
            var requestBody =JSON.stringify(window.roleIdArray);
            $.ajax({

                url:"role/remove/by/role/id/array.json",
                type:"post",
                data:requestBody,
                dataType:"json",
                contentType:"application/json;charset=utf-8",
                "success":function(response){

                    var result = response.result;

                    if(result == "SUCCESS") {
                        layer.msg("操作成功！");

                        // 重新加载分页数据
                        generatePage();
                    }

                    if(result == "FAILED") {
                        layer.msg("操作失败！"+response.message);
                    }

                },
                "error":function(response){
                    layer.msg(response.status+" "+response.statusText);
                }
            });
            $("#confirmModal").modal("hide");
        });

        // 给单条的删除按钮点击事件
        $("#rolePageBody").on("click",".removeBtn",function () {

            // 从当前的按钮找到角色的名称
           var roleName =  $(this).parent().prev().text();

            // 创建一个role对象
            var roleArray = [{
                roleId:this.id,
                roleName:roleName
            }]

            console.log(roleArray);
            showConfirmModal(roleArray);
        });

        // 给总的checkbox绑定单击响应函数
        $("#summaryBox").click(function () {
            // 获取当前自选框的状态
            var currentStatus = this.checked;

            // 使用当前的状态来给其他的多选框的状态
            $("input.itemBox").prop("checked",currentStatus);
        });

        // 全选和全不选的反向操作 动态的绑定单击函数
        $("#rolePageBody").on("click",".itemBox",function () {

            // 获取当前已选中的.itemBox的数量
            var checkBoxCount = $(".itemBox:checked").length;

            // 获取全部的.itemBox得数量
            var totalBoxCount = $(".itemBox").length;

            $("#summaryBox").prop("checked",checkBoxCount == totalBoxCount);
        });

        // 批量删除绑定单击响应函数
        $("#batchRemoveBtn").click(function () {

            var roleArray = [];
            // 遍历当前选中的单选框
            $(".itemBox:checked").each(function () {
                var roleId = this.id;
                var roleName = $(this).parent().next().text();
                roleArray.push({"roleId":roleId,"roleName":roleName});
            });

            // 审查元素roleArray的长度是否为零
            if(roleArray.length == 0){
                layer.msg("请选择至少一个删除");
                return;
            }
            showConfirmModal(roleArray);
        });

        // 给分配权限按钮添加响应单击函数
        $("#rolePageBody").on("click",".checkBtn",function () {
            // 把当前角色的id存入全局变量
            window.roleId = this.id;
            $("#assignModal").modal("show");

            //  在模态框中加载树形数据
            filAuthTree()
        });

        // 给分配权限模态框中的分配按钮绑定单击响应函数
        $("#assignBtn").click(function () {

            // 手机树形结构的被勾选的节点,需要节点的id,声明一个数组来存放被勾选的id
            var authIdArray = [];

            // 获取zTreeObj对象
            var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");

            // 获取全部被勾选的节点 可以忽略true的参数，默认是true；
            var checkedNodes = zTreeObj.getCheckedNodes();

            // 遍历数组
            for (var i = 0; i < checkedNodes.length; i++) {
                var checkNode = checkedNodes[i];
                var authId = checkNode.id;

                authIdArray.push(authId);
            }

            // 发送请求执行分配
            var requestBody = {
                "authIdArray":authIdArray,
                // 为了服务器端handler方法能够统一使用LIst<Integer>方法接受数据，roleId 也存入数组
                "roleId": [window.roleId]
            };

            requestBody = JSON.stringify(requestBody);
            console.log(requestBody);
            $.ajax({
                url:"assign/do/role/assign/auth.json",
                data: requestBody,
                type: "post",
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (response) {
                    var result = response.result;
                    if(result=="SUCCESS"){
                        layer.msg("操作成功");

                    }
                    if(result == "FAILED"){
                        layer.msg("操作失败"+response.message);
                    }
                },
                error: function (response) {
                    layer.msg(response.status+" " +response.statusText)
                }
            });
            $("#assignModal").modal("hide");
        });


    });
</script>
<body>

<%--	<%@ include file="/WEB-INF/include-nav.jsp"%>--%>
<jsp:include page="include-nav.jsp"/>
<div class="container-fluid">
    <div class="row">
        <%--			<%@ include file="/WEB-INF/include-sidebar.jsp"%>--%>
        <jsp:include page="include-sidebar.jsp"/>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <i class="glyphicon glyphicon-th"></i> 数据列表
                    </h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float: left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning">
                            <i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger"
                            style="float: right; margin-left: 10px;">
                        <i class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button
                            type="button"
                            id="showAddModalBtn" class="btn btn-primary"
                            style="float: right;">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear: both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody"></tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="modal-role-add.jsp"/>
<jsp:include page="modal-role-edit.jsp"/>
<jsp:include page="modal-role-confirm.jsp"/>
<jsp:include page="modal-role-assign-auth.jsp"/>

</body>
</html>