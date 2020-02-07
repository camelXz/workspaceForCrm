<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function () {
			$("#qx").click(function () {
				var $c=$("input[name=xz]");
				if ($("#qx").prop("checked")){
					for (var i=0;i<$c.length;i++){
						$c[i].checked=true;
					}
				} else {
					for (var i=0;i<$c.length;i++){
						$c[i].checked=false;
					}
				}
			})
			$("input[name=xz]").click(function () {
				$("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length);
			})
			$("#delDicValueBut").click(function () {
				var param="";
				var $c=$("input[name=xz]:checked");
				if ($c.length==0){
					alert("请选择删除项")
				} else if ($c.length>0) {
					if (confirm("确定要删除所选记录吗？")){
						for (var i=0;i<$c.length;i++){
							var z=$($c[i]).val();
							param+="id="+z;
							if (i<$c.length-1){
								param+="&";
							}
						}
						window.location.href="settings/dictionary/value/delDicValueById.do?"+param
					}
				}
			})
			$("#upDateDicValueBtu").click(function () {
				var $c=$("input[name=xz]:checked");
				if ($c.length==0){
					alert("请选择要修改的项")
				} else if ($c.length>1){
					alert("做多可选择一项进行修改")
				} else {
					var id=$c.val();
					window.location.href="settings/dictionary/value/toUpdateDicValue.do?id="+id;
				}
			})
		})
	</script>
</head>
<body>

	<div>
		<div style="position: relative; left: 30px; top: -10px;">
			<div class="page-header">
				<h3>字典值列表</h3>
			</div>
		</div>
	</div>
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
		  <button type="button" class="btn btn-primary" onclick="window.location.href='settings/dictionary/value/initType.do'"><span class="glyphicon glyphicon-plus"></span> 创建</button>
		  <button type="button" class="btn btn-default" id="upDateDicValueBtu"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
		  <button type="button" class="btn btn-danger" id="delDicValueBut"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	<div style="position: relative; left: 30px; top: 20px;">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" id="qx" /></td>
					<td>序号</td>
					<td>字典值</td>
					<td>文本</td>
					<td>排序号</td>
					<td>字典类型编码</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${vulist}" var="vl" varStatus="var">
				<tr>
					<td><input type="checkbox" value="${vl.id}" name="xz"/></td>
					<td>${var.count}</td>
					<td>${vl.value}</td>
					<td>${vl.text}</td>
					<td>${vl.orderNo}</td>
					<td>${vl.typeCode}</td>
				</tr>
			</c:forEach>
				<%--<tr class="active">
					<td><input type="checkbox" /></td>
					<td>1</td>
					<td>m</td>
					<td>男</td>
					<td>1</td>
					<td>sex</td>
				</tr>--%>
			</tbody>
		</table>
	</div>
	
</body>
</html>