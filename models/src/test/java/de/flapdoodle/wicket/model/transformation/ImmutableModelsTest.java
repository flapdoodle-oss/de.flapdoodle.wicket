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
import de.flapdoodle.wicket.model.IMappableObjectAwareModel;
import de.flapdoodle.wicket.model.Models;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.immutables.value.Value;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ImmutableModelsTest {

	@Test
	void simplePropertyModel() {
		Model<ImmutableRoot> sourceModel = Model.of(sample());

		IMappableObjectAwareModel<String> nameModel = Models.on(sourceModel)
			.copyOnChangeProperty(ImmutableRoot::name, ImmutableRoot::withName)
			.withObjectType(String.class);

		assertThat(sourceModel.getObject().name()).isEqualTo("name");
		nameModel.setObject("test");
		assertThat(sourceModel.getObject().name()).isEqualTo("test");
	}

	@Test
	void nestedPropertModel() {
		Model<ImmutableRoot> sourceModel = Model.of(sample());

		IMappableModel<Integer> numberModel = Models.on(sourceModel)
			.copyOnChangeProperty(ImmutableRoot::sibling, ImmutableRoot::withSibling)
			.copyOnChangeProperty(ImmutableSibling::copyOf, Sibling::number, ImmutableSibling::withNumber);

		IMappableModel<List<Item>> listModel = Models.on(sourceModel)
			.copyOnChangeProperty(ImmutableRoot::sibling, ImmutableRoot::withSibling)
			.copyOnChangeProperty(ImmutableSibling::copyOf, Sibling::items, ImmutableSibling::withItems);

		IModel<Item> secondItem = Models.copyOnChangeItem(listModel, 1);

		assertThat(sourceModel.getObject().sibling().number()).isEqualTo(12);
		assertThat(sourceModel.getObject().sibling().items()).containsExactly(
			Item.builder().pos(1).label("foo").build(),
			Item.builder().pos(2).label("bar").build()
		);
		numberModel.setObject(144);
//		listModel.setObject(List.of(Item.builder().pos(3).label("baz").build()));
		secondItem.setObject(Item.builder().pos(3).label("baz").build());

		assertThat(sourceModel.getObject().sibling().number()).isEqualTo(144);
		assertThat(sourceModel.getObject().sibling().items()).containsExactly(
			Item.builder().pos(1).label("foo").build(),
			Item.builder().pos(3).label("baz").build()
		);
	}

	private static ImmutableRoot sample() {
		return Root.builder()
			.name("name")
			.sibling(Sibling.builder()
				.number(12)
				.addItems(Item.builder()
					.pos(1).label("foo")
					.build())
				.addItems(Item.builder()
					.pos(2).label("bar")
					.build())
				.build())
			.build();
	}

	@Value.Immutable
	public interface Root extends Serializable {
		String name();

		Sibling sibling();

		static ImmutableRoot.Builder builder() {
			return ImmutableRoot.builder();
		}
	}

	@Value.Immutable
	public interface Sibling extends Serializable {
		int number();

		List<Item> items();

		static ImmutableSibling.Builder builder() {
			return ImmutableSibling.builder();
		}
	}

	@Value.Immutable
	public interface Item extends Serializable {
		int pos();
		String label();

		static ImmutableItem.Builder builder() {
			return ImmutableItem.builder();
		}
	}
}
