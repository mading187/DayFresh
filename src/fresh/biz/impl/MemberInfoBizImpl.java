package fresh.biz.impl;

import fresh.biz.IMemberInfoBiz;
import fresh.dao.IMemberInfoDao;
import fresh.dao.impl.MemberInfoDaoImpl;
import fresh.entity.MemberInfo;
import fresh.util.Stringutil;

public class MemberInfoBizImpl implements IMemberInfoBiz{

	@Override
	public MemberInfo login(String account, String pwd) {
		if(Stringutil.checkNull(account , pwd)) {
			return null;
			}
		
		IMemberInfoDao memberInfoDao = new MemberInfoDaoImpl();
		return memberInfoDao.login(account, pwd);
	}

}
