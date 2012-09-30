package de.flapdoodle.usage.model;

import java.util.List;

import org.apache.wicket.model.IModel;

import de.flapdoodle.functions.Function1;
import de.flapdoodle.wicket.model.Models;


public final class UseModels {
	
	public static IModel<Integer> getSizeOfListAsModel(IModel<List<String>> listModel) {
		
		return Models.on(listModel).apply(new Function1<Integer, List<String>>() {
			@Override
			public Integer apply(List<String> value) {
				return value!=null ? value.size() : 0;
			}
		});
		
	}
}
