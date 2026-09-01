package de.flapdoodle.wicket.markup.html.form.select;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.form.select.IOptionRenderer;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOptions;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableFunction;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class OptionGroupSelect<T, G> extends FormComponentPanel<T> {
	private final Select<T> select;

	public OptionGroupSelect(
		String id,
		IModel<T> model,
		IModel<? extends List<G>> groupModel,
		SerializableFunction<? super G, Collection<? extends T>> groupItems,
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

	public static <T, G> Builder<T, G> builder(IModel<T> model, IModel<? extends List<G>> groupModel) {
		return new Builder<>(model, groupModel);
	}

	public static class Builder<T, G> implements Serializable {

		private final IModel<T> model;
		private final IModel<? extends List<G>> groupModel;

		private SerializableFunction<? super G, Collection<? extends T>> groupItems=null;
		private SerializableFunction<? super G, String> groupLabel=null;
		private SerializableFunction<T, String> itemLabel=null;
		private SerializableFunction<T, IModel<T>> value2Model=null;

		public Builder(IModel<T> model, IModel<? extends List<G>> groupModel) {
			this.model = Objects.requireNonNull(model,"model is null");
			this.groupModel = Objects.requireNonNull(groupModel,"groupModel is null");
		}

		public Builder<T, G> groupItems(SerializableFunction<? super G, Collection<? extends T>> groupItems) {
			if (this.groupItems!=null) throw new IllegalStateException("groupItems already set");
			this.groupItems = groupItems;
			return this;
		}

		public Builder<T, G> groupLabel(SerializableFunction<? super G, String> groupLabel) {
			if (this.groupLabel!=null) throw new IllegalStateException("groupLabel already set");
			this.groupLabel = groupLabel;
			return this;
		}

		public Builder<T, G> itemLabel(SerializableFunction<T, String> itemLabel) {
			if (this.itemLabel!=null) throw new IllegalStateException("itemLabel already set");
			this.itemLabel = itemLabel;
			return this;
		}

		public Builder<T, G> value2Model(SerializableFunction<T, IModel<T>> value2Model) {
			if (this.value2Model!=null) throw new IllegalStateException("value2Model already set");
			this.value2Model = value2Model;
			return this;
		}

		public OptionGroupSelect<T, G> build(String id) {
			return new OptionGroupSelect<T, G>(
				Objects.requireNonNull(id,"id is null"),
				model,
				groupModel,
				Objects.requireNonNull(groupItems,"groupItems not set"),
				Objects.requireNonNull(groupLabel, "groupLabel not set"),
				Objects.requireNonNull(itemLabel,"itemLabel not set"),
				Objects.requireNonNull(value2Model,"value2Model not set")
			);
		}
	}
}
