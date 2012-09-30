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
package de.flapdoodle.wicket.examples;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.Models;


public class TestReadme {

	// simple model transformation
	public IModel<Integer> createSumModel(IModel<List<Integer>> source) {
		return Models.on(source).apply(new Function1<Integer, List<Integer>>() {
			@Override
			public Integer apply(List<Integer> values) {
				int sum=0;
				for (Integer v : values!=null ? values : new ArrayList<Integer>()) {
					if (v!=null) sum=sum+v;
				}
				return sum;
			}
		});
	}
}
