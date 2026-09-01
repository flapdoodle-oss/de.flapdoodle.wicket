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
package de.flapdoodle.wicket.examples.form;

import de.flapdoodle.wicket.markup.html.form.select.OptionGroupSelect;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import java.io.Serializable;
import java.util.List;

public class FormExamplePage extends WebPage {

	public FormExamplePage() {

		Model<Item> itemModel = Model.of();
		
		Form<Void> form = new Form<>("form") {
			@Override
			protected void onSubmit() {
				System.out.println("Model: "+itemModel.getObject());
			}
		};

		form.add(OptionGroupSelect.builder(itemModel, Model.ofList(sample()))
			.groupItems(Group::entries)
			.groupLabel(Group::name)
			.itemLabel(Item::name)
			.value2Model(Model::of)
			.build("group"));

		form.add(new Button("submit"));
		add(form);
	}

	private List<Group> sample() {
		return List.of(
			new Group("A", List.of(
				new Item("A.1"),
				new Item("A.2"),
				new Item("A.3"),
				new Item("A.4")
			)),
			new Group("B", List.of(
				new Item("B.1")
			)),
			new Group("C", List.of(
				new Item("C.foo"),
				new Item("C.bar")
			))
		);
	}

	record Group(String name, List<Item> entries) implements Serializable {

	}

	record Item(String name) implements Serializable {

	}
}
