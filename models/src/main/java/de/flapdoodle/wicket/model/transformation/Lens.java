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

import org.danekja.java.util.function.serializable.SerializableBiFunction;
import org.danekja.java.util.function.serializable.SerializableFunction;

import java.io.Serializable;

public class Lens<T, M, IM extends M> implements Serializable {
	private final SerializableFunction<M, IM> copy;
	private final SerializableFunction<M, T> readProperty;
	private final SerializableBiFunction<IM, T, M> changeProperty;
	private Lens(
		SerializableFunction<M, IM> copy,
		SerializableFunction<M, T> readProperty,
		SerializableBiFunction<IM, T, M> changeProperty
	) {
		this.copy = copy;
		this.readProperty = readProperty;
		this.changeProperty = changeProperty;
	}

	public T read(M model) {
		return readProperty.apply(model);
	}

	public M change(M model, T value) {
		return changeProperty.apply(copy.apply(model), value);
	}

	public M map(M model, SerializableFunction<T, T> map) {
		return change(model, map.apply(read(model)));
	}

	public <U> Lens<U, M, ?> and(Lens<U, T, ?> next) {
		return new Lens<>(
			copy,
			target -> next.read(this.read(target)),
			(target, value) -> this.change(target, next.change(this.read(target), value))
		);
	}

	public static <T, M, IM extends M> Lens<T, M, IM> of(
		SerializableFunction<M, IM> asImmutable,
		SerializableFunction<M, T> readProperty,
		SerializableBiFunction<IM, T, M> changeProperty
	) {
		return new Lens<>(asImmutable, readProperty, changeProperty);
	}

	public static <T, M> Lens<T, M, M> of(
		SerializableFunction<M, T> readProperty,
		SerializableBiFunction<M, T, M> changeProperty
	) {
		return new Lens<>(it -> it, readProperty, changeProperty);
	}

	public static <T, M> WithGetter<T, M> ofProperty(SerializableFunction<M, T> read) {
		return new WithGetter<>(read);
	}

	public record WithGetter<T, M>(SerializableFunction<M, T> read) {
		public <IM extends M> Lens<T, M, IM> changeBy(
			SerializableFunction<M, IM> copy,
			SerializableBiFunction<IM, T, M> change
		) {
			return Lens.of(copy, read, change);
		}

		public Lens<T, M, M> changeBy(
			SerializableBiFunction<M, T, M> change
		) {
			return Lens.of(read, change);
		}
	}
}
