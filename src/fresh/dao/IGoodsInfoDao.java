package fresh.dao;

import java.util.List;

import fresh.entity.GoodsInfo;

public interface IGoodsInfoDao {

	public  List<GoodsInfo> findBypage(int page , int rows);
	
	public int total() ;
	
	public int total(String tno , String ganme);
	
	public List<GoodsInfo> findByCondition(String tno , String tname , int page ,int rows);
	
	public List<GoodsInfo> findIndex();
	
	public GoodsInfo findByGno(String gno);
	
	public List<GoodsInfo> findByTno(String tno , int page , int rows);
	
	public int total(String tno);
}
