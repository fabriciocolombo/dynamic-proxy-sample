package br.com.fabriciodeb.sample.proxy;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class DynamicProxyEntityInterceptor<T> implements MethodInterceptor, EntityInterceptor<T> {

	private class EntryValue {

		private Object value;
		private Class<?> type;

		public EntryValue(Object value, Class<?> type) {
			this.value = value;
			this.type = type;
		}
	}

	private Object target;

	private Map<String, EntryValue> properties = new HashMap<String, EntryValue>();

	public DynamicProxyEntityInterceptor(Object target) {
		this.target = target;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		String fieldName = getFieldName(method);

		if (method.getName().equals("applyChanges")) {
			for (Entry<String, EntryValue> entry : properties.entrySet()) {
				Method setter = target.getClass().getMethod("set" + entry.getKey(), entry.getValue().type);

				setter.invoke(target, entry.getValue().value);
			}

			return target;
		}

		if (IsGetter(method)) {

			if (properties.containsKey(fieldName)) {
				return properties.get(fieldName).value;
			}

			Object result = method.invoke(target, args);

			if (result instanceof Collection) {
				Collection collection = (Collection) result.getClass().newInstance();

				for (Object item : (Collection) result) {
					collection.add(DynamicProxyEntityInterceptor.createProxy(item));
				}

				result = collection;
			}

			properties.put(fieldName, new EntryValue(result, method.getReturnType()));

			return result;
		}

		if (IsSetter(method) && (args.length == 1)) {
			properties.put(fieldName, new EntryValue(args[0], method.getParameterTypes()[0]));

			return null;
		}

		return null;
	}

	private boolean IsGetter(Method method) {
		return method.getName().toLowerCase().startsWith("get");
	}

	private String getFieldName(Method method) {
		if (IsGetter(method) || IsSetter(method)) {
			return method.getName().substring(3);
		}

		return null;
	}

	private boolean IsSetter(Method method) {
		return method.getName().toLowerCase().startsWith("set");
	}

	@SuppressWarnings({ "unchecked" })
	public static <T> T createProxy(Object target) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setInterfaces(new Class[] { EntityInterceptor.class });
		enhancer.setCallback(new DynamicProxyEntityInterceptor<T>(target));

		return (T) enhancer.create();
	}

	public T applyChanges() {
		return null;
	}

}
