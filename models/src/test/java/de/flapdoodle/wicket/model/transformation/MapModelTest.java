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
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.flapdoodle.wicket.model.AbstractModelTest;
import de.flapdoodle.wicket.model.ModelProxy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MapModelTest extends AbstractModelTest {

	@Test
	public void emptyCollectionShouldGiveEmptyMap() {
		MapModel<String, String> model = new MapModel<String,String>(Model.ofList(new ArrayList<String>()), x->x);
		assertThat(model.getObject().isEmpty()).isTrue();
	}

	@Test
	public void detachMustPropagate() {
		IModel<? extends List<? extends String>> src = Model.ofList(new ArrayList<String>());
		ModelProxy<? extends List<? extends String>> proxy = new ModelProxy<>(src);
		MapModel<String, String> model = new MapModel<String,String>(proxy, x->x);

		assertThat((Object) proxy.detachCalled()).isEqualTo(0);
		model.getObject();
		model.detach();
		assertThat((Object) proxy.detachCalled()).isEqualTo(1);
	}

	@Test
	public void keyCollisionMustThrowException() {
		List<String> src=new ArrayList<>();
		src.add("a");
		src.add("b");
		src.add("a");
		assertThatThrownBy(() -> MapModel.asMap(src, x->x))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
