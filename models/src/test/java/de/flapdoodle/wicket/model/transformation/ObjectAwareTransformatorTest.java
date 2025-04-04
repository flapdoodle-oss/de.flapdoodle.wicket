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
import org.apache.wicket.model.Model;

import de.flapdoodle.wicket.model.AbstractModelTest;
import de.flapdoodle.wicket.model.ModelProxy;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectAwareTransformatorTest extends AbstractModelTest {

	@Test
	public void modelMustBeSerializable() {
		Model<Integer> intModel = Model.of(12);
		IModel<String> model = new ObjectAwareTransformator<Integer, String>(intModel, String.class, Object::toString, Integer::valueOf);
		model.setObject("13");
		model.getObject();
		model.detach();
		serialize(model);
	}
	
	@Test
	public void detachMustPropagate() {
		Model<Integer> intModel = Model.of(12);
		ModelProxy<Integer> proxy = new ModelProxy<>(intModel);
		IModel<String> model = new ObjectAwareTransformator<Integer, String>(proxy, String.class, Object::toString, Integer::valueOf);

		assertThat((Object) proxy.detachCalled()).isEqualTo(0);
		model.detach();
		assertThat((Object) proxy.detachCalled()).isEqualTo(1);
	}

	@Test
	public void getObjectMustMatchPropertyValue() {
		Model<Integer> intModel = Model.of(12);
		IModel<String> model = new ObjectAwareTransformator<Integer, String>(intModel, String.class, Object::toString, Integer::valueOf);
		assertThat((Object) model.getObject()).isEqualTo("12");
	}
	
	@Test
	public void setObjectMustSetPropertyValue() {
		Model<Integer> intModel = new Model<>();
		IModel<String> model = new ObjectAwareTransformator<Integer, String>(intModel, String.class, Object::toString, Integer::valueOf);

		assertThat((Object) intModel.getObject()).isNull();
		model.setObject("1231131");
		assertThat((Object) intModel.getObject()).isEqualTo(Integer.valueOf(1231131));
	}
}
