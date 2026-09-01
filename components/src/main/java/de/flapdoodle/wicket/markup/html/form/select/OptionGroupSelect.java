package de.flapdoodle.wicket.markup.html.form.select;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.form.select.IOptionRenderer;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOptions;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableFunction;

import java.util.List;

public class OptionGroupSelect<T, G> extends FormComponentPanel<T> {
	private final Select<T> select;

	public OptionGroupSelect(
		String id,
		IModel<T> model,
		IModel<? extends List<G>> groupModel,
		SerializableFunction<? super G, List<? extends T>> groupItems,
		SerializableFunction<? super G, String> groupLabel,
		SerializableFunction<T, String> itemLabel,
		SerializableFunction<T, IModel<T>> value2Model
	) {
		super(id, model);

		Select<T> select = new Select<>("select", model);
		select.add(new ListView<G>("groups", groupModel) {
			@Override
			protected void populateItem(ListItem<G> item) {
				WebMarkupContainer group = new WebMarkupContainer("group");
				group.add(AttributeModifier.replace("label", item.getModel().map(groupLabel)));

				SelectOptions<T> options = new SelectOptions<>("items",
					item.getModel().map(groupItems), new IOptionRenderer<T>() {
					@Override
					public String getDisplayValue(T object) {
						return itemLabel.apply(object);
					}
					@Override
					public IModel<T> getModel(T value) {
						return value2Model.apply(value);
					}
				});
				options.setRecreateChoices(true);
				group.add(options);
				item.add(group);
			}
		});
		add(select);
		this.select = select;
	}

	@Override
	public void convertInput() {
		setConvertedInput(select.getConvertedInput());
	}
}
