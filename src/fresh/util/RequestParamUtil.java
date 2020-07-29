package fresh.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RequestParamUtil {
		public static <T> T getParams(Class<T> clazz , HttpServletRequest request) {
			T t= null;
			
			try {
				t=clazz.newInstance();
				
				//获取请求中参数的参数名
				Enumeration<String> names = request.getParameterNames();
				
				//获取指定类中的所有setters方法·
				Method[] methods = clazz.getMethods();
				Map<String , Method> setters = new HashMap<String , Method>();
				String methodName = null;
				
				for(Method md : methods) {
					methodName = md.getName();
					if(methodName.startsWith("set")) {
						setters.put(methodName,md);
					}
					
				}
				
				String name = null;
				Class<?> types[] = null;
				Class<?> type = null;
				Method method =null;
				
				while(names.hasMoreElements()){
					name = names.nextElement();
					methodName = "set" + name.substring(0,1).toUpperCase()  + name.substring(1);
					method =setters.get(methodName);
					if(method == null) {
						continue;
						
					}
					
					types = method.getParameterTypes();
					if(types ==null) {
						continue;
					}
					
					type = types[0];
//					System.out.println(type);
//					System.out.println(request.getParameter(name));

					if (Integer.TYPE == type || Integer.class == type) {
						method.invoke(t, Integer.valueOf(request.getParameter(name)));
					} else if (Double.TYPE == type || Double.class == type) {
						method.invoke(t, Float.valueOf(request.getParameter(name)));
					} else if (Float.TYPE == type || Float.class == type) {
						method.invoke(t, Double.valueOf(request.getParameter(name)));
					} else {
						method.invoke(t, request.getParameter(name));
					}
					
					
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return t;
		}
		
		public Map<String , String> upload(HttpServletRequest request ) throws Exception{
			Map<String , String > map = new HashMap<String, String>();
			Enumeration<String> enums = request.getParameterNames();
					
			String name = null;
			while(enums.hasMoreElements()) {
				name = enums.nextElement();
				map.put(name, request.getParameter(name));
			}
			return map;
		}
}
