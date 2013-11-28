package de.flapdoodle.wicket.model.property;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IObjectClassAwareModel;


public class PropertyAccessModel<T, S> implements IObjectClassAwareModel<T> {

	private final IModel<S> _source;
	private final IPropertyAccess<T, S> _access;

	public PropertyAccessModel(IModel<S> source, IPropertyAccess<T, S> access) {
		_source = source;
		_access = access;
	}
	
	@Override
	public T getObject() {
		return _access.read(_source.getObject());
	}

	@Override
	public void setObject(T value) {
		_access.write(_source.getObject(), value);
	}

	@Override
	public void detach() {
		_source.detach();
	}

	@Override
	public Class<T> getObjectClass() {
		return _access.type();
	}

}
