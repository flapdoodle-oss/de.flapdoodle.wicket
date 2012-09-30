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
package de.flapdoodle.wicket.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.model.util.ListModel;

import de.flapdoodle.wicket.model.transformation.ModelSet;

public abstract class Models
{
	@SuppressWarnings("unchecked")
	private static final IModel<?> EMPTY_MODEL=new EmptyModel();

	public static <T, M1> ModelSet.Set1<M1> on(IModel<M1> model)
	{
		return new ModelSet.Set1<M1>(model);
	}

	public static <T, M1, M2> ModelSet.Set2<M1, M2> on(IModel<M1> model, IModel<M2> model2)
	{
		return new ModelSet.Set2<M1, M2>(model, model2);
	}

	public static <T, M1, M2, M3> ModelSet.Set3<M1, M2, M3> on(IModel<M1> model, IModel<M2> model2, IModel<M3> model3)
	{
		return new ModelSet.Set3<M1, M2, M3>(model, model2, model3);
	}

	public static <T> IModel<List<T>> ofList(Collection<T> list) {
		return ofList(new ArrayList<T>(list));
	}

	public static <T> IModel<List<T>> ofList(List<T> list)
	{
		return new ListModel<T>(new ArrayList<T>(list));
	}

	public static <T extends Enum<T>> IModel<List<T>> listOf(T... list)
	{
		return ofList(Arrays.asList(list));
	}

	public static IModel<List<String>> listOfStrings(String... list)
	{
		return ofList(Arrays.asList(list));
	}

	public static <T extends Enum<T>> IModel<List<T>> listAllOf(Class<T> enumType)
	{
		return listOf(enumType.getEnumConstants());
	}

	public static <K,V> IModel<List<K>> listOfKeys(final IModel<? extends Map<? extends K, V>> mapModel) {
		return new AbstractReadOnlyModel<List<K>>()
		{
			@Override
			public List<K> getObject()
			{
				return new ArrayList<K>(mapModel.getObject().keySet());
			}
		};
	}

	public static <T> IModel<T> newCompoundProperty(T bean) {
		return new CompoundPropertyModel<T>(bean);
	}

	public static <T> IModel<T> newCompoundProperty(IModel<T> beanModel) {
		return new CompoundPropertyModel<T>(beanModel);
	}

	@SuppressWarnings("unchecked")
	public static <T> IModel<T> empty()
	{
		return (IModel<T>) EMPTY_MODEL;
	}

	static class EmptyModel<T> extends AbstractReadOnlyModel<T>
	{
		@Override
		public T getObject()
		{
			return null;
		}
	}

	private static final class NotModel implements IModel<Boolean> {

		private final IModel<Boolean> _model;

		NotModel(IModel<Boolean> model)
		{
			_model=model;
		}

		public Boolean getObject()
		{
			return !_model.getObject();
		}

		public void setObject(Boolean object)
		{
			_model.setObject(! object);
		}

		public void detach()
		{
			_model.detach();
		}

	}
}
