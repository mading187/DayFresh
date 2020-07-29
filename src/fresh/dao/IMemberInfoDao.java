package fresh.dao;

import fresh.entity.MemberInfo;

public interface IMemberInfoDao {
	public MemberInfo login(String account , String pwd);
}
