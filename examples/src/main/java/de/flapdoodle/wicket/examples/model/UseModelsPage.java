package de.flapdoodle.wicket.examples.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.Models;


public class UseModelsPage extends WebPage {
	
	public UseModelsPage() {
		ArrayList<String> source = new ArrayList<String>(Arrays.asList("first","second","third"));
		
		IModel<List<String>> model = Models.unmodifiable(source);
		
		Models.on(model).apply(new Function1<Integer, List<String>>() {
			@Override
			public Integer apply(List<String> value) {
				return null;
			}
		});
	}
}
