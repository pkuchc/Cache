package com.shch.cache.ShchCacheManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.shch.cache.Cache;
import com.shch.cache.CacheManager;

public class ConcurrentHashMapCacheManager implements CacheManager {
	public List<Cache> cacheList=new ArrayList<>(); //需要在XML中配置的属性
	private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>(16); //默认只能该管理器能管理16个Cache实例
     //待注入多个ConcurrentHashMapCache的实例
	
	public void loadCacheMap(){ //初始化cacheMap
		for(int i=0;i<cacheList.size();i++){
			cacheMap.put(cacheList.get(i).getName(), cacheList.get(i));
		}
	}
	public void setcacheList(List<Cache> cacheList){ //配合XML配置属性注入的set方法
		this.cacheList=cacheList;
	}
	@Override
	public Cache getCache(String cacheName) {
		// TODO Auto-generated method stub
		Cache cache=null;
		if(cacheMap.containsKey(cacheName)){
		    cache = this.cacheMap.get(cacheName);
		}
		return cache;
	}

	@Override
	public Collection<String> getCacheNames() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableSet(this.cacheMap.keySet()); //返回所有的Key集合
	}
	@Override
    public List<Cache> getCacheList(){//返回该管理器管理的所有Cache实例集合
    	return cacheList;
    }
}
