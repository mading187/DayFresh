package fresh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fresh.biz.ICartInfoBiz;
import fresh.biz.impl.CartInfoBizImpl;
import fresh.entity.MemberInfo;

@WebServlet("/cart")
public class CartController extends BasicServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String op = req.getParameter("op");

		if ("info".equals(op)) {
			info(req, resp);
		}
	}

	private void info(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		Object obj = session.getAttribute("currentLoginMember");
		if (obj == null) {
			this.send(resp, 500, "", null);
			return;
		}

		// 查数据库
		ICartInfoBiz cartInfoBiz = new CartInfoBizImpl();
		MemberInfo mf = (MemberInfo) obj;
		this.send(resp, 200, "", cartInfoBiz.findCart(String.valueOf(mf.getMno())));
	}

}
