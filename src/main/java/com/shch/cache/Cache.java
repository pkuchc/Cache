package com.shch.cache;



public interface Cache {

	/**
	 * Return the cache name.
	 */
	String getName();


	/**
	 * Return the value to which this cache maps the specified key.
	 * <p>Returns {@code null} if the cache contains no mapping for this key;
	 * otherwise, the cached value (which may be {@code null} itself) will
	 * be returned in a {@link ValueWrapper}.
	 * @param key the key whose associated value is to be returned
	 * @return the value to which this cache maps the specified key,
	 * contained within a {@link ValueWrapper} which may also hold
	 * a cached {@code null} value. A straight {@code null} being
	 * returned means that the cache contains no mapping for this key.
	 * @see #get(Object, Class)
	 */
	//ValueWrapper get(Object key); //待研究为啥要将Object类型的value封装一层，ValueWrapper
	Object get(Object key);


	void put(Object key, Object value);	

	/**
	 * Evict the mapping for this key from this cache if it is present.
	 * @param key the key whose mapping is to be removed from the cache
	 */
	void evict(Object key);

	/**
	 * Remove all mappings from the cache.
	 */
	void clear();  //当注解@CacheEvict的参数allEntries=true时，则方法调用后将立即清空所有的缓存


}

