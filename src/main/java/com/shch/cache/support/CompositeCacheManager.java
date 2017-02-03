package com.shch.cache.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.shch.cache.Cache;
import com.shch.cache.CacheManager;
import com.shch.cache.mapping.CacheMapping;

public class CompositeCacheManager implements CacheManager {

	private final List<CacheManager> cacheManagers = new ArrayList<CacheManager>(); //需要XML配置该属性
	private final ConcurrentHashMap<String,Cache> cacheCollection=new ConcurrentHashMap<String,Cache>();//存储所有缓存实例的集合
	
	private static Logger logger=Logger.getLogger(CompositeCacheManager.class);
	/**
	 * Construct an empty CompositeCacheManager, with delegate CacheManagers to
	 * be added via the {@link #setCacheManagers "cacheManagers"} property.
	 */
	public CompositeCacheManager() {
	}

	//初始化方法
	public void loadCache(){ //初始化的时候收集所有的缓存实例,在xml配置文件中指定为初始化方法
		for(CacheManager cacheManager:cacheManagers){
			for(Cache cache:cacheManager.getCacheList()){
				cacheCollection.put(cache.getName(), cache);
			}
		}
	}
	/**
	 * Construct a CompositeCacheManager from the given delegate CacheManagers.
	 * @param cacheManagers the CacheManagers to delegate to
	 */
	public CompositeCacheManager(CacheManager... cacheManagers) { //可变参数方法
		setCacheManagers(Arrays.asList(cacheManagers));
	}


	/**
	 * Specify the CacheManagers to delegate to.
	 */
	public void setCacheManagers(Collection<CacheManager> cacheManagers) {
		this.cacheManagers.addAll(cacheManagers);
	}

//	@Override
//	public Cache getCache(String name) {
//		for (CacheManager cacheManager : this.cacheManagers) {
//			Cache cache = cacheManager.getCache(name);
//			if (cache != null) {
//				return cache;
//			}
//		}
//		return null;
//	}

	@Override
	public Collection<String> getCacheNames() {  //返回所有Cache实例的名字
		Set<String> names = new LinkedHashSet<String>();
		for (CacheManager manager : this.cacheManagers) {
			names.addAll(manager.getCacheNames());
		}
		return Collections.unmodifiableSet(names);
	}

	@Override
	public Cache getCache(String cacheName) { //stock.info
		// TODO Auto-generated method stub
		
		//是否在这里加入路由策略，根据缓存name和key同时查找缓存实例
		Cache cache=null;	
		if(cacheName!=null){
			cache=cacheCollection.get(cacheName);
			logger.debug("Cache实例的名字："+cacheName);
		}else{
			logger.error("cacheName为空，无法查找缓存实例！");
		}

		return cache;
	}

	public List<CacheManager> getCacheManager() {
		// TODO Auto-generated method stub
		
		return cacheManagers;
	}

	@Override
	public List<Cache> getCacheList() {
		// TODO Auto-generated method stub
		return null;
	}

}
