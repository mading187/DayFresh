package fresh.dao.impl;

import java.util.ArrayList;
import java.util.List;

import fresh.dao.DBHelper;
import fresh.dao.IGoodsInfoDao;
import fresh.entity.GoodsInfo;
import fresh.util.Stringutil;

public class GoodsInfoDaoImpl implements IGoodsInfoDao {

	@Override
	public List<GoodsInfo> findBypage(int page, int rows) {
		DBHelper db = new DBHelper();
		String sql = "select gno , gname ,g.tno , tname , price , concat(balance , '/' , unit) unit , intro from goodsinfo g , goodstype t"
				+ " where g.tno = t.tno order by gno desc  limit ?,?";
		return db.finds(GoodsInfo.class, sql, (page - 1) * rows, rows);
	}

	@Override
	public int total() {
		DBHelper db = new DBHelper();
		String sql = "select count(gno) from goodsinfo";
		return db.total(sql);
	}

	@Override
	public int total(String tno, String gname) {
		DBHelper db = new DBHelper();
		String sql = "select count(gno) from goodsinfo where 1=1";
		List<Object> params = new ArrayList<Object>();
		if(!Stringutil.checkNull(tno)) {
			sql +=" and tno=? ";
			params.add(tno);
		}
		
		
		if(!Stringutil.checkNull(gname)) {
			sql +=" and gname like concat('%' , ? , '%') ";
			params.add(gname);
		}
		return db.total(sql , params);
	}

	@Override
	public List<GoodsInfo> findByCondition(String tno, String gname, int page, int rows) {
		DBHelper db = new DBHelper();
		String sql = "select gno , gname ,g.tno , tname , price , concat(balance , '/' , unit) unit , intro from goodsinfo g , goodstype t"
				+ " where g.tno = t.tno";
		List<Object> params = new ArrayList<Object>();
		
		if(!Stringutil.checkNull(tno)) {
			sql +=" and tno=? ";
			params.add(tno);
		}
		
		
		if(!Stringutil.checkNull(gname)) {
			sql +=" and gname like concat('%' , ? , '%') ";
			params.add(gname);
		}
		sql +=" order by gno desc  limit ?,?";
		params.add( ( page-1 )*rows);
		params.add(rows);
		return db.finds(GoodsInfo.class, sql, params);
	}

	@Override
	public List<GoodsInfo> findIndex() {
		DBHelper db = new DBHelper();
		String sql = "select gno , gname , price ,tno ,pics from goodsinfo g1 where  4 > "
				+ "(select count(gno) from goodsinfo g2 where g1.tno=g2.tno and g1.gno<g2.gno) order by g1.tno asc , g1.gno desc";
		return db.finds(GoodsInfo.class,sql );
	}

	@Override
	public GoodsInfo findByGno(String gno) {
		DBHelper db = new DBHelper();
		String sql = "select gno , gname , price , intro , balance ,pics , unit ,weight , qperied , descr from goodsinfo where gno=?";
		return db.find(GoodsInfo.class , sql , gno);
	}

	@Override
	public List<GoodsInfo> findByTno(String tno, int page, int rows) {
		DBHelper db = new DBHelper();
		String sql = "select gno , gname , price   ,pics , unit ,weight from goodsinfo where tno=? order by gno desc limit ?,?";
		return db.finds(GoodsInfo.class , sql , tno , (page-1)*rows , rows);
	}

	@Override
	public int total(String tno) {
		DBHelper db = new DBHelper();
		String sql = "select count(tno) from goodsinfo where tno=?";
		return db.total(sql , tno);
	}

}
