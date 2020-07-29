package fresh.dao;

import java.util.List;

import fresh.entity.GoodsType;

public interface IGoodsTypeDao {
	 public List<GoodsType> findAll();
	 
	 public int update(GoodsType type);
	 
	 public int add(GoodsType type);
}
