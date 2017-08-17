var publicDom = {

	//配置项
	config:{
		rootUrl: "",
//		url: "http://192.168.253.215:8080/Bd_Materials/actRunTool?" // 请求接口的url
		// url:"http://192.168.199.136:8080/Bd_Materials/actRunTool?"
		url: fyToolUrl + "&"
	},

	getData: function(type, url, param, f, isAsync, err) {
		var ajaxParam  = {
			type: type,
			url: url,
			data: param,
			async: isAsync !== false,
			success: function(data) {				
				var jsonData;
				try{
					jsonData = $.parseJSON(data);
				}catch(e) { // 返回值不是json格式
					if(typeof f !== 'function') { // 如果没有回调函数,抛出异常。
						throw new Error('请求数据之后，没有回调函数!');
					}
					f(data);
					return;
				}
				f(jsonData);
			},
			error: function(xhr,textStatus) {
				// 
			}
		};
		$.ajax(ajaxParam);
	}
};