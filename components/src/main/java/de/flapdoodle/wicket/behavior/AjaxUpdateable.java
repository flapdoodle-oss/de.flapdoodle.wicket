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
package de.flapdoodle.wicket.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.IAjaxRegionMarkupIdProvider;
import org.apache.wicket.behavior.Behavior;

public class AjaxUpdateable extends Behavior implements IAjaxRegionMarkupIdProvider {

	private final String _tag;

	public AjaxUpdateable() {
		this("div");
	}

	public AjaxUpdateable(String tag) {
		_tag = tag;
	}
	
	@Override
	public void bind(Component component) {
		super.bind(component);
		component.setOutputMarkupPlaceholderTag(true);
	}

	@Override
	public void beforeRender(Component component) {
		component.getResponse().write("<" + _tag + " id=" + getAjaxRegionMarkupId(component) + ">");
		super.beforeRender(component);
	}

	@Override
	public void afterRender(Component component) {
		super.afterRender(component);
		component.getResponse().write("</" + _tag + ">");
	}

	@Override
	public String getAjaxRegionMarkupId(Component component) {
		return component.getMarkupId() + "_border";
	}
}
