package fresh.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fresh.util.SessionKey;
import fresh.util.Stringutil;

//拦截所有的请求
@WebFilter(filterName = "CharacterEncodingFilter" , value="/back/manager/*" , initParams = @WebInitParam(name="errorpage",value="back/index.html"))
public class CheckLoginFilter implements Filter{

	private String path = "index.html";
	 
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String temp = filterConfig.getInitParameter("erroepage");
		if(!Stringutil.checkNull(temp)) {
			path =temp;
		}
	}
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
			
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		//判断用户是否登录 -》 如果没有登录 ，则session中有当前用户登录西悉尼
		if(request.getSession().getAttribute(SessionKey.currentLoginAdmin) == null){  //说明没有登录
			response.setContentType("text/html;charset=utf-8");
			
			PrintWriter out = response.getWriter();
			String basePath = request.getScheme() + "://" +request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
			out.print("<script> alert('请先登录'); location.href ='"+basePath+path+"'</script> ");
			out.flush();
			out.close();
		}else {   //如果登录了，则请求下一个过滤器
			chain.doFilter(request, response);
		}

	}

}
