$(function () {

    M_table.init();
    M_table.getNav();
    M_table.getList();
    bindEvent();
});


var M_table = {
    url:publicDom.config.url,
    tree:[],
    currentListId: '',
    materialId_bg:-1,
    type_father:'',
    status:-1,//1是新增，2是修改
    kucun_sel:'',
    init:function(){},
    getNav:function(){},
    getList:function(){},
    getDetail:function(){},
    getSelection:function(){},
    getSelection_edit:function(){},
    createList:function(){},
    saveInfo:function(){},
    updateInfo:function(){},
    deleteInfo:function(){},
    importExcel:function(){},
    showConfirmModal:function(){}
}

M_table.init = function () {

}

M_table.getList = function(curr){

    var newURL = M_table.url + 'toolAction=listMaterials';
    var param = {
        stockState: M_table.kucun_sel || '',
        typeId: M_table.currentListId || '',
        key: $('#filter').val() || '',
        currentPage: curr || 1,
        rows: $('#show_page').val() || 10
    }
    publicDom.getData('post',newURL,param,function(data){
        if(data.state == true ){
            console.log(data.value.data);
            M_table.createList(data.value.data);
            $('.tableNeed thead th').find('input[type="checkbox"]').prop('checked', false);
            laypage({
                cont : 'page1',
                pages :data.value.pages,
                curr : curr || 1,
                skin:'#337ab7',
                skip:true,
                jump : function(obj,first){
                    if(!first){
                        M_table.getList(obj.curr);  
                    }
                }
            });


            $('.tableNeed tbody tr').click(function() {
                M_table.materialId_bg = $(this).find('td').eq(1).text();
                console.log(M_table.materialId_bg);
                $(this).addClass('bg-primary').siblings().removeClass('bg-primary');
            });


        }else{

        }
    })
}

M_table.createList = function(list){
    $('.tableNeed tbody').empty();
    for(var i = 0 ;i < list.length; i++ ){
        var color_kucun = '';
        if(list[i].currentStock>list[i].maxStock){
            color_kucun = 'red';
        }else if(list[i].currentStock<list[i].minStock){
            color_kucun = 'blue';
        }else if((list[i].currentStock>=list[i].minStock)&&(list[i].currentStock<=list[i].safeStock)){
            color_kucun = '#50cb00';
        }
        var item = '<tr>\
                        <td><input type="checkbox" class="check-del" aria-label="..."></td>\
                        <td>'+list[i].materialsId+'</td>\
                        <td>'+list[i].description+'</td>\
                        <td>'+list[i].bdType+'</td>\
                        <td>'+list[i].bdUnit+'</td>\
                        <td>'+list[i].bdSpec+'</td>\
                        <td>'+list[i].bdModel+'</td>\
                        <td>'+list[i].source+'</td>\
                        <td class="kucun_td" style="color:'+color_kucun+';">'+list[i].currentStock+'</td>\
                        <td>'+list[i].minStock+'</td>\
                        <td>'+list[i].maxStock+'</td>\
                        <td>'+list[i].safeStock+'</td>\
                        <td>'+list[i].price+'</td>\
                        <td>'+list[i].bdCurrency+'</td>\
                        <td style="max-width:100px;overflow:auto;">'+list[i].remark+'</td>\
                    </tr>'
        $('.tableNeed tbody').append(item);
    }
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
                    $('.tree>li>ul').append(item_1).slideUp(0);
                }else{
                    item_1 =  '<li><a href="##" id="'+data.value[i].typeId+'"><span class=""></span> '+data.value[i].description+'</a><ul></ul></li>';
                    $('#'+data.value[i].superType).parent().find('>ul').append(item_1).slideUp(0);
                }
            }

            // $('.tree>li>ul').hide();


            $('.tree a').on('click',  function() {
                $('.content-main-add').hide();
                $('.content-main').show();
                M_table.currentListId = $(this).attr('id');
                M_table.getList();
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
                    $(this).addClass('fa fa-plus-square');
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

M_table.saveInfo = function() {
    var newURL = M_table.url + 'toolAction=saveMaterials';
     var param = {
        description: $('#description').val(),
        source: $('#source').val(),
        maxStock: $('#maxStock').val(),
        minStock: $('#minStock').val(),
        safeStock: $('#safeStock').val(),
        price: $('#price').val(),
        remark: $('#remark').val(),
        // bdCurrency: $('#bdCurrency').val(),
        // bdType: $('#bdType').val(),
        // superType: $('#superType').val(),
        // bdSpec: $('#bdSpec').val(),
        // bdModel: $('#bdModel').val(),
        // bdUnit: $('#bdUnit').val()
    }

    if($('#unit-name').is(':visible')){
        param.hasNewUnit = true; 
        param.bdUnit = $('#unit-name').val();
    }else{
        param.bdUnit = $('#bdUnit').find('option:checked').attr('id');
    }

    if($('#type-name').is(':visible')){
        param.hasNewType = true;
        param.superType = M_table.type_father || '';
        param.bdType = $('#type-name').val();
    }else{
        param.bdType = $('#bdType').find('option:checked').attr('id');
    }

    if($('#currency-name').is(':visible')){
        param.hasNewCurrency = true;
        param.bdCurrency = $('#currency-name').val();
    }else{
        param.bdCurrency = $('#bdCurrency').find('option:checked').attr('id');
    }

    if($('#model-name').is(':visible')){
        param.hasNewModel = true;
        param.bdModel = $('#model-name').val();
    }else{
        param.bdModel = $('#bdModel').find('option:checked').attr('id');
    }

    if($('#spec-name').is(':visible')){
        param.hasNewSpec = true;
        param.bdSpec = $('#spec-name').val();
    }else{
        param.bdSpec = $('#bdSpec').find('option:checked').attr('id');
    }

    // if(!(/^[1-9]+0*(\.[0-9]{1,2})?$|^0\.([0-9]{1})?([1-9]{1})$|^0\.([1-9]{1})0$/).exec($('#price').val()) ||
    //     !(/^[1-9]+0*(\.[0-9]{1,2})?$|^0\.([0-9]{1})?([1-9]{1})$|^0\.([1-9]{1})0$/).exec($('#minStock').val()) ||
    //     !(/^[1-9]+0*(\.[0-9]{1,2})?$|^0\.([0-9]{1})?([1-9]{1})$|^0\.([1-9]{1})0$/).exec($('#maxStock').val()) ||
    //     !(/^[1-9]+0*(\.[0-9]{1,2})?$|^0\.([0-9]{1})?([1-9]{1})$|^0\.([1-9]{1})0$/).exec($('#safeStock').val())
    //   )
    // {
    //     alert(1);
    // }
    if(!(/^[1-9]+0*(\.[0-9]{1,2})?$|^0\.([0-9]{1})?([1-9]{1})$|^0\.([1-9]{1})0$/).exec($('#price').val())){
        M_table.showConfirmModal('错误','success','参考单价填写有误！');
    }else if(!(/^[1-9]+0*(\.[0-9]{1,2})?$|^0\.([0-9]{1})?([1-9]{1})$|^0\.([1-9]{1})0$|^0$/).exec($('#minStock').val())){
        M_table.showConfirmModal('错误','success','最低库存填写有误！');
    }else if(!(/^[1-9]+0*(\.[0-9]{1,2})?$|^0\.([0-9]{1})?([1-9]{1})$|^0\.([1-9]{1})0$/).exec($('#maxStock').val())){
        M_table.showConfirmModal('错误','success','最高库存填写有误！');
    }else if(!(/^[1-9]+0*(\.[0-9]{1,2})?$|^0\.([0-9]{1})?([1-9]{1})$|^0\.([1-9]{1})0$/).exec($('#safeStock').val())){
        M_table.showConfirmModal('错误','success','安全库存填写有误！');
    }else if($('#description').val()=="" || $('#minStock').val()=="" || $('#maxStock').val()=="" || $('#safeStock').val()=="" ||
             $('#price').val()=="" || (($('#bdCurrency').val()=="")&&($('#currency-name').val()==""))){
        M_table.showConfirmModal('错误','success','请检查所有必填项(*)是否填写完整！')
    }else{
        publicDom.getData('post',newURL,param,function(data){
            if(data.state == true){
                M_table.showConfirmModal('成功','success','添加成功！');
                $('.confirm').on('click', function() {
                    location.reload();
                });
            }else{
                M_table.showConfirmModal('错误','success','添加失败！');
            }
        })
    }
   
    
}

M_table.getSelection = function() {
    var newURL = M_table.url + 'toolAction=getSelection';
    var param_1 = {
        target: 'type'
    };
    publicDom.getData('post',newURL,param_1,function(data){
        if(data.state == true){
            $('#bdType').empty();
            for(var i = 0 ; i < data.value.length ; i++ ){
                var item = '<option id="'+data.value[i].typeId+'">'+data.value[i].description+'</option>';
                $('#bdType').append(item);
            }

            $('.type_father ul').empty();
            $('.type_father ul').append('<li id=""><a href="##">无上级</a></li><li role="separator" class="divider"></li>');
            for(var i = 0 ; i < data.value.length ; i++ ){
                var item1 = '<li id="'+data.value[i].typeId+'"><a href="##">'+data.value[i].description+'</a></li>';
                $('.type_father ul').append(item1);
            }

            $('.type_father ul li').on('click', function() {
                var _this = this;
                $('.type_father').find('>button').empty().html($(_this).text()+' <span class="caret"></span>');
                M_table.type_father = $(_this).attr('id');

            });

            $('#bdType').on('click', function() {
                if($('#unit-name').is(':visible')){
                    $('.add-type-unit label[for="type-name"]').hide();
                    $('.type_father').parent().parent().hide();
                    $('.add-type-unit label[for="unit-name"]').addClass('col-md-offset-6');
                    // $('.add-type-unit').hide();
                }else{
                    $('.add-type-unit').hide();
                    $('.add-type-unit label[for="type-name"]').hide();
                    $('.type_father').parent().parent().hide();
                }
        
            });
        }else{

        }
    });
    var param_2 = {
        target: 'unit'
    };
    publicDom.getData('post',newURL,param_2,function(data){
        if(data.state == true){
            $('#bdUnit').empty();
            for(var i = 0 ; i < data.value.length ; i++ ){
                var item = '<option id="'+data.value[i].unitId+'">'+data.value[i].description+'</option>';
                $('#bdUnit').append(item);
            }

            $('#bdUnit').on('click', function() {
                if($('#type-name').is(':visible')){
                    $('.add-type-unit label[for="unit-name"]').hide();
                    $('#unit-name').parent().hide();
                    $('.add-type-unit label[for="unit-name"]').removeClass('col-md-offset-6');
                    // $('.add-type-unit').hide();
                }else{
                     $('.add-type-unit').hide();
                     $('.add-type-unit label[for="unit-name"]').hide();
                     $('#unit-name').parent().hide();
                }
            });
        }else{

        }
    });
    var param_3 = {
        target: 'spec'
    };
    publicDom.getData('post',newURL,param_3,function(data){
        if(data.state == true){
            $('#bdSpec').empty();
            for(var i = 0 ; i < data.value.length ; i++ ){
                var item = '<option id="'+data.value[i].specId+'">'+data.value[i].description+'</option>';
                $('#bdSpec').append(item);
            }
            $('#bdSpec').on('click', function() {
                // $('.add-spec-model').hide();

                if($('#spec-name').is(':visible')){
                    $('.add-spec-model label[for="spec-name"]').hide();
                    $('#spec-name').parent().hide();
                    $('.add-spec-model label[for="model-name"]').addClass('col-md-offset-6');
                    // $('.add-type-unit').hide();
                }else{
                    $('.add-spec-model').hide();
                    $('.add-spec-model label[for="spec-name"]').hide();
                    $('#spec-name').parent().hide();
                }
            });
        }else{

        }
    });
    var param_4 = {
        target: 'model'
    };
    publicDom.getData('post',newURL,param_4,function(data){
        if(data.state == true){
            $('#bdModel').empty();
            for(var i = 0 ; i < data.value.length ; i++ ){
                var item = '<option id="'+data.value[i].modelId+'">'+data.value[i].description+'</option>';
                $('#bdModel').append(item);
            }
            $('#bdModel').on('click', function() {
                // $('.add-spec-model').hide();
                if($('#spec-name').is(':visible')){
                    $('.add-spec-model label[for="model-name"]').hide();
                    $('#model-name').parent().hide();
                    $('.add-spec-model label[for="model-name"]').removeClass('col-md-offset-6');
                    // $('.add-type-unit').hide();
                }else{
                     $('.add-spec-model').hide();
                     $('.add-spec-model label[for="model-name"]').hide();
                     $('#model-name').parent().hide();
                }
            });
        }else{

        }
    });
    var param_5 = {
        target: 'currency'
    };
    publicDom.getData('post',newURL,param_5,function(data){
        if(data.state == true){
            $('#bdCurrency').empty();
            for(var i = 0 ; i < data.value.length ; i++ ){
                var item = '<option id="'+data.value[i].currencyId+'">'+data.value[i].description+'</option>';
                $('#bdCurrency').append(item);
            }
            $('#bdCurrency').on('click', function() {
                $('.add-currency').hide();
            });
        }else{

        }
    });


}

M_table.getSelection_edit = function(A,B,C,D,E) {
    var newURL = M_table.url + 'toolAction=getSelection';
    var param_1 = {
        target: 'type'
    };
    publicDom.getData('post',newURL,param_1,function(data){
        if(data.state == true){
            $('#bdType').empty();
            $('#bdType').prepend('<option id="" checked>'+ A +'</option>');
            for(var i = 0 ; i < data.value.length ; i++ ){
                var item = '<option id="'+data.value[i].typeId+'">'+data.value[i].description+'</option>';
                $('#bdType').append(item);
            }

            $('.type_father ul').empty();
            $('.type_father ul').append('<li id=""><a href="##">无上级</a></li><li role="separator" class="divider"></li>');
            for(var i = 0 ; i < data.value.length ; i++ ){
                var item1 = '<li id="'+data.value[i].typeId+'"><a href="##">'+data.value[i].description+'</a></li>';
                $('.type_father ul').append(item1);
            }

            $('.type_father ul li').on('click', function() {
                var _this = this;
                $('.type_father').find('>button').empty().html($(_this).text()+' <span class="caret"></span>');
                M_table.type_father = $(_this).attr('id');

            });

            $('#bdType').on('click', function() {
                if($('#unit-name').is(':visible')){
                    $('.add-type-unit label[for="type-name"]').hide();
                    $('.type_father').parent().parent().hide();
                    $('.add-type-unit label[for="unit-name"]').addClass('col-md-offset-6');
                    // $('.add-type-unit').hide();
                }else{
                    $('.add-type-unit').hide();
                    $('.add-type-unit label[for="type-name"]').hide();
                    $('.type_father').parent().parent().hide();
                }
            
            });
        }else{

        }
    });
    var param_2 = {
        target: 'unit'
    };
    publicDom.getData('post',newURL,param_2,function(data){
        if(data.state == true){
            $('#bdUnit').empty();
            $('#bdUnit').prepend('<option id="" checked>'+ B+'</option>');
            for(var i = 0 ; i < data.value.length ; i++ ){
                var item = '<option id="'+data.value[i].unitId+'">'+data.value[i].description+'</option>';
                $('#bdUnit').append(item);
            }

            $('#bdUnit').on('click', function() {
                if($('#type-name').is(':visible')){
                    $('.add-type-unit label[for="unit-name"]').hide();
                    $('#unit-name').parent().hide();
                    $('.add-type-unit label[for="unit-name"]').removeClass('col-md-offset-6');
                    // $('.add-type-unit').hide();
                }else{
                     $('.add-type-unit').hide();
                     $('.add-type-unit label[for="unit-name"]').hide();
                     $('#unit-name').parent().hide();
                }
            });
        }else{

        }
    });
    var param_3 = {
        target: 'spec'
    };
    publicDom.getData('post',newURL,param_3,function(data){
        if(data.state == true){
            $('#bdSpec').empty();
            $('#bdSpec').prepend('<option id="" checked>'+ C +'</option>');
            for(var i = 0 ; i < data.value.length ; i++ ){
                var item = '<option id="'+data.value[i].specId+'">'+data.value[i].description+'</option>';
                $('#bdSpec').append(item);
            }
            $('#bdSpec').on('click', function() {
                // $('.add-spec-model').hide();

                if($('#spec-name').is(':visible')){
                    $('.add-spec-model label[for="spec-name"]').hide();
                    $('#spec-name').parent().hide();
                    $('.add-spec-model label[for="model-name"]').addClass('col-md-offset-6');
                    // $('.add-type-unit').hide();
                }else{
                    $('.add-spec-model').hide();
                    $('.add-spec-model label[for="spec-name"]').hide();
                    $('#spec-name').parent().hide();
                }
            });
        }else{

        }
    });
    var param_4 = {
        target: 'model'
    };
    publicDom.getData('post',newURL,param_4,function(data){
        if(data.state == true){
            $('#bdModel').empty();
            $('#bdModel').prepend('<option id="" checked>'+ D +'</option>');
            for(var i = 0 ; i < data.value.length ; i++ ){
                var item = '<option id="'+data.value[i].modelId+'">'+data.value[i].description+'</option>';
                $('#bdModel').append(item);
            }
            $('#bdModel').on('click', function() {
                // $('.add-spec-model').hide();
                if($('#spec-name').is(':visible')){
                    $('.add-spec-model label[for="model-name"]').hide();
                    $('#model-name').parent().hide();
                    $('.add-spec-model label[for="model-name"]').removeClass('col-md-offset-6');
                    // $('.add-type-unit').hide();
                }else{
                     $('.add-spec-model').hide();
                     $('.add-spec-model label[for="model-name"]').hide();
                     $('#model-name').parent().hide();
                }
            });
        }else{

        }
    });
    var param_5 = {
        target: 'currency'
    };
    publicDom.getData('post',newURL,param_5,function(data){
        if(data.state == true){
            $('#bdCurrency').empty();
            $('#bdCurrency').prepend('<option id="" checked>'+ E +'</option>');
            for(var i = 0 ; i < data.value.length ; i++ ){
                var item = '<option id="'+data.value[i].currencyId+'">'+data.value[i].description+'</option>';
                $('#bdCurrency').append(item);
            }
            $('#bdCurrency').on('click', function() {
                $('.add-currency').hide();
            });
        }else{

        }
    });
}


M_table.getDetail = function() {
    var newURL = M_table.url + 'toolAction=getMaterials';
    var param = {
        materialsId: M_table.materialId_bg
    }
    publicDom.getData('post',newURL,param,function(data){
        if(data.state == true){
            $('#materialsId').val(data.value.materialsId).prop('readonly', true);
            $('#description').val(data.value.description);
            $('#source').val(data.value.source);
            // $('#source').find('option').each(function(index, el) {
            //     if($(this).val()==data.value.source){

            //     }
            // });
            $('#spec-name').val(data.value.bdSpec);
            $('#maxStock').val(data.value.maxStock);
            $('#minStock').val(data.value.minStock);
            $('#safeStock').val(data.value.safeStock);
            $('#price').val(data.value.price);
            $('#remark').val(data.value.remark);
            $('#superType').val(data.value.superType);
            M_table.getSelection_edit(data.value.bdType,data.value.bdUnit,data.value.bdSpec,data.value.bdModel,data.value.bdCurrency);
            // $('#bdType').find('option').eq(0).val(data.value.bdType);
            // $('#bdCurrency').find('option').eq(0).val(data.value.bdCurrency);
            // $('#bdModel').find('option').eq(0).val(data.value.bdModel);
            // $('#bdSpec').find('option').eq(0).val(data.value.bdSpec);
            // $('#bdUnit').find('option').eq(0).val(data.value.bdUnit);
        }else{

        }
    });
}


M_table.updateInfo = function() {
    var newURL = M_table.url + 'toolAction=updateMaterials';
    var param = {
        materialsId: $('#materialsId').val(),
        description: $('#description').val(),
        source: $('#source').val(),
        maxStock: $('#maxStock').val(),
        minStock: $('#minStock').val(),
        safeStock: $('#safeStock').val(),
        price: $('#price').val(),
        remark: $('#remark').val(),
        // superType: $('#superType').val()
    }

    if($('#unit-name').is(':visible')){
        param.hasNewUnit = true;
        param.bdUnit = $('#unit-name').val();
    }else{
        if($('#bdUnit').find('option:checked').attr('id')!=""){
            param.bdUnit = $('#bdUnit').find('option:checked').attr('id');
        }
    }

    if($('#type-name').is(':visible')){
        param.hasNewType = true;
        param.superType = M_table.type_father || '';
        param.bdType = $('#type-name').val();
    }else{
        if($('#bdType').find('option:checked').attr('id')!=""){
            param.bdType = $('#bdType').find('option:checked').attr('id');
        }
    }

    if($('#currency-name').is(':visible')){
        param.hasNewCurrency = true;
        param.bdCurrency = $('#currency-name').val();
    }else{
        if($('#bdCurrency').find('option:checked').attr('id')!=""){
            param.bdCurrency = $('#bdCurrency').find('option:checked').attr('id');
        }
    }

    if($('#model-name').is(':visible')){
        param.hasNewModel = true;
        param.bdModel = $('#model-name').val();
    }else{
        if($('#bdModel').find('option:checked').attr('id')!=""){
            param.bdModel = $('#bdModel').find('option:checked').attr('id');
        }
    }

    if($('#spec-name').is(':visible')){
        param.hasNewSpec = true;
        param.bdSpec = $('#spec-name').val();
    }else{
        if($('#bdSpec').find('option:checked').attr('id')!=""){
            param.bdSpec = $('#bdSpec').find('option:checked').attr('id');
        }
    }

    if(!(/^[1-9]+0*(\.[0-9]{1,2})?$|^0\.([0-9]{1})?([1-9]{1})$|^0\.([1-9]{1})0$/).exec($('#price').val())){
        M_table.showConfirmModal('错误','success','参考单价填写有误！');
    }else if(!(/^[1-9]+0*(\.[0-9]{1,2})?$|^0\.([0-9]{1})?([1-9]{1})$|^0\.([1-9]{1})0$|^0$/).exec($('#minStock').val())){
        M_table.showConfirmModal('错误','success','最低库存填写有误！');
    }else if(!(/^[1-9]+0*(\.[0-9]{1,2})?$|^0\.([0-9]{1})?([1-9]{1})$|^0\.([1-9]{1})0$/).exec($('#maxStock').val())){
        M_table.showConfirmModal('错误','success','最高库存填写有误！');
    }else if(!(/^[1-9]+0*(\.[0-9]{1,2})?$|^0\.([0-9]{1})?([1-9]{1})$|^0\.([1-9]{1})0$/).exec($('#safeStock').val())){
        M_table.showConfirmModal('错误','success','安全库存填写有误！');
    }else if($('#description').val()=="" || $('#minStock').val()=="" || $('#maxStock').val()=="" || $('#safeStock').val()=="" ||
             $('#price').val()=="" || (($('#bdCurrency').val()=="")&&($('#currency-name').val()==""))){
        M_table.showConfirmModal('错误','success','请检查所有必填项(*)是否填写完整！')
    }else{
        publicDom.getData('post',newURL,param,function(data){
            if(data.state == true){
                M_table.showConfirmModal('成功','success','修改成功！');
                $('.confirm').on('click', function() {
                    location.reload();
                });
            }else{
                M_table.showConfirmModal('错误','success','修改失败！');
            }
        })
    }
}

M_table.deleteInfo = function(string) {
    var newURL = M_table.url + 'toolAction=deleteMaterials';
    var param = {
        materialsId: string
    }
    publicDom.getData('post',newURL,param,function(data){
        if(data.state == true){
            M_table.showConfirmModal('成功','success','删除成功！');
            $('.confirm').on('click',function() {
                location.reload();
            });
        }else{
            M_table.showConfirmModal('错误','success','删除失败！');
        }
    });
}

M_table.importExcel = function(file){
    var newURL = M_table.url + 'toolAction=importExecl';

    
    $.ajax({
        url: newURL,
        type: 'post',
        enctype: 'multipart/form-data',
        data: file,
    })
    .done(function() {
        alert("success");
    })
    .fail(function() {
        // alert("error");
    });

    // publicDom.getData('post',newURL, file, function(data){
    //     if(data.state==false){

    //     }
    // })
    
}

var bindEvent = function() {
    var showNav = false;

    $('.header').on('click', function() {
        location.reload();
    });

    $('#show_page').on('keyup', function() {
        M_table.getList();
    });

    $('.showNav').on('click',  function() {
        
        if(!showNav){
            $('.content-nav').stop().animate({'margin-left':'0px'}, 300);
            $('.content-main').stop().animate({'width':'86%'}, 300);
            // $(this).text('隐藏导航').css('opacity', '.4');
            $(this).text('隐藏导航').css({
                'opacity': '.5',
                'background': '#fff',
                'color': '#377ab7',
                'border': '1px solid #377ab7'
            }); 
            showNav = true;
        }else{
            $('.content-nav').stop().animate({'margin-left':'-800px'}, 300);
            // $('.content-nav').stop().animate({
            //     'margin-left':'-800px'},
            //     200, function() {
            //     $('.content-main').stop().animate({'width':'100%'}, 200);
            // });
            $('.content-main').stop().animate({'width':'100%'}, 300);
            // $(this).text('显示导航').css('opacity', '1');
            $(this).text('显示导航').css({
               'opacity': '1',
               'background': '#377ab7',
               'color': '#fff',
               'border': '1px solid #377ab7'
            });
            showNav = false;
        }
        
    });

    $('#importBtn').on('click',function() {
        $('#file_import').trigger('click');
    });
   
    $('#filter').on('keyup', function(e) {
        M_table.getList();
    });

    $('.content-nav h4').on('click', function() {
        location.reload();
    });

    $('#add-li-btn').on('click', function() {
        M_table.status = 1;
        $('.content-main').hide();
        $('.content-main-add').show();
        $('.add-spec-model,.add-currency,.add-type-unit').hide();
        $('.content-main-add .panel-body input,.content-main-add .panel-body textarea').val("").prop('readonly', false);
        $('#materialsId').prop('readonly',true );
        M_table.getSelection();
    });
    
    $('#bdType-btn').on('click', function() {
        $('.add-type-unit').show();
        // $('#unit-name').hide();
        $('.add-type-unit label[for="type-name"]').show();
        $('#type-name').parent().parent().show();
        $('#type-name').focus();
        if($('#unit-name').is(':visible')){
           $('.add-type-unit label[for="unit-name"]').removeClass('col-md-offset-6');
        }else{
            $('.add-type-unit label[for="unit-name"]').hide();
            $('#unit-name').parent().hide();
        }

    });

    $('#bdUnit-btn').on('click', function() {
       $('.add-type-unit').show();
       // $('#type-name').hide();
       // $('.add-type-unit label[for="type-name"]').hide();
       $('#unit-name').parent().show();
       $('#unit-name').focus();
       if($('#type-name').is(':visible')){
           $('.add-type-unit label[for="unit-name"]').show();
       }else{
           $('.add-type-unit label[for="unit-name"]').show().addClass('col-md-offset-6');
       }
    });

    $('#bdSpec-btn').on('click', function() {
        $('.add-spec-model').show();
        // $('#model-name').hide();
        $('.add-spec-model label[for="spec-name"]').show();
        $('#spec-name').parent().show();
        $('#spec-name').focus();
        if($('#model-name').is(':visible')){
           $('.add-spec-model label[for="model-name"]').removeClass('col-md-offset-6');
        }

    });

    $('#bdModel-btn').on('click', function() {
        $('.add-spec-model').show();
        // $('#spec-name').hide();
        // $('.add-spec-model label[for="spec-name"]').hide();
        $('#model-name').parent().show();
        $('#model-name').focus();
        if($('#spec-name').is(':visible')){
            $('.add-spec-model label[for="model-name"]').show();
        }else{
            $('.add-spec-model label[for="model-name"]').show().addClass('col-md-offset-6');
        }
    });

    $('#bdCurrency-btn').on('click', function() {
        $('.add-currency').show();
        // $('#unit-name').hide();
        $('.add-currency label[for="currency-name"]').show();
        $('#currency-name').parent().show();
        $('#currency-name').focus();

    });



    $('#edit-li-btn').on('click', function() {
        M_table.status = 2;
        if(M_table.materialId_bg==(-1)){
            M_table.showConfirmModal('错误','success','请单击选择以下其中一行进行修改！');
        }else{
            $('.content-main-add .panel-body input,.content-main-add .panel-body textarea').val("");
            $('.add-spec-model,.add-currency,.add-type-unit').hide();
            $('.content-main-add').show();
            $('.content-main').hide();
            M_table.getDetail();
        }
       
    });

    $('#del-li-btn').on('click', function() {
        
        if(M_table.materialId_bg==(-1) && $('.tableNeed input:checked').length == 0){
            M_table.showConfirmModal('错误','success','请先选择删除项再进行删除！');
        }else{
            M_table.showConfirmModal('警告','delete','是否确定删除？');
            $('.deleteConfirm').on('click', function() {
                var deleteItem = '';
                var check_length = $('.tableNeed tbody tr input:checked').length;
                // alert(check_length)
                if(check_length==0){
                    deleteItem = M_table.materialId_bg;
                }else{
                    $('.tableNeed tbody tr input:checked').each(function(index, el) {
                        if(check_length==(index+1)){
                             deleteItem = deleteItem + $(this).parent().next().text();
                        }else{
                             deleteItem = deleteItem + $(this).parent().next().text() + ',';
                        }

                    });
                }
               
                M_table.deleteInfo(deleteItem);
            }); 
        }

            
  
    });

    $('#saveBtn').on('click', function() {
        if(M_table.status == 1){
            M_table.saveInfo();
        }else if(M_table.status == 2){
             M_table.updateInfo();
        }
       
    });

    $('#prevPage').on('click', function() {
        location.reload();
    });

    $('.tableNeed thead th input[type="checkbox"]').on('click', function() {
        if(!$(this).is(':checked')){
            $(this).prop('checked',false);
            $('.tableNeed tbody td input[type="checkbox"]').prop('checked',false);
        }else{
            $(this).prop('checked',true);
            $('.tableNeed tbody td input[type="checkbox"]').prop('checked',true);
        }
    });

    $('#kucun_ul li').on('click', function() {
        var text_sel = $(this).find('>a').text();
        // alert(text_sel);
        $('.sort-kucun button').find('>b').text(text_sel);
        M_table.kucun_sel = $(this).data('id');
        // alert(M_table.kucun_sel);
        M_table.getList();
    });

    $('#file_import').on('change',function() {
        var file = this.files[0];
        var formData = new FormData($('#importForm')[0]);
        console.log(file);
        console.log(formData);
        // var reader = new FileReader(file);
        // reader.readAsDataURL(file);

        if(!(/.\.xls|.\.xlsx/).exec(file.name)){
            M_table.showConfirmModal('错误','success','请确保上传.xls或.xlsx文件格式！')
        }else{
            // M_table.importExcel(file);
            var newURL = M_table.url + 'toolAction=importExecl';

            $.ajax({  
                  url: newURL ,  
                  type: 'POST',  
                  data: formData,
                  async: false,  
                  cache: false,  
                  contentType: false,  
                  enctype:'multipart/form-data',
                  processData: false,  
                  success: function (data) {  
                    if(data.state){
                        if(data.value.error==0){
                            M_table.showConfirmModal('成功','success','导入成功！成功导入<b class="text-primary">' + (data.value.total) + '</b>条数据!');
                            $('.confirm').on('click',  function() {
                                location.reload();
                            });
                        }else{
                            $('.tableInsert tbody').empty();
                            $('#myModalLabel').find('b').empty();
                            $('#myModalLabel').find('b').text('( 共'+data.value.total+'条,其中'+data.value.error+'条导入失败 )')
                                .addClass('text-danger')
                                .css({
                                    // color: 'red',
                                    fontSize: '14px',
                                    textDecoration: 'underline'
                                });
                            for(var i = 0 ;i < data.value.errorList.length; i++ ){
                               
                                var item = '<tr>\
                                                <td>'+data.value.errorList[i].description+'</td>\
                                                <td>'+data.value.errorList[i].bdType+'</td>\
                                                <td>'+data.value.errorList[i].bdUnit+'</td>\
                                                <td>'+data.value.errorList[i].bdSpec+'</td>\
                                                <td>'+data.value.errorList[i].bdModel+'</td>\
                                                <td>'+data.value.errorList[i].source+'</td>\
                                                <td>'+data.value.errorList[i].minStock+'</td>\
                                                <td>'+data.value.errorList[i].maxStock+'</td>\
                                                <td>'+data.value.errorList[i].safeStock+'</td>\
                                                <td>'+data.value.errorList[i].price+'</td>\
                                                <td>'+data.value.errorList[i].bdCurrency+'</td>\
                                                <td style="color:red;">'+data.value.errorList[i].errorMsg+'</td>\
                                            </tr>'
                                $('.tableInsert tbody').append(item);
                            }
                            $('#import_err_Btn').trigger('click');
                        }
                        
                    }else{
                        M_table.showConfirmModal('错误','success',data.value);
                    }
                  },  
                  error: function (data) { 
                          
                  }  
            }); 
        }
    });

    $('#export').on('click', function() {
        var newURL = M_table.url + 'toolAction=exportExecl';
        var param = {};
        publicDom.getData('post',newURL,param,function(data){
            if(data.state == false){
                M_tabl.showConfirmModal('错误','success','导出失败！');
            }
            window.location.href = newURL;
        })

    });




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