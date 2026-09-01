package de.flapdoodle.wicket.markup.html.form.select;

import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOption;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.apache.wicket.util.visit.IVisit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.List;

class OptionGroupSelectTest {

	private WicketTester tester;

	@BeforeEach
	public void setUp() {
		tester = new WicketTester();
	}

	@Test
	public void testRenderMyPage() {
		tester.startPage(FormPage.class);

		tester.assertComponent("form:group", OptionGroupSelect.class);
		tester.assertComponent("form:submit", Button.class);

		Select<FormPage.Item> select =
			(Select<FormPage.Item>) tester.getComponentFromLastRenderedPage("form:group:select");

		SelectOption<FormPage.Item> option = select.visitChildren(SelectOption.class,
			(SelectOption<FormPage.Item> o, IVisit<SelectOption<FormPage.Item>> visit) -> {
				if (new FormPage.Item("A.2").equals(o.getDefaultModelObject())) {
					visit.stop(o);
				}
			});


		FormTester formTester = tester.newFormTester("form");
		formTester.setValue(select, option.getValue());
		formTester.submit();

		tester.assertModelValue("form:group", new FormPage.Item("A.2"));
	}

	public static class FormPage extends WebPage {

		public FormPage() {
			Model<Item> itemModel = Model.of();

			Form<Void> form = new Form<>("form") {
				@Override
				protected void onSubmit() {
					System.out.println("Model: " + itemModel.getObject());
				}
			};

			form.add(OptionGroupSelect.builder(itemModel, Model.ofList(sample()))
				.groupItems(Group::entries)
				.groupLabel(Group::name)
				.itemLabel(Item::name)
				.value2Model(Model::of)
				.build("group"));

//			form.add(new OptionGroupSelect<>(
//				"group",
//				itemModel,
//				Model.ofList(sample()),
//				Group::entries,
//				Group::name,
//				Item::name,
//				Model::of
//			));
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
}