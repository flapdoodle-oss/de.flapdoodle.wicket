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
import java.util.Collections;
import java.util.List;

import org.apache.wicket.model.IModel;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.functions.Function2;
import de.flapdoodle.functions.Function3;
import de.flapdoodle.wicket.model.transformation.Functions;
import de.flapdoodle.wicket.model.transformation.Lazy;
import de.flapdoodle.wicket.model.transformation.ModelSet;

/**
 * wicket model support functions 
 */
public abstract class Models
{

	private Models() {
		// no instance
	}

	/**
	 * Creates a model based on a set of models and a function.
	 * <ul>
	 * 	<li>step 1: create a model set</li>
	 * 	<li>step 2: provide a function</li>
	 * </ul>
	 * IModel<X> model=Models.on(source).apply(function);
	 * 
	 * @param <T> model type
	 * @param source source model
	 * @return model set with one model
	 */
	public static <T> ModelSet.Set1<T> on(IModel<T> source)
	{
		return new ModelSet.Set1<T>(source);
	}

	/**
	 * Creates a model based on a set of models and a function.
	 * <ul>
	 * 	<li>step 1: create a model set</li>
	 * 	<li>step 2: provide a function</li>
	 * </ul>
	 * IModel<X> model=Models.on(first,second).apply(function);
	 * 
	 * @param <T1> first model type
	 * @param <T2> second model type
	 * @param first first source model
	 * @param second second source model
	 * @return model set with two models
	 */
	public static <T1, T2> ModelSet.Set2<T1, T2> on(IModel<T1> first, IModel<T2> second)
	{
		return new ModelSet.Set2<T1, T2>(first, second);
	}

	/**
	 * Creates a model based on a set of models and a function.
	 * <ul>
	 * 	<li>step 1: create a model set</li>
	 * 	<li>step 2: provide a function</li>
	 * </ul>
	 * IModel<X> model=Models.on(first,second,third).apply(function);
	 * 
	 * @param <T1> first model type
	 * @param <T2> second model type
	 * @param <T3> third model type
	 * @param first first source model
	 * @param second second source model
	 * @param third second source model
	 * @return model set with three models
	 */
	public static <T1, T2, T3> ModelSet.Set3<T1, T2, T3> on(IModel<T1> first, IModel<T2> second, IModel<T3> third)
	{
		return new ModelSet.Set3<T1, T2, T3>(first, second, third);
	}

	/**
	 * Creates a model based on a function and a set of models.
	 * <ul>
	 * 	<li>step 1: provide a function</li>
	 * 	<li>step 2: chose matching models</li>
	 * </ul>
	 * IModel<X> model=Models.apply(function).to(source);
	 *
	 * @param <R> resulting model type
	 * @param <T> source model type
	 * @param function transforming function
	 * @return a function reference
	 */
	public static <R,T> Functions.Reference1<R,T> apply(Function1<R, T> function) {
		return new Functions.Reference1<R,T>(function);
	}
	
	/**
	 * Creates a model based on a function and a set of models.
	 * <ul>
	 * 	<li>step 1: provide a function</li>
	 * 	<li>step 2: chose matching models</li>
	 * </ul>
	 * IModel<X> model=Models.apply(function).to(first,second);
	 * 
	 * @param <R> resulting model type
	 * @param <T1> first source model type
	 * @param <T2> second source model type
	 * @param function transforming function
	 * @return a function reference
	 */
	public static <R,T1,T2> Functions.Reference2<R,T1,T2> apply(Function2<R, T1, T2> function) {
		return new Functions.Reference2<R,T1,T2>(function);
	}
	
	/**
	 * Creates a model based on a function and a set of models.
	 * <ul>
	 * 	<li>step 1: provide a function</li>
	 * 	<li>step 2: chose matching models</li>
	 * </ul>
	 * IModel<X> model=Models.apply(function).to(first,second,third);
	 * 
	 * @param <R> resulting model type
	 * @param <T1> first source model type
	 * @param <T2> second source model type
	 * @param <T3> third source model type
	 * @param function transforming function
	 * @return a function reference
	 */
	public static <R,T1,T2,T3> Functions.Reference3<R,T1,T2,T3> apply(Function3<R, T1, T2, T3> function) {
		return new Functions.Reference3<R,T1,T2,T3>(function);
	}

	/**
	 * Creates a model based on a function and a set of models.
	 * <ul>
	 * 	<li>step 1: provide a function</li>
	 * 	<li>step 2: chose matching models</li>
	 * </ul>
	 * IModel<X> model=Models.applyLazy(function).to(source);
	 *
	 * @param <R> resulting model type
	 * @param <T> source model type
	 * @param function transforming function
	 * @return a function reference
	 */
	public static <R,T> Functions.LazyReference1<R,T> applyLazy(Function1<R, ? super Lazy<? extends T>> function) {
		return new Functions.LazyReference1<R,T>(function);
	}
	
	/**
	 * Creates a model based on a function and a set of models.
	 * <ul>
	 * 	<li>step 1: provide a function</li>
	 * 	<li>step 2: chose matching models</li>
	 * </ul>
	 * IModel<X> model=Models.applyLazy(function).to(first,second);
	 * 
	 * @param <R> resulting model type
	 * @param <T1> first source model type
	 * @param <T2> second source model type
	 * @param function transforming function
	 * @return a function reference
	 */
	public static <R,T1,T2> Functions.LazyReference2<R,T1,T2> applyLazy(Function2<R, ? super Lazy<? extends T1>, ? super Lazy<? extends T2>> function) {
		return new Functions.LazyReference2<R,T1,T2>(function);
	}
	
	/**
	 * Creates a model based on a function and a set of models.
	 * <ul>
	 * 	<li>step 1: provide a function</li>
	 * 	<li>step 2: chose matching models</li>
	 * </ul>
	 * IModel<X> model=Models.applyLazy(function).to(first,second,third);
	 * 
	 * @param <R> resulting model type
	 * @param <T1> first source model type
	 * @param <T2> second source model type
	 * @param <T3> third source model type
	 * @param function transforming function
	 * @return a function reference
	 */
	public static <R,T1,T2,T3> Functions.LazyReference3<R,T1,T2,T3> applyLazy(Function3<R, ? super Lazy<? extends T1>, ? super Lazy<? extends T2>, ? super Lazy<? extends T3>> function) {
		return new Functions.LazyReference3<R,T1,T2,T3>(function);
	}

	/**
	 * creates a read only model from a list and make the model list unmodifiable on read access
	 * @param <T> list type
	 * @param source source model
	 * @return resulting model
	 */
	public static <T> IModel<List<T>> unmodifiable(IModel<? extends List<? extends T>> source) {
		return Models.on(source).apply(new UnmodifiableIfNotNull<T>());
	}
	
	/**
	 * creates a read only model from a list, the list is unmodifiable 
	 * @param <T> list type
	 * @param list
	 * @return resulting model
	 */
	public static <T> IModel<List<T>> unmodifiable(final List<? extends T> list) {
		return new AbstractReadOnlyDetachedModel<List<T>>() {
			@Override
			protected List<T> load() {
				return Collections.unmodifiableList(list);
			}
		};
	}
	
	/**
	 * creates a model which returns an empty list of source list is null
	 * @param source source
	 * @return resulting model
	 */
	public static <T> IModel<List<T>> emptyIfNull(IModel<? extends List<T>> source) {
		return Models.on(source).apply(new EmptyListIfNull<T>());
	}
	
	/**
	 * creates a read only version of a model, setObject will throw an exception
	 * @param source source
	 * @return model of same type
	 */
	public static <T> IModel<T> readOnly(IModel<? extends T> source) {
		return Models.on(source).apply(new Noop<T>());
	}

	private static final class UnmodifiableIfNotNull<T> implements Function1<List<T>, List<? extends T>> {

		@Override
		public List<T> apply(List<? extends T> value) {
			return value!=null ? Collections.unmodifiableList(value) : null;
		}
	}

	private static final class EmptyListIfNull<T> implements Function1<List<T>, List<T>> {

		@Override
		public List<T> apply(List<T> value) {
			return value!=null ? value : new ArrayList<T>();
		}
	}

	private static final class Noop<T> implements Function1<T, T> {

		@Override
		public T apply(T value) {
			return value;
		}
	}
}
