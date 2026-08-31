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
package de.flapdoodle.wicket.model.functions;

import org.danekja.java.util.function.serializable.SerializableFunction;

public interface SymetricalFunction<S, D> extends SerializableFunction<S, D> {
	SymetricalFunction<D, S> reverse();

	static <S, D> SymetricalFunction<S, D> with(SerializableFunction<S, D> to, SerializableFunction<D, S> from) {
		return new SymetricalFunction<S, D>() {

			@Override
			public D apply(S value) {
				return to.apply(value);
			}

			@Override
			public SymetricalFunction<D, S> reverse() {
				return with(from, to);
			}
		};
	}
}
