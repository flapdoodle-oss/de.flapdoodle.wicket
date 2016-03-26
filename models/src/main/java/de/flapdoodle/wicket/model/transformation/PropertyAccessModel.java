package de.flapdoodle.wicket.model.transformation;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.lambda.SerializableBiConsumer;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.IMapableObjectAwareModel;

public class PropertyAccessModel<M, R> implements IMapableObjectAwareModel<R> {

	private final IModel<M> model;
	private final Class<R> type;
	private final Function1<R, ? super M> read;
	private final SerializableBiConsumer<? super M, R> write;

	public PropertyAccessModel(IModel<M> m1, Class<R> type, Function1<R, ? super M> read,
			SerializableBiConsumer<? super M, R> write) {
		this.model = m1;
		this.type = type;
		this.read = read;
		this.write = write;
	}

	@Override
	public R getObject() {
		return read.apply(model.getObject());
	}

	@Override
	public void setObject(R value) {
		write.accept(model.getObject(), value);
	}

	@Override
	public void detach() {
		model.detach();
	}

	@Override
	public Class<R> getObjectClass() {
		return type;
	}

}
