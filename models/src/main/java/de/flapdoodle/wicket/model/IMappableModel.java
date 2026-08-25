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
package de.flapdoodle.wicket.model;

import de.flapdoodle.wicket.model.functions.Function1;
import de.flapdoodle.wicket.model.functions.Functions;
import de.flapdoodle.wicket.model.functions.SerializableFunction;
import de.flapdoodle.wicket.model.functions.SymetricalFunction;
import de.flapdoodle.wicket.model.transformation.ModelSet;
import de.flapdoodle.wicket.model.transformation.ObjectAwareModelDelegate;
import de.flapdoodle.wicket.model.transformation.ObjectAwareTransformator;
import de.flapdoodle.wicket.model.transformation.TransformatorModel;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableBiFunction;

/**
 *
 * @author mosmann
 */
public interface IMappableModel<T> extends IModel<T> {
	default <R> IMappableObjectAwareModel<R> map(Class<R> type, SymetricalFunction<T, R> mapping) {
		return map(type, mapping, mapping.reverse());
	}

	default <R> IMappableObjectAwareModel<R> map(Class<R> type, Function1<R, ? super T> read, Function1<T, ? super R> write) {
		return new ObjectAwareTransformator<T, R>(this, type, read, write);
	}

	default <R> IMappableObjectAwareModel<R> mapNullable(Class<R> type, SymetricalFunction<T, R> mapping) {
		return mapNullable(type, mapping, mapping.reverse());
	}

	default <R> IMappableObjectAwareModel<R> mapNullable(Class<R> type, Function1<R, ? super T> read, Function1<T, ? super R> write) {
		return new ObjectAwareTransformator<T, R>(this, type, Functions.orNull(read), Functions.orNull(write));
	}

	default <R> IMappableModel<R> map(SymetricalFunction<T, R> mapping) {
		return map(mapping, mapping.reverse());
	}

	default <R> IMappableModel<R> map(Function1<R, ? super T> read, Function1<T, ? super R> write) {
		return new TransformatorModel<T, R>(this, read, write);
	}

	default <R> IMappableModel<R> mapNullable(SymetricalFunction<T, R> mapping) {
		return mapNullable(mapping, mapping.reverse());
	}

	default <R> IMappableModel<R> mapNullable(Function1<R, ? super T> read, Function1<T, ? super R> write) {
		return new TransformatorModel<T, R>(this, Functions.orNull(read), Functions.orNull(write));
	}

	default IMappableModel<T> andDetach(IDetachable detachable) {
		IMappableModel<T> delegate = this;

		return new IMappableModel<T>() {
			@Override
			public T getObject() {
				return delegate.getObject();
			}

			@Override
			public void setObject(T object) {
				delegate.setObject(object);
			}

			@Override
			public void detach() {
				delegate.detach();
				detachable.detach();
			}
		};
	}

	default IMappableObjectAwareModel<T> withObjectType(Class<T> objectType) {
		return new ObjectAwareModelDelegate<>(this, objectType);
	}

	default <R> IMappableModel<R> copyOnChangeProperty(SerializableFunction<T, R> readProperty, SerializableBiFunction<T, R, T> changeProperty) {
		return new ModelSet.Set1<>(this).copyOnChangeProperty(readProperty, changeProperty);
	}

	default <R, IT extends T> IMappableModel<R> copyOnChangeProperty(SerializableFunction<T, IT> asImmutable, SerializableFunction<T, R> readProperty, SerializableBiFunction<IT, R, T> changeProperty) {
		return new ModelSet.Set1<>(this).copyOnChangeProperty(asImmutable, readProperty, changeProperty);
	}

}
