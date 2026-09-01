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

		form.add(new OptionGroupSelect<Item, Group>(
			"group",
			itemModel,
			Model.ofList(sample()),
			Group::entries,
			Group::name,
			Item::name,
			Model::of
		));
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
