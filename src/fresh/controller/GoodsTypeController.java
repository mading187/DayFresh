package fresh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fresh.biz.IGoodsTypeBiz;
import fresh.biz.impl.GoodsTypeBizImpl;
import fresh.entity.GoodsType;
import fresh.util.RequestParamUtil;

@WebServlet("/type")
public class GoodsTypeController extends BasicServlet{

	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String op = req.getParameter("op");
		if("findAll".equals(op)) {
			findAll(req,resp);
		} else if("add".equals(op)) {
			add(req,resp);			
		} else if("update".equals(op)) {
			update(req,resp);
		}
	}


	private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		GoodsType type = RequestParamUtil.getParams(GoodsType.class , req);
		IGoodsTypeBiz goodsTypeBiz = new GoodsTypeBizImpl();
		this.send(resp, goodsTypeBiz.update(type));
	}


	private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		GoodsType type = RequestParamUtil.getParams(GoodsType.class , req);
		IGoodsTypeBiz goodsTypeBiz = new GoodsTypeBizImpl();
		this.send(resp, goodsTypeBiz.add(type));
	}


	private void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		IGoodsTypeBiz goodsTypeBiz = new GoodsTypeBizImpl();
		this.send(resp, goodsTypeBiz.findAll());
	}
}
