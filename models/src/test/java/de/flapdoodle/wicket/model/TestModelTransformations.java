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
package de.flapdoodle.wicket.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Assert;
import org.junit.Test;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.functions.Function2;
import de.flapdoodle.functions.Function3;
import de.flapdoodle.wicket.model.transformation.Lazy;


public class TestModelTransformations {

	@Test
	public void sourceModelAndFunction() {
		Model<Integer> source = Model.of(1);
		IModel<String> model = Models.on(source).apply(new IntegerToString());
		checkModelModifications(source, model);
		checkExceptionOnSetObject(model,"Some String");
	}
	
	@Test
	public void functionAndSourceModel() {
		Model<Integer> source = Model.of(1);
		IModel<String> model = Models.apply(new IntegerToString()).to(source);
		checkModelModifications(source, model);
		checkExceptionOnSetObject(model,"Some String");
	}

	private void checkModelModifications(Model<Integer> source, IModel<String> model) {
		Assert.assertEquals("1", model.getObject());
		source.setObject(2);
		Assert.assertEquals("1", model.getObject());
		model.detach();
		Assert.assertEquals("2", model.getObject());
	}

	@Test
	public void twoModelsAndAFunction() {
		Model<Integer> a = Model.of(1);
		Model<Integer> b = Model.of(2);
		IModel<Integer> model = Models.on(a,b).apply(new AddTwoNumbers());
		checkModelModifications(a, b, model);
		checkExceptionOnSetObject(model,100);
	}
	
	@Test
	public void functionAndTwoModels() {
		Model<Integer> a = Model.of(1);
		Model<Integer> b = Model.of(2);
		IModel<Integer> model = Models.apply(new AddTwoNumbers()).to(a,b);
		checkModelModifications(a, b, model);
		checkExceptionOnSetObject(model,100);
	}
	
	private void checkModelModifications(Model<Integer> a, Model<Integer> b, IModel<Integer> model) {
		Assert.assertEquals(Integer.valueOf(3), model.getObject());
		a.setObject(2);
		Assert.assertEquals(Integer.valueOf(3), model.getObject());
		model.detach();
		Assert.assertEquals(Integer.valueOf(4), model.getObject());
		b.setObject(3);
		Assert.assertEquals(Integer.valueOf(4), model.getObject());
		model.detach();
		Assert.assertEquals(Integer.valueOf(5), model.getObject());
	}
	
	@Test
	public void threeModelsAndAFunction() {
		Model<Integer> a = Model.of(1);
		Model<Integer> b = Model.of(2);
		Model<String> postfix = Model.of("Kinder");
		IModel<String> model = Models.on(a,b,postfix).apply(new AddToNumbersAndAString());
		checkModelModifications(a, b, postfix, model);
		checkExceptionOnSetObject(model,"nix");
	}

	@Test
	public void functionAndThreeModels() {
		Model<Integer> a = Model.of(1);
		Model<Integer> b = Model.of(2);
		Model<String> postfix = Model.of("Kinder");
		IModel<String> model = Models.apply(new AddToNumbersAndAString()).to(a,b,postfix);
		checkModelModifications(a, b, postfix, model);
		checkExceptionOnSetObject(model,"nix");
	}
	
	@Test
	public void genericMethodSignatureForFunction1ShouldBeFlexible() {
		IModel<? extends List<? extends String>> listModel=new Model<ArrayList<? extends String>>(new ArrayList<String>());
		
		assertNotNull(Models.on(listModel).apply(new StringList2String()).getObject());
		assertNotNull(Models.on(listModel).applyLazy(new LazyStringList2String()).getObject());
		assertNotNull(Models.apply(new StringList2String()).to(listModel).getObject());
		assertNotNull(Models.applyLazy(new LazyStringList2String()).to(listModel).getObject());
	}
	
	@Test
	public void genericMethodSignatureForFunction2ShouldBeFlexible() {
		IModel<? extends List<? extends String>> listModel=new Model<ArrayList<? extends String>>(new ArrayList<String>());
		IModel<? extends List<? extends Integer>> numberModel=new Model<ArrayList<? extends Integer>>(new ArrayList<Integer>());
		
		assertNotNull(Models.on(listModel,numberModel).apply(new StringAndIntList2String()).getObject());
		assertNotNull(Models.on(listModel,numberModel).applyLazy(new LazyStringAndIntList2String()).getObject());
		assertNotNull(Models.apply(new StringAndIntList2String()).to(listModel,numberModel).getObject());
		assertNotNull(Models.applyLazy(new LazyStringAndIntList2String()).to(listModel,numberModel).getObject());
	}
	
	@Test
	public void genericMethodSignatureForFunction3ShouldBeFlexible() {
		IModel<? extends List<? extends String>> listModel=new Model<ArrayList<? extends String>>(new ArrayList<String>());
		IModel<? extends List<? extends Integer>> numberModel=new Model<ArrayList<? extends Integer>>(new ArrayList<Integer>());
		IModel<? extends List<? extends Boolean>> boolModel=new Model<ArrayList<? extends Boolean>>(new ArrayList<Boolean>());
		
		assertNotNull(Models.on(listModel,numberModel,boolModel).apply(new StringIntAndBoolList2String()).getObject());
		assertNotNull(Models.on(listModel,numberModel,boolModel).applyLazy(new LazyStringIntAndBoolList2String()).getObject());
		assertNotNull(Models.apply(new StringIntAndBoolList2String()).to(listModel,numberModel,boolModel).getObject());
		assertNotNull(Models.applyLazy(new LazyStringIntAndBoolList2String()).to(listModel,numberModel,boolModel).getObject());
	}
	
	private void checkModelModifications(Model<Integer> a, Model<Integer> b, Model<String> postfix, IModel<String> model) {
		Assert.assertEquals("3 Kinder", model.getObject());
		a.setObject(2);
		Assert.assertEquals("3 Kinder", model.getObject());
		model.detach();
		Assert.assertEquals("4 Kinder", model.getObject());
		b.setObject(3);
		Assert.assertEquals("4 Kinder", model.getObject());
		model.detach();
		Assert.assertEquals("5 Kinder", model.getObject());
		postfix.setObject("Äpfel");
		Assert.assertEquals("5 Kinder", model.getObject());
		model.detach();
		Assert.assertEquals("5 Äpfel", model.getObject());
	}
	
	private <T> void checkExceptionOnSetObject(IModel<T> model,T value) {
		Exception e=null;
		try {
			model.setObject(value);
		} catch (Exception ex) {
			e=ex;
		}
		Assert.assertNotNull("Exception on setObject",e);
	}

	private final class LazyStringAndIntList2String implements Function2<String, Lazy<? extends List<? extends String>>, Lazy<? extends List<? extends Integer>>> {

		@Override
		public String apply(Lazy<? extends List<? extends String>> value, Lazy<? extends List<? extends Integer>> value2) {
			return value.get().toString()+value2.get().toString();
		}
	}

	private final class LazyStringIntAndBoolList2String implements Function3<String, Lazy<? extends List<? extends String>>, Lazy<? extends List<? extends Integer>>, Lazy<? extends List<? extends Boolean>>> {

		@Override
		public String apply(Lazy<? extends List<? extends String>> value, Lazy<? extends List<? extends Integer>> value2, Lazy<? extends List<? extends Boolean>> value3) {
			return value.get().toString()+value2.get().toString()+value3.get().toString();
		}
	}

	private final class StringAndIntList2String implements Function2<String, List<? extends String>, List<? extends Integer>> {

		@Override
		public String apply(List<? extends String> value, List<? extends Integer> value2) {
			return value.toString()+value2.toString();
		}
	}

	private final class StringIntAndBoolList2String implements Function3<String, List<? extends String>, List<? extends Integer>, List<? extends Boolean>> {

		@Override
		public String apply(List<? extends String> value, List<? extends Integer> value2, List<? extends Boolean> value3) {
			return value.toString()+value2.toString()+value3.toString();
		}
	}

	private final class LazyStringList2String implements Function1<String, Lazy<? extends List<? extends String>>> {

		@Override
		public String apply(Lazy<? extends List<? extends String>> value) {
			return value.get().toString();
		}
	}

	private final class StringList2String implements Function1<String, List<? extends String>> {

		@Override
		public String apply(List<? extends String> value) {
			return value.toString();
		}
	}

	private final class AddToNumbersAndAString implements Function3<String, Integer, Integer, String> {

		@Override
		public String apply(Integer value, Integer value2,String value3) {
			return ""+(value+value2)+" "+value3;
		}
	}

	private final class AddTwoNumbers implements Function2<Integer, Integer, Integer> {

		@Override
		public Integer apply(Integer value, Integer value2) {
			return value+value2;
		}
	}

	private final class IntegerToString implements Function1<String, Integer> {

		@Override
		public String apply(Integer value) {
			return ""+value;
		}
	}

}
