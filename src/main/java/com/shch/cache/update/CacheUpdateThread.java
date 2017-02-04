package com.shch.cache.update;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.shch.cache.mapping.CacheOperation;
import com.shch.cache.mapping.ICacheOperation;

public  class CacheUpdateThread  implements Runnable{ //在IOC容器中是prototype类型
    private  Object key;
    private ICacheScheduleUpdate cacheScheduleUpdate;
	//@Autowired
    public ICacheOperation cacheOperation=CacheOperation.getObject();
//	@Autowired //
//	public ICacheScheduleUpdate cacheScheduleUpdate;//待注入，或自动注入，待考虑：如果存在多个实现接口的实例，怎么办？？
	private Logger logger=Logger.getLogger(CacheUpdateThread.class);
	
	public void setKey(Object key) {
		this.key = key;
	}
	public void setCacheScheduleUpdate(ICacheScheduleUpdate cacheScheduleUpdate){
		this.cacheScheduleUpdate=cacheScheduleUpdate;
	}

	@Override
	public void run() {		
		// TODO Auto-generated method stub
//		result=cacheScheduleUpdate.GetNewCacheResult(key);
//		cacheOperation.doPut(key, result);
		
        if(cacheScheduleUpdate.IsNeedUpdated(cacheOperation,key)){			
			cacheOperation.doPut(key,(Serializable)cacheScheduleUpdate.GetNewCacheResult(key));//更新缓存
			logger.info("缓存过期，定时任务更新缓存！");			
		}else{
			logger.info("缓存未过期，定时任务无需更新缓存！");
		}		
	}	
	  
  }
