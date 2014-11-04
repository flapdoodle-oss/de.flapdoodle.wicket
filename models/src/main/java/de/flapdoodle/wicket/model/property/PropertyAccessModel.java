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
import org.apache.wicket.model.IObjectClassAwareModel;


public class PropertyAccessModel<T, S> implements IObjectClassAwareModel<T> {

	private final IModel<S> _source;
	private final IPropertyAccess<T, S> _access;

	public PropertyAccessModel(IModel<S> source, IPropertyAccess<T, S> access) {
		_source = source;
		_access = access;
	}
	
	@Override
	public T getObject() {
		return _access.read(_source.getObject());
	}

	@Override
	public void setObject(T value) {
		_access.write(_source.getObject(), value);
	}

	@Override
	public void detach() {
		_source.detach();
	}

	@Override
	public Class<T> getObjectClass() {
		return _access.type();
	}

}
