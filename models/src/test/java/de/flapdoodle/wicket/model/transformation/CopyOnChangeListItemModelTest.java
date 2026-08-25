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
import org.apache.wicket.model.Model;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CopyOnChangeListItemModelTest {

	@Test
	void changeListEntry() {
		List<String> firstObject = List.of("foo", "bar", "baz");
		IModel<List<String>> listModel = Model.ofList(firstObject);

		assertThatThrownBy(() -> listModel.getObject().set(2,"peng"))
			.isInstanceOf(UnsupportedOperationException.class);

		IModel<String> listItemModel = new CopyOnChangeListItemModel<>(listModel, 1);

		listItemModel.setObject("peng");

		assertThat(listModel.getObject())
			.hasSize(3)
			.containsExactly("foo", "peng", "baz");

		assertThat(listModel.getObject()).isNotSameAs(firstObject);
	}
}