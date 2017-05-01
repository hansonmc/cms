<%@page language="java" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>卖点图修改<%--author:gzz_gzz@163.com,DATE:2016-09-10 14:43:41--%></title>
</head>
<body>
	<form:form id="form1" method="post" commandName="point">
		<div class="ti">卖点图修改</div>
		<form:hidden path="id" />
		<table class="dataTab">
			<tr>
				<td class="right30">名称</td>
				<td class="left30"><form:input path="name" maxlength="255"  class="text-m" /><font color="#CE0000">*</font></td>
				<td class="left30_red" id="name_td"><form:errors path="name" /></td>
			</tr>
			<tr>
				<td class="right30">显示文字</td>
				<td class="left30"><form:input path="text" maxlength="255"  class="text-m" /><font color="#CE0000">*</font></td>
				<td class="left30_red" id="text_td"><form:errors path="text" /></td>
			</tr>
			<tr>
				<td class="right30">顺序</td>
				<td class="left30"><form:input path="order_num" maxlength="10"  class="text-m" /><font color="#CE0000">*</font></td>
				<td class="left30_red" id="order_num_td"><form:errors path="order_num" /></td>
			</tr>
			<tr>
				<td class="right30">存储路径</td>
				<td class="left30"><form:input path="picture_path" maxlength="255"  class="text-m" /><font color="#CE0000">*</font></td>
				<td class="left30_red" id="picture_path_td"><form:errors path="picture_path" /></td>
			</tr>
			<tr>
				<td class="right30">访问地址</td>
				<td class="left30"><form:input path="picture_url" maxlength="255"  class="text-m" /><font color="#CE0000">*</font></td>
				<td class="left30_red" id="picture_url_td"><form:errors path="picture_url" /></td>
			</tr>
			<tr>
				<td class="right30">跳转地址</td>
				<td class="left30"><form:input path="jump_url" maxlength="255"  class="text-m" /><font color="#CE0000">*</font></td>
				<td class="left30_red" id="jump_url_td"><form:errors path="jump_url" /></td>
			</tr>
			<tr>
				<td class="right30">备注</td>
				<td class="left30"><form:textarea path="remark" maxlength="512"  class="tarea" />
					<div id="remark_div">最大长度为512个字节</div></td>
				<td class="left30_red" id="remark_td"><form:errors path="remark" /></td>
			</tr>
			<tr>
				<td class="right30">状态</td>
				<td class="left30"><form:input path="status" maxlength="3"  class="text-m" /><font color="#CE0000">*</font></td>
				<td class="left30_red" id="status_td"><form:errors path="status" /></td>
			</tr>
			<tr>
				<td class="right30">所属终端</td>
				<td class="left30"><form:input path="type" maxlength="3"  class="text-m" /><font color="#CE0000">*</font></td>
				<td class="left30_red" id="type_td"><form:errors path="type" /></td>
			</tr>
			<tr>
				<td class="right30">时间戳</td>
				<td class="left30"><form:input path="ts" format="yyyy-MM-dd HH:mm:ss" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  class="text-m" /><font color="#CE0000">*</font></td>
				<td class="left30_red" id="ts_td"><form:errors path="ts" /></td>
			</tr>
		</table>
		<center class="btn_div">
			<input type="button" icon="icon-save" onclick="toSave();" value="保存"/>
			<input type="button" icon="icon-retry" onclick="toAction('${webPath}/point/list');" value="返回"/>
		</center>
	</form:form>
</body>
<script type="text/javascript">
	function toSave() {//更新记录
		//请修改或替换如下验证方法
		checkBlank([['name', '名称'],['text', '显示文字'],['order_num', '顺序'],['picture_path', '存储路径'],['picture_url', '访问地址'],
					['jump_url', '跳转地址'],['status', '状态'],['type', '所属终端'],['ts', '时间戳']]);
		if (count > 0) {
			count = 0;
			return false;
		}
		popupMasker();//弹出蔗遮罩层防止重复提交
		toAction("${webPath}/point/update");
	}
	//输入实时验证;1:文本框,下拉框2:日期框3:文本域
	realTimeCheck([[1,'name', '名称'],[1,'text', '显示文字'],[1,'order_num', '顺序'],[1,'picture_path', '存储路径'],[1,'picture_url', '访问地址'],
			[1,'jump_url', '跳转地址'],[3,'remark'],[1,'status', '状态'],[1,'type', '所属终端'],[2,'ts', '时间戳'],
		]);
</script>
</html>