package de.flapdoodle.wicket.model.transformation;

import org.apache.wicket.model.IModel;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.IMapableObjectAwareModel;

public class ObjectAwareTransformator<T, R> implements IMapableObjectAwareModel<R> {

	private final IModel<T> model;
	private final Class<R> type;
	private final Function1<R, ? super T> read;
	private final Function1<T, ? super R> write;

	public ObjectAwareTransformator(IModel<T> model, Class<R> type, Function1<R, ? super T> read, Function1<T, ? super R> write) {
		this.model = model;
		this.type = type;
		this.read = read;
		this.write = write;
	}

	@Override
	public Class<R> getObjectClass() {
		return type;
	}

	@Override
	public R getObject() {
		return read.apply(model.getObject());
	}

	@Override
	public void setObject(R value) {
		model.setObject(write.apply(value));
	}

	@Override
	public void detach() {
		model.detach();
	}
}
