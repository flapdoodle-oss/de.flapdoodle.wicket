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
package de.flapdoodle.wicket.examples.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.Models;


public class UseModelsPage extends WebPage {
	
	public UseModelsPage() {
		final ArrayList<String> source = new ArrayList<String>(Arrays.asList("first","second","third"));
		
		IModel<List<String>> listModel = Models.unmodifiable(source);
		
		IModel<List<String>> emptyIfNullListModel = Models.emptyIfNull(listModel);
		
		IModel<Integer> listSizeModel = Models.on(emptyIfNullListModel).apply(new Function1<Integer, List<String>>() {
			@Override
			public Integer apply(List<String> value) {
				return value.size();
			}
		});
		
		IModel<String> firstEntryModel = Models.on(emptyIfNullListModel).apply(new Function1<String, List<String>>() {
			@Override
			public String apply(List<String> value) {
				return !value.isEmpty() ? value.get(0) : null;
			}
		});
		

		final WebMarkupContainer ajaxBorder=new WebMarkupContainer("ajaxBorder");
		ajaxBorder.setOutputMarkupId(true);
		
		ajaxBorder.add(new ListView<String>("list",emptyIfNullListModel) {
			@Override
			protected void populateItem(ListItem<String> item) {
				item.add(new Label("label",item.getModel()));
				item.add(new AjaxLink<String>("link",Model.of(item.getModelObject())) {
					@Override
					public void onClick(AjaxRequestTarget target) {
						source.remove(getModelObject());
						target.add(ajaxBorder);
					}
				});
			}
		});
		
		ajaxBorder.add(new Label("size",listSizeModel));
		ajaxBorder.add(new Label("first",firstEntryModel));
		add(ajaxBorder);
	}
}
