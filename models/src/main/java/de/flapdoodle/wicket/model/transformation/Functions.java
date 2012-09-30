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
package de.flapdoodle.wicket.model.transformation;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.functions.Function2;
import de.flapdoodle.functions.Function3;
import de.flapdoodle.wicket.model.Models;

/**
 * function reference for model transformation
 */
public abstract class Functions implements Serializable {
	
	/**
	 * a function reference for model transformation
	 * @see Models#on(IModel)
	 *
	 * @param <R> result model type
	 * @param <T> source model type
	 */
	public static final class Reference1<R,T>
	{
		private final Function1<R, T> _function;

		public Reference1(Function1<R, T> function) {
			_function = function;
		}

		/**
		 * create a model from a function and a model
		 * @see Models#on(IModel)
		 * 
		 * @param <R> result model type
		 * @param <T> source model type
		 * @param source source model
		 * @return model
		 */
		public IModel<R> to(IModel<T> source) {
			return new Transformator.Model1<R, T>(source, _function);
		}
	}
	
	/**
	 * a function reference for model transformation
	 * @see Models#on(IModel, IModel)
	 *
	 * @param <R> result model type
	 * @param <T1> first model type
	 * @param <T2> second model type
	 */
	public static final class Reference2<R,T1,T2>
	{
		private final Function2<R, T1, T2> _function;

		public Reference2(Function2<R, T1, T2> function) {
			_function = function;
		}
		
		/**
		 * create a model from a function and a model
		 * @see Models#on(IModel)
		 * 
		 * @param <R> result model type
		 * @param <T1> first model type
		 * @param <T2> second model type
		 * @param first first model
		 * @param second second model
		 * @return model
		 */
		public IModel<R> to(IModel<T1> first, IModel<T2> second) {
			return new Transformator.Model2<R, T1, T2>(first,second, _function);
		}
	}
	
	/**
	 * a function reference for model transformation
	 * @see Models#on(IModel, IModel, IModel)
	 *
	 * @param <R> result model type
	 * @param <T1> first model type
	 * @param <T2> second model type
	 * @param <T3> third model type
	 */
	public static final class Reference3<R,T1,T2,T3>
	{
		private final Function3<R, T1, T2, T3> _function;

		public Reference3(Function3<R, T1, T2, T3> function) {
			_function = function;
		}
		
		/**
		 * create a model from a function and a model
		 * @see Models#on(IModel)
		 * 
		 * @param <R> result model type
		 * @param <T1> first model type
		 * @param <T2> second model type
		 * @param <T3> third model type
		 * @param first first model
		 * @param second second model
		 * @param third second model
		 * @return model
		 */
		public IModel<R> to(IModel<T1> first, IModel<T2> second, IModel<T3> third) {
			return new Transformator.Model3<R, T1, T2, T3>(first,second,third, _function);
		}
	}
}
