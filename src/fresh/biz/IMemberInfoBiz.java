package fresh.biz;

import fresh.entity.MemberInfo;

public interface IMemberInfoBiz {
	public MemberInfo login(String account , String pwd);
}
