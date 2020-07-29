$(function(){
	$.post("../Fresh/jugement",{op : "Loding" , type:"id"},function(data) {
		var str='';
	  	var title = "";// 左侧导航栏
        for(var i=0; i<data[0].length;i++){  
		if(data[0][i] == $(".typename").val() && data[0][i] != 'lunbo' )   {
		    for (var j = 0; j < data[1][data[0][i]].length; j++) {
		        str+='<ul class="goods_type_list clearfix">';
				str+=' <li>';
				str+='	<a href="commodityInfo.jsp?commodityName='+data[1][data[0][i]][j].gtitle +'"><img src="'+ data[1][data[0][i]][j].photo +'"></a>';
				str+='	<h4><a href="#">'+data[1][data[0][i]][j].gtitle+'</a></h4>';
				str+='<div class="operate">';
				str+='	<span class="prize">￥'+ data[1][data[0][i]][j].gprice +'</span>';
				str+='	<span class="unit">500g</span>';
				str+='<a href="javascript:void(0);" class="add_goods" onclick="addcar('+data[1][data[0][i]][j].id+')" title="加入购物车"></a>';
				str+='	</div>';
				str+='</li>';
		        str+='<ul>'; 
		             }
				}
        	}
        
        for (var i = 0; i < data[0].length; i++) {// map的key
    		if (data[0][i] != "lunbo") {// 去除轮播图的部分
    			// 添加导航栏
    			title += "<li><a href='goods.jsp?typename="
    					+ data[0][i]
    					+ "' class='" + data[0][i] + "'>"
    					+ data[0][i] + "</a></li>";
    				} 
    			}
        	$(".subnav").html(title);
        	$(".show1").html(str);
	    },'json');
	

		$(".type").on("click", function() {
			var type = "id";
			$(".type").removeClass("active ");
			if($(this).html() == "默认"){
				type = "id";
				$("#normal").addClass("active");
			}else if($(this).html() == "价格"){
				type = "gprice";
				$("#price").addClass("active");
			}else if($(this).html() == "人气"){
				type = "gsales";
				$("#manUse").addClass("active");
			}
			$.post("../Fresh/jugement",{op : "Loding" , type:type},function(data) {
				var str='';
		        for(var i=0; i<data[0].length;i++){  
				if(data[0][i] == $(".typename").val() && data[0][i] != 'lunbo' )   {
				    for (var j = 0; j < data[1][data[0][i]].length; j++) {
				        str+='<ul class="goods_type_list clearfix">';
						str+=' <li>';
						str+='	<a href="commodityInfo.jsp?commodityName='+data[1][data[0][i]][j].gtitle +'"><img src="'+ data[1][data[0][i]][j].photo +'"></a>';
						str+='	<h4><a href="#">'+data[1][data[0][i]][j].gtitle+'</a></h4>';
						str+='<div class="operate">';
						str+='	<span class="prize">￥'+ data[1][data[0][i]][j].gprice +'</span>';
						str+='	<span class="unit">500g</span>';
						str+='<a href="javascript:void(0);" class="add_goods" onclick="addcar('+data[1][data[0][i]][j].id+')" title="加入购物车"></a>';
						str+='	</div>';
						str+='</li>';
				        str+='<ul>'; 
				             }
						}
		        }
		        $(".show1").html(str);
			},'json');
			
		})
     })
     
      function addcar(gtitle){
			var commodityName = gtitle;//类型名称
			var num = $('.num_show').val();
			$.post("../Fresh/jugement",{op:"addCar",commodityName: commodityName,num:1,type:"id"},function(data){
			if(data == 0){
				//加入失败
				alert("加入购物车失败!!!!")
			}else{
				//加入成功
				alert("加入购物车成功!!!!")
			}
				
			},"json")
		}