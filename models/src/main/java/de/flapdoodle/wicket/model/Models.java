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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.functions.Function2;
import de.flapdoodle.functions.Function3;
import de.flapdoodle.wicket.model.transformation.Functions;
import de.flapdoodle.wicket.model.transformation.ModelSet;

public abstract class Models
{
	private Models() {
		// no instance
	}
	
	@SuppressWarnings("unchecked")
	private static final IModel<?> EMPTY_MODEL=new EmptyModel();

	public static <M1> ModelSet.Set1<M1> on(IModel<M1> model)
	{
		return new ModelSet.Set1<M1>(model);
	}

	public static <M1, M2> ModelSet.Set2<M1, M2> on(IModel<M1> model, IModel<M2> model2)
	{
		return new ModelSet.Set2<M1, M2>(model, model2);
	}

	public static <M1, M2, M3> ModelSet.Set3<M1, M2, M3> on(IModel<M1> model, IModel<M2> model2, IModel<M3> model3)
	{
		return new ModelSet.Set3<M1, M2, M3>(model, model2, model3);
	}

	public static <R,T> Functions.Reference1<R,T> apply(Function1<R, T> function) {
		return new Functions.Reference1<R,T>(function);
	}
	
	public static <R,T1,T2> Functions.Reference2<R,T1,T2> apply(Function2<R, T1, T2> function) {
		return new Functions.Reference2<R,T1,T2>(function);
	}
	
	public static <R,T1,T2,T3> Functions.Reference3<R,T1,T2,T3> apply(Function3<R, T1, T2, T3> function) {
		return new Functions.Reference3<R,T1,T2,T3>(function);
	}
	
	public static <T> IModel<List<T>> copyOfList(Collection<T> list) {
		return copyOfList(new ArrayList<T>(list));
	}

	public static <T> IModel<List<T>> copyOfList(List<T> list)
	{
		return new ListModel<T>(new ArrayList<T>(list));
	}

	public static <T extends Enum<T>> IModel<List<T>> listOf(T... list)
	{
		return new ListModel<T>(Arrays.asList(list));
	}

	public static IModel<List<String>> listOfStrings(String... list)
	{
		return new ListModel<String>(Arrays.asList(list));
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
}
