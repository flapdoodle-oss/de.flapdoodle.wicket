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
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.functions.Function3;
import de.flapdoodle.wicket.model.Models;


public class TestReadme {

/*
Any model used as source model for a model transformation are detached if the transformating model ist detached. Any transformation evaluation is only done
once as in LoadableDetachedModel.

#### simple model transformation
*/
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

/*
#### complex model transformation setup
*/
	public <T> IModel<List<T>> subListModel(IModel<List<T>> source,IModel<Integer> offsetModel, IModel<Integer> sizeModel) {
		IModel<List<T>> emptyIfNull = Models.emptyIfNull(source);
		
		return Models.on(emptyIfNull,offsetModel,sizeModel).apply(new Function3<List<T>, List<T>, Integer, Integer>() {
			@Override
			public List<T> apply(List<T> list, Integer offset, Integer size) {
				int startIdx=Math.min(list.size(), offset);
				int lastIdx=Math.min(list.size(),offset+size);
				return list.subList(startIdx, lastIdx);
			}
		});
	}
	
/*
#### unmodifiable and read only

A model is read only if setObject can not be used. But if you can change the content of the
model value, the model is not unmodifiable. Its not the best idea to change a model value so there
are some functions to prevent this. 
*/
	public void unmodifiableAndReadOnly() {
		
	List<Integer> source=new ArrayList<Integer>(Arrays.asList(1,2,3));
	IModel<List<Integer>> unmodifiableListModel = Models.unmodifiable(source);
	
	IModel<List<Integer>> modifiableListModel = Model.ofList(Arrays.asList(1,2,3));
	IModel<List<Integer>> asUnmodifiableListModel = Models.unmodifiable(modifiableListModel);
	IModel<List<Integer>> readOnlyListModel = Models.readOnly(modifiableListModel);
	
	}
}
