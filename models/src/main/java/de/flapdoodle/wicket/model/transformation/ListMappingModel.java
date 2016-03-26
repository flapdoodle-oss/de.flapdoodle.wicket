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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.AbstractReadOnlyDetachedModel;
import de.flapdoodle.wicket.model.IReadOnlyListModel;

public class ListMappingModel<T,I extends Iterable<T>,D> extends AbstractReadOnlyDetachedModel<List<D>> implements IReadOnlyListModel<D> {

	private final IModel<I> source;
	private final Function1<D, T> map;

	public ListMappingModel(IModel<I> source, Function1<D, T> map) {
		this.source = source;
		this.map = map;
	}

	@Override
	protected List<D> load() {
		ArrayList<D> ret=new ArrayList<>();
		for (T value : source.getObject()) {
			ret.add(map.apply(value));
		}
		return ret;
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		source.detach();
	}
}