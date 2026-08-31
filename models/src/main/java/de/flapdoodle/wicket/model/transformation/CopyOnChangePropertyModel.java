/*
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Jan Bernitt <unknown@email.de>
 *
 * with contributions from
 * 	nobody yet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.wicket.model.transformation;

import de.flapdoodle.wicket.model.IMappableModel;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableBiFunction;
import org.danekja.java.util.function.serializable.SerializableFunction;

public class CopyOnChangePropertyModel<T, M, IM> implements IMappableModel<T> {

	private final IModel<M> sourceModel;
	private final SerializableFunction<M, IM> asImmutable;
	private final SerializableFunction<M, T> readProperty;
	private final SerializableBiFunction<IM, T, M> changeProperty;

	public CopyOnChangePropertyModel(
		IModel<M> sourceModel,
		SerializableFunction<M, IM> asImmutable,
		SerializableFunction<M, T> readProperty,
		SerializableBiFunction<IM, T, M> changeProperty
	) {
		this.sourceModel = sourceModel;
		this.asImmutable = asImmutable;
		this.readProperty = readProperty;
		this.changeProperty = changeProperty;
	}

	@Override
	public T getObject() {
		return readProperty.apply(sourceModel.getObject());
	}

	@Override
	public void setObject(T object) {
		sourceModel.setObject(changeProperty.apply(asImmutable.apply(sourceModel.getObject()), object));
	}

	@Override
	public void detach() {
		sourceModel.detach();
	}

	public static <T, M> IMappableModel<T> of(
		IModel<M> source,
		SerializableFunction<M, T> readProperty,
		SerializableBiFunction<M, T, M> changeProperty
	) {
		return new CopyOnChangePropertyModel<>(source, it -> it, readProperty, changeProperty);
	}

	public static <T, M, IM> IMappableModel<T> of(
		IModel<M> source,
		SerializableFunction<M, IM> asImmutable,
		SerializableFunction<M, T> readProperty,
		SerializableBiFunction<IM, T, M> changeProperty
	) {
		return new CopyOnChangePropertyModel<>(source, asImmutable, readProperty, changeProperty);
	}
}
