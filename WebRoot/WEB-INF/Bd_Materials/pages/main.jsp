<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<script type="text/javascript">
	var fyToolUrl = '${fyToolUrl}';
</script>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>物料信息管理工具</title>
	<link rel="stylesheet" href="${resourceUrl}/js/bootstrap-3.3.5/css/bootstrap.min.css">
	<link rel="stylesheet" href="${resourceUrl}/css/font-awesome.css">
	<link rel="stylesheet" href="${resourceUrl}/css/reset.css">
</head>
<body>
	<div class="header" style="position:relative;cursor:pointer;">
	</div>


	<div class="Yahei showNav" style="width:23px;height:100px;position:fixed;left:0;top:50%;margin-top:-50px;background:#377ab7;color:#fff;border-radius:0 14px 14px 0;cursor:pointer;padding-top:9px;padding-left:2px;z-index:999;">
		显示导航
	</div>

	<div class="content">

		<div class="content-nav" style="">
			<h4 style="margin-top:5px;margin-bottom:15px;font-size:18px;font-family:'楷体';font-weight:bold;cursor:pointer;">物料信息管理工具</h4>
			<div style="text-align:left;width: 100%;">
				<a href="${fyForwardUrl}&forwardPage=typeManage.jsp" target="_blank" style="display:inline-block;text-decoration: none;padding:4px 8px;border-radius:4px;color:#fff;background:rgb(66,185,131);margin-bottom:15px;">
					<span class="fa fa-cog fa-spin"></span> 类别管理
				</a>
			</div>
			<ul class="tree" style="padding-left:0;width:240px;padding-bottom:30px;">
			  <li><a href="##" class="active" style="background:#337ab7;color:#fff;"><span class="fa fa-plus-square"></span> 物料</a>
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

		<div class="content-main">
 
			<div class="tools" style="">
				<div class="search col-md-2" style="padding:0;">
					<form class="form-inline">
					  <div class="input-group" style="font-weight:normal;">
					    <input type="text" class="form-control" id="filter" placeholder="请输入搜索内容" onkeypress="if(event.keyCode == 13) return false;">
					    <span class="input-group-btn">
					        <button type="button" id="search" class="btn btn-primary">搜索 </button>
					    </span>
					  </div>
					  <!-- <button type="button" id="search" class="btn btn-primary" style="margin-left:4px;">搜索 </button> -->
					 
					  
					</form>

				</div>
				<!-- <div class="clear"></div> -->

				<div class=" dropdown sort-kucun" style="padding:0;margin-left:20px;float:left;">
				  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
				    <span class="fa fa-search"> </span>
				     <b style="font-weight:normal;">所有库存分类</b> 
				    <span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu" id="kucun_ul" aria-labelledby="dropdownMenu1" >  
				  	<li data-id=""><a href="#">所有库存分类 </a></li>
				    <li role="separator" class="divider"></li> 
				    <li data-id="5"><a href="#">介于最低和最高库存</a></li>
				    <li data-id="1"><a href="#" >高于最高库存</a></li>
				    <li data-id="4"><a href="#">介于最高和安全库存</a></li>
				    <li data-id="2"><a href="#" >低于最低库存</a></li>
				    <li data-id="3"><a href="#" >介于最低和安全库存</a></li>
				    
				  </ul>

				</div>

				<div class=" btn-group importDIV Yahei" role="group" style="float:left;padding:0;margin-top:-2px;margin-left:20px">
					<label class="file" style="">
						
							<form id="importForm">
								<span style="float:left;margin-top:8px;font-weight:normal;margin-right:6px;">导入</span>			
								<!-- <button type="button" id="importBtn" class="btn btn-default">导入 </button> -->
		  						<input type="file" class="btn btn-default" id="file_import" name="file_import" value="导入" style="float:left;width:240px;">
		  						<input type="submit" value="提交"  id="importSubmit" style="display:none;" />
		  					
		  					</form>
		  			
					</label>	
				</div>

				<div class="clear_op" style="width:1px;"></div>

				<div class=" btn-group  Yahei" role="group" style="float:left;margin-left:20px"">
					
					<button type="button" id="download_tem" class="btn btn-default"><a href="${resourceUrl}/images/导入物料信息模版.xls" style="text-decoration:none;color:#333;">下载模板</a></button>
				</div>

				<div class="btn-group exportDIV Yahei" role="group" style="float:left;margin-left:20px;">
					
					<button type="button" id="export" class="btn btn-default">导出 </button>
				</div>


				<div class="col-md-1 dropdown show_page_div " style="margin-left:20px;padding:0;float:left;">
				  <form class="form-inline" role="form">
					<div class="form-group">
						<div class="input-group" >
						    <span class="input-group-addon">每页</span>
						 	<input type="text" class="form-control" id="show_page" value="10" style="min-width:50px;" onkeypress="if(event.keyCode == 13) return false;">
						 	<span class="input-group-addon">条</span>
					 	</div>
					</div>
				  </form>
				</div>

				<div class="operation" style="float:right;">
					<!-- <button type="button" class="btn btn-success" id="addBtn"><span class="fa fa-plus"></span> 新增</button>
					<button type="button" class="btn btn-primary" id="modifyBtn">新增</button>
					<button type="button" class="btn btn-danger" id="addBtn">新增</button> -->
					<ul class="pull-right" style="">
						<li title="新增物料" id="add-li-btn"><span class="glyphicon glyphicon-plus text-success"></span></li>
						<li title="修改物料" id="edit-li-btn"><span class="glyphicon glyphicon-edit text-primary"></span></li>
						<li title="删除物料" id="del-li-btn"><span class="glyphicon glyphicon-remove text-danger"></span></li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>

			<div class="tableDIV">
				<table class="table table-bordered tableNeed">
				 	<thead>
				 		<tr>
				 			<th><input type="checkbox" class="check-del" aria-label="..."></th>
				 			<th>物料ID</th>
				 			<th>名称</th>
				 			<th>类别</th>
				 			<th>单位</th>
				 			<th>规格</th>
				 			<th>型号</th>
				 			<th>来源</th>
				 			<th>库存</th>
				 			<th>最低库存</th>
				 			<th>最高库存</th>
				 			<th>安全库存</th>
				 			<th>参考单价</th>
				 			<th>货币</th>
				 			<th style="max-width:100px;">备注</th>
				 		</tr>
				 	</thead>

				 	<tbody>
				 		<tr>
				 		</tr>
				 	</tbody>
				</table>
				<div  id="page1" align="right" style="margin-bottom:20px;margin-top:30px;">
				</div>
			</div>


		</div>

		<div class="content-main-add" style="display:none;">
			<div class="panel panel-default">		
				<div class="panel-heading">
					物料详情
					<!-- <a href="##" class="pull-right" ><span class="fa fa-times text-danger" style="font-size:20px;"></span></a> -->
				</div>
				<div class="panel-body">
			      	  <form class="form-horizontal" id="modifyForm">
			      	  	<div class="form-group">
			      	  		  <label for="description" class="col-md-2 control-label" style="font-weight:normal;"><span class="text-danger">* </span> 名称</label>
			      	  		  <div class="col-md-4">
			      	  		    <input type="text" class="form-control" id="description" name="description" >
			      	  		  </div>

			    	  	  	  <label for="materialsId" class="col-md-2 control-label" style="font-weight:normal;">物料ID</label>
			    	  	  	  <div class="col-md-3">
			    	  	  	    <input type="text" class="form-control" name="materialsId" id="materialsId" >
			    	  	  	  </div>


			    	  	</div>
			    	  	  	
<!-- 			    	  	<div class="form-group">
			    	  	  	  <label for="whType" class="col-md-2 control-label" style="font-weight:normal;">名称</label>
			    	  	  	  <div class="col-md-3">
			    	  	  	    <input type="text" class="form-control" id="whType" name="whType" >
			    	  	  	  </div>
			      	  	</div> -->

			      	  	<div class="form-group">
			    	  	  	  <label for="bdType" class="col-md-2 control-label" style="font-weight:normal;">类别</label>
			    	  	  	  <div class="col-md-4">
			    	  	  	  	<div class="input-group">
			    	  	  	  		<!-- <input type="text" class="form-control" id="bdType" > -->
			    	  	  	  		<select class="form-control" id="bdType">
			    	  	  	  		</select>
			    	  	  	    	<span class="input-group-btn">
			    	  	  	           <button type="button" class="btn btn-default" id="bdType-btn" >添加</button>
			    	  	  	        </span>
			    	  	  	  	</div>
			    	  	  	  </div>

			    	  	  	  <label for="" class="col-md-2 control-label" style="font-weight:normal;">单位</label>
			    	  	  	  <div class="col-md-3">
			    	  	  	  	<div class="input-group">
			    	  	  	  		<!-- <input type="text" class="form-control" id="bdUnit" > -->
			    	  	  	  		<select class="form-control" id="bdUnit" >
			    	  	  	  		</select>
			    	  	  	    	<span class="input-group-btn">
			    	  	  	           <button type="button" class="btn btn-default" id="bdUnit-btn" >添加</button>
			    	  	  	        </span>
			    	  	  	  	</div>
			    	  	  	  </div>
			    	  	</div>

	  	      	  		<div class="form-group add-type-unit" style="display:none;">
	  		    	  	  	   <label for="type-name" class="col-md-2 control-label" style="font-weight:normal;display:none;"><span class="text-danger">* </span> 类别名称</label>
	  		    	  	  	  <div class="col-md-4" style="display:none;">
	  		    	  	  	  	<div class="input-group">
	  		    	  	  	  		<div class="input-group-btn type_father">
  		    	  	  	  		        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >无上级 <span class="caret"></span></button>
  		    	  	  	  		        <ul class="dropdown-menu" style="height:400px;overflow:auto;">
  		    	  	  	  		          <li><a href="##">无上级</a></li>
  		    	  	  	  		          <li role="separator" class="divider"></li>
  		    	  	  	  		        </ul>
  		    	  	  	  		    </div>
	  		    	  	  	  		<span class="input-group-addon" id="sizing-addon1">/</span>
	  		    	  	  	  		<input type="text" class="form-control" id="type-name" placeholder="先选上级目录">
	  		    	  	  	  	</div>
	  		    	  	  	  	
	  		    	  	  	  </div>

	  		    	  	  	   <label for="unit-name" class="col-md-2 control-label" style="font-weight:normal;display:none;"><span class="text-danger">* </span> 单位名称</label>
	  		    	  	  	  <div class="col-md-3" style="display:none;">
	  		    	  	  	    <input type="text" class="form-control" id="unit-name">
	  		    	  	  	  </div>
	  		      	  	</div>
			    	  	  	

			      	  	<div class="form-group">
			    	  	  	  <!-- <label for="" class="col-md-2 control-label" style="font-weight:normal;">规格</label>
			    	  	  	  <div class="col-md-4">
			    	  	  	  	<div class="input-group"> -->
			    	  	  	  		<!-- <input type="text" class="form-control" id="bdSpec" > -->
			    	  	  	  		<!-- <select class="form-control" id="bdSpec" >
			    	  	  	  		</select>
			    	  	  	    	<span class="input-group-btn">
			    	  	  	           <button type="button" class="btn btn-default" id="bdSpec-btn" >添加</button>
			    	  	  	        </span>
			    	  	  	  	</div>
			    	  	  	  </div> -->
			    	  	  	  <!-- <button type="button" class="btn btn-primary" id="" style="margin-left:10px;">添加</button> -->

			    	  	  	  <label for="" class="col-md-2 control-label" style="font-weight:normal;">型号</label>
			    	  	  	 <div class="col-md-4">
			    	  	  	 	<div class="input-group">
			    	  	  	 		<!-- <input type="text" class="form-control" id="bdModel" > -->
			    	  	  	 		<select class="form-control" id="bdModel" >
			    	  	  	 		</select>
			    	  	  	   	<span class="input-group-btn">
			    	  	  	          <button type="button" class="btn btn-default" id="bdModel-btn" >添加</button>
			    	  	  	       </span>
			    	  	  	 	</div>
			    	  	  	 </div>

			    	  	  	  <label for="spec-name" class="col-md-2 control-label" style="font-weight:normal;"> 规格名称</label>
			    	  	  	  <div class="col-md-3">
			    	  	  	    <input type="text" class="form-control" id="spec-name">
			    	  	  	  </div>

			    	  	  	  
			      	  	</div>

		      	  		<div class="form-group add-spec-model" style="display:none;">
			    	  	  	  <!--  <label for="spec-name" class="col-md-2 control-label" style="font-weight:normal;display:none;"> 规格名称</label>
			    	  	  	  <div class="col-md-4" style="display:none;">
			    	  	  	    <input type="text" class="form-control" id="spec-name">
			    	  	  	  </div> -->

			    	  	  	   <label for="model-name" class="col-md-2 control-label" style="font-weight:normal;display:none;"><span class="text-danger">* </span> 型号名称</label>
			    	  	  	  <div class="col-md-4" style="display:none;">
			    	  	  	    <input type="text" class="form-control" id="model-name">
			    	  	  	  </div>
			      	  	</div>

			   

			      	  	<div class="form-group">
			    	  	  	  <label for="" class="col-md-2 control-label" style="font-weight:normal;">来源</label>
			    	  	  	  <div class="col-md-4"><!-- 
			    	  	  	    <input type="text" class="form-control" id="source" > -->
			    	  	  	    <select class="form-control" id="source">
			    	  	  	      <option>采购</option>
			    	  	  	      <option>机加工</option>
			    	  	  	    </select>
			    	  	  	  </div>
<!-- 
			    	  	  	  <label for="" class="col-md-2 control-label" style="font-weight:normal;">库存</label>
			    	  	  	  <div class="col-md-3">
			    	  	  	    <input type="text" class="form-control" id="currentStock" >
			    	  	  	  </div> -->
			      	  	</div>

			      	  	<!-- <div class="form-group">
			    	  	  	  <label for="" class="col-md-2 control-label" style="font-weight:normal;">库存</label>
			    	  	  	  <div class="col-md-3">
			    	  	  	    <input type="text" class="form-control" id="address" >
			    	  	  	  </div>
			      	  	</div> -->

			      	  	<div class="form-group">
			    	  	  	  <label for="" class="col-md-2 control-label" style="font-weight:normal;"><span class="text-danger">* </span> 最低库存</label>
			    	  	  	  <div class="col-md-4">
			    	  	  	    <input type="text" class="form-control" id="minStock" >
			    	  	  	  </div>

			    	  	  	   <label for="" class="col-md-2 control-label" style="font-weight:normal;"><span class="text-danger">* </span> 最高库存</label>
			    	  	  	  <div class="col-md-3">
			    	  	  	    <input type="text" class="form-control" id="maxStock" >
			    	  	  	  </div>
			      	  	</div>

			      <!-- 	  	<div class="form-group">
			    	  	  	  <label for="" class="col-md-2 control-label" style="font-weight:normal;">最高库存</label>
			    	  	  	  <div class="col-md-3">
			    	  	  	    <input type="text" class="form-control" id="address" >
			    	  	  	  </div>
			      	  	</div> -->

			      	  	<div class="form-group">
			    	  	  	  <label for="" class="col-md-2 control-label" style="font-weight:normal;"><span class="text-danger">* </span> 安全库存</label>
			    	  	  	  <div class="col-md-4">
			    	  	  	    <input type="text" class="form-control" id="safeStock" >
			    	  	  	  </div>

			    	  	  	   <label for="" class="col-md-2 control-label" style="font-weight:normal;"><span class="text-danger">* </span> 参考单价</label>
			    	  	  	  <div class="col-md-3">
			    	  	  	    <input type="text" class="form-control" id="price" >
			    	  	  	  </div>
			      	  	</div>
<!-- 
			      	  	<div class="form-group">
			    	  	  	  <label for="" class="col-md-2 control-label" style="font-weight:normal;">参考单价</label>
			    	  	  	  <div class="col-md-3">
			    	  	  	    <input type="text" class="form-control" id="address" >
			    	  	  	  </div>
			      	  	</div> -->

			      	  	<div class="form-group">
			    	  	  	  <label for="" class="col-md-2 control-label" style="font-weight:normal;"><span class="text-danger">* </span> 货币</label>
			    	  	  	  <div class="col-md-4">
			    	  	  	  	<div class="input-group">
			    	  	  	  		<!-- <input type="text" class="form-control" id="bdCurrency" > -->
			    	  	  	  		<select class="form-control" id="bdCurrency" >
			    	  	  	  		</select>
			    	  	  	    	<span class="input-group-btn">
			    	  	  	           <button type="button" class="btn btn-default" id="bdCurrency-btn" >添加</button>
			    	  	  	        </span>
			    	  	  	  	</div>
			    	  	  	  </div>
			      	  	</div>

			      	  	<div class="form-group add-currency" style="display:none;">
	  		    	  	  	   <label for="currency-name" class="col-md-2 control-label" style="font-weight:normal;display:none;"><span class="text-danger">* </span> 货币名称</label>
	  		    	  	  	  <div class="col-md-4" style="display:none;">
	  		    	  	  	    <input type="text" class="form-control" id="currency-name">
	  		    	  	  	  </div>
	  		      	  	</div>

			      	  	<div class="form-group">
			    	  	  	  <label for="" class="col-md-2 control-label" style="font-weight:normal;">备注</label>
			    	  	  	  <div class="col-md-9">
			    	  	  	    <textarea type="text" class="form-control" id="remark" rows="4" ></textarea>
			    	  	  	  </div>
			      	  	</div>
			      	  </form>

				</div>
				<div class="panel-footer">
					<div class="col-md-offset-2">
						<button type="button" class="btn btn-primary" id="saveBtn" style="">保存</button><!-- 
						<button type="button" class="btn btn-primary" id="modifyBtn" style="margin-left:10px;">修改</button> -->
						<button type="button" class="btn btn-danger" id="deleteBtn" style="display:none;margin-left:10px;">删除</button>
						<button type="button" class="btn btn-default" id="prevPage" style="margin-left:10px;">返回列表</button>
					</div>
					<div class="clear">
						
					</div>
				</div>
			</div>
		</div>
		
		
	</div>

	<button type="button" id="import_err_Btn" class="btn btn-default comFirmModal hide" data-toggle = "modal" data-target = "#myModal"></button>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog modal-lg" role="document" style="width:95%;">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title text-primary" id="myModalLabel"><span class="fa fa-warning"></span> 以下为失败项以及错误信息：
		        	&nbsp;&nbsp;<b></b>
		        </h4>
		      </div>
		      <div class="modal-body " style="padding-left:20px;padding-right:20px;padding-bottom:0;height:460px;overflow:auto;">
		        
		      	<table class="table table-bordered tableInsert">
				 	<thead>
				 		<tr>
				 			<th>名称</th>
				 			<th>类别</th>
				 			<th>单位</th>
				 			<th>规格</th>
				 			<th>型号</th>
				 			<th>来源</th>
				 			<th>最低库存</th>
				 			<th>最高库存</th>
				 			<th>安全库存</th>
				 			<th>参考单价</th>
				 			<th>货币</th>
				 			<th>错误信息</th>
				 		</tr>
				 	</thead>

				 	<tbody>
				 	</tbody>
				 
				</table>
		      </div>
		      <div class="modal-footer">
		     	<button type="button" class="btn btn-primary confirmSelect" data-dismiss="modal">确定</button><!-- 
		        <button type="button" class="btn btn-default cancel" data-dismiss="modal">取消</button> -->

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
	<script src="${resourceUrl}/js/main.js"></script>
</body>
</html>