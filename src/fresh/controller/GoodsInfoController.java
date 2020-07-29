package fresh.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fresh.biz.IGoodsInfoBiz;
import fresh.biz.impl.GoodsInfoBizImpl;


@WebServlet("/goods")
public class GoodsInfoController extends BasicServlet{

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String op = req.getParameter("op");
		if("finds".equals(op)) {
			finds(req,resp);
		} else if("findByPage".equals(op)) {
			findByPage(req,resp);			
		} else if("findByCondition".equals(op)) {
			findByCondition(req,resp);
		} else if("findIndex".equals(op)) {
			findIndex(req,resp);
		} else if("findByGno".equals(op)) {
			findByGno(req,resp);
		}
	}

	private void findByGno(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String gno = req.getParameter("gno");
		IGoodsInfoBiz  goodsInfoBiz = new GoodsInfoBizImpl();
		this.send(resp , 200 , "" , goodsInfoBiz.findByGno(gno));
		
	}

	private void findIndex(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		IGoodsInfoBiz goodsInfoBiz = new GoodsInfoBizImpl();
		
		this.send(resp, 200 , "" ,goodsInfoBiz.findIndex());
	}

	private void findByCondition(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int page = Integer.parseInt( req.getParameter("page") );
		int rows = Integer.parseInt( req.getParameter("rows") );
		String tno = req.getParameter("tno");
		String gname = req.getParameter("gname");

		
		IGoodsInfoBiz  goodsInfoBiz = new GoodsInfoBizImpl();
		this.send(resp, goodsInfoBiz.findByContition(tno, gname, page, rows));		
	}

	private void findByPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int page = Integer.parseInt( req.getParameter("page") );
		int rows = Integer.parseInt( req.getParameter("rows") );
		IGoodsInfoBiz  goodsInfoBiz = new GoodsInfoBizImpl();
		this.send(resp, goodsInfoBiz.finds(page, rows));
		
		
	}

	private void finds(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int page = Integer.parseInt( req.getParameter("page") );
		int rows = Integer.parseInt( req.getParameter("rows") );
		IGoodsInfoBiz  goodsInfoBiz = new GoodsInfoBizImpl();
		this.send(resp, goodsInfoBiz.finds(page, rows));
		
	}

}
