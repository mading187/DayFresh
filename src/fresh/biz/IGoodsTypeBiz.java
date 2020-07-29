package fresh.biz;

import java.util.List;

import fresh.entity.GoodsType;

public interface IGoodsTypeBiz {
     public List<GoodsType> findAll();
	 
	 public int update(GoodsType type);
	 
	 public int add(GoodsType type);
	
	
}
