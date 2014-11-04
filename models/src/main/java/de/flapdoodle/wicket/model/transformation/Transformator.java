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
import org.apache.wicket.model.LoadableDetachableModel;

import de.flapdoodle.functions.Function;
import de.flapdoodle.functions.Function1;
import de.flapdoodle.functions.Function2;
import de.flapdoodle.functions.Function3;

/**
 * model implementation using source models and a tranformation function 
 * @param <T>
 */
abstract class Transformator<T> extends LoadableDetachableModel<T>
{
	private final IModel<?>[] _subModels;
	private final Function _function;
	
	protected Transformator(Function function, IModel<?> ... subModels)
  {
		_subModels=subModels;
		_function=function;
  }
	
	@Override
	protected void onDetach()
	{
		for (IModel<?> m : _subModels)
		{
			m.detach();
		}
	}
	
	@Override
	public void setObject(T object) {
		throw new UnsupportedOperationException("Model " + getClass() + " with " + _function +
				" does not support setObject(Object)");
	};
	
	final static class Model1<T,M1> extends Transformator<T>
	{
		IModel<? extends M1> _m1;
		Function1<T, ? super M1> _function;
		
		public Model1(IModel<? extends M1> m1, Function1<T, ? super M1> function)
    {
	    super(function, m1);
	    
	    _m1=m1;
	    _function=function;
    }
		
		@Override
		protected T load()
		{
		  return _function.apply(_m1.getObject());
		}
	}
	
	final static class Model2<T,M1,M2> extends Transformator<T>
	{
		IModel<? extends M1> _m1;
		IModel<? extends M2> _m2;
		Function2<T, ? super M1, ? super M2> _function;
		
		public Model2(IModel<? extends M1> m1, IModel<? extends M2> m2, Function2<T, ? super M1, ? super M2> function)
    {
	    super(function, m1,m2);
	    
	    _m1=m1;
	    _m2=m2;
	    _function=function;
    }
		
		@Override
		protected T load()
		{
		  return _function.apply(_m1.getObject(),_m2.getObject());
		}
	}

	final static class Model3<T,M1,M2,M3> extends Transformator<T>
	{
		IModel<? extends M1> _m1;
		IModel<? extends M2> _m2;
		IModel<? extends M3> _m3;
		Function3<T, ? super M1, ? super M2, ? super M3> _function;
		
		public Model3(IModel<? extends M1> m1, IModel<? extends M2> m2, IModel<? extends M3> m3, Function3<T, ? super M1, ? super M2, ? super M3> function)
    {
	    super(function, m1,m2,m3);
	    
	    _m1=m1;
	    _m2=m2;
	    _m3=m3;
	    _function=function;
    }
		
		@Override
		protected T load()
		{
		  return _function.apply(_m1.getObject(),_m2.getObject(),_m3.getObject());
		}
	}


	final static class LazyModel1<T,M1> extends Transformator<T>
	{
		IModel<? extends M1> _m1;
		Function1<T, ? super Lazy<? extends M1>> _function;
		
		public LazyModel1(IModel<? extends M1> m1, Function1<T, ? super Lazy<? extends M1>> function)
    {
	    super(function, m1);
	    
	    _m1=m1;
	    _function=function;
    }
		
		@Override
		protected T load()
		{
		  return _function.apply(lazy(_m1));
		}
	}
	
	final static class LazyModel2<T,M1,M2> extends Transformator<T>
	{
		IModel<? extends M1> _m1;
		IModel<? extends M2> _m2;
		Function2<T, ? super Lazy<? extends M1>, ? super Lazy<? extends M2>> _function;
		
		public LazyModel2(IModel<? extends M1> m1, IModel<? extends M2> m2, Function2<T, ? super Lazy<? extends M1>, ? super Lazy<? extends M2>> function)
    {
	    super(function, m1,m2);
	    
	    _m1=m1;
	    _m2=m2;
	    _function=function;
    }
		
		@Override
		protected T load()
		{
		  return _function.apply(lazy(_m1),lazy(_m2));
		}
	}

	final static class LazyModel3<T,M1,M2,M3> extends Transformator<T>
	{
		IModel<? extends M1> _m1;
		IModel<? extends M2> _m2;
		IModel<? extends M3> _m3;
		Function3<T, ? super Lazy<? extends M1>, ? super Lazy<? extends M2>, ? super Lazy<? extends M3>> _function;
		
		public LazyModel3(IModel<? extends M1> m1, IModel<? extends M2> m2, IModel<? extends M3> m3, Function3<T, ? super Lazy<? extends M1>, ? super Lazy<? extends M2>, ? super Lazy<? extends M3>> function)
    {
	    super(function, m1,m2,m3);
	    
	    _m1=m1;
	    _m2=m2;
	    _m3=m3;
	    _function=function;
    }
		
		@Override
		protected T load()
		{
		  return _function.apply(lazy(_m1),lazy(_m2),lazy(_m3));
		}
	}
	
	private static <T> Lazy<T> lazy(IModel<T> model) {
		return new LazyModelAdapter<T>(model);
	}

	final static class LazyModelAdapter<T> implements Lazy<T> {
		
		private final IModel<T> _model;

		public LazyModelAdapter(IModel<T> model) {
			_model = model;
		}

		@Override
		public T get() {
			return _model.getObject();
		}
		
	}
}
