package fresh.biz.impl;

import java.util.List;

import fresh.biz.IGoodsTypeBiz;
import fresh.dao.IGoodsTypeDao;
import fresh.dao.impl.GoodsTypeDaoImpl;
import fresh.entity.GoodsType;

public class GoodsTypeBizImpl implements IGoodsTypeBiz{

	@Override
	public List<GoodsType> findAll() {
		IGoodsTypeDao goodTypeDao = new GoodsTypeDaoImpl(); 
		return goodTypeDao.findAll();
	}

	@Override
	public int update(GoodsType type) {
		IGoodsTypeDao goodTypeDao = new GoodsTypeDaoImpl(); 
		return goodTypeDao.update(type);
	}

	@Override
	public int add(GoodsType type) {
		IGoodsTypeDao goodTypeDao = new GoodsTypeDaoImpl(); 
		return goodTypeDao.add(type);
	}

}
