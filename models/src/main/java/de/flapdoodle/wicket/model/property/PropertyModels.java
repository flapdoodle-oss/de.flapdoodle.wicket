/**
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
package de.flapdoodle.wicket.model.property;

import org.apache.wicket.model.IModel;

@Deprecated
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
