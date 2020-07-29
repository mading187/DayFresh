$(function(){
	$.post("../Fresh/jugement" , {op:"buyCar"} , function(data){
		console.log( data );
		var str = '';
		str+='<div class="total_count">全部商品<em>'+data.length+'</em>件</div>';
		str+='<ul class="cart_list_th clearfix"><li class="col01">商品名称</li>';
		str+='<li class="col02">商品单位</li><li class="col03">商品价格</li>';
		str+='<li class="col04">数量</li><li class="col05">小计</li><li class="col06">操作</li></ul>';
		var all = 0;
		for (var i = 0;i < data.length; i++){
			console.log(data[i]);
			str+='<ul class="cart_list_td clearfix" id="goods_">';
			str+='<li class="col01"><input type="checkbox" name="" id="check_goods" checked="checked"></li>';
			str+='<li class="col02"><img src='+ data[i].photo+'></li>';
			str+='<li class="col03">'+data[i].gtitle+'<br><em>' + data[i].gprice + '元/500g</em><br><em>库存：<span id="goods_kucun">'+data[i].gunit+'</span></em></li>';
			//str+='<li class="title'+i+'">' + data[i].gtitle + '<br> </li>';
			str+='<li class="col04">500g</li>';
			str+='<li class="col05" id="gprice">' + data[i].gprice + '元/500g</li>';
			str+='<li class="col06">';
		
			str+='<div class="num_add">';
			
			str+='<a href="javascript:;" class="add fl">+</a>';
			str+='<input type="text" class="num_show fl" value="'+ data[i].count+'" kucun="'+data[i].gunit+'">';
			str+='<a href="javascript:;" class="minus fl">-</a>';
			str+='</div>';
			str+='<li class="col07 " id="tott">'+ data[i].count*data[i].gprice+'元</li>';
			all += data[i].count*data[i].gprice;
			str+='<li class="col08"><a href="javascript:void(0);" onclick="deltt('+data[i].id+')">删除</a></li></ul></div>';
		}
		str+='<ul class="settlements"><li class="col01"><input type="checkbox" name="" checked="" id="check_all"></li>';
		str+='<li class="col02">全选</li>';
		str+='<li class="col03">合计(不含运费)：<span>￥</span><em id="gtotal">'+all+'</em><br>共计<b>'+data.length+'</b>件商品</li>';
		str+='<li class="col04" style="background:#ff3d3d; font-size: 24px;"><a href="inorder.html">去结算</a></li></ul>';
		$(".buyCarBody").html(str)
	},"json");
})

function deltt(id){
    del = confirm('确定删除');	
	
		$.post("../Fresh/jugement" , {op:"deleteBcar",id:id} , function(data){
		if(data>0){
			alert("删除成功");
			location.reload();
			
			}else{
				alert("删除失败");
			}
			
		},"json");
	}

	