$(function (){
	$.post("../Fresh/jugement" , {op:"buyCar"} , function(data){
	var str='';	
	str+='<ul class="goods_list_th clearfix">';
	str+='<li class="col01">商品名称</li>';
	str+='	<li class="col02">商品单位</li>';
	str+='<li class="col03">商品价格</li>';
	str+='<li class="col04">数量</li>';
	str+='<li class="col05">小计</li>';
	str+='</ul>';
	 var all=0; 
	for (var i = 0;i < data.length; i++){    
	str+='<ul class="goods_list_td clearfix" id="83">';
	str+='<li class="col01">'+data[i].gid+'</li>';
	str+='<li class="col02"><img src="'+data[i].photo +'"></li>';
	str+='<li class="col03">'+data[i].gtitle+'</li>';
    str+='<li class="col04">500g</li>';
    str+='<li class="col05">'+data[i].gprice+'</li>';
	str+='<li class="col06">'+data[i].count+'</li>';
	str+='<li class="col07">'+data[i].gprice*data[i].count+'元</li>';
	str+='</ul>';
	   all+=data[i].gprice*data[i].count;
	}
	$(".show2").html(str);
	 
     var stu='';
     out=all+10;
	stu+=' <div class="settle_con">';
	stu+='<div class="total_goods_count">';
	stu+='	共<em>'+data.length+'</em>件商品，总金额<b>'+all+'</b>';
	stu+='</div>';
	stu+='<div class="transit">';
	stu+='	运费：<b>10元</b>';
	stu+='</div>';
	stu+='<div class="total_pay">';
	
	stu+='	实付款：<b>'+out+'</b>';
	stu+='</div>';
	stu+='   </div>';
	$(".show4").html(stu);
		
	},'json');	
})