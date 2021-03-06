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

import junit.framework.Assert;

import org.junit.Test;

public class TestExceptions {

	@Test
	public void testRootException() {
		A top=new A("a",new B("b",new C("c",new D("root"))));
		Assert.assertEquals("type", D.class,Exceptions.rootCause(top).getClass());
		Assert.assertEquals("message", "root",Exceptions.rootCause(top).getMessage());
	}
	
	@Test
	public void testAnyOf() {
		A top=new A("a",new B("b",new D("d",new B("root"))));
		
		Throwable anyCauseOf = Exceptions.anyCauseOf(top,B.class);
		
		Assert.assertEquals("type", B.class,anyCauseOf.getClass());
		Assert.assertEquals("message", "b",anyCauseOf.getMessage());
		
		anyCauseOf = Exceptions.anyCauseOf(top,C.class);
		
		Assert.assertNull(anyCauseOf);

		anyCauseOf = Exceptions.anyCauseOf(top,A.class);
		
		Assert.assertEquals("type", A.class,anyCauseOf.getClass());
		Assert.assertEquals("message", "a",anyCauseOf.getMessage());
	}
	
	@Test
	public void testFirstNot() {
		A top=new A("a",new B("b",new D("d",new B("root"))));
		
		Throwable anyCauseOf = Exceptions.firstCauseNotOf(top,B.class,A.class);
		
		Assert.assertEquals("type", D.class,anyCauseOf.getClass());
		Assert.assertEquals("message", "d",anyCauseOf.getMessage());
		
		anyCauseOf = Exceptions.firstCauseNotOf(top,B.class,D.class,A.class);
		
		Assert.assertNull(anyCauseOf);

		anyCauseOf = Exceptions.firstCauseNotOf(top,D.class,B.class);
		
		Assert.assertEquals("type", A.class,anyCauseOf.getClass());
		Assert.assertEquals("message", "a",anyCauseOf.getMessage());
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
