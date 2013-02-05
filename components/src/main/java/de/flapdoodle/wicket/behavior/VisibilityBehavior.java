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
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.model.IModel;

/**
 * changes visibility of a component based on a model value 
 * @author mosmann
 */
public class VisibilityBehavior extends Behavior {

	private final IModel<? extends Boolean> _model;

	public VisibilityBehavior(IModel<? extends Boolean> model) {
		_model = model;
	}

	@Override
	public void onConfigure(Component component) {
		super.onConfigure(component);

		Boolean visible = _model.getObject();
		component.setVisible(visible != null
				? visible
				: false);
	}
	
	@Override
	public void detach(Component component) {
		super.detach(component);
		_model.detach();
	}
}
