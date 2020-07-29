$(function(){
	//每次进入到网页检测有没有登录
	$.post("../Fresh/jugement" , {op:"checkLogin"} , function(data){
		if(data.code==1){
			$("#showLogin").css("display","none");
			$("#exitspan").css("display","block");
			$("#exitspan").html("欢迎您:"+ data.obj.uname+ "<span>|</span> <a id='exitspan' 		style='cursor:pointer;  font-style: normal;'>退出</a>");
		}else{
			location.href = "login.html";				 
		}
	},"json");

	$("#exitspan").on("click",function(){
		$.post("../Fresh/jugement" , {op:"exitLogin"} ,function(data){
			$("#showLogin").css("display","block");   
			$("#exitspan").css("display","none"); 
		},"json");

	});  
})

