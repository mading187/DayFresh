package fresh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fresh.biz.IMemberInfoBiz;
import fresh.biz.impl.MemberInfoBizImpl;
import fresh.entity.MemberInfo;

@WebServlet("/member")
public class MemberInfoController extends BasicServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String op = req.getParameter("op");

		if ("login".equals(op)) {
			login(req, resp);
		} else if ("info".equals(op)) {
			info(req, resp);
		}

	}

	private void info(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		
		Object obj = session.getAttribute("currentLoginMember");
		if(obj == null) {
			return;
		}
		this.send(resp, 200 , "",obj);
	}

	private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String account = req.getParameter("account");
		String pwd = req.getParameter("pwd");
		String code = req.getParameter("code");
		HttpSession session = req.getSession();
		String vcode = String.valueOf(session.getAttribute("validatecode"));
		if(!code.equalsIgnoreCase(vcode)) {
			System.out.println(333);
			this.send(resp , 500 , "" ,null);
			return;
		}
		
		IMemberInfoBiz memberInfoBiz = new MemberInfoBizImpl() ;
		MemberInfo memberInfo = memberInfoBiz.login(account, pwd);
		if(memberInfo == null) {
			this.send(resp, 501,"",null);
			return;
		}
		
		session.setAttribute("currentLoginMember", memberInfo);
		this.send(resp, 200 , "",null);
		
	}
}
