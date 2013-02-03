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
