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
package de.flapdoodle.wicket.model.transformation;

import org.apache.wicket.model.IModel;

import de.flapdoodle.wicket.model.IReadOnlyIterableModel;

public class IterableModel<T, I extends Iterable<T>> implements IReadOnlyIterableModel<T, I> {

	private final IModel<I> source;

	public IterableModel(IModel<I> source) {
		this.source = source;
	}
	
	@Override
	public I getObject() {
		return source.getObject();
	}

	@Override
	public void detach() {
		source.detach();
	}

}
