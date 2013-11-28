package de.flapdoodle.wicket.model.property;


public abstract class AbstractPropertyAccess<T, S> implements IPropertyAccess<T, S> {

	private final Class<T> _type;

	public AbstractPropertyAccess(Class<T> type) {
		_type = type;
	}
	
	@Override
	public Class<T> type() {
		return _type;
	}
}
