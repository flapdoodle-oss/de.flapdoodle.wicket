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

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableBiFunction;
import org.danekja.java.util.function.serializable.SerializableFunction;

public interface IReadOnlyModel<T> extends IModel<T> {

	@Override
	@Deprecated
	default void setObject(T object) {
		throw new UnsupportedOperationException("Model " + getClass() +
			" does not support setObject(Object)");
	}

	@Override
	default <R> IReadOnlyModel<R> map(SerializableFunction<? super T, R> map) {
		return Models.on(this).apply(map);
	}

	@Override
	default <R, U> IModel<R> combineWith(IModel<U> other, SerializableBiFunction<? super T, ? super U, R> combiner) {
		return Models.on(this, other).apply(combiner);
	}

	default IReadOnlyModel<T> andDetach(IDetachable detachable) {
		IReadOnlyModel<T> delegate = this;

		return new IReadOnlyModel<T>() {
			@Override
			public T getObject() {
				return delegate.getObject();
			}

			@Override
			public void detach() {
				delegate.detach();
				detachable.detach();
			}
		};
	}
}
