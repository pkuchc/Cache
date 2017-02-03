package com.shch.cache;

import java.util.Collection;
import java.util.List;

public interface CacheManager {

	/**
	 * Return the cache associated with the given name.
	 * @param name the cache identifier (must not be {@code null})
	 * @return the associated cache, or {@code null} if none found
	 */
	//Cache getCache(String name,String key); //在Collection属性中找到cache
	                              //通过注解的缓存名和key查找cache的实例
	Cache getCache(String cacheName); //根据缓存名查找缓存实例
	/**
	 * Return a collection of the cache names known by this manager.
	 * @return the names of all caches known by the cache manager
	 */
	Collection<String> getCacheNames(); //返回所有该管理器管理的缓存实例的名字
	List<Cache> getCacheList();//返回所有该管理器管理的缓存实例对象
    
}
