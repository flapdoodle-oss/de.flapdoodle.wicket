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
package de.flapdoodle.wicket.functions;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.functions.Function2;
import de.flapdoodle.functions.Function3;
import de.flapdoodle.functions.Functions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestFunctions {

	@Test
	public void joinFunction1() {
		Inc a = new Inc(1);
		Inc b = new Inc(2);
		Function1<Integer, Integer> ab = Functions.join(a, b);
		Function1<Integer, Integer> ba = Functions.join(b, a);
		Function1<Integer, Integer> abba = Functions.join(ab, ba);

		assertThat(a.apply(1)).isEqualTo(Integer.valueOf(2));
		assertThat(b.apply(1)).isEqualTo(Integer.valueOf(3));
		assertThat(ab.apply(1)).isEqualTo(Integer.valueOf(4));
		assertThat(ba.apply(1)).isEqualTo(Integer.valueOf(4));

		assertThat(abba.apply(1)).isEqualTo(Integer.valueOf(7));

		Function1<? extends Integer, ? super Integer> genericSuperA=a;
		Function1<? extends Integer, ? super Integer> genericSuperB=b;
		Function1<? extends Integer, Integer> res = Functions.join(genericSuperA, genericSuperB);
		assertThat(res.apply(1)).isEqualTo(Integer.valueOf(4));
	}

	@Test
	public void joinFunction2() {
		AwithString aString = new AwithString();
		Decorate<String> decorate= new Decorate<>();
		Function2<String, A, String> aAny = Functions.join(aString,decorate);
		assertThat(aAny.apply(new A(), "hi")).isEqualTo("A[hi]");
	}
	
	@Test
	public void joinFunction3() {
		AwithString aString = new AwithString();
		BwithC bc = new BwithC();
		Function3<String, A, B, C> abc = Functions.join(aString, bc);
		assertThat(abc.apply(new A(), new B(), new C())).isEqualTo("ABC");
	}
	
	@Test
	public void joinFunction33() {
		AwithB ab = new AwithB();
		BwithC bc = new BwithC();
		Concat all = new Concat();
		Function3<String, A, B, C> abc = Functions.join(all, ab, bc);

		assertThat(abc.apply(new A(), new B(), new C())).isEqualTo("ABBC");

		Function3<String, C, B, A> cba = Functions.join(Functions.swap(all), Functions.swap(bc), Functions.swap(ab));

		assertThat(cba.apply(new C(), new B(), new A())).isEqualTo("ABBC");
	}

	@Test
	public void swapFunctionType() {
		AwithB ab = new AwithB();

		assertThat(ab.apply(new A(), new B())).isEqualTo("AB");
		assertThat(Functions.swap(ab).apply(new B(), new A())).isEqualTo("AB");
	}

	static class Inc implements Function1<Integer, Integer> {

		final int _inc;

		public Inc(int inc) {
			_inc = inc;
		}

		@Override
		public Integer apply(Integer value) {
			return value + _inc;
		}

	}

	static class Decorate<T> implements Function1<String, T> {

		@Override
		public String apply(T value) {
			return "[" + value + "]";
		}

	}

	static abstract class AbstractSimple {

		@Override
		public String toString() {
			return getClass().getSimpleName();
		}
	}

	static class A extends AbstractSimple {

	}

	static class B extends AbstractSimple {

	}

	static class C extends AbstractSimple {

	}

	static abstract class AbstractABC<T1, T2> implements Function2<String, T1, T2> {

		@Override
		public String apply(T1 a, T2 b) {
			return "" + a + b;
		}

	}

	static class AwithB extends AbstractABC<A, B> {
	}

	static class BwithC extends AbstractABC<B, C> {

	}

	static class AwithString extends AbstractABC<A, String> {

	}

	static class Concat extends AbstractABC<String, String> {

	}

}
