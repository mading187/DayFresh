package fresh.dao.impl;

import java.util.List;

import fresh.dao.DBHelper;
import fresh.dao.IGoodsTypeDao;
import fresh.entity.GoodsType;

public class GoodsTypeDaoImpl implements IGoodsTypeDao{

	@Override
	public List<GoodsType> findAll() {
		DBHelper db = new DBHelper();
		String sql = "select tno , tname , pic ,status from goodstype";
		return db.finds(GoodsType.class, sql);
	}

	@Override
	public int update(GoodsType type) {
		DBHelper db = new DBHelper();
		String sql = "update goodstype set tname=? , pic=? , status = ? where tno=?";
		return db.update(sql, type.getTname() , type.getPic() , type.getTno());
	}

	@Override
	public int add(GoodsType type) {
		DBHelper db = new DBHelper();
		String sql = "inset into goodstype values(0 , ? ,? , ?)";
		return db.update(sql,  type.getTname() , type.getPic() , type.getStatus());
	}

}
