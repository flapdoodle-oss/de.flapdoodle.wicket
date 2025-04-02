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

import java.io.Serializable;
import java.util.UUID;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.flapdoodle.wicket.model.AbstractModelTest;
import de.flapdoodle.wicket.model.ModelProxy;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectAwarePropertyAccessModelTest extends AbstractModelTest {

	@Test
	public void modelMustBeSerializable() {
		Model<Foo> fooModel = Model.of(new Foo());
		IModel<String> propertyModel = new ObjectAwarePropertyAccessModel<>(fooModel, String.class, Foo::getText, Foo::setText);
		propertyModel.setObject("Fooo");
		propertyModel.getObject();
		propertyModel.detach();
		serialize(propertyModel);
	}
	
	@Test
	public void detachMustPropagate() {
		Model<Foo> fooModel = Model.of(new Foo());
		ModelProxy<Foo> proxy = new ModelProxy<>(fooModel);
		IModel<String> propertyModel = new ObjectAwarePropertyAccessModel<>(proxy, String.class, Foo::getText, Foo::setText);

		assertThat((Object) proxy.detachCalled()).isEqualTo(0);
		propertyModel.detach();
		assertThat((Object) proxy.detachCalled()).isEqualTo(1);
	}
	
	@Test
	public void getObjectMustMatchPropertyValue() {
		Foo foo = new Foo();
		foo.setText(randomString());
		Model<Foo> fooModel = Model.of(foo);
		IModel<String> propertyModel = new ObjectAwarePropertyAccessModel<>(fooModel, String.class, Foo::getText, Foo::setText);

		Object expected = foo.getText();
		assertThat((Object) propertyModel.getObject()).isEqualTo(expected);
	}
	
	@Test
	public void setObjectMustSetPropertyValue() {
		Foo foo = new Foo();
		Model<Foo> fooModel = Model.of(foo);
		IModel<String> propertyModel = new ObjectAwarePropertyAccessModel<>(fooModel, String.class, Foo::getText, Foo::setText);

		assertThat((Object) foo.getText()).isEqualTo(null);
		String randomString = randomString();
		propertyModel.setObject(randomString);
		assertThat((Object) foo.getText()).isEqualTo(randomString);
	}

	private static String randomString() {
		return UUID.randomUUID().toString();
	}
	
	static class Foo implements Serializable {
		String text;
		
		public String getText() {
			return text;
		}
		
		public void setText(String text) {
			this.text = text;
		}
	}
}
