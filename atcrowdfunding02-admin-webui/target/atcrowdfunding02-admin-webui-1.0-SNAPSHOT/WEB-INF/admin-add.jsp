<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
   <jsp:include page="include-head.jsp"/>
</head>

<body>

<jsp:include page="include-nav.jsp"/>

<div class="container-fluid">
    <div class="row">
<%--        <jsp:include page="include-sidebar.jsp">--%>
        <jsp:include page="include-sidebar.jsp"/>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="admin/to/main/page.html">首页</a></li>
                <li><a href="admin/get/page.html">数据列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form action="admin/save.html" method="post" role="form">
                        <p>${requestScope.exception.message}</p>
                        <div class="form-group">
                            <label for="exampleInputPassword1">登录账号</label>
                            <input type="text" class="form-control" id="exampleInputPassword1"
            placeholder="请输入登陆账号" name="loginAcct">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">登录密码</label>
                            <input type="text" class="form-control" id="exampleInputPassword1"
            placeholder="请输入登陆密码" name="userPswd">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">用户昵称</label>
                            <input type="text" class="form-control" id="exampleInputPassword1"
            placeholder="请输入用户昵称" name="userName">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">邮箱地址</label>
                            <input type="email" class="form-control" id="exampleInputEmail1"
            placeholder="请输入邮箱地址" name="email">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                        <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增
            </button>
                        <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置
            </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">帮助</h4>
            </div>
            <div class="modal-body">
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题1</h4>
                    <p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
                </div>
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题2</h4>
                    <p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
                </div>
            </div>
            <!--
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
            -->
        </div>
    </div>
</div>
<script src="jquery/jquery-2.1.1.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="script/docs.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
    });
</script>
</body>
</html>
