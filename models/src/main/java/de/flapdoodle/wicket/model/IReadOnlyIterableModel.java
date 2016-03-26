/**
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

import org.apache.wicket.model.IModel;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.transformation.IterableModel;
import de.flapdoodle.wicket.model.transformation.ListMappingModel;
import de.flapdoodle.wicket.model.transformation.MapModel;

public interface IReadOnlyIterableModel<T,I extends Iterable<T>> extends IReadOnlyModel<I> {

	public default <D> IReadOnlyListModel<D> mapEach(Function1<D, T> map) {
		return new ListMappingModel<T, I, D>(this,map);
	}
	
	public default <K> MapModel<K, T> asMap(Function1<K, T> keyTransformation) {
		return new MapModel<>(this, keyTransformation);
	}
	
	public static <T,I extends Iterable<T>> IReadOnlyIterableModel<T,I> asIterable(IModel<I> source) {
		return new IterableModel<>(source);
	}
}
