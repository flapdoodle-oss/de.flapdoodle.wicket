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
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

import de.flapdoodle.wicket.model.AbstractModelTest;

public class ListMappingModelTest extends AbstractModelTest {

	@Test
	public void emptyListMustGiveEmptyResult() {
		List<Object> result = ListMappingModel.map(new ArrayList<>(), x->{fail("should not be called"); return x;});
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void mapShouldMapEachEntry() {
		List<String> result = ListMappingModel.map(Arrays.asList("1","2"), x -> "["+x+"]");
		assertEquals("[[1], [2]]",result.toString());
	}
	
	@Test
	public void cacheResultUntilDetach() {
		List<Integer> sourceList = new ArrayList<>(Arrays.asList(1,2,3));
		IModel<? extends List<? extends Integer>> listModel = Model.ofList(sourceList);
		ListMappingModel<Integer, String> listMappingModel = new ListMappingModel<Integer,String>(listModel, x -> x.toString());
		assertEquals("[1, 2, 3]", listMappingModel.getObject().toString());
		sourceList.add(4);
		assertEquals("[1, 2, 3]", listMappingModel.getObject().toString());
		listMappingModel.detach();
		assertEquals("[1, 2, 3, 4]", listMappingModel.getObject().toString());
	}
}
