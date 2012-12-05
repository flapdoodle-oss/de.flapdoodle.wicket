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
