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
package de.flapdoodle.functions;

/**
 * function aggregation helper
 */
public class Functions {

	private Functions() {
		// no instance
	}

	/**
	 * joins two functions with matching types to a new function
	 * converts T to X to R
	 * 
	 * @param outer
	 *          the last applied function
	 * @param inner
	 *          the first applied function
	 * @return a new function
	 */
	public static <R, X, T> Function1<R, T> join(Function1<R, X> outer, Function1<X, T> inner) {
		return new JoinedFunction1<R, X, T>(outer, inner);
	}

	/**
	 * joins to functions with matching types to a new function
	 * converts T2 to X and T1 to R
	 * @param outer
	 *          the last applied function
	 * @param inner
	 *          the first applied function
	 * @return a new function
	 */
	public static <R, T1, T2, X> Function2<R, T1, T2> join(Function2<R, T1, X> outer, Function1<X, T2> inner) {
		return new JoinedFunction2<R, T1, T2, X>(outer, inner);
	}

	/**
	 * joins to functions with matching types to a new function
	 * converts T2 and T3 to X and T1 to R
	 * @param outer
	 *          the last applied function
	 * @param inner
	 *          the first applied function
	 * @return a new function
	 */
	public static <R, T1, T2, T3, X> Function3<R, T1, T2, T3> join(Function2<R, T1, X> outer, Function2<X, T2, T3> inner) {
		return new JoinedFunction3<R, T1, T2, T3, X>(outer, inner);
	}
	
	/**
	 * joins to functions with matching types to a new function
	 * converts T2 and T3 to X and T1 to R
	 * @param outer
	 *          the last applied function
	 * @param inner
	 *          the first applied function
	 * @return a new function
	 */
	public static <R, T1, T2, T3, A, B> Function3<R, T1, T2, T3> join(Function2<R, A, B> outer, Function2<A, T1, T2> left, Function2<B, T2, T3> right) {
		return new JoinedFunction33<R, T1, T2, T3, A, B>(outer, left, right);
	}
	
	/**
	 * swap function type signature without changing behavior
	 * 
	 * @param source
	 *          source function
	 * @return function adapter with flipped types
	 */
	public static <R, T1, T2> Function2<R, T1, T2> swap(Function2<R, T2, T1> source) {
		return new SwappedTypeFunction<R, T1, T2>(source);
	}
	
	static class JoinedFunction1<R, X, T> implements Function1<R, T> {

		private final Function1<R, X> _outer;
		private final Function1<X, T> _inner;

		public JoinedFunction1(Function1<R, X> a, Function1<X, T> b) {
			_outer = a;
			_inner = b;
		}

		@Override
		public R apply(T value) {
			return _outer.apply(_inner.apply(value));
		}

	}

	static class JoinedFunction2<R, T1, T2, X> implements Function2<R, T1, T2> {

		private final Function2<R, T1, X> _outer;
		private final Function1<X, T2> _inner;

		public JoinedFunction2(Function2<R, T1, X> outer, Function1<X, T2> inner) {
			_outer = outer;
			_inner = inner;
		}

		@Override
		public R apply(T1 a, T2 b) {
			return _outer.apply(a, _inner.apply(b));
		}

	}
	
	static class JoinedFunction3<R, T1, T2, T3, X> implements Function3<R, T1, T2, T3> {

		private final Function2<R, T1, X> _outer;
		private final Function2<X, T2, T3> _inner;

		public JoinedFunction3(Function2<R, T1, X> outer, Function2<X, T2, T3> inner) {
			_outer = outer;
			_inner = inner;
		}

		@Override
		public R apply(T1 a, T2 b, T3 c) {
			return _outer.apply(a, _inner.apply(b, c));
		}

	}

	static class JoinedFunction33<R, T1, T2, T3, A, B> implements Function3<R, T1, T2, T3> {

		private final Function2<R, A, B> _outer;
		private final Function2<A, T1, T2> _left;
		private final Function2<B, T2, T3> _right;

		public JoinedFunction33(Function2<R, A, B> outer, Function2<A, T1, T2> left, Function2<B, T2, T3> right) {
			_outer = outer;
			_left = left;
			_right = right;
		}

		@Override
		public R apply(T1 a, T2 b, T3 c) {
			return _outer.apply(_left.apply(a, b),_right.apply(b, c));
		}

	}
	
	static class SwappedTypeFunction<R, T1, T2> implements Function2<R, T1, T2> {

		private final Function2<R, T2, T1> _source;

		public SwappedTypeFunction(Function2<R, T2, T1> source) {
			_source = source;
		}

		@Override
		public R apply(T1 a, T2 b) {
			return _source.apply(b, a);
		}

	}

}
