package de.flapdoodle.wicket.model;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.wicket.model.IModel;

public class ModelProxy<T> extends DelegatingModel<T> {

	AtomicInteger detachCalled=new AtomicInteger(0);
	AtomicInteger setObjectCalled=new AtomicInteger(0);
	AtomicInteger getObjectCalled=new AtomicInteger(0);
	AtomicReference<T> setObjectValue=new AtomicReference<>();
	
	public ModelProxy(IModel<T> delegate) {
		super(delegate);
	}
	
	@Override
	public void detach() {
		detachCalled.incrementAndGet();
		super.detach();
	}
	
	@Override
	public void setObject(T object) {
		setObjectCalled.incrementAndGet();
		setObjectValue.set(object);
		super.setObject(object);
	}
	
	@Override
	public T getObject() {
		getObjectCalled.incrementAndGet();
		return super.getObject();
	}

	public int getObjectCalled() {
		return getObjectCalled.get();
	}
	
	public int setObjectCalled() {
		return setObjectCalled.get();
	}
	
	public int detachCalled() {
		return detachCalled.get();
	}
	
	public T lastSetObjectValue() {
		return setObjectValue.get();
	}
}
