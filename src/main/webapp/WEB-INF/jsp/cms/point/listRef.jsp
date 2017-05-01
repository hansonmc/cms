<%@page language="java" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>卖点图选择列表<%--author:gzz_gzz@163.com,DATE:2016-09-10 17:29:50--%></title>
</head>
<body>
	<form:form id="form1" method="post" commandName="cond">
		<div class="ti">卖点图查询</div>
		<table class="SearchTab">
			<tr>
				<td align="right">名称</td>
				<td><form:input path="name_c" class="text-m" /></td>
				<td align="right">显示文字</td>
				<td><form:input path="text_c" class="text-m" /></td>
				<td align="right">顺序</td>
				<td><form:input path="order_num_c" class="text-m" /></td>
				<td align="right">存储路径</td>
				<td><form:input path="picture_path_c" class="text-m" /></td>
			</tr>
			<tr>
				<td align="right">访问地址</td>
				<td><form:input path="picture_url_c" class="text-m" /></td>
				<td align="right">跳转地址</td>
				<td><form:input path="jump_url_c" class="text-m" /></td>
				<td align="right">备注</td>
				<td><form:input path="remark_c" class="text-m" /></td>
				<td align="right">状态</td>
				<td><form:input path="status_c" class="text-m" /></td>
			</tr>
			<tr>
				<td align="right">所属终端</td>
				<td><form:input path="type_c" class="text-m" /></td>
				<td align="right">时间戳</td>
				<td><form:input path="ts_c" format="yyyy-MM-dd HH:mm:ss" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  class="text-m" /></td>
				<td></td>
				<td></td>
				<td></td>
				<td>
					<input type="button" icon="icon-search" value="查询" onclick="toQuery();" /> 
					<input type="button" icon="icon-reload" value="清空"	onclick="toClear();" />
				</td>
			</tr>
		</table>
		<table class="titleTab">
			<tr>
				<td class="ti">卖点图选择列表</td>
				<td class="bu">
					<input type="button" icon="icon-add" value="确定" onclick="toValue();" />
				</td>
			</tr>
		</table>
		<table class="dataTab">
			<tr>
				<th><%--<input type="checkbox" id="chkAll" onclick="checkAll();">--%>序号</th>
				<th>名称</th>
				<th>显示文字</th>
				<th>顺序</th>
				<th>存储路径</th>
				<th>访问地址</th>
				<th>跳转地址</th>
				<th>备注</th>
				<th>状态</th>
				<th>所属终端</th>
				<th>时间戳</th>
			</tr>
			<c:forEach items="${dataList}" var="wz" varStatus="vs">
				<tr>
					<td class="sqe_w" ><input type="radio" name="chk" value="${wz.id}" title="${wz.name}" /> <u:sequence index="${vs.count}" /></td>
					<td>${wz.name}</td>
					<td>${wz.text}</td>
					<td>${wz.order_num}</td>
					<td>${wz.picture_path}</td>
					<td>${wz.picture_url}</td>
					<td>${wz.jump_url}</td>
					<td>${wz.remark}</td>
					<td>${wz.status}</td>
					<td>${wz.type}</td>
					<td><fmt:formatDate value="${wz.ts}" type="both" /></td>
				</tr>
			</c:forEach>
		</table>
		<jsp:include page="/WEB-INF/jsp/page.jsp" />
	</form:form>
</body>
<script type="text/javascript">
	function toQuery() {//按条件查询
		toAction("${webPath}/point/ref?rname=${param.rname}&rvalue=${param.rvalue}");
	}
	function toClear() {//清空查询条件
		clearCond(['name_c','text_c','order_num_c','picture_path_c','picture_url_c','jump_url_c','remark_c','status_c','type_c','ts_c']);
		//toAction('${webPath}/point/list');//清空后直接查询全部记录
	}
	function toValue() {//反回值
		var id = $("input:radio[name='chk']:checked").val();
		var ename = $("input:radio[name='chk']:checked").attr('title');
		alert('${param.rvalue}');
		alert('${param.rname}');
		if (typeof (id) == "undefined") {
			alert("请选择记录!");
			return false;
		}
		$(parent.frames["center"].document).find('#${param.rvalue}').val(id);
		$(parent.frames["center"].document).find('#${param.rname}').val(ename);
		top.dhxWins.window("win001").close();
	}
</script>
</html>