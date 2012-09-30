package de.flapdoodle.wicket.model.transformation;

import org.apache.wicket.model.IModel;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.functions.Function2;
import de.flapdoodle.functions.Function3;


public abstract class Functions {
	
	public static final class Reference1<R,T>
	{
		private final Function1<R, T> _function;

		public Reference1(Function1<R, T> function) {
			_function = function;
		}
		
		public IModel<R> on(IModel<T> m1) {
			return new Transformator.Model1<R, T>(m1, _function);
		}
	}
	
	public static final class Reference2<R,T1,T2>
	{
		private final Function2<R, T1, T2> _function;

		public Reference2(Function2<R, T1, T2> function) {
			_function = function;
		}
		
		public IModel<R> on(IModel<T1> m1, IModel<T2> m2) {
			return new Transformator.Model2<R, T1, T2>(m1,m2, _function);
		}
	}
	
	public static final class Reference3<R,T1,T2,T3>
	{
		private final Function3<R, T1, T2, T3> _function;

		public Reference3(Function3<R, T1, T2, T3> function) {
			_function = function;
		}
		
		public IModel<R> on(IModel<T1> m1, IModel<T2> m2, IModel<T3> m3) {
			return new Transformator.Model3<R, T1, T2, T3>(m1,m2,m3, _function);
		}
	}
}
