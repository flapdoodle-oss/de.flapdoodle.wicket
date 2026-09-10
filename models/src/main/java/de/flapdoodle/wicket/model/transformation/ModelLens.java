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

public interface ModelLens<M, T> extends Serializable {

	T read(M model);

	M change(M model, T value);

	default M map(M model, SerializableFunction<T, T> map) {
		return change(model, map.apply(read(model)));
	}

	default <U> ModelLens<M, U> and(ModelLens<T, U> next) {
		ModelLens<M, T> that=this;

		return new ModelLens<>() {
			@Override
			public U read(M model) {
				return next.read(that.read(model));
			}

			@Override
			public M change(M model, U value) {
				return that.change(model, next.change(that.read(model), value));
			}
		};
	}

	default SerializableFunction<M, M> change(T value) {
		return model -> change(model, value);
	}

	default SerializableFunction<M, M> map(SerializableFunction<T, T> map) {
		return model -> map(model, map);
	}


	static <T, M, IM extends M> ModelLens<M, T> of(
		SerializableFunction<M, IM> asImmutable,
		SerializableFunction<M, T> readProperty,
		SerializableBiFunction<IM, T, M> changeProperty
	) {
		return CopyOnChangeModelLens.of(asImmutable, readProperty, changeProperty);
	}

	static <T, M> ModelLens<M, T> of(
		SerializableFunction<M, T> readProperty,
		SerializableBiFunction<M, T, M> changeProperty
	) {
		return CopyOnChangeModelLens.of(readProperty, changeProperty);
	}

	static <T, M> CopyOnChangeModelLens.WithGetter<T, M> ofProperty(SerializableFunction<M, T> read) {
		return CopyOnChangeModelLens.ofProperty(read);
	}
}
