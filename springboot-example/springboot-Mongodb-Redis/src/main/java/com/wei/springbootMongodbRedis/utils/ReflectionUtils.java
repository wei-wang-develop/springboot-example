package com.wei.springbootMongodbRedis.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionUtils<T> {

private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Class<T> getSuperClassGenricType(final Class clazz){
		return getSuperClassGenricType(clazz, 0);
	}
	
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(final Class clazz, final int index){
		Type genType = clazz.getGenericSuperclass();  
		  
        if (!(genType instanceof ParameterizedType)) {  
            logger.warn(clazz.getSimpleName()  
                    + "'s superclass not ParameterizedType");  
            return Object.class;  
        }  
  
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
  
        if (index >= params.length || index < 0) {  
            logger.warn("Index: " + index + ", Size of "  
                    + clazz.getSimpleName() + "'s Parameterized Type: "  
                    + params.length);  
            return Object.class;  
        }  
        if (!(params[index] instanceof Class)) {  
            logger.warn(clazz.getSimpleName()  
                    + " not set the actual class on superclass generic parameter");  
            return Object.class;  
        }  
  
        return (Class) params[index];  
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "null" })
	public static <T> Class<T> getClazz(final Class clazz){
		if (clazz == null) {
			ParameterizedType type = (ParameterizedType) clazz.getGenericSuperclass();
			return ((Class) type.getActualTypeArguments()[0]);
		}
		return clazz;
	}
	
	/**
	 * reflection operation: get the Class property Descriptor
	 * @param Class  class object
	 * @param String propertyName
	 * @return PropertyDescriptor this class property descriptor
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static PropertyDescriptor getPropertyDescriptor(Class clazz, String propertyName){
		// this variable string to construct the method name
		StringBuffer sb = new StringBuffer(); 
		Method setMethod = null;
		Method getMethod = null;
		PropertyDescriptor propertyDescriptor = null;
		try{
			Field field = clazz.getDeclaredField(propertyName);
			if (field != null){
				// Build the suffix of the method
				String methodSuffix = propertyName.substring(0,1)
						.toUpperCase()+ propertyName.substring(1); 
				sb.append("set" + methodSuffix); // construct setter method
				setMethod = clazz.getDeclaredMethod(sb.toString(), new Class[]{ field.getType()});
				
				sb.delete(0, sb.length()); //Empty the entire variable
				
				sb.append("get" + methodSuffix); //construct getter method
				getMethod = clazz.getDeclaredMethod(sb.toString(), new Class[]{  });
				
				//Construct a property descriptor to store the get and set methods
				propertyDescriptor = new PropertyDescriptor(propertyName, getMethod, setMethod);
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return propertyDescriptor;
	}
	
	
	/**
	 * reflection operation : get object Property
	 * @param Object obj
	 * @param String propertyName
	 * @return Object this object property
	 */
	@SuppressWarnings("rawtypes")
	public static Object getProperty(Object obj, String propertyName){
		Class clazz = obj.getClass(); //Gets the type of the object
		PropertyDescriptor propertyDescriptor = getPropertyDescriptor(clazz, propertyName);
		
		// Get the getter method from the property descriptor
		Method getMethod = propertyDescriptor.getReadMethod(); 
		Object value = null;
		try{
			// Call the method to get the return value of the getter method
			value = getMethod.invoke(obj, new Object[]{} );
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return value;
	}
	
	/**
	 * reflection operation : modify object Property
	 * @param Object obj
	 * @param String propertyName
	 * @param Object value
	 */
	@SuppressWarnings("rawtypes")
	public static void setProperty(Object obj, String propertyName, Object value){
		Class clazz = obj.getClass(); //Gets the type of the object
		PropertyDescriptor propertyDescriptor = getPropertyDescriptor(clazz, propertyName);
		
		// Get the setter method from the property descriptor
		Method setMethod = propertyDescriptor.getWriteMethod();
		try{
			// Call the setter method to save the value to the property
			setMethod.invoke(obj, new Object[]{value} );
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
}
