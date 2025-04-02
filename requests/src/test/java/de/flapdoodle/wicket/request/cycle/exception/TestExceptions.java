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
package de.flapdoodle.wicket.request.cycle.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestExceptions {

	@Test
	public void testRootException() {
		A top=new A("a",new B("b",new C("c",new D("root"))));
		assertThat(Exceptions.rootCause(top).getClass()).describedAs("type").isEqualTo(D.class);
		assertThat(Exceptions.rootCause(top).getMessage()).describedAs("message").isEqualTo("root");
	}

	@Test
	public void testAnyOf() {
		A top=new A("a",new B("b",new D("d",new B("root"))));
		
		Throwable anyCauseOf = Exceptions.anyCauseOf(top,B.class);

		assertThat(anyCauseOf.getClass()).describedAs("type").isEqualTo(B.class);
		assertThat(anyCauseOf.getMessage()).describedAs("message").isEqualTo("b");

		anyCauseOf = Exceptions.anyCauseOf(top,C.class);

		assertThat(anyCauseOf).isNull();

		anyCauseOf = Exceptions.anyCauseOf(top,A.class);

		assertThat(anyCauseOf.getClass()).describedAs("type").isEqualTo(A.class);
		assertThat(anyCauseOf.getMessage()).describedAs("message").isEqualTo("a");
	}

	@Test
	public void testFirstNot() {
		A top=new A("a",new B("b",new D("d",new B("root"))));
		
		Throwable anyCauseOf = Exceptions.firstCauseNotOf(top,B.class,A.class);

		assertThat(anyCauseOf.getClass()).describedAs("type").isEqualTo(D.class);
		assertThat(anyCauseOf.getMessage()).describedAs("message").isEqualTo("d");

		anyCauseOf = Exceptions.firstCauseNotOf(top,B.class,D.class,A.class);

		assertThat(anyCauseOf).isNull();

		anyCauseOf = Exceptions.firstCauseNotOf(top,D.class,B.class);

		assertThat(anyCauseOf.getClass()).describedAs("type").isEqualTo(A.class);
		assertThat(anyCauseOf.getMessage()).describedAs("message").isEqualTo("a");
	}
	
	

	static class A extends Exception {

		public A(String message) {
			super(message);
		}

		public A(String message, Throwable cause) {
			super(message, cause);
		}
	}

	static class B extends Exception {

		public B(String message) {
			super(message);
		}

		public B(String message, Throwable cause) {
			super(message, cause);
		}
	}
	
	static class C extends Exception {

		public C(String message) {
			super(message);
		}

		public C(String message, Throwable cause) {
			super(message, cause);
		}
	}

	static class D extends Exception {

		public D(String message) {
			super(message);
		}

		public D(String message, Throwable cause) {
			super(message, cause);
		}
	}

}
