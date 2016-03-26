package de.flapdoodle.wicket.model;

import org.apache.wicket.model.IModel;

import de.flapdoodle.functions.Function1;

public interface IReadOnlyModel<T> extends IModel<T> {

	@Override
	@Deprecated
	void setObject(T object);
	
	public default <R> IReadOnlyModel<R> apply(Function1<R, ? super T> map) {
		return Models.on(this).apply(map);
	}
}
