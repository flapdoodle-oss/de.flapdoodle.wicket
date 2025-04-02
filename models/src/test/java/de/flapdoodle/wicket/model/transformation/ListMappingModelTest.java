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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.flapdoodle.wicket.model.AbstractModelTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ListMappingModelTest extends AbstractModelTest {

	@Test
	public void emptyListMustGiveEmptyResult() {
		List<Object> result = ListMappingModel.map(new ArrayList<>(), x->{fail("should not be called"); return x;});
		assertThat(result.isEmpty()).isTrue();
	}
	
	@Test
	public void mapShouldMapEachEntry() {
		List<String> result = ListMappingModel.map(Arrays.asList("1","2"), x -> "["+x+"]");
		assertThat(result.toString()).isEqualTo("[[1], [2]]");
	}
	
	@Test
	public void cacheResultUntilDetach() {
		List<Integer> sourceList = new ArrayList<>(Arrays.asList(1,2,3));
		IModel<? extends List<? extends Integer>> listModel = Model.ofList(sourceList);
		ListMappingModel<Integer, String> listMappingModel = new ListMappingModel<Integer,String>(listModel, x -> x.toString());
		assertThat(listMappingModel.getObject().toString()).isEqualTo("[1, 2, 3]");
		sourceList.add(4);
		assertThat(listMappingModel.getObject().toString()).isEqualTo("[1, 2, 3]");
		listMappingModel.detach();
		assertThat(listMappingModel.getObject().toString()).isEqualTo("[1, 2, 3, 4]");
	}
}
