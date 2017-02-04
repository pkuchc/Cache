package com.shch;

import org.apache.log4j.Logger;
import com.shch.cache.mapping.ICacheOperation;
import com.shch.cache.update.ICacheScheduleUpdate;

//@Component
public class CacheScheduleUpdate1 implements ICacheScheduleUpdate{ //用户需要实现定义的规范接口
	
	public int num=3;
	private static Logger logger=Logger.getLogger(CacheScheduleUpdate1.class);
	
    @Override
	public Object GetNewCacheResult(Object key) { //用户需要扩展
		//传入key，从数据源获得最新的缓存结果
		num++;
		return num;
	}
    @Override
    public Object GetKey(){
    	return "stock.info";
    }
	@Override
	public Object GetCron() {
		// TODO Auto-generated method stub
		return "0/2 * * * * ?";
	}
	@Override
	public boolean IsNeedUpdated(ICacheOperation cacheOperation, Object key) {
		// TODO Auto-generated method stub
		boolean isSame=true;
		Object obsoleteResult=cacheOperation.doGet(key);//旧的缓存值
		Object newResult=GetNewCacheResult(key);
        logger.info("过时缓存值："+obsoleteResult+"  新的缓存值："+newResult);
        if(obsoleteResult!=null&&obsoleteResult.equals(newResult)){ 
        	logger.debug("obsoleteResult:"+obsoleteResult+"   newResult:"+newResult);
			isSame=false;
		}		
		logger.debug("是否需要更新缓存："+isSame);
		return isSame;
	}


}
