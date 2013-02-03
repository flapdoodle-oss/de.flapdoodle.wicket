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
