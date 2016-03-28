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

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.lambda.SerializableBiConsumer;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.functions.Function2;
import de.flapdoodle.functions.Function3;
import de.flapdoodle.wicket.model.IMapableModel;
import de.flapdoodle.wicket.model.IMapableObjectAwareModel;
import de.flapdoodle.wicket.model.IReadOnlyModel;
import de.flapdoodle.wicket.model.Models;

/**
 * model sets
 */
public abstract class ModelSet
{
	/**
	 * a model set with one model
	 *
	 * @param <T1> source model type
	 */
	public static final class Set1<T1>
	{
		IModel<T1> _m1;
		
		public Set1(IModel<T1> m1)
		{
			_m1=m1;
		}
		
		/**
		 * create a model with a transforming function
		 * @see Models#on(IModel)
		 * @param <R> result model type
		 * @param <T1> first model type from set 
		 * @param function transforming function
		 * @return model
		 */
		public <R> IReadOnlyModel<R> apply(Function1<R, ? super T1> function)
		{
			return new Transformator.Model1<R, T1>(_m1,function);
		}
		
		/**
		 * create a model with a transforming function with model value access indirection
		 * @see Models#on(IModel)
		 * @see Lazy
		 * @param <R> result model type
		 * @param <T1> first model type from set 
		 * @param function transforming function
		 * @return model
		 */
		public <R> IReadOnlyModel<R> applyLazy(Function1<R, ? super Lazy<? extends T1>> function)
		{
			return new Transformator.LazyModel1<R, T1>(_m1,function);
		}
		
		public <R> IMapableObjectAwareModel<R> mapProperty(Class<R> type, Function1<R, ? super T1> read, SerializableBiConsumer<? super T1, R> write) {
			return new ObjectAwarePropertyAccessModel<>(_m1, type, read, write);
		}
                
		public <R> IMapableModel<R> mapProperty(Function1<R, ? super T1> read, SerializableBiConsumer<? super T1, R> write) {
			return new PropertyAccessModel<>(_m1, read, write);
		}
	}
	
	/**
	 * a model set with one model
	 *
	 * @param <T1> first model type
	 * @param <T2> second model type
	 */
	public static final class Set2<T1,T2>
	{
		IModel<T1> _m1;
		IModel<T2> _m2;
		
		public Set2(IModel<T1> m1,IModel<T2> m2)
		{
			_m1=m1;
			_m2=m2;
		}
		
		/**
		 * create a model with a transforming function
		 * @see Models#on(IModel,IModel)
		 * @param <R> result model type
		 * @param <T1> first model type from set 
		 * @param <T2> first model type from set 
		 * @param function transforming function
		 * @return model
		 */
		public <T> IReadOnlyModel<T> apply(Function2<T, ? super T1, ? super T2> function)
		{
			return new Transformator.Model2<T, T1, T2>(_m1,_m2,function);
		}
		
		/**
		 * create a model with a transforming function with model value access indirection
		 * @see Models#on(IModel,IModel)
		 * @param <R> result model type
		 * @param <T1> first model type from set 
		 * @param <T2> first model type from set 
		 * @param function transforming function
		 * @return model
		 */
		public <T> IReadOnlyModel<T> applyLazy(Function2<T, ? super Lazy<? extends T1>, ? super Lazy<? extends T2>> function)
		{
			return new Transformator.LazyModel2<T, T1, T2>(_m1,_m2,function);
		}
	}

	/**
	 * a model set with one model
	 *
	 * @param <T1> first model type
	 * @param <T2> second model type
	 * @param <T3> third model type
	 */
	public static final class Set3<T1,T2,T3>
	{
		IModel<T1> _m1;
		IModel<T2> _m2;
		IModel<T3> _m3;
		
		public Set3(IModel<T1> m1,IModel<T2> m2,IModel<T3> m3)
		{
			_m1=m1;
			_m2=m2;
			_m3=m3;
		}
		
		/**
		 * create a model with a transforming function
		 * @see Models#on(IModel,IModel,IModel)
		 * @param <R> result model type
		 * @param <T1> first model type from set 
		 * @param <T2> first model type from set 
		 * @param function transforming function
		 * @return model
		 */
		public <T> IReadOnlyModel<T> apply(Function3<T, ? super T1, ? super T2, ? super T3> function)
		{
			return new Transformator.Model3<T, T1, T2, T3>(_m1,_m2,_m3, function);
		}
		
		/**
		 * create a model with a transforming function with model value access indirection
		 * @see Models#on(IModel,IModel,IModel)
		 * @param <R> result model type
		 * @param <T1> first model type from set 
		 * @param <T2> first model type from set 
		 * @param function transforming function
		 * @return model
		 */
		public <T> IReadOnlyModel<T> applyLazy(Function3<T, ? super Lazy<? extends T1>, ? super Lazy<? extends T2>, ? super Lazy<? extends T3>> function)
		{
			return new Transformator.LazyModel3<T, T1, T2, T3>(_m1,_m2,_m3, function);
		}
	}
}
