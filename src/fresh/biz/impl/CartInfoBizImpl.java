package fresh.biz.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import fresh.biz.ICartInfoBiz;
import fresh.dao.ICartInfoDao;
import fresh.dao.impl.CartInfoDaoImpl;
import fresh.entity.CartInfo;
import fresh.util.Stringutil;

public class CartInfoBizImpl implements ICartInfoBiz{

	@Override
	public List<Map<String, Object>> findCart(String mno) {
		if(Stringutil.checkNull(mno)) {
			return Collections.emptyList();
		}
		ICartInfoDao cartInfoDao = new CartInfoDaoImpl();
		return cartInfoDao.findCart(mno);
	}

	@Override
	public List<CartInfo> finds(String mno) {
		if(Stringutil.checkNull(mno)) {
			return Collections.emptyList();
		}
		ICartInfoDao cartInfoDao = new CartInfoDaoImpl();
		return cartInfoDao.finds(mno);
	}

	@Override
	public int updateNum(String cno, int num) {
		if(Stringutil.checkNull(cno)) {
			return -1;
		}
		ICartInfoDao cartInfoDao = new CartInfoDaoImpl();
		return cartInfoDao.updateNum(cno, num);
	}

	@Override
	public int add(CartInfo cf) {
		if(Stringutil.checkNull(cf.getCno())) {
			return -1;
		}
		ICartInfoDao cartInfoDao = new CartInfoDaoImpl();
		return cartInfoDao.add(cf);
	}

	@Override
	public int dalete(String[] cnos) {
		
		return 1;
	}

	@Override
	public int delete(String cno) {
		if(Stringutil.checkNull(cno)) {
			return -1;
		}
		ICartInfoDao cartInfoDao = new CartInfoDaoImpl();
		if(cno.contains(",")) {
			
		}
		return cartInfoDao.delete(cno);
	}

}
