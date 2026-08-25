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

import de.flapdoodle.wicket.model.transformation.CopyOnChangeListItemModel;
import de.flapdoodle.wicket.model.transformation.ListModelDelegate;
import org.apache.wicket.model.IModel;

import java.util.List;

public interface IListModel<T> extends IMappableModel<List<T>> {

	default IMappableModel<T> itemAt(int index) {
		return new CopyOnChangeListItemModel<T>(this, index);
	}

	static <T> IListModel<T> asListModel(IModel<List<T>> model) {
		return new ListModelDelegate<>(model);
	}
}
