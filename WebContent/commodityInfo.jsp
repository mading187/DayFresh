<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<link href="images/1.jpg" rel="icon" type="images/x-icon" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>天天生鲜-商品详情</title>

<link rel="stylesheet" type="text/css" href="css/reset.css">
<link rel="stylesheet" type="text/css" href="css/main.css">


<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="js/check.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript">
	

$(function(){
			//alert($(".commodityName").val());
			var commodityName = $(".commodityName").val();
			var  num = parseInt($('.num_show').val());
            var title = "";// 左侧导航栏\
        	$.post("../Fresh/jugement",{op : "Loding",type:"id"},function(data) {
            for (var i = 0; i < data[0].length; i++) {// map的key
				if (data[0][i] != "lunbo") {// 去除轮播图的部分
					// 添加导航栏
					title += "<li><a href='goods.jsp?typename="
							+ data[0][i]
							+ "' class='" + data[0][i] + "'>"
							+ data[0][i] + "</a></li>";
	    			} 
     			}
            console.log(title);
            $(".subnav").html(title);
        	},"json")
            
            
			$.post("../Fresh/jugement",{op:"commodityLoding",commodityName:commodityName},function(data){
				 var str = '<div class="goods_detail_pic fl"><img width="222" height="222" src="'+ data[0].photo +'"></div>';
				
				 str += '<div class="goods_detail_list fr">';
				 str+= '<input type="hidden" class="Name" value="'+ data[0].gtitle +'" />'
				str+= '	<h3 >'+ data[0].gtitle +'</h3>';
				str+='<p>'+ data[0].gtips+'</p>';
				str+= '	<div class="prize_bar">';
				str+= '	<span class="show_pirze">¥<em id="gprice">'+ data[0].gprice +'</em></span>';
				str+= '	<span class="show_unit">单  位：500g</span>';
				str+='<span class="show_unit">库存：<span class="goods_kucun">'+ data[0].gunit+'</span></span>';
				str+= '</div>';
				str+= '<div class="goods_num clearfix">';
				str+= '	<div class="num_name fl">数 量：</div>';
				str+= '	<div class="num_add fl">';
				str+= '		<input type="text" class="num_show fl" name="count" value="1">';
				str+= '		<a href="javascript:plus();" class="add fr">+</a>';
				str+= '		<a href="javascript:minus();" class="minus fr">-</a>	';
				str+= '	</div> ';
				
				str+= '	</div>';
				str+= ' <div>';
				
				str+= '    <ul>';
		                    
				str+= '     </ul>';
				str+= ' </div>';
				str+='<div class="total">总价：<em id="gtotal">'+ data[0].gprice +'元</em></div>';
				str+= '<div class="operate_btn">';
				str+= "	<a href='javascript:void(0);' onclick='add()' class='buy_btn' id='buy_now' style='background-color: #ffeded; color: #c40000;'>立即购买</a>";
				str+= "	<a href='javascript:void(0);'    onclick='addcar()'  class='add_cart' id='add_cart'>加入购物车</a>";
				str+= "    <input type='hidden' name='csrfmiddlewaretoken' value='smozgNGahBRc4aoYIVpTZ4MOPHwWvJ3r' />";
				str+= '</div>';
				str  += '</div>' ;
				 $(".show1").html(str); 
				var stu='';
			    stu+='	<ul class="detail_tab clearfix">';
			    stu+='<li class="active" id="detail">商品介绍</li>';
				stu+='<li id="comment">评论</li>';
			    stu+='</ul>';
				
				stu+='<div class="tab_content" id="tab_detail">';
				stu+='<dl>';
				stu+='<dt>商品详情：</dt>';
				stu+='<dd><p>'+ data[0].gcontent+'</p></dd>';
				stu+='</dl>';
				stu+='</div>';
			  	 
				 $(".show3").html(stu); 			    							
			},"json");
			
		})
		
		function addcar(){
			var commodityName = $(".Name").val();//类型名称
			var num = $('.num_show').val();
			$.post("../Fresh/jugement",{op:"addCar",commodityName: commodityName,num:num,type:"name"},function(data){
			if(data == 0){
				//加入失败
				alert("加入购物车失败!!!!")
			}else{
				//加入成功
				alert("加入购物车成功!!!!")
			}
				
			},"json")
		}
		
	function add(){
		alert("aaa");
		location.href="inorder.jsp?commodityName="+$(".Name").val() +'&commodityNum=' + parseFloat($('.num_show').val()) ;
	} 

	</script>

</head>
<body>
	<input type="hidden" class="commodityName"
		value="${param.commodityName}" />
	<div class="header_con">
		<div class="header">
			<div class="welcome fl">欢迎来到天天生鲜!</div>
			<div class="fr">
				<div class="login_btn fl">
					<a href="login.html"><span class="showLogin" id="showLogin">
					</span></a> <span id="exitspan" style="display: none">退出</span>
				</div>

				<div class="user_link fl">
					<span>|</span> <a href="user_Info.jsp">用户中心</a> <span>|</span> <a
						href="buyCar.html">我的购物车</a> <span>|</span> <a href="order.html">我的订单</a>
				</div>
			</div>
		</div>
	</div>
	<div class="search_bar clearfix">
		<a href="/" class="logo fl"><img src="./images/logo.png"></a>
		<div class="search_con fl">
				<input type="text" class="input_text fl" name="q" placeholder="搜索商品">
				<input type="button" class="input_btn fr" onclick="search()" name="" value="搜索">
		</div>

		<div class="guest_cart fr">
			<a href="buyCar.html" class="cart_name fl">我的购物车</a>
			<div class="goods_count fl" id="show_count">0</div>
		</div>
	</div>



	<div class="navbar_con">
		<div class="navbar clearfix">
			<div class="subnav_con fl">
				<h1>全部商品分类</h1>
				<span></span>
				<ul class="subnav">

				</ul>
			</div>
			<ul class="navlist fl">
				<li><a href="index.html">首页</a></li>


			</ul>
		</div>
	</div>


	<div class="breadcrumb">
		<a href="#">全部分类</a> <span>></span> <a href="#">水果</a> <span>></span>
		<span>商品详情</span>
	</div>


	<div class="goods_detail_con clearfix show1"></div>


	<div class="main_wrap clearfix ">
		<div class="l_wrap fl clearfix">
			<div class="new_goods">
				<h3>新品推荐</h3>
				<ul>

					<li><a href="#"><img
							src="./images/wKgTg1tykRGAJ4ULAAA5OS4Kl4c4194242.jpg"></a>
						<h4>
							<a href="#">基围虾</a>
						</h4>
						<div class="prize">￥100.80</div></li>

					<li><a href="#"><img
							src="./images/wKgTg1tykL2AQNUUAAAkaP_7_189281285.jpg"></a>
						<h4>
							<a href="#">秋刀鱼</a>
						</h4>
						<div class="prize">￥50.00</div></li>

				</ul>
			</div>
		</div>

		<div class="r_wrap fr clearfix show3  ">




			<div class="tab_content" id="tab_comment" style="display: none;">
				<!-- 	<dl>
                    
					<dt>评论时间：2018年8月27日 16:10&nbsp;&nbsp;用户名:administrator</dt>
                    <dd>评论内容:个大肉多，味道非常鲜美</dd>
                    
					<dt>评论时间：2019年1月14日 15:04&nbsp;&nbsp;用户名:zxx123456789</dt>
                    <dd>评论内容:</dd>
                    
					<dt>评论时间：2019年1月23日 16:10&nbsp;&nbsp;用户名:zxx123456789</dt>
                    <dd>评论内容:</dd>
                    
					<dt>评论时间：2019年2月12日 20:24&nbsp;&nbsp;用户名:111111</dt>
                    <dd>评论内容:</dd>
                    
					<dt>评论时间：2019年3月6日 00:11&nbsp;&nbsp;用户名:qq740980764</dt>
                    <dd>评论内容:</dd>
                    
				</dl> -->
				<ul class="tab clearfix">
					<li onclick="tabs('#comment',0)" class="curr">图文介绍<strong></strong><span></span></li>
					<li onclick="tabs('#comment',1)">评论<strong>(1)</strong><span></span></li>
				</ul>
				<div class="mc tabcon hide" style="display: block;">
					<div class="norecode">暂无好评！</div>
				</div>
			</div>

		</div>
	</div>



	<div class="add_jump"></div>


	<script type="text/javascript">
function total(){
     var num = parseInt($('.num_show').val());
     var gprice=parseFloat($('#gprice').text());
     $('#gtotal').text(num*gprice+'元');
	
}

function plus() {
    num = parseFloat($('.num_show').val());
    kucun = parseFloat($('.goods_kucun').html());
    if(num<=kucun){
        $('.num_show').val(num+1);
        $('.num_show').blur();
        total();
    }else{
    	alert("库存不够");
    }
}

function minus() {
    num = parseFloat($('.num_show').val());
    if(num>1)
    {
        $('.num_show').val(num-1);
        $('.num_show').blur();
        total();
    }
    else {
        
    }
}


$(function () {
	$('.num_show').blur(function () {
    num = parseInt($('.num_show').val());
    if(num<=1){
    	num = 1;
    }
     kucun = parseInt($('.goods_kucun').text());
     if(num>=kucun)
         num = kucun;
     price = parseFloat($('#gprice').text());
     total = num*price;
     $('.num_show').val(num);
     $('#gtotal').html(ss);
    });
});
</script>

	<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
	<script type="text/javascript">
$(function(){
    $('#add_cart').click(function() {
        var $add_x = $('#add_cart').offset().top;
        var $add_y = $('#add_cart').offset().left;
        var $to_x = $('#show_count').offset().top;
        var $to_y = $('#show_count').offset().left;
        $(".add_jump").css({'left':$add_y+80,'top':$add_x+10,'display':'block'});
        $('#add_cart').click(function() {
            $(".add_jump").stop().animate({
                    'left': $to_y + 7,
                    'top': $to_x + 7
                },
                "fast", function () {
                    $(".add_jump").fadeOut('fast', function () {
                        count = $('.num_show').val();
                        $('#show_count').html(count);
                });
            });

          
        });
    });

});

</script>
	<div class="footer">
		<div class="foot_link">
			<a href="#">关于我们</a> <span>|</span> <a href="#">联系我们</a> <span>|</span>
			<a href="#">招聘人才</a> <span>|</span> <a href="#">友情链接</a>
		</div>
		<p>CopyRight © 2018 衡阳市源辰技术有限公司 All Rights Reserved</p>
		<p>电话：0734-8355998 湘ICP备18051957号-1</p>
	</div>

	<!-- 	<div class="add_jump"></div>
 -->
</body>
</html>
