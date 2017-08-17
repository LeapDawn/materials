<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<script type="text/javascript">
	var fyToolUrl = '${fyToolUrl}';
</script>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title></title>
	<link rel="stylesheet" href="${resourceUrl}/js/bootstrap-3.3.5/css/bootstrap.min.css">
	<link rel="stylesheet" href="${resourceUrl}/css/font-awesome.css">
	<link rel="stylesheet" href="${resourceUrl}/css/reset.css">
	<style type="text/css">
		.tree ul{
			padding-left: 50px;
		}
/*		.content-1 .content-lf ul ul{
			padding-left: 26px;
		}*/
		.tree a{
			line-height: 24px;
			padding: 4px 8px;
			border-radius: 3px;
			text-decoration: none;
		}
	</style>
</head>
<body>
	<div class="header" style="position:relative;cursor:pointer;">
	</div>

	<div class="content-1" style="width: 96%;margin:20px auto;">
		<div class="content-lf" style="width:400px;float: left;">
			<h4 style="padding-left: 20px;font-size: 20px;margin-bottom: 20px;">物料工具——类别管理</h4>
			<ul class="tree" style="padding-left: 20px;">
			  <li><a href="##" id="" class="active" style="background:#337ab7;color:#fff;"><span class="fa fa-plus-square"></span> 物料</a>
			    <ul>
			      <!-- <li><a href="#">物料1</a>
			        <ul>
			          <li><a href="#">物料1</a></li>
			          <li><a href="#">物料1</a></li>
			          <li><a href="#">物料1</a></li>
			        </ul>
			      </li>
			      <li><a href="#">物料2</a>
			        <ul>
			          <li><a href="#">物料2</a></li>
			          <li><a href="#">物料2</a></li>
			          <li><a href="#">物料2</a></li>
			        </u	l>
			      </li> -->
			    </ul>
			  </li>
			</ul>
		</div>
		<div class="content-rt" style="float: left;">
			<div class="alert alert-info" role="alert" style="margin-bottom: 40px;">
				请先点击选择目录，再进行新增、重命名、删除操作！ *新增：选择父级新增子级目录!
			</div>
			<div class="btn-group-1" style="font-family: '微软雅黑';">
				<button type="button" class="btn btn-success add-btn" style="margin-right: 5px;"><span class="fa fa-plus"></span> 新增</button>
				<button type="button" class="btn btn-primary edit-btn" style="margin-right: 5px;"><span class="fa fa-edit"></span> 重命名</button>
				<button type="button" class="btn btn-danger del-btn" style="margin-right: 35px;"><span class="fa fa-minus"></span> 删除</button>
				<a style="display:inline-block;" href="${fyForwardUrl}&forwardPage=main.jsp" target="_blank"><button type="button" class="btn btn-default" style=""><span class="fa fa-cog fa-spin"></span> 返回物料管理</button></a>
			</div>
			
			<div class="input-div" style="margin-top: 20px;display: none;">
			   <div class="input-group" style="width: 380px;">
			     <input type="text" class="form-control" id="filter">
			     <span class="input-group-btn">
			       <button class="btn btn-default" type="button" id="confirm-input">确定</button>
			     </span>
			   </div>
			</div>

		</div>
	</div>
	
	<script src="${resourceUrl}/js/jquery.min.js"></script>
	<script src="${resourceUrl}/js/jquery.form.js"></script>
	<script src="${resourceUrl}/js/bootstrap-3.3.5/js/bootstrap.min.js"></script>
	<script src="${resourceUrl}/js/layer/layer.js"></script>
	<script src="${resourceUrl}/js/laypage/laypage.js"></script>
	<script src="${resourceUrl}/js/publicDom.js"></script>
	<script src="${resourceUrl}/js/typeManage.js"></script>
</body>
</html>