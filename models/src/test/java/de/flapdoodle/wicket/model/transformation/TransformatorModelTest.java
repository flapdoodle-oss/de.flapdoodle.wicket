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

import static org.junit.Assert.assertEquals;

import org.apache.wicket.model.Model;
import org.junit.Test;

import de.flapdoodle.wicket.model.AbstractModelTest;
import de.flapdoodle.wicket.model.ModelProxy;

public class TransformatorModelTest extends AbstractModelTest {

	@Test
	public void setAndGetObjectMustMapValue() {
		Model<Integer> srcModel = Model.of(2);
		TransformatorModel<Integer, String> transformationModel = new TransformatorModel<Integer, String>(srcModel, Object::toString, Integer::valueOf);
		assertEquals("2",transformationModel.getObject());
		transformationModel.setObject("3");
		assertEquals(Integer.valueOf(3),srcModel.getObject());
		assertEquals("3",transformationModel.getObject());
	}
	
	@Test
	public void detachMustPropagate() {
		Model<Integer> srcModel = Model.of(2);
		ModelProxy<Integer> proxy=new ModelProxy<>(srcModel);
		TransformatorModel<Integer, String> transformationModel = new TransformatorModel<Integer, String>(proxy, Object::toString, Integer::valueOf);
		
		assertEquals(0,proxy.detachCalled());
		transformationModel.detach();
		assertEquals(1,proxy.detachCalled());
	}
}
