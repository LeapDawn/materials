$(function () {

    M_table.getNav();

    bindEvent();
});

var M_table = {
    url:publicDom.config.url,
    currentListId:'',
    operation:-1,
    init:function(){},
    getNav:function(){},
    triggerTree:function(){},
    showConfirmModal:function(){}
}


var bindEvent = function () {

    $('.add-btn').click(function(event) {
        $('.input-div').show();
        M_table.operation=1;
    });

    $('.edit-btn').click(function(event) {
        if(M_table.currentListId==''){
            M_table.showConfirmModal('错误','success','不能修改该类别！');
        }else{
            $('.input-div').show();
            M_table.operation=2;
        }
        
    });

    $('.del-btn').click(function(event) {
        if(M_table.currentListId==''){
            M_table.showConfirmModal('错误','success','不能删除该分类！');
        }else{
            M_table.showConfirmModal('警告','delete','该操作将同时删除子目录, 是否确定删除？');
            $('.deleteConfirm').on('click', function() {
                var newURL = M_table.url + 'toolAction=deleteType';
                var param = {
                    typeId: M_table.currentListId
                }
                publicDom.getData('post',newURL,param,function(data){
                    if(data.state == true){
                        M_table.showConfirmModal('成功','success','删除成功！');
                        $('.confirm').click(function(event) {
                            location.reload();
                        });
                    }else{
                        M_table.showConfirmModal('错误','success',data.value);
                        $('.confirm').click(function(event) {
                            location.reload();
                        });
                    }
                })
            })
        }
    });

    $('#confirm-input').click(function(event) {

        if(M_table.operation==1){
            if($('#filter').val()==''){
                M_table.showConfirmModal('错误','success','添加名称不能为空！请重新输入！');
            }else{
                var newURL = M_table.url + 'toolAction=saveType';
                var param = {
                    description: $('#filter').val(),
                    superType: M_table.currentListId
                }
                publicDom.getData('post',newURL,param,function(data){
                    if(data.state == true){
                        M_table.showConfirmModal('成功','success','添加成功！');
                        $('.confirm').click(function(event) {
                            location.reload();
                        });
                    }else{
                        M_table.showConfirmModal('错误','success','添加失败！请重新添加！');
                    }
                })
            }
            
        }else if(M_table.operation==2){
            if($('#filter').val()==''){
                M_table.showConfirmModal('错误','success','修改名称不能为空！请重新输入！');
            }else{
                var newURL = M_table.url + 'toolAction=updateType';
                var param = {
                    description: $('#filter').val(),
                    typeId: M_table.currentListId
                }
                publicDom.getData('post',newURL,param,function(data){
                    if(data.state == true){
                        M_table.showConfirmModal('成功','success','修改成功！');
                        $('.confirm').click(function(event) {
                            location.reload();
                        });
                    }else{
                        M_table.showConfirmModal('错误','success','修改失败！请重新修改！');
                    }
                })
            }
        }
        
    });


}

M_table.triggerTree = function() {
    // $('.tree>li>a').trigger('click');
    // $('.tree>ul>a').trigger('click');
}


M_table.getNav = function() {
    var newURL = M_table.url + 'toolAction=getSelection';
    var param = {
        target:"type"
    }
    publicDom.getData('post',newURL,param,function(data){
        if(data.state == true){
            console.log(data.value);
            $('.tree>ul').remove();
            // var child_1 = '';

            // for( var i=0; i< data.value.length ; i++ ){
            //     var child_2 = '';
            //     for( var j=0; j<data.value.length; j++){
            //         var child_3 = '';
            //         for( var k=0; k<data.value.length; k++){
            //             if(data.value[k].superType == data.value[j].typeId){
            //                 var item_3 = '<li><a href="##" data-id="'+data.value[k].typeId+'"><span class=""></span> ' +   data.value[k].description + '</a></li>';
            //                 child_3 += item_3;
            //             }
            //         }

            //         if(data.value[j].superType == data.value[i].typeId){
            //             var item_2 = '<li><a href="##" data-id="'+data.value[j].typeId+'"><span class=""></span> ' +   data.value[j].description + '</a><ul>'+ child_3 +'</ul></li>';
            //             child_2 += item_2;
            //         }
            //     }
            //     if(data.value[i].level == 1){
            //         var item_1 = '<li><a href="##" data-id="'+data.value[i].typeId+'"><span class=""></span> ' +   data.value[i].description + '</a><ul>'+ child_2 +'</ul></li>';
            //         child_1 += item_1;
            //     }
                
              
            //     // $('.tree>li').append('<ul>'+child_2+'</ul>');
            // }
            // $('.tree>li').append('<ul>'+child_1+'</ul>');

            $('.tree>ul').empty();
            for( var i = 0; i < data.value.length ; i++ ){
                var item_1 = '';
                if(data.value[i].level == 1){
                    item_1 =  '<li><a href="##" id="'+data.value[i].typeId+'"><span class=""></span> '+data.value[i].description+'</a><ul></ul></li>';
                    $('.tree>li>ul').append(item_1).slideDown(0);
                }else{
                    item_1 =  '<li><a href="##" id="'+data.value[i].typeId+'"><span class=""></span> '+data.value[i].description+'</a><ul></ul></li>';
                    $('#'+data.value[i].superType).parent().find('>ul').append(item_1).slideDown(0);
                }
            }

            // $('.tree>li>ul').hide();

            M_table.triggerTree();
            $('.tree a').on('click',  function() {
                $('.content-main-add').hide();
                $('.content-main').show();
                M_table.currentListId = $(this).attr('id');
                // alert( M_table.currentListId);
                // M_table.getList();
                $('.tree a').css({
                    color: '#337ab7',
                    background: '#fff'
                });
                $(this).css({
                    color: '#fff',
                    background: '#337ab7'
                });
                if($(this).find('>span').hasClass('fa-plus-square')){
                    $(this).find('>span').removeClass('fa-plus-square').addClass('fa-minus-square');
                    $(this).parent().find('>ul').stop().slideDown(300);
                }else{
                    $(this).find('>span').removeClass('fa-minus-square').addClass('fa-plus-square');
                    $(this).parent().find('>ul').stop().slideUp(300);
                }
            });

            $('.tree a span').each(function(index, el) {
                if(($(this).parent().parent().find('ul').length!=0)&&($(this).parent().parent().find('ul').text()!="")){
                    $(this).addClass('fa fa-minus-square');
                }else{
                    $(this).addClass('fa fa-minus-square');
                }
            });

            // $('.tree a').on('click',  function() {
            //     // if(($(this).parent().parent().find('ul').length!=0)&&($(this).parent().parent().find('ul').text()!="")){
            //     //     $(this).removeClass('fa-minus-square').addClass('fa-plus-square');
            //     // }
            //     if($(this).find('>span').hasClass('fa-plus-square')){
            //         $(this).find('>span').removeClass('fa-plus-square').addClass('fa-minus-square');
            //         $(this).parent().find('>ul').stop().slideDown(300);
            //     }else{
            //         $(this).find('>span').removeClass('fa-minus-square').addClass('fa-plus-square');
            //         $(this).parent().find('>ul').stop().slideUp(300);
            //     }
            // });

        }else{

        }
    })
}


M_table.showConfirmModal=function(info,status,msgBody){
    var gobalModal = "";
    $("#confirm").remove();
    var font_icon = '';
    if(info == '成功'){
        font_icon = 'glyphicon glyphicon-ok';
    }else if(info == '警告'){
        font_icon = 'glyphicon glyphicon-warning-sign';
    }
    else{
        font_icon = 'glyphicon glyphicon-remove';
    }


    if(status!="success"&&status!="delete"){
        gobalModal = '<button type="button" id="confirmBtn" class="btn btn-default comFirmModal hide" data-toggle = "modal" data-target = "#confirm">确定</button>'+
            '<div class = "modal fade" id = "confirm" tabindex = "-1" role = "dialog" aria-labelledby = "myModalLabel" aria-hidden = "true">'+
                '<div class = "modal-dialog">'+
                  '<div class = "modal-content">'+
                     '<div class = "modal-header">'+
                        '<button type = "button" class = "close" data-dismiss = "modal" aria-hidden = "true">&times;</button>'+  
                        '<h4 class = "modal-title text-danger" id = "myModalLabel">&nps<span class="'+ font_icon +'"></span> '+info+'</h4>'+
                     '</div>'+
                     '<div class = "modal-body">'+msgBody+'</div>'+
                     '<div class = "modal-footer">'+
                        '<button type = "button" class = "btn btn-danger confirm" data-dismiss = "modal">确定</button>'+
                     '</div>'+
                  '</div>'+
               '</div>'+
            '</div>';
    }
    else if(status=="delete"){
        gobalModal = '<button type="button" id="confirmBtn" class="btn btn-default comFirmModal hide" data-toggle = "modal" data-target = "#confirm">确定</button>'+
                    '<div class = "modal fade" id = "confirm" tabindex = "-1" role = "dialog" aria-labelledby = "myModalLabel" aria-hidden = "true">'+
                        '<div class = "modal-dialog">'+
                          '<div class = "modal-content">'+
                             '<div class = "modal-header">'+
                                '<button type = "button" class = "close" data-dismiss = "modal" aria-hidden = "true">&times;</button>'+  
                                '<h4 class = "modal-title text-primary" id = "myModalLabel"><span class="'+ font_icon +'"></span> '+info+'</h4>'+
                             '</div>'+
                             '<div class = "modal-body">'+msgBody+'<span class="text-danger font-d-under font-w-bold"></span></div>'+
                             '<div class = "modal-footer">'+
                                '<button type = "button" class = "btn btn-primary deleteConfirm" data-dismiss = "modal">确定</button>'+
                                '<button type = "button" class = "btn btn-default confirm" data-dismiss = "modal">取消</button>'+
                             '</div>'+
                          '</div>'+
                       '</div>'+
                    '</div>';
    }
    else{
        gobalModal = '<button type="button" id="confirmBtn" class="btn btn-default comFirmModal hide" data-toggle = "modal" data-target = "#confirm">确定</button>'+
            '<div class = "modal fade" id = "confirm" tabindex = "-1" role = "dialog" aria-labelledby = "myModalLabel" aria-hidden = "true">'+
                '<div class = "modal-dialog">'+
                  '<div class = "modal-content">'+
                     '<div class = "modal-header">'+
                        '<button type = "button" class = "close" data-dismiss = "modal" aria-hidden = "true">&times;</button>'+  
                        '<h4 class = "modal-title text-primary" id = "myModalLabel"><span class="'+ font_icon +'"></span> '+info+'</h4>'+
                     '</div>'+
                     '<div class = "modal-body">'+msgBody+'</div>'+
                     '<div class = "modal-footer">'+
                        '<button type = "button" class = "btn btn-primary confirm" data-dismiss = "modal">确定</button>'+
                     '</div>'+
                  '</div>'+
               '</div>'+
            '</div>';
    }
    $("body").append(gobalModal);
    $("#confirmBtn").trigger('click');
}