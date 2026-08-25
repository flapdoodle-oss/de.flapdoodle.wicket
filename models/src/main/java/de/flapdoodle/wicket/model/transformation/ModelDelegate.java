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

import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableBiFunction;
import org.danekja.java.util.function.serializable.SerializableFunction;
import org.danekja.java.util.function.serializable.SerializablePredicate;
import org.danekja.java.util.function.serializable.SerializableSupplier;

public class ModelDelegate<T> implements IModel<T> {

	private final IModel<T> delegate;
	public ModelDelegate(IModel<T> delegate) {
		this.delegate = delegate;
	}

	public T getObject() {
		return delegate.getObject();
	}

	public void setObject(T object) {
		delegate.setObject(object);
	}

	public void detach() {
		delegate.detach();
	}

	public IModel<T> filter(SerializablePredicate<? super T> predicate) {
		return delegate.filter(predicate);
	}

	public <R> IModel<R> map(SerializableFunction<? super T, R> mapper) {
		return delegate.map(mapper);
	}

	public <R, U> IModel<R> combineWith(IModel<U> other,
		SerializableBiFunction<? super T, ? super U, R> combiner) {
		return delegate.combineWith(other, combiner);
	}

	public <R> IModel<R> flatMap(
		SerializableFunction<? super T, IModel<R>> mapper) {
		return delegate.flatMap(mapper);
	}

	public IModel<T> orElse(T other) {
		return delegate.orElse(other);
	}

	public IModel<T> orElseGet(SerializableSupplier<? extends T> other) {
		return delegate.orElseGet(other);
	}

	public IModel<Boolean> isPresent() {
		return delegate.isPresent();
	}
	
	public <R extends T> IModel<R> as(Class<R> clazz) {
		return delegate.as(clazz);
	}
}
