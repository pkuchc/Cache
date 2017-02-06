package com.shch.cache.update;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.shch.cache.mapping.CacheOperation;
import com.shch.cache.mapping.ICacheOperation;

public  class CacheUpdateTask  implements Runnable{ //在IOC容器中是prototype类型
    private  Object key;
    private ICacheScheduleUpdate cacheScheduleUpdate;

    public ICacheOperation cacheOperation=CacheOperation.getObject();
    
	private Logger logger=Logger.getLogger(CacheUpdateTask.class);
	
	public void setKey(Object key) {
		this.key = key;
	}
	public void setCacheScheduleUpdate(ICacheScheduleUpdate cacheScheduleUpdate){
		this.cacheScheduleUpdate=cacheScheduleUpdate;
	}

	@Override
	public void run() {		
		// TODO Auto-generated method stub
		
        if(cacheScheduleUpdate.IsNeedUpdated(cacheOperation,key)){			
			cacheOperation.doPut(key,(Serializable)cacheScheduleUpdate.GetNewCacheResult(key));//更新缓存
			logger.info("缓存过期，定时任务更新缓存！");			
		}else{
			logger.info("缓存未过期，定时任务无需更新缓存！");
		}		
	}	
	  
  }
