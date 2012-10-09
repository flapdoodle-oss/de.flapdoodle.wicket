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
package de.flapdoodle.wicket.examples.debug;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

public class ModelNotDetachedPage extends WebPage {

	public ModelNotDetachedPage() {
		final IModel<String> nameModel=new LoadableDetachableModel<String>() {
			@Override
			protected String load() {
				return "Klaus";
			}
		};
		
		IModel<String> wrongProxyModel=new AbstractReadOnlyModel<String>() {
			@Override
			public String getObject() {
				return nameModel.getObject();
			}
		};
		
		add(new Label("name",wrongProxyModel));
		
		add(new Link<Integer>("link",Model.of(0)) {
			@Override
			public void onClick() {
				setModelObject(getModelObject()+1);
			}
		});
	}
}
