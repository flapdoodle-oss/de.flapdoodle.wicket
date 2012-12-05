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
package de.flapdoodle.wicket.examples.requests;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;


public class BadPanel extends Panel {

	public BadPanel(String id) {
		super(id);
		
		add(new Label("label",new LoadableDetachableModel<String>() {
			@Override
			protected String load() {
				return "Fuhh";
			}
		}));
		
		add(new AjaxLink<Void>("ajaxLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				badThingsHappenSometimes("ajaxLink clicked");
			}
		});
		
		add(new Link<Void>("link") {
			@Override
			public void onClick() {
				throw new RuntimeException("bad link");
			}
		});
	}
	
	private void badThingsHappenSometimes(String message) {
		throw new BadThingHappenException(message);
	}
}
