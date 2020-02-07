<%@ page import="com.wkcto.crm.settings.domain.DicType" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
   List<DicType> list= (List<DicType>) request.getAttribute("list");
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#qx").click(function () {
                var $qx=$("input[name=xz]");
                if ($("#qx").prop("checked")){
                    for (var i=0;i<$qx.length;i++){
                       $qx[i].checked=true;
                    }
                }else {
                    for (var i=0;i<$qx.length;i++){
                        $qx[i].checked=false;
                }
                }
            })
            $("input[name=xz]").click(function () {
                $("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length);
            })
            $("#toTypeupdateBut").click(function () {
                var $c=$("input[name=xz]:checked");
                if ($c.length==0){
                    alert("请选择编辑项");
                }else if ($c.length>1){
                    alert("只能选择一条记录执行修改操作");
                }else {
                    var code=$c.val();
                    window.location.href="settings/dictionary/type/toTypeUpdate.do?code="+code;
                }
            })
            $("#delDicTypeBut").click(function () {
                var $c= $("input[name=xz]:checked");
                var param="";
                for (var i=0;i<$c.length;i++){
                    $z=$c[i];
                    param+="code="+$($z).val();
                    if (i<$c.length-1){
                        param+="&";
                    }
                }
                if ($c.length==0){
                    alert("请选择所要删除的记录")
                }else {
                    if (confirm("确定要删除所选记录吗？")){
                        window.location.href="settings/dictionary/type/delDicType.do?"+param
                    }
                }
            })
        })

    </script>
</head>
<body>

<div>
    <div style="position: relative; left: 30px; top: -10px;">
        <div class="page-header">
            <h3>字典类型列表</h3>
        </div>
    </div>
</div>
<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
    <div class="btn-group" style="position: relative; top: 18%;">
        <button type="button" class="btn btn-primary" onclick="window.location.href='settings/dictionary/type/toDicSave.do'"><span
                class="glyphicon glyphicon-plus"></span> 创建
        </button>
        <button type="button" class="btn btn-default" id="toTypeupdateBut"><span
                class="glyphicon glyphicon-edit" ></span> 编辑
        </button>
        <button type="button" class="btn btn-danger" id="delDicTypeBut"><span class="glyphicon glyphicon-minus"></span> 删除</button>
    </div>
</div>
<div style="position: relative; left: 30px; top: 20px;">
    <table class="table table-hover">
        <thead>
        <tr style="color: #B3B3B3;">
            <td><input type="checkbox" id="qx"/></td>
            <td>序号</td>
            <td>编码</td>
            <td>名称</td>
            <td>描述</td>
        </tr>
        <c:forEach items="${diclist}" var="dl" varStatus="var">
           <tr class="active">
                <td><input type="checkbox" value="${dl.code}" name="xz" /></td>
                <td>${var.count}</td>
                <td>${dl.code}</td>
                <td>${dl.name}</td>
                <td>${dl.description}</td>
            </tr>
        </c:forEach>
       <%-- <%
            for (int i=0;i<list.size();i++){
               DicType dicType =list.get(i);
                %>
        <tr class="active">
            <td><input type="checkbox" value="<%=dicType.getCode()%>" /></td>
            <td><%=i+1%></td>
            <td><%=dicType.getCode()%></td>
            <td><%=dicType.getName()%></td>
            <td><%=dicType.getDescription()%></td>
        </tr>
           <%
            }
        %>--%>
        </thead>
        <tbody>

        </tbody>
    </table>
</div>

</body>
</html>