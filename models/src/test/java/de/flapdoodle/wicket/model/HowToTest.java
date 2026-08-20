/*
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

import de.flapdoodle.commons.testdoc.Recorder;
import de.flapdoodle.commons.testdoc.Recording;
import de.flapdoodle.commons.testdoc.TabSize;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HowToTest {
	@RegisterExtension
	public static Recording recording = Recorder.with("HowToUseModels.md", TabSize.spaces(2));

	/*
Any model used as source model for a model transformation are detached if the transformating model ist detached. Any transformation evaluation is only done
once as in LoadableDetachedModel.

#### simple model transformation
*/
	@Test
	void sumModel() {
		recording.begin();
		IModel<List<Integer>> listModel = Model.ofList(List.of(1, 3, 7));
		IReadOnlyModel<Integer> sumModel = Models.on(listModel)
			.apply(list -> list.stream().mapToInt(Integer::intValue).sum());

		assertThat(sumModel.getObject()).isEqualTo(11);
		recording.end();
	}

	/*
	#### complex model transformation setup
	*/
	@Test
	void subList() {
		recording.begin();
		IModel<List<String>> source=Model.ofList(List.of("A", "B", "C", "D", "E"));
		IModel<Integer> offsetModel=Model.of(1);
		IModel<Integer> sizeModel=Model.of(2);

		IModel<List<String>> emptyIfNull = Models.emptyIfNull(source);

		IReadOnlyModel<List<String>> testee = Models.on(emptyIfNull, offsetModel, sizeModel)
			.apply((List<String> list, Integer offset, Integer size) -> {
				int startIdx = Math.min(list.size(), offset);
				int lastIdx = Math.min(list.size(), offset + size);
				return list.subList(startIdx, lastIdx);
			});

		assertThat(testee.getObject()).containsExactlyInAnyOrder("B", "C");
		recording.end();
	}


	/*
	#### unmodifiable and read only

	A model is read only if setObject can not be used. But if you can change the content of the
	model value, the model is not unmodifiable. Its not the best idea to change a model value so there
	are some functions to prevent this.
	*/
	@Test
	void unmodifiable() {
		recording.begin();
		List<Integer> source=new ArrayList<Integer>(Arrays.asList(1,2,3));
		IModel<? extends List<? extends Integer>> unmodifiableListModel = Models.unmodifiable(source);

		IModel<List<Integer>> modifiableListModel = Model.ofList(Arrays.asList(1, 2, 3));
		IModel<List<Integer>> asUnmodifiableListModel = Models.unmodifiable(modifiableListModel);
		IModel<List<Integer>> readOnlyListModel = Models.readOnly(modifiableListModel);
		recording.end();
	}
}
