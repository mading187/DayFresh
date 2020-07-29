$(function() {
	$.post("../Fresh/jugement",{op : "Loding",type:"id"},function(data) {
						var title = "";// 左侧导航栏
						var lunbo = "";// 轮播图部分
						var body = "";// 商品主体
						var str='';
						for (var i = 0; i < data[0].length; i++) {// map的key
							if (data[0][i] != "lunbo") {// 去除轮播图的部分
								console.log(data[0][i]);
								// 添加导航栏
								title += "<li><a href='#"
										+ data[0][i]
										+ "' class='" + data[0][i] + "'>"
										+ data[0][i] + "</a></li>";
								body += '	<div class="' + data[0][i] +'">'
										+ '<div class="list_title clearfix">'
										+ '	<h3 class="fl" id="model01">'+ data[0][i] +'</h3>'
										+ '	<a href="goods.jsp?typename='+ data[0][i] +'" name="' + data[0][i] +'" class="goods_more fr" id="fruit_more">查看更多 ></a>'
										+ '</div>'
										+ '<div class="goods_con clearfix">'										
										+ '	<div class="goods_banner fl"><img src="'+ data[1][data[0][i]][0].gphoto +'"></div>'
										+ '	<ul class="goods_list fl">'
										
								for (var j = 0; j < 4; j++) {
									body += '<li>'
											+ '<h4><a href="commodityInfo.jsp?commodityName='+data[1][data[0][i]][j].gtitle +'">'+ data[1][data[0][i]][j].gtitle +'</a></h4>'
											+ '<a href="commodityInfo.jsp?commodityName='+data[1][data[0][i]][j].gtitle +'"><img src="'+ data[1][data[0][i]][j].photo +'"></a>'
											+ '<div class="prize">¥ '+ data[1][data[0][i]][j].gprice +'</div>'
											+ '</li>'
								}
								body += '</ul></div></div>'
							} else {
								// 轮播图部分
								lunbo += '<ul class="slide_pics">'
									for (var k = 0; k < data[1][data[0][i]].length; k++) {
									lunbo += '<li>'
											+ '	<a href="/detail/6/"><img src="'
											+ data[1][data[0][i]][k].photo
											+ '" alt="幻灯片"></a>' + '</li>'
									}
								lunbo+='</ul>'
										+ '<div class="prev"></div>'
										+ '<div class="next"></div>'
										+ '<ul class="points"></ul>'
							}
						}
						
						$(".subnav").html(title);
						$(".commodityBody").html(body);
						//$(".lunboBody").html(lunbo);
					}, "json");
         })


function search(){
	var commodityName = $(".input_text").val();
	$.post("../Fresh/jugement",{op : "commodityLoding",commodityName:commodityName},function(data) {
		var body = "";// 商品主体
		body += '	<div class="">'
		+ '<div class="list_title clearfix">'
		+ '	<a href="#" class="goods_more fr" id="fruit_more">查看更多 ></a>'
		+ '</div>'
		+ '<div class="goods_con clearfix">'
		+ '	<ul class="goods_list fl">'
		if(data.length == 0 ){
			alert("商品不存在!!!!!!")
		}else{
			for (var i = 0; i < data.length; i++) {
				body += '<li>'
					+ '<h4><a href="commodityInfo.jsp?commodityName='+data[i].gtitle +'">'+ data[i].gtitle +'</a></h4>'
					+ '<a href="commodityInfo.jsp?commodityName='+data[i].gtitle +'"><img src="'+ data[i].photo +'"></a>'
					+ '<div class="prize">¥ '+ data[i].gprice +'</div>'
					+ '</li>'
			}
			body += '</ul></div></div>'
		}
		$(".commodityBody").html(body);
		
	}, "json");
}