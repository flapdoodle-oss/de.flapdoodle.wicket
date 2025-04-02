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

import de.flapdoodle.wicket.model.AbstractModelTest;
import de.flapdoodle.wicket.model.ModelProxy;
import org.apache.wicket.model.Model;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TransformatorModelTest extends AbstractModelTest {

	@Test
	public void setAndGetObjectMustMapValue() {
		Model<Integer> srcModel = Model.of(2);
		TransformatorModel<Integer, String> transformationModel = new TransformatorModel<Integer, String>(srcModel, Object::toString, Integer::valueOf);
		assertThat((Object) transformationModel.getObject()).isEqualTo("2");
		transformationModel.setObject("3");
		assertThat((Object) srcModel.getObject()).isEqualTo(Integer.valueOf(3));
		assertThat((Object) transformationModel.getObject()).isEqualTo("3");
	}
	
	@Test
	public void detachMustPropagate() {
		Model<Integer> srcModel = Model.of(2);
		ModelProxy<Integer> proxy=new ModelProxy<>(srcModel);
		TransformatorModel<Integer, String> transformationModel = new TransformatorModel<Integer, String>(proxy, Object::toString, Integer::valueOf);

		assertThat((Object) proxy.detachCalled()).isEqualTo(0);
		transformationModel.detach();
		assertThat((Object) proxy.detachCalled()).isEqualTo(1);
	}
}
