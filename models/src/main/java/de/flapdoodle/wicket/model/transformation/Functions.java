/*
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

import de.flapdoodle.wicket.model.Models;
import de.flapdoodle.wicket.model.functions.SerializableTriFunction;
import org.apache.wicket.model.IModel;
import org.danekja.java.util.function.serializable.SerializableBiFunction;
import org.danekja.java.util.function.serializable.SerializableFunction;

import java.io.Serializable;

/**
 * function reference for model transformation
 */
public abstract class Functions implements Serializable {

	private Functions() {
		// no instance
	}

	/**
	 * a function reference for model transformation
	 *
	 * @param <R> result model type
	 * @param <T> source model type
	 * @see Models#on(IModel)
	 */
	public static final class Reference1<R, T> {
		private final SerializableFunction<T, R> _function;

		public Reference1(SerializableFunction<T, R> function) {
			_function = function;
		}

		/**
		 * create a model from a function and a model
		 *
		 * @param <R>    result model type
		 * @param <T>    source model type
		 * @param source source model
		 * @return model
		 * @see Models#on(IModel)
		 */
		public IModel<R> to(IModel<? extends T> source) {
			return new Transformator.Model1<R, T>(source, _function);
		}
	}

	/**
	 * a function reference for model transformation
	 *
	 * @param <R>  result model type
	 * @param <T1> first model type
	 * @param <T2> second model type
	 * @see Models#on(IModel, IModel)
	 */
	public static final class Reference2<R, T1, T2> {
		private final SerializableBiFunction<T1, T2, R> _function;

		public Reference2(SerializableBiFunction<T1, T2, R> function) {
			_function = function;
		}

		/**
		 * create a model from a function and a model
		 *
		 * @param <R>    result model type
		 * @param <T1>   first model type
		 * @param <T2>   second model type
		 * @param first  first model
		 * @param second second model
		 * @return model
		 * @see Models#on(IModel)
		 */
		public IModel<R> to(IModel<? extends T1> first, IModel<? extends T2> second) {
			return new Transformator.Model2<R, T1, T2>(first, second, _function);
		}
	}

	/**
	 * a function reference for model transformation
	 *
	 * @param <R>  result model type
	 * @param <T1> first model type
	 * @param <T2> second model type
	 * @param <T3> third model type
	 * @see Models#on(IModel, IModel, IModel)
	 */
	public static final class Reference3<R, T1, T2, T3> {
		private final SerializableTriFunction<T1, T2, T3, R> _function;

		public Reference3(SerializableTriFunction<T1, T2, T3, R> function) {
			_function = function;
		}

		/**
		 * create a model from a function and a model
		 *
		 * @param <R>    result model type
		 * @param <T1>   first model type
		 * @param <T2>   second model type
		 * @param <T3>   third model type
		 * @param first  first model
		 * @param second second model
		 * @param third  second model
		 * @return model
		 * @see Models#on(IModel)
		 */
		public IModel<R> to(IModel<? extends T1> first, IModel<? extends T2> second, IModel<? extends T3> third) {
			return new Transformator.Model3<R, T1, T2, T3>(first, second, third, _function);
		}
	}

	/**
	 * a function reference for model transformation
	 *
	 * @param <R> result model type
	 * @param <T> source model type
	 * @see Models#on(IModel)
	 */
	public static final class LazyReference1<R, T> {
		private final SerializableFunction<? super Lazy<? extends T>, R> _function;

		public LazyReference1(SerializableFunction<? super Lazy<? extends T>, R> function) {
			_function = function;
		}

		/**
		 * create a model from a function and a model
		 *
		 * @param <R>    result model type
		 * @param <T>    source model type
		 * @param source source model
		 * @return model
		 * @see Models#on(IModel)
		 */
		public IModel<? extends R> to(IModel<? extends T> source) {
			return new Transformator.LazyModel1<R, T>(source, _function);
		}
	}

	/**
	 * a function reference for model transformation
	 *
	 * @param <R>  result model type
	 * @param <T1> first model type
	 * @param <T2> second model type
	 * @see Models#on(IModel, IModel)
	 */
	public static final class LazyReference2<R, T1, T2> {
		private final SerializableBiFunction<? super Lazy<? extends T1>, ? super Lazy<? extends T2>, R> _function;

		public LazyReference2(SerializableBiFunction<? super Lazy<? extends T1>, ? super Lazy<? extends T2>, R> function) {
			_function = function;
		}

		/**
		 * create a model from a function and a model
		 *
		 * @param <R>    result model type
		 * @param <T1>   first model type
		 * @param <T2>   second model type
		 * @param first  first model
		 * @param second second model
		 * @return model
		 * @see Models#on(IModel)
		 */
		public IModel<? extends R> to(IModel<? extends T1> first, IModel<? extends T2> second) {
			return new Transformator.LazyModel2<R, T1, T2>(first, second, _function);
		}
	}

	/**
	 * a function reference for model transformation
	 *
	 * @param <R>  result model type
	 * @param <T1> first model type
	 * @param <T2> second model type
	 * @param <T3> third model type
	 * @see Models#on(IModel, IModel, IModel)
	 */
	public static final class LazyReference3<R, T1, T2, T3> {
		private final SerializableTriFunction<? super Lazy<? extends T1>, ? super Lazy<? extends T2>, ? super Lazy<? extends T3>, R> _function;

		public LazyReference3(SerializableTriFunction<? super Lazy<? extends T1>, ? super Lazy<? extends T2>, ? super Lazy<? extends T3>, R> function) {
			_function = function;
		}

		/**
		 * create a model from a function and a model
		 *
		 * @param <R>    result model type
		 * @param <T1>   first model type
		 * @param <T2>   second model type
		 * @param <T3>   third model type
		 * @param first  first model
		 * @param second second model
		 * @param third  second model
		 * @return model
		 * @see Models#on(IModel)
		 */
		public IModel<? extends R> to(IModel<? extends T1> first, IModel<? extends T2> second, IModel<? extends T3> third) {
			return new Transformator.LazyModel3<R, T1, T2, T3>(first, second, third, _function);
		}
	}
}
