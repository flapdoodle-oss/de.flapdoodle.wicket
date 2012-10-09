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
