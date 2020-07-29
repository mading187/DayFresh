$(function(){
	$.post("../Fresh/jugement" , {op:"order"} , function(data){
		var strHead = "欢迎您：<em>" + data[0].uname + " <span>&nbsp;</span>"
		$(".login_info").html(strHead);
		var str = '';
		
		str+="				<h3 class='common_title2'>全部订单</h3>";
		for (var i = 0; i < data.length; i++) {
			var count = 0 ;
			str+="<ul class='order_list_th w978 clearfix'>'";
			str+='<li class="col01">'+ data[i].odate +'</li>';
			str+='<li class="col02" style="width:55%">订单号：'+ data[i].oid +'</li>';
			str+='<li class="col02 stress"></li>';
			str+='</ul>';
			
			str+='<table class="order_list_table w980">';
			str+='	<tbody>';
			str+='		<tr>';
			str+='			<td width="55%">';
			
			for (var j = 0; j < data[i].list.length; j++) {
				str+='				<ul class="order_goods_list clearfix">	';				
				str+='					<li class="col01"><img src="'+ data[i].list[j].photo +'"></li>';
				str+='					<li class="col02">'+ data[i].list[j].gtitle +'<em>'+ data[i].list[j].gprice +'元/500g</em></li>';
				str+='					<li class="col03">'+ data[i].list[j].gcount +'</li>';
				str+='					<li class="col04">'+ data[i].list[j].gcount* data[i].list[j].gprice +'元</li>';
				count += data[i].list[j].gcount* data[i].list[j].gprice ;
				str+='				</ul>';
			}
			
			str+='			</td>';
			str+='			<td width="15%">'+ count+'元</td>';
			str+='			<td width="15%">';
			str+='			</td>';
			if(data[i].oispay == 1){
				str+='			<td width="15%"><a href="#" class="oper_btn">去付款</a></td>';
			}else if(data[i].oispay == 2){
				str+='			<td width="15%"><a href="#" class="oper_btn">确认收货</a></td>';
			}else if(data[i].oispay == 3){
				str+='			<td width="15%"><a href="#" class="oper_btn">去评价</a></td>';
			}
			
			str+='		</tr>';
			str+='	</tbody>';
			str+='</table>';	
		}
	
		$(".right_content").html(str)
					
				
		
		
	},"json");
})