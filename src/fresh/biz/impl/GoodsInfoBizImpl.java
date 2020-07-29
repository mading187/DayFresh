package fresh.biz.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fresh.biz.IGoodsInfoBiz;
import fresh.dao.IGoodsInfoDao;
import fresh.dao.IGoodsInfoDao;
import fresh.dao.IGoodsTypeDao;
import fresh.dao.impl.GoodsInfoDaoImpl;
import fresh.dao.impl.GoodsTypeDaoImpl;
import fresh.entity.GoodsInfo;

public class GoodsInfoBizImpl implements IGoodsInfoBiz{

  	@Override
	public List<GoodsInfo> findBypage(int page, int rows) {
  		IGoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
  		return goodsInfoDao.findBypage(page, rows);
  		
	}

	@Override
	public Map<String, Object> finds(int page, int rows) {
		IGoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("total",goodsInfoDao.total());
		map.put("rows",goodsInfoDao.findBypage(page, rows));
		return map;
	}

	@Override
	public Map<String, Object> findByContition(String tno, String gname, int page, int rows) {
		IGoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("total",goodsInfoDao.total(tno,gname));
		map.put("rows",goodsInfoDao.findByCondition(tno, gname, page, rows));
		return map;
	}

	@Override
	public Map<String, Object> findIndex() {
		Map<String , Object>  map = new HashMap<String, Object>();
		IGoodsTypeDao goodsTypeDao = new GoodsTypeDaoImpl();
		
		IGoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		
		map.put("types", goodsTypeDao.findAll());
		map.put("goods", goodsInfoDao.findIndex());
		System.out.println(goodsInfoDao.findIndex());
		
		return map;
	}

	@Override
	public GoodsInfo findByGno(String gno) {
		IGoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
  		return goodsInfoDao.findByGno(gno);
	}

	@Override
	public List<GoodsInfo> findByTno(String tno, int page, int rows) {
		IGoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
  		return goodsInfoDao.findByTno(tno, page , rows);
	}

	@Override
	public Map<String, Object> findByTnos(String tno, int page, int rows) {
		Map<String , Object> map = new HashMap<String, Object>();
		IGoodsInfoDao goodsInfoDao = new GoodsInfoDaoImpl();
		map.put("total" , goodsInfoDao.total(tno));
		map.put("rows" , goodsInfoDao.findByTno(tno, page, rows));
		return map;
	}

	

}
