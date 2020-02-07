<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">

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
            /*    $.fn.datetimepicker.dates['zh-CN'] = {
                    days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
                    daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
                    daysMin: ["日", "一", "二", "三", "四", "五", "六", "日"],
                    months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                    monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                    today: "今天",
                    suffix: [],
                    meridiem: ["上午", "下午"]
                };*/
            $(".time").datetimepicker({
                minView: "month",
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true, //在选中了日期之后，日历控件自动关闭
                todayBtn: true, //展现 能够直接选择 "今天" 的按钮
                pickerPosition: "bottom-left" //日历控件展现的位置
            });
            //加载添加下拉框的拥有人
            $("#toSaveActivityBut").click(function () {
                $.ajax({
                    url: "workbench/activity/findAllUser.do",
                    dataType: "json",
                    type: "get",

                    success: function (data) {
                        var s = "<option></option>"
                        $.each(data, function (i, n) {
                            s += "<option value='" + n.id + "'>" + n.name + "</option>";
                        })
                        var z = "${user.id}";
                        $("#create-owner").html(s);
                        $("#create-owner").val(z);
                        $("#createActivityModal").modal("show");
                    }
                })

            })
            //添加
            $("#saveActivityBut").click(function () {
                $.ajax({
                    url: "workbench/activity/saveActivity.do",
                    data: {
                        "owner": $.trim($("#create-owner").val()),
                        "name": $.trim($("#create-name").val()),
                        "startDate": $.trim($("#create-startDate").val()),
                        "endDate": $.trim($("#create-endDate").val()),
                        "cost": $.trim($("#create-cost").val()),
                        "description": $.trim($("#create-description").val())
                    },
                    dataType: "json",
                    type: "post",
                    success: function (data) {
                        if (data.success) {
                            $("#createActivityModal").modal("hide");
                            $("#saveActivityForm")[0].reset();
                        } else {
                            alert("添加失败")
                        }

                    }
                })
            })
            //修改
            $("#toUpdateActivityBut").click(function () {
                var $count = $("input[name=xz]:checked").length
                if ($count == 0) {
                    alert("请选择操作项")
                } else if ($count > 1) {
                    alert("一次只允许处理一项")
                } else {
                    /*  $.ajax({
                          url: "workbench/activity/findAllUser.do",
                          dataType: "json",
                          type: "get",
                          success: function (data) {
                              var s = "<option></option>"
                              $.each(data, function (i, n) {
                                  s += "<option value='" + n.id + "'>" + n.name + "</option>";
                              })
                              $("#edit-marketActivityOwner").html(s);
                              $("#editActivityModal").modal("show");
                          }
                      })*/
                    $.ajax({
                        url: "workbench/activity/findActivityById.do",
                        data: {
                            id: $("input[name=xz]:checked").val()
                        },
                        dataType: "json",
                        type: "get",
                        success: function (data) {
                            var s = "<option></option>"
                            $.each(data.userList, function (i, n) {
                                s += "<option value='" + n.id + "'>" + n.name + "</option>";
                            })
                            $("#edit-marketActivityOwner").html(s);
                            $("#editActivityModal").modal("show");

                            $("#edit-id").val(data.activity.id)
                            $("#edit-marketActivityOwner").val(data.activity.owner);
                            $("#edit-marketActivityName").val(data.activity.name);
                            $("#edit-startTime").val(data.activity.startDate);
                            $("#edit-endTime").val(data.activity.endDate);
                            $("#edit-cost").val(data.activity.cost);
                            $("#edit-describe").val(data.activity.description);
                        }
                    })
                }

            })
            pageList(1, 2)
            //显示列表
            $("#findActivityByCondition").click(function () {
                pageList(1, 3)
            })
            //单选框 全选
            $("#qx").click(function () {
                var $xz = $("input[name=xz]");
                if ($("#qx").prop("checked")) {
                    for (var i = 0; i < $xz.length; i++) {
                        $xz[i].checked = true;
                    }
                } else {
                    for (var i = 0; i < $xz.length; i++) {
                        $xz[i].checked = false;
                    }
                }
            })

            /* $("input[name=xz]").click(function () {
                 alert("1")
                 $("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length)
             })*/
            //单选框2
            $("#activitytbody").on("click", "input[name=xz]", function () {
                $("#qx").prop("checked", $("input[name=xz]").length == $("input[name=xz]:checked").length)
            })
            //修改
            $("#updateActivityBut").click(function () {
                $.ajax({
                    url: "workbench/activity/updateActivity.do",
                    data: {
                        id: $("#edit-id").val(),
                        owner: $("#edit-marketActivityOwner").val(),
                        name: $("#edit-marketActivityName").val(),
                        startDate: $("#edit-startTime").val(),
                        endDate: $("#edit-endTime").val(),
                        cost: $("#edit-cost").val(),
                        description: $("#edit-describe").val()
                    },
                    dataType: "json",
                    type: "post",
                    success: function (data) {
                        if (data.success) {
                            alert("修改成功")
                            $("#editActivityModal").modal("hide");

                            pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
                                , $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
                            //第一个参数，当前页面 第二个参数 保持每页展示数量不变
                        } else {
                            alert("修改失败")
                        }
                    }
                })

            })
            //删除
            $("#delActivityBtu").click(function () {
                var $count = $("input[name=xz]:checked").length;
                if ($count == 0) {
                    alert("请选择要删除的项");
                } else {
                    var parem = "";
                    $xz = $("input[name=xz]:checked")
                    for (var i = 0; i < $xz.length; i++) {
                        parem += "ids=" + $($xz[i]).val();
                        if (i < $xz.length - 1) {
                            parem += "&";
                        }
                    }
                    if (confirm("确定要删除吗？")) {
                        $.ajax({
                            url: "workbench/activity/delActivityById.do",
                            data: parem,
                            dataType: "json",
                            type: "post",
                            success: function (data) {
                                if (data.success) {
                                    pageList(1, 2);
                                } else {
                                    alert("删除失败")
                                }
                            }
                        })
                    }

                }
            })
            $("#exportActivityAllBtn").click(function () {
                window.location.href = "workbench/activity/exportAllActivity.do"
            })
            $("#exportActivityXzBtn").click(function () {
                var param = "";
                var $xz = $("input[name=xz]:checked")
                for (var i = 0; i < $xz.length; i++) {
                    var xz = $($xz[i]).val()
                    param += "ids=" + xz;
                    if (i < $xz.length - 1) {
                        param += "&"
                    }
                }
                window.location.href = "workbench/activity/exportActivityById.do?" + param;
            })
            $("#importActivityBtn").click(function () {
                var fileName = $("#activityFile").val();
                var suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
                if (suffix != "xls" && suffix != "xlsx") {
                    alert("上传文件类型错误")
                } else {
                    var activityFile = $("#activityFile")[0].files[0];
                    if (activityFile.size > 1024 * 1024 * 5) {
                        alert("文件体积不能大于5M")
                    }
                    var formData = new FormData();
                    formData.append("myFile", activityFile);
                    $.ajax({
                        url:"workbench/activity/importActivityById.do",
                        data:formData,
                        type:"post",
                        dataType:"json",
                        processData:false,
                        contentType:false,
                        success:function (data) {
                            if (data.success){
                                alert('成功上传了'+data.count+'条记录！');
                               pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
                               $("#activityFile").val("");
                                $("#importActivityModal").modal("hide");
                            } else {
                                alert("上传失败");
                            }

                        }
                    })
                }

            })


        });

        function pageList(pageNo, pageSize) {
            $.ajax({
                url: "workbench/activity/pageList2.do",
                data: {
                    "pageNoStr": pageNo,
                    "pageSizeStr": pageSize,
                    "name": $.trim($("#search-name").val()),
                    "owner": $.trim($("#search-owner").val()),
                    "startDate": $.trim($("#search-startDate").val()),
                    "endDate": $.trim($("#search-endDate").val())
                },
                dataType: "json",
                type: "get",
                success: function (data) {
                    var html = "";
                    $.each(data.alist, function (i, n) {
                        html += '<tr class="active">';
                        html += '<td><input type="checkbox" name="xz" id="id" value="' + n.id + '"/></td>';
                        html += '<td><a style="text-decoration: none; cursor: pointer;"   onclick="window.location.href=\'workbench/activity/toDetail.do?id='+n.id+'\';">' + n.name + '</a></td>';
                        html += '<td>' + n.owner + '</td>';
                        html += '<td>' + n.startDate + '</td>';
                        html += '<td>' + n.endDate + '</td>';
                        html += '</tr>';
                    })
                    $("#activitytbody").html(html);
                    //================================
                    //以下用bootstrap来分页显示数据
                    $("#hidden-name").val($("#search-name").val());
                    $("#hidden-owner").val($("#search-owner").val());
                    $("#hidden-startdate").val($("#search-startDate").val());
                    $("#hidden-name").val($("#search-endDate").val());
                    var totalPages = data.total % pageSize == 0 ? data.total / pageSize : parseInt(data.total / pageSize) + 1;
                    $("#activityPage").bs_pagination({
                        currentPage: pageNo, // 页码
                        rowsPerPage: pageSize, // 每页显示的记录条数
                        maxRowsPerPage: 20, // 每页最多显示的记录条数
                        totalPages: totalPages, // 总页数
                        totalRows: data.total, // 总记录条数

                        visiblePageLinks: 2, // 显示几个卡片

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,

                        /*

                            这个函数，是在我们点击分页组件的时候触发的

                         */
                        onChangePage: function (event, data) {
                            $("#search-name").val($("#hidden-name").val());
                            $("#search-owner").val($("#hidden-owner").val());
                            $("#search-startDate").val($("#hidden-startdate").val());
                            $("#search-endDate").val($("#hidden-name").val());
                            pageList(data.currentPage, data.rowsPerPage);
                        }
                    });


                }
            })

        }

    </script>
</head>
<body>

<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form" id="saveActivityForm">

                    <div class="form-group">
                        <label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-owner">
                            </select>
                        </div>
                        <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" readonly class="form-control time" id="create-startDate">
                        </div>
                        <label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" readonly class="form-control time" id="create-endDate">
                        </div>
                    </div>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-description"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="saveActivityBut">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">

                    <div class="form-group">
                        <input type="hidden" id="edit-id" value="">
                        <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="edit-marketActivityOwner">
                            </select>
                        </div>
                        <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-marketActivityName" value="">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-startTime" value="">
                        </div>
                        <label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-endTime" value="">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-cost" value="">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="edit-describe"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="updateActivityBut">更新</button>
            </div>
        </div>
    </div>
</div>

<!-- 导入市场活动的模态窗口 -->
<div class="modal fade" id="importActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
            </div>
            <div class="modal-body" style="height: 350px;">
                <div style="position: relative;top: 20px; left: 50px;">
                    请选择要上传的文件：
                    <small style="color: gray;">[仅支持.xls或.xlsx格式]</small>
                </div>
                <div style="position: relative;top: 40px; left: 50px;">
                    <input type="file" id="activityFile">
                </div>
                <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;">
                    <h3>重要提示</h3>
                    <ul>
                        <li>操作仅针对Excel，仅支持后缀名为XLS/XLSX的文件。</li>
                        <li>给定文件的第一行将视为字段名。</li>
                        <li>请确认您的文件大小不超过5MB。</li>
                        <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                        <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                        <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                        <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                    </ul>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>市场活动列表</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" type="text" id="search-name">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" type="text" id="search-owner">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input class="form-control time" type="text" id="search-startDate"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input class="form-control time" type="text" id="search-endDate">
                    </div>
                </div>

                <button type="button" class="btn btn-default" id="findActivityByCondition">查询</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" class="btn btn-primary" id="toSaveActivityBut"><span
                        class="glyphicon glyphicon-plus"></span> 创建
                </button>
                <button type="button" class="btn btn-default" id="toUpdateActivityBut" data-toggle="modal"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button type="button" class="btn btn-danger" id="delActivityBtu"><span
                        class="glyphicon glyphicon-minus"></span> 删除
                </button>
            </div>
            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" class="btn btn-default" id="importActivityBut" data-toggle="modal"
                        data-target="#importActivityModal">
                    <span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）
                </button>
                <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span
                        class="glyphicon glyphicon-export"></span> 下载列表数据（全部导出）
                </button>
                <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span
                        class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）
                </button>
            </div>
        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <div>
                    <input type="hidden" id="hidden-name"/>
                    <input type="hidden" id="hidden-owner"/>
                    <input type="hidden" id="hidden-startdate"/>
                    <input type="hidden" id="hidden-enddate"/>
                </div>
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input id="qx" type="checkbox"/></td>
                    <td>名称</td>
                    <td>所有者</td>
                    <td>开始日期</td>
                    <td>结束日期</td>
                </tr>
                </thead>
                <tbody id="activitytbody">
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