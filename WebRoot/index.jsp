<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>工具开发平台</title>
	<style type="text/css" media="screen">
      html,body {
	    height: 100%;
        }

body {
	margin: 0;
	padding: 0;
	overflow: auto;
	text-align: center;
	background-color: #f6f9fe;
	font: 14px/24px "Microsoft YaHei","Arial",sans-serif;
    color: #222222;
}

#flashContent {
	display: none;
}
.title{
	padding: 50px;
}

button,input,select,textarea {
    font:12px \5b8b\4f53,arial,sans-serif;
}
input,select,textarea {
    font-size:100%;
    width: 380px;
    outline: 0 none;border: 1px solid #C5C5C5;height: 40px;line-height: 30px;vertical-align: middle;border-radius: 3px;-webkit-border-radius: 3px;-moz-border-radius: 3px;box-shadow: 0 1px 1px rgba(0, 0, 0, 0.06) inset;-webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.06) inset;padding: 0 10px;
}
/* 去掉 table cell 的边距并让其边重合 */
table {
    border:1px;
    width: 100%;
}
/* ie bug：th 不继承 text-align */
th {
    text-align:inherit;
}
.main{
	width:100%;
	height:500px; 
	background:#B3D0F0 no-repeat;
	text-align: center;
}
.table_{
	width: 600px;
	left:50%; 
	margin: 50px 0 0 -250px;
	position:absolute;
}

.table_ tr{
	height: 62px;
}

.table_ .right{
	text-align: right;
	width: 100px;
	padding-right: 30px;
}
.btn {border-radius: 5px;-webkit-border-radius: 5px;-moz-border-radius: 5px;cursor: pointer;display: inline-block;font-size: 14px;height: 35px;line-height: 35px;overflow: visible;padding: 0 26px;text-align: center;text-decoration: none;vertical-align: middle;}

.tool_res{
	
}
</style>
<script type="text/javascript" src="jquery-1.7.2.min.js"></script>
<script>
	function submitAction(){
		var form = document.getElementById("form1");
		form.action = "runTool";
		form.method = "post";
		form.target = "_blank";
		form.submit();
	}
</script>

  <body>
    <div class="title">
    	<h1>工具开发调试环境</h1>
    </div>
	<div>
		<lable>注意：工具名称一定要与config.xml配置文件中的ToolName一致</lable>
	</div>
	<div class="main">
	<div class="table_">
		<form id="form1">
			<table>
				<tr>
					<td class="right">工具名:</td>
					<td><input type="text" name="toolName" id="toolName">* </td>
				</tr>
				<tr>
					<td class="right">工具的操作平台</td>
					<td>
						<select type="text" name="oPlatform" id="oPlatform">
							<option value="0">PC端工具</option>
							<option value="1">移动端工具 </option>
							<option value="2">无用户操作</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="right">工具的执行平台</td>
					<td>
						<select type="text" name="rPlatform" id="rPlatform">
							<option value="0">终端</option>
							<option value="1">后端（服务器端）</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="right">工具类型:</td>
					<td>
						<select type="text" name="runType" id="runType">
							<option value="0">Java有页面工具（jsp+java）</option>
							<option value="1">纯Java无页面工具</option>
							<option value="2">纯页面工具（Jsp）</option>
							<option value="3">PHP工具</option>
							<option value="4">python工具</option>
							<option value="5">C工具</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="right">用户名:</td>
					<td><input type="text" name="userAccount" id="userAccount"></td>
				</tr>
				<tr>
					<td class="right">工具运行参数:</td>
					<td><input type="text" name="param" id="param"></td>
				</tr>
			</table>
			<input type="hidden" name="here" value="0"/><br/>
			<hr>
			<button class="btn" onclick="submitAction();">开发运行</button>
		</form>
	</div>
	</div>
	<div class="tool_res">
		<span id="tool_res"></span>
	</div>
  </body>
</html>
