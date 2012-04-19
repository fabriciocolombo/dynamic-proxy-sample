package br.com.fabriciodeb.sample.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFoo implements InvocationHandler {

	private Object target;

	public ProxyFoo(Object t) {
		this.target = t;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("Before testing for the colors of the feathers");

		method.invoke(target, args);

		System.out.println("After testing for the colors of the feathers");

		return null;
	}

	public static Object createProxy(Object t) {
		return Proxy.newProxyInstance(t.getClass().getClassLoader(), t.getClass().getInterfaces(), new ProxyFoo(t));
	}

	public void setTarget(Object target) {
		this.target = target;
	}

}
