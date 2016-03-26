package de.flapdoodle.wicket.model;

import org.apache.wicket.model.IObjectClassAwareModel;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.transformation.ObjectAwareTransformator;

public interface IMapableObjectAwareModel<T> extends IObjectClassAwareModel<T> {

	public default <R> IMapableObjectAwareModel<R> map(Class<R> type, Function1<R, ? super T> read, Function1<T, ? super R> write) {
		return new ObjectAwareTransformator<T, R>(this, type, read, write);
	}
}