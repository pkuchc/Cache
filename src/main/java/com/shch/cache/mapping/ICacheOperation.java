package com.shch.cache.mapping;


public interface ICacheOperation {
	
	Object doGet(Object key);
	void doPut(Object key,Object result);
	void doEvict(Object key);
	void doClear(Object key);


}
