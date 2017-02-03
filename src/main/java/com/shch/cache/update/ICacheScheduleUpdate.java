package com.shch.cache.update;

import com.shch.cache.mapping.ICacheOperation;

public interface ICacheScheduleUpdate { //定义缓存定时刷新逻辑接口，定时返回缓存的更新值
	
    boolean IsNeedUpdated(ICacheOperation opera,Object key); //是否需要更新判断逻辑
    Object GetNewCacheResult(Object key); //提供新的缓存值
    Object GetKey();
}
