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

import de.flapdoodle.wicket.model.IMappableModel;
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.List;

public class CopyOnChangeListItemModel<T> implements IMappableModel<T> {

	private final IModel<List<T>> source;
	private final int index;
	public CopyOnChangeListItemModel(IModel<List<T>> source, int index) {
		this.source = source;
		this.index = index;
	}

	@Override
	public T getObject() {
		List<? extends T> list = source.getObject();
		return list.size() > index ? list.get(index) : null;
	}

	@Override
	public void setObject(T object) {
		List<? extends T> list = source.getObject();
		if (list.size() > index) {
			List<T> changed = new ArrayList<>();
			changed.addAll(list.subList(0, index));
			changed.add(object);
			changed.addAll(list.subList(index+1, list.size()));
			source.setObject(changed);
		}
	}

	@Override
	public void detach() {
		source.detach();
	}
}
