<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">

    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

    <script type="text/javascript">

        $(function () {

            $("#toStudentSaveBtn").click(function () {
                $("#createStudentModal").modal("show");
            })
            $("#searchBut").click(function () {
                pageList(1, 2);
            })
            pageList(1, 2);
            $("#saveStudentBtn").click(function () {
                $.ajax({
                    url: "workbench/stu/addStu.do",
                    data: {
                        name: $("#add-name").val(),
                        address: $("#add-address").val(),
                        phone: $("#add-phone").val(),
                        contactName: $("#add-contactName").val(),
                        description: $("#add-description").val()
                    },
                    dataType: "json",
                    type: "post",
                    success: function (data) {
                        if (data.success) {
                            $("#createStudentModal").modal("hide");
                             pageList(1, 2);
                        } else {
                            alert("添加失败")
                        }
                    }
                })


            })

        })

        function pageList(pageNo, pageSize) {
            $.ajax({
                url: "workbench/stu/pageList.do",
                data: {
                    pageNo: pageNo,
                    pageSize: pageSize,
                    name: $("#search-name").val(),
                    address: $("#search-address").val(),
                    phone: $("#search-phone").val(),
                    contactName: $("#search-contactName").val()
                },
                dataType: "json",
                type: "post",
                success: function (data) {
                    var html='';
                    $.each(data.slist,function (i,n) {
                        html += '<tr >';
                        html += '<td><input type="checkbox" name="xz" id="id" value="' + n.id + '"/></td>';
                        html += '<td>' +n.name+' </td>';
                        html += '<td>' + n.address + '</td>';
                        html += '<td>' + n.phone + '</td>';
                        html += '<td>' + n.contactName + '</td>';
                        html += '</tr>';
                    })
                    $("#studentBody").html(html);
                    var totalPages= data.total % pageSize == 0 ? data.total / pageSize : parseInt(data.total / pageSize) + 1;
                    $("#activityPage").bs_pagination({
                        currentPage: pageNo, // 页码
                        rowsPerPage: pageSize, // 每页显示的记录条数
                        maxRowsPerPage: 20, // 每页最多显示的记录条数
                        totalPages: totalPages, // 总页数
                        totalRows: data.total, // 总记录条数

                        visiblePageLinks: 3, // 显示几个卡片

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,

                        //该函数是在我们点击分页组件的时候，触发的
                        onChangePage : function(event, data){
                            pageList(data.currentPage , data.rowsPerPage);
                        }
                    });
                }
            })


        }

    </script>
</head>
<body>


<!-- 创建学生信息的模态窗口 -->
<div class="modal fade" id="createStudentModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建学生信息</h4>
            </div>
            <div class="modal-body">

                <form id="activitySaveFrom" class="form-horizontal" role="form">

                    <div class="form-group">
                        <label for="create-marketActivityOwner" class="col-sm-2 control-label">学生姓名<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="add-name">
                        </div>
                        <label for="create-marketActivityName" class="col-sm-2 control-label">家庭地址<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="add-address">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startTime" class="col-sm-2 control-label">联系电话</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="add-phone">
                        </div>
                        <label for="create-endTime" class="col-sm-2 control-label">联系人姓名</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="add-contactName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-describe" class="col-sm-2 control-label">学生描述</label>
                        <div class="col-sm-10" style="width: 81%;">

                            <textarea class="form-control" rows="3" id="add-description"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <!--

                    data-dismiss="modal":关闭模态窗口

                -->
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="saveStudentBtn">保存</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>学生信息列表</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">学生姓名</div>
                        <input class="form-control" type="text" id="search-name">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">家庭地址</div>
                        <input class="form-control" type="text" id="search-address">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">联系电话</div>
                        <input class="form-control" type="text" id="search-phone"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">联系人姓名</div>
                        <input class="form-control" type="text" id="search-contactName">
                    </div>
                </div>

                <button type="button" id="searchBut" class="btn btn-default">查询</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">

                <button type="button" class="btn btn-primary" id="toStudentSaveBtn"><span
                        class="glyphicon glyphicon-plus"></span> 创建
                </button>
            </div>
            <div class="btn-group" style="position: relative; top: 18%;">

            </div>
        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="qx"/></td>
                    <td>学生姓名</td>
                    <td>家庭地址</td>
                    <td>联系电话</td>
                    <td>联系人姓名</td>
                </tr>
                </thead>
                <tbody id="studentBody">
                <%--<tr class="active">
                    <td><input type="checkbox" /></td>
                    <td>吴亦凡</td>
                    <td>大族企业湾</td>
                    <td>13338475283</td>
                    <td>鹿晗</td>
                </tr>
                <tr class="active">
                    <td><input type="checkbox" /></td>
                    <td>蔡徐坤</td>
                    <td>大族广场</td>
                    <td>138124234234</td>
                    <td>篮球大使</td>
                </tr>--%>
                </tbody>
            </table>
        </div>

        <div style="height: 50px; position: relative;top: 30px;">

            <div id="activityPage"></div>

        </div>

    </div>

</div>
</body>
</html>