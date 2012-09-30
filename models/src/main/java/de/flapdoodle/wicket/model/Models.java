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

import org.apache.wicket.model.IModel;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.functions.Function2;
import de.flapdoodle.functions.Function3;
import de.flapdoodle.wicket.model.transformation.Functions;
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
	 * IModel<X> model=Models.apply(function).to(first,second);
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

}
