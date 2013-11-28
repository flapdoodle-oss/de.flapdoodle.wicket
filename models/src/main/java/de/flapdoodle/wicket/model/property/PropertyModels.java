package de.flapdoodle.wicket.model.property;

import org.apache.wicket.model.IModel;

public abstract class PropertyModels {

	private PropertyModels() {
		// no instance
	}

	public static <T, S> Property<T, S> property(IPropertyAccess<T, S> propertyAccess) {
		return new Property<T, S>(propertyAccess);
	}

	public static <T, S> OnModel<S> on(IModel<S> source) {
		return new OnModel<S>(source);
	}

	public static class OnModel<S> {

		private final IModel<S> _source;

		public OnModel(IModel<S> source) {
			_source = source;
		}

		public <T> IModel<T> property(IPropertyAccess<T, S> propertyAccess) {
			return new PropertyAccessModel<T, S>(_source, propertyAccess);
		}

	}

	public static class Property<T, S> {

		private final IPropertyAccess<T, S> _propertyAccess;

		public Property(IPropertyAccess<T, S> propertyAccess) {
			_propertyAccess = propertyAccess;
		}

		public IModel<T> of(IModel<S> source) {
			return new PropertyAccessModel<T, S>(source, _propertyAccess);
		}
	}
}
