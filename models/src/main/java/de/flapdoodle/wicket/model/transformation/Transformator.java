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

import de.flapdoodle.functions.Function1;
import de.flapdoodle.functions.Function2;
import de.flapdoodle.functions.Function3;

public abstract class Transformator<T> extends LoadableDetachableModel<T>
{
	IModel<?>[] _subModels;
	
	protected Transformator(IModel<?> ... subModels)
  {
		_subModels=subModels;
  }
	
	@Override
	protected void onDetach()
	{
		for (IModel<?> m : _subModels)
		{
			m.detach();
		}
	}
	
	public static class Model1<T,M1> extends Transformator<T>
	{
		IModel<M1> _m1;
		Function1<T, M1> _function;
		
		public Model1(IModel<M1> m1, Function1<T, M1> function)
    {
	    super(m1);
	    
	    _m1=m1;
	    _function=function;
    }
		
		@Override
		protected T load()
		{
		  return _function.apply(_m1.getObject());
		}
	}
	
	public static class Model2<T,M1,M2> extends Transformator<T>
	{
		IModel<M1> _m1;
		IModel<M2> _m2;
		Function2<T, M1, M2> _function;
		
		public Model2(IModel<M1> m1, IModel<M2> m2, Function2<T, M1, M2> function)
    {
	    super(m1,m2);
	    
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

	public static class Model3<T,M1,M2,M3> extends Transformator<T>
	{
		IModel<M1> _m1;
		IModel<M2> _m2;
		IModel<M3> _m3;
		Function3<T, M1, M2, M3> _function;
		
		public Model3(IModel<M1> m1, IModel<M2> m2, IModel<M3> m3, Function3<T, M1, M2, M3> function)
    {
	    super(m1,m2,m3);
	    
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
}
