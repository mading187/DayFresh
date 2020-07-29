package fresh.dao.impl;

import fresh.dao.DBHelper;
import fresh.dao.IMemberInfoDao;
import fresh.entity.MemberInfo;

public class MemberInfoDaoImpl implements IMemberInfoDao {

	@Override
	public MemberInfo login(String account, String pwd) { 
		DBHelper db = new DBHelper();
		String sql = "select mno , nickName , realName , tel , email from memberInfo where (nickName=? or tel=? or email=?) and pwd = md5(?)";
		return db.find(MemberInfo.class, sql , account , account ,account ,pwd);
	}

}
