package br.com.fabriciodeb.sample.proxy;

public interface EntityInterceptor<T> {

	T applyChanges();
}
