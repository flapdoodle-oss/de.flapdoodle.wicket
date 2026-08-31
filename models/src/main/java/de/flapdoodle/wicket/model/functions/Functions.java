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
package de.flapdoodle.wicket.model.functions;

import org.danekja.java.util.function.serializable.SerializableBiFunction;
import org.danekja.java.util.function.serializable.SerializableFunction;

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
	 * @param outer the last applied function
	 * @param inner the first applied function
	 * @return a new function
	 */
	public static <R, X, T> SerializableFunction<T, R> join(SerializableFunction<? super X, R> outer, SerializableFunction<? super T, X> inner) {
		return new JoinedFunction1<>(outer, inner);
	}

	/**
	 * joins to functions with matching types to a new function
	 * converts T2 to X and T1 to R
	 *
	 * @param outer the last applied function
	 * @param inner the first applied function
	 * @return a new function
	 */
	public static <R, T1, T2, X> SerializableBiFunction<T1, T2, R> join(SerializableBiFunction<? super T1, ? super X, R> outer, SerializableFunction<? super T2, X> inner) {
		return new JoinedFunction2<R, T1, T2, X>(outer, inner);
	}

	/**
	 * joins to functions with matching types to a new function
	 * converts T2 and T3 to X and T1 to R
	 *
	 * @param outer the last applied function
	 * @param inner the first applied function
	 * @return a new function
	 */
	public static <R, T1, T2, T3, X> SerializableTriFunction<T1, T2, T3, R> join(SerializableBiFunction<? super T1, ? super X, R> outer, SerializableBiFunction<? super T2, ? super T3, X> inner) {
		return new JoinedSerializableTriFunction<R, T1, T2, T3, X>(outer, inner);
	}

	/**
	 * joins to functions with matching types to a new function
	 * converts T2 and T3 to X and T1 to R
	 *
	 * @param outer the last applied function
	 * @param left the first applied function
	 * @param right the first applied function
	 * @return a new function
	 */
	public static <R, T1, T2, T3, A, B> SerializableTriFunction<T1, T2, T3, R> join(SerializableBiFunction<? super A, ? super B, R> outer, SerializableBiFunction<? super T1, ? super T2, A> left,
		SerializableBiFunction<? super T2, ? super T3, B> right) {
		return new JoinedSerializableTriFunction3<>(outer, left, right);
	}

	/**
	 * swap function type signature without changing behavior
	 *
	 * @param source source function
	 * @return function adapter with flipped types
	 */
	public static <R, T1, T2> SerializableBiFunction<T1, T2, R> swap(SerializableBiFunction<? super T2, ? super T1, R> source) {
		return new SwappedTypeFunction<R, T1, T2>(source);
	}

	public static <R, T> SerializableFunction<T, R> orNull(SerializableFunction<T, R> transformation) {
		return value -> value != null ? transformation.apply(value) : null;
	}

	static class JoinedFunction1<R, X, T> implements SerializableFunction<T, R> {

		private final SerializableFunction<? super X, R> _outer;
		private final SerializableFunction<? super T, X> _inner;

		public JoinedFunction1(SerializableFunction<? super X, R> a, SerializableFunction<? super T, X> b) {
			_outer = a;
			_inner = b;
		}

		@Override
		public R apply(T value) {
			return _outer.apply(_inner.apply(value));
		}

	}

	static class JoinedFunction2<R, T1, T2, X> implements SerializableBiFunction<T1, T2, R> {

		private final SerializableBiFunction<? super T1, ? super X, R> _outer;
		private final SerializableFunction<? super T2, X> _inner;

		public JoinedFunction2(SerializableBiFunction<? super T1, ? super X, R> outer, SerializableFunction<? super T2, X> inner) {
			_outer = outer;
			_inner = inner;
		}

		@Override
		public R apply(T1 a, T2 b) {
			return _outer.apply(a, _inner.apply(b));
		}

	}

	static class JoinedSerializableTriFunction<R, T1, T2, T3, X> implements SerializableTriFunction<T1, T2, T3, R> {

		private final SerializableBiFunction<? super T1, ? super X, R> _outer;
		private final SerializableBiFunction<? super T2, ? super T3, X> _inner;

		public JoinedSerializableTriFunction(SerializableBiFunction<? super T1, ? super X, R> outer, SerializableBiFunction<? super T2, ? super T3, X> inner) {
			_outer = outer;
			_inner = inner;
		}

		@Override
		public R apply(T1 a, T2 b, T3 c) {
			return _outer.apply(a, _inner.apply(b, c));
		}

	}

	static class JoinedSerializableTriFunction3<R, T1, T2, T3, A, B> implements SerializableTriFunction<T1, T2, T3, R> {

		private final SerializableBiFunction<? super A, ? super B, R> _outer;
		private final SerializableBiFunction<? super T1, ? super T2, A> _left;
		private final SerializableBiFunction<? super T2, ? super T3, B> _right;

		public JoinedSerializableTriFunction3(SerializableBiFunction<? super A, ? super B, R> outer, SerializableBiFunction<? super T1, ? super T2, A> left, SerializableBiFunction<? super T2, ? super T3, B> right) {
			_outer = outer;
			_left = left;
			_right = right;
		}

		@Override
		public R apply(T1 a, T2 b, T3 c) {
			return _outer.apply(_left.apply(a, b), _right.apply(b, c));
		}

	}

	static class SwappedTypeFunction<R, T1, T2> implements SerializableBiFunction<T1, T2, R> {

		private final SerializableBiFunction<? super T2, ? super T1, R> _source;

		public SwappedTypeFunction(SerializableBiFunction<? super T2, ? super T1, R> source) {
			_source = source;
		}

		@Override
		public R apply(T1 a, T2 b) {
			return _source.apply(b, a);
		}

	}

}
