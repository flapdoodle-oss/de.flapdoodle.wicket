/**
 * Copyright (C) 2011
 * Michael Mosmann <michael@mosmann.de>
 * Jan Bernitt <unknown@email.de>
 * 
 * with contributions from
 * nobody yet
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.wicket.examples.components.visibility;

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
import de.flapdoodle.wicket.behavior.AjaxUpdateable;
import de.flapdoodle.wicket.behavior.VisibilityBehavior;
import de.flapdoodle.wicket.model.Models;

public class UseVisibilityBehaviorPage extends WebPage {

	public UseVisibilityBehaviorPage() {

		Model<Boolean> visibilityModel = Model.of(true);

		final SimplePanel label = new SimplePanel("panel");
		label.add(new VisibilityBehavior(visibilityModel));
		label.add(new AjaxUpdateable());
		add(label);

		add(new AjaxLink<Boolean>("link", visibilityModel) {

			@Override
			public void onClick(AjaxRequestTarget target) {
				setModelObject(!getModelObject());
				target.add(label);
			}
		});
	}
}
