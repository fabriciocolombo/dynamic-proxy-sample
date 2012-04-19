package br.com.fabriciodeb.sample.proxy;

import java.lang.reflect.Method;

import org.apache.commons.logging.LogFactory;

import org.apache.commons.logging.Log;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class LoggerInterceptor<T> implements MethodInterceptor {

	private static final Log LOG = LogFactory.getLog(LoggerInterceptor.class);

	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		LOG.debug("before method: " + method);

		Object result = proxy.invokeSuper(obj, args);

		LOG.debug("after method: " + method);
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T createProxy(Class<?> clazz) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new LoggerInterceptor<T>());

		return (T) enhancer.create();
	}

}
