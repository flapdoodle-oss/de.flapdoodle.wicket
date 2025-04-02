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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.functions.Function2;
import de.flapdoodle.functions.Function3;
import de.flapdoodle.wicket.model.transformation.Lazy;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestModelTransformations extends AbstractModelTest {

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
		assertThat((Object) model.getObject()).isEqualTo("1");
		source.setObject(2);
		assertThat((Object) model.getObject()).isEqualTo("1");
		model.detach();
		assertThat((Object) model.getObject()).isEqualTo("2");
		serialize(model);
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
		assertThat((Object) model.getObject()).isEqualTo(Integer.valueOf(3));
		a.setObject(2);
		assertThat((Object) model.getObject()).isEqualTo(Integer.valueOf(3));
		model.detach();
		assertThat((Object) model.getObject()).isEqualTo(Integer.valueOf(4));
		b.setObject(3);
		assertThat((Object) model.getObject()).isEqualTo(Integer.valueOf(4));
		model.detach();
		assertThat((Object) model.getObject()).isEqualTo(Integer.valueOf(5));
		serialize(model);
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

		assertThat((Object) Models.on(listModel).apply(new StringList2String()).getObject()).isNotNull();
		assertThat((Object) Models.on(listModel).applyLazy(new LazyStringList2String()).getObject()).isNotNull();
		assertThat((Object) Models.apply(new StringList2String()).to(listModel).getObject()).isNotNull();
		assertThat((Object) Models.applyLazy(new LazyStringList2String()).to(listModel).getObject()).isNotNull();
	}

	@Test
	public void genericMethodSignatureForFunction2ShouldBeFlexible() {
		IModel<? extends List<? extends String>> listModel=new Model<ArrayList<? extends String>>(new ArrayList<String>());
		IModel<? extends List<? extends Integer>> numberModel=new Model<ArrayList<? extends Integer>>(new ArrayList<Integer>());

		assertThat((Object) Models.on(listModel, numberModel).apply(new StringAndIntList2String()).getObject()).isNotNull();
		assertThat((Object) Models.on(listModel, numberModel).applyLazy(new LazyStringAndIntList2String()).getObject()).isNotNull();
		assertThat((Object) Models.apply(new StringAndIntList2String()).to(listModel, numberModel).getObject()).isNotNull();
		assertThat((Object) Models.applyLazy(new LazyStringAndIntList2String()).to(listModel, numberModel).getObject()).isNotNull();
	}
	
	@Test
	public void genericMethodSignatureForFunction3ShouldBeFlexible() {
		IModel<? extends List<? extends String>> listModel=new Model<ArrayList<? extends String>>(new ArrayList<String>());
		IModel<? extends List<? extends Integer>> numberModel=new Model<ArrayList<? extends Integer>>(new ArrayList<Integer>());
		IModel<? extends List<? extends Boolean>> boolModel=new Model<ArrayList<? extends Boolean>>(new ArrayList<Boolean>());

		assertThat((Object) Models.on(listModel, numberModel, boolModel).apply(new StringIntAndBoolList2String()).getObject()).isNotNull();
		assertThat((Object) Models.on(listModel, numberModel, boolModel).applyLazy(new LazyStringIntAndBoolList2String()).getObject()).isNotNull();
		assertThat((Object) Models.apply(new StringIntAndBoolList2String()).to(listModel, numberModel, boolModel).getObject()).isNotNull();
		assertThat((Object) Models.applyLazy(new LazyStringIntAndBoolList2String()).to(listModel, numberModel, boolModel).getObject()).isNotNull();
	}
	
	private void checkModelModifications(Model<Integer> a, Model<Integer> b, Model<String> postfix, IModel<String> model) {
		assertThat((Object) model.getObject()).isEqualTo("3 Kinder");
		a.setObject(2);
		assertThat((Object) model.getObject()).isEqualTo("3 Kinder");
		model.detach();
		assertThat((Object) model.getObject()).isEqualTo("4 Kinder");
		b.setObject(3);
		assertThat((Object) model.getObject()).isEqualTo("4 Kinder");
		model.detach();
		assertThat((Object) model.getObject()).isEqualTo("5 Kinder");
		postfix.setObject("Äpfel");
		assertThat((Object) model.getObject()).isEqualTo("5 Kinder");
		model.detach();
		assertThat((Object) model.getObject()).isEqualTo("5 Äpfel");
		serialize(model);
	}
	
	private <T> void checkExceptionOnSetObject(IModel<T> model,T value) {
		Exception e=null;
		try {
			model.setObject(value);
		} catch (Exception ex) {
			e=ex;
		}
		assertThat(e).describedAs("Exception on setObject").isNotNull();
	}
	
	private static final class LazyStringAndIntList2String implements Function2<String, Lazy<? extends List<? extends String>>, Lazy<? extends List<? extends Integer>>> {

		@Override
		public String apply(Lazy<? extends List<? extends String>> value, Lazy<? extends List<? extends Integer>> value2) {
			return value.get().toString()+value2.get().toString();
		}
	}

	private static final class LazyStringIntAndBoolList2String implements Function3<String, Lazy<? extends List<? extends String>>, Lazy<? extends List<? extends Integer>>, Lazy<? extends List<? extends Boolean>>> {

		@Override
		public String apply(Lazy<? extends List<? extends String>> value, Lazy<? extends List<? extends Integer>> value2, Lazy<? extends List<? extends Boolean>> value3) {
			return value.get().toString()+value2.get().toString()+value3.get().toString();
		}
	}

	private static final class StringAndIntList2String implements Function2<String, List<? extends String>, List<? extends Integer>> {

		@Override
		public String apply(List<? extends String> value, List<? extends Integer> value2) {
			return value.toString()+value2.toString();
		}
	}

	private static final class StringIntAndBoolList2String implements Function3<String, List<? extends String>, List<? extends Integer>, List<? extends Boolean>> {

		@Override
		public String apply(List<? extends String> value, List<? extends Integer> value2, List<? extends Boolean> value3) {
			return value.toString()+value2.toString()+value3.toString();
		}
	}

	private static final class LazyStringList2String implements Function1<String, Lazy<? extends List<? extends String>>> {

		@Override
		public String apply(Lazy<? extends List<? extends String>> value) {
			return value.get().toString();
		}
	}

	private static final class StringList2String implements Function1<String, List<? extends String>> {

		@Override
		public String apply(List<? extends String> value) {
			return value.toString();
		}
	}

	private static final class AddToNumbersAndAString implements Function3<String, Integer, Integer, String> {

		@Override
		public String apply(Integer value, Integer value2,String value3) {
			return ""+(value+value2)+" "+value3;
		}
	}

	private static final class AddTwoNumbers implements Function2<Integer, Integer, Integer> {

		@Override
		public Integer apply(Integer value, Integer value2) {
			return value+value2;
		}
	}

	private static final class IntegerToString implements Function1<String, Integer> {

		@Override
		public String apply(Integer value) {
			return ""+value;
		}
	}

}
