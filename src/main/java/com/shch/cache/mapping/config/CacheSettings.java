package com.shch.cache.mapping.config;
/*采用@Value注解引入applicaiton.properties配置文件的信息，适用于配置项比较固定的情况。
 * 如：数据库的配置信息：
 * spring.datasource.url=jdbc:mysql://localhost:3306/test
 * spring.datasource.username=root
 * spring.datasource.password=123456
 * spring.datasource.driver-class-name=com.mysql.jdbc.Driver
 * */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//@Component
public class CacheSettings {
	@Value("#{config.stock}") //注入配置文件的值
	public String stock;
	@Value("#{config.clear}")
	public String clear;
	@Value("#{config.risk}")
	public String risk;
	//待添加其他缓存选项
	
	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public CacheSettings(){
		
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getClear() {
		return clear;
	}

	public void setClear(String clear) {
		this.clear = clear;
	}
		

}
