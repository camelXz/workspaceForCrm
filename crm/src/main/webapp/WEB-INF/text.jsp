<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">


    $.ajax({
    url:"workbench/clue/",
    data:{},
    dataType:"json",
    type:"post",
    success:function (data) {
    }
    })


    $(".time").datetimepicker({
    minView: "month",
    language: 'zh-CN',
    format: 'yyyy-mm-dd',
    autoclose: true, //在选中了日期之后，日历控件自动关闭
    todayBtn: true, //展现 能够直接选择 "今天" 的按钮
    pickerPosition: "bottom-left" //日历控件展现的位置
    });
