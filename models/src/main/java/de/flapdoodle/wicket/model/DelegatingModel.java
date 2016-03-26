package de.flapdoodle.wicket.model;

import org.apache.wicket.model.IModel;

public class DelegatingModel<T> implements IModel<T> {

	private final IModel<T> delegate;

	public DelegatingModel(IModel<T> delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public void detach() {
		delegate.detach();
	}

	@Override
	public T getObject() {
		return delegate.getObject();
	}

	@Override
	public void setObject(T object) {
		delegate.setObject(object);
	}

}
