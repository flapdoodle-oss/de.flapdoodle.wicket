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
import java.util.Objects;

public class ModelLens<M, T, IM extends M> implements Serializable {
	private final SerializableFunction<M, IM> copy;
	private final SerializableFunction<M, T> readProperty;
	private final SerializableBiFunction<IM, T, M> changeProperty;
	private ModelLens(
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

	public <U> ModelLens<M, U, ?> and(ModelLens<T, U, ?> next) {
		return new ModelLens<>(
			copy,
			target -> next.read(this.read(target)),
			(target, value) -> this.change(target, next.change(this.read(target), value))
		);
	}

	public static <T, M, IM extends M> ModelLens<M, T, IM> of(
		SerializableFunction<M, IM> asImmutable,
		SerializableFunction<M, T> readProperty,
		SerializableBiFunction<IM, T, M> changeProperty
	) {
		return new ModelLens<>(asImmutable, readProperty, changeProperty);
	}

	public static <T, M> ModelLens<M, T, M> of(
		SerializableFunction<M, T> readProperty,
		SerializableBiFunction<M, T, M> changeProperty
	) {
		return new ModelLens<>(it -> it, readProperty, changeProperty);
	}

	public static <T, M> WithGetter<T, M> ofProperty(SerializableFunction<M, T> read) {
		return new WithGetter<>(read);
	}

	public static final class WithGetter<T, M> {
		private final SerializableFunction<M, T> read;
		public WithGetter(SerializableFunction<M, T> read) {
			this.read = read;
		}
			public <IM extends M> ModelLens<M, T, IM> changeBy(
				SerializableFunction<M, IM> copy,
				SerializableBiFunction<IM, T, M> change
			) {
				return ModelLens.of(copy, read, change);
			}

			public ModelLens<M, T, M> changeBy(
				SerializableBiFunction<M, T, M> change
			) {
				return ModelLens.of(read, change);
			}
		public SerializableFunction<M, T> read() {
			return read;
		}
		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || obj.getClass() != this.getClass()) return false;
			var that = (WithGetter) obj;
			return Objects.equals(this.read, that.read);
		}
		@Override
		public int hashCode() {
			return Objects.hash(read);
		}
		@Override
		public String toString() {
			return "WithGetter[" +
				"read=" + read + ']';
		}

		}
}
