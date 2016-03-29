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
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

import de.flapdoodle.wicket.model.AbstractModelTest;
import de.flapdoodle.wicket.model.ModelProxy;

public class MapModelTest extends AbstractModelTest {

	@Test
	public void emptyCollectionShouldGiveEmptyMap() {
		MapModel<String, String> model = new MapModel<String,String>(Model.ofList(new ArrayList<String>()), x->x);
		assertTrue(model.getObject().isEmpty());
	}
	
	@Test
	public void detachMustPropagate() {
		IModel<? extends List<? extends String>> src = Model.ofList(new ArrayList<String>());
		ModelProxy<? extends List<? extends String>> proxy = new ModelProxy<>(src);
		MapModel<String, String> model = new MapModel<String,String>(proxy, x->x);
		
		assertEquals(0,proxy.detachCalled());
		model.getObject();
		model.detach();
		assertEquals(1,proxy.detachCalled());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void keyCollisionMustThrowException() {
		List<String> src=new ArrayList<>();
		src.add("a");
		src.add("b");
		src.add("a");
		MapModel.asMap(src, x->x);
	}
}
