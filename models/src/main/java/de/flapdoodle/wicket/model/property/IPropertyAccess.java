package de.flapdoodle.wicket.model.property;


public interface IPropertyAccess<T, S> {

	T read(S source);

	void write(S source, T newValue);

	Class<T> type();
}
