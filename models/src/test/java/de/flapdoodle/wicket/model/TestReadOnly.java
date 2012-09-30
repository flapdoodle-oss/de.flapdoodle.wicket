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
package de.flapdoodle.wicket.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Assert;
import org.junit.Test;


public class TestReadOnly {

	@Test
	public void modelFromList() {
		ArrayList<String> sourceList = new ArrayList<String>(Arrays.asList("Ha","Hu","Ha"));
		IModel<List<String>> model = Models.unmodifiable(sourceList);
		
		Assert.assertEquals("Hu", model.getObject().get(1));
		sourceList.remove(1);
		Assert.assertEquals("Ha", model.getObject().get(1));
		Assert.assertNotNull(exceptionFromRemoveFirst(model));
		Assert.assertNotNull(exceptionFromSetObject(model, new ArrayList<String>()));
	}

	@Test
	public void modelFromAListModel() {
		ArrayList<String> sourceList = new ArrayList<String>(Arrays.asList("Ha","Hu","Ha"));
		IModel<List<? extends String>> source = Model.ofList(sourceList);
		IModel<List<? extends String>> model = Models.readOnly(source);
		
		Assert.assertEquals("Hu", model.getObject().get(1));
		sourceList.remove(1);
		Assert.assertEquals("Ha", model.getObject().get(1));
		Assert.assertNull(exceptionFromRemoveFirst(model));
		Assert.assertNotNull(exceptionFromSetObject(model, new ArrayList<String>()));
	}
	
	@Test
	public void modelFromAListModelAsUnmodifiable() {
		ArrayList<String> sourceList = new ArrayList<String>(Arrays.asList("Ha","Hu","Ha"));
		IModel<List<? extends String>> source = Model.ofList(sourceList);
		IModel<List<String>> model = Models.unmodifiable(source);
		
		Assert.assertEquals("Hu", model.getObject().get(1));
		sourceList.remove(1);
		Assert.assertEquals("Ha", model.getObject().get(1));
		Assert.assertNotNull(exceptionFromRemoveFirst(model));
		Assert.assertNotNull(exceptionFromSetObject(model, new ArrayList<String>()));
	}
	
	public void listEmptyIfNull() {
		IModel<List<? extends String>> sourceModel=Model.<String>ofList(null);
		IModel<List<? extends String>> nullCheckedModel = Models.emptyIfNull(sourceModel);
		Assert.assertNotNull(nullCheckedModel.getObject());
		Assert.assertTrue(nullCheckedModel.getObject().isEmpty());
	}
	
	private Exception exceptionFromRemoveFirst(IModel<? extends List<?>> model) {
		Exception e=null;
		try {
			model.getObject().remove(0);
		} catch (Exception ex) {
			e=ex;
		}
		return e;
	}
	
	private <T> Exception exceptionFromSetObject(IModel<T> model, T value) {
		Exception e=null;
		try {
			model.setObject(value);
		} catch (Exception ex) {
			e=ex;
		}
		return e;
	}
}
