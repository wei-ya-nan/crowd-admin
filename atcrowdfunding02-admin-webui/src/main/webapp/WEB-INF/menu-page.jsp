<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<%--<%@include file="/WEB-INF/include-head.jsp"%>--%>
<jsp:include page="include-head.jsp"/>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-manu.js"></script>
<script type="text/javascript">
	$(function () {
		generateTree();

		// 显示添加的模态框
		$("#treeDemo").on("click",".addBtn",function () {
			// 将单前的节点的id，作为新节点的id保存到全局变量
			window.pid = this.id;

			$("#menuAddModal").modal("show");
			return false;
		})

		// 给字节点的模态框的保存按钮添加单击事件
		$("#menuSaveBtn").click(function () {
			// 手机表单的数据
			var name = $.trim($("#menuAddModal [name=name]").val());
			var url = $.trim($("#menuAddModal [name=url]").val());
			// 单选按钮要定位到被选中的哪一个
			var icon = $.trim($("#menuAddModal [name=icon]:checked").val());
			// 发送ajax的请求
			$.ajax({
				url:"menu/save.json",
				type:"post",
				data:{
					pid:window.pid,
					name:name,
					url:url,
					icon:icon
				},
				dataType:"json",
				success:function (response) {
					var result = response.result;
					if(result == "SUCCESS"){
						layer.msg("操作成功！");
						// 重新加载树形结构，注意：要在确认服务器端完成保存操作后再刷新
						// 否则有可能刷新不到最新的数据，因为这里是异步的
						generateTree();

					}
					if(result == "FAILED"){
						layer.msg(response.result);
					}
				},
				"error":function(response){
					layer.msg(response.status+" "+response.statusText);
				}
			});
			$("#menuAddModal").modal("hide");
			// 清空表单
			// jQuery对象调用click()函数，里面不传任何参数，相当于用户点击了一下
			$("#menuResetBtn").click();

		});

		// 表单数据的回显
		$("#treeDemo").on("click",".editBtn",function () {

			// 将当前节点的id保存到全局变量
			window.id = this.id;

			// 打开模态框
			$("#menuEditModal").modal("show");

			// 获取zTreeObj对象
			var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");

			// 根据id属性查询节点对象
			// 用来搜索节点的属性名
			var key = "id";

			// 用来搜索节点的属性值
			var value = window.id;

			var currentNode = zTreeObj.getNodeByParam(key, value);

			// 回显表单数据
			$("#menuEditModal [name=name]").val(currentNode.name);
			$("#menuEditModal [name=url]").val(currentNode.url);

			// 回显radio可以这样理解：被选中的radio的value属性可以组成一个数组，
			// 然后再用这个数组设置回radio，就能够把对应的值选中
			$("#menuEditModal [name=icon]").val([currentNode.icon]);
			return false;
		});

		// 回显数据之后给更新的按钮绑定单击响应函数
		$("#menuEditBtn").click(function () {

			// 收集表单数据
			var name = $("#menuEditModal [name=name]").val();
			var url = $("#menuEditModal [name=url]").val();
			var icon = $("#menuEditModal [name=icon]:checked").val();
			$.ajax({
				url:"menu/update.json",
				type:"post",
				data:{
					id:window.id,
					name:name,
					url:url,
					icon:icon
				},
				dataType:"json",
				"success":function(response){
					var result = response.result;

					if(result == "SUCCESS") {
						layer.msg("操作成功！");

						// 重新加载树形结构，注意：要在确认服务器端完成保存操作后再刷新
						// 否则有可能刷新不到最新的数据，因为这里是异步的
						generateTree();
					}

					if(result == "FAILED") {
						layer.msg("操作失败！"+response.message);
					}
				},
				"error":function(response){
					layer.msg(response.status+" "+response.statusText);
				}
			});

			$("#menuEditModal").modal("hide");
		});

		// 回显数据到模态框
		$("#treeDemo").on("click",".removeBtn",function () {
			window.id = this.id;
			$("#menuConfirmModal").modal("show");

			// 获取zTree对象
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");

			var id = "id";
			var value = window.id;
			var currentNode = zTree.getNodeByParam(id,value);
			$("#removeNodeSpan").html("【<i class='"+currentNode.icon+"'></i>"+currentNode.name+"】");
			return false;
		});

		// 给模态框的删除按钮添加点击函数
		$("#confirmBtn").click(function () {
			$.ajax({
				url:"menu/remove.json",
				type:"post",
				data:{id:window.id},
				dataType:"json",
				"success":function(response){
					var result = response.result;

					if(result == "SUCCESS") {
						layer.msg("操作成功！");

						// 重新加载树形结构，注意：要在确认服务器端完成保存操作后再刷新
						// 否则有可能刷新不到最新的数据，因为这里是异步的
						generateTree();
					}

					if(result == "FAILED") {
						layer.msg("操作失败！"+response.message);
					}
				},
				"error":function(response){
					layer.msg(response.status+" "+response.statusText);
				}
			});

			// 关闭模态框
			$("#menuConfirmModal").modal("hide");

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
						<i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
						<div style="float: right; cursor: pointer;" data-toggle="modal"
							data-target="#myModal">
							<i class="glyphicon glyphicon-question-sign"></i>
						</div>
					</div>
					<div class="panel-body">
						<!-- 这个ul标签是zTree动态生成的节点所依附的静态节点 -->
						<ul id="treeDemo" class="ztree"></ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="modal-menu-add.jsp"/>
	<jsp:include page="modal-menu-confirm.jsp"/>
	<jsp:include page="modal-menu-edit.jsp"/>

</body>
</html>