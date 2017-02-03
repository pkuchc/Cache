package com.shch.cache.mapping.config;

import java.util.HashMap;
import java.util.Properties;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/*适用于applicaiton.properties配置文件的配置项不确定的情况，将配置文件信息封装到一个Map中，方便查找
 * 扩展Spring的PropertyPlaceholderConfigurer类，
 * PropertyPlaceholderConfigurer是Bean工厂后置处理器的实现，即实现BeanFactoryPostProcessor接口
 * */
public class PropertyConfigurer extends PropertyPlaceholderConfigurer{
   private static HashMap<String,Object> configMap=new HashMap<String,Object>();
   
   @Override
   public void processProperties(ConfigurableListableBeanFactory beanFactory,Properties props){
	   //执行父类的processProperties方法
	   super.processProperties(beanFactory, props);
	   //扩展父类的processProperties方法，增加自定义的逻辑
	   for(Object key:props.keySet()){
		   configMap.put(key.toString(), props.get(key));
	   }
   }
   //对外提供查询服务：根据key返回配置文件中的Value
   public Object getPropertyValue(String key){
	   return  configMap.get(key);
   }
	

}
