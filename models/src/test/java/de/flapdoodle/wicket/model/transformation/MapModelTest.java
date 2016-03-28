package de.flapdoodle.wicket.model.transformation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

import de.flapdoodle.wicket.model.AbstractModelTest;
import de.flapdoodle.wicket.model.ModelProxy;

public class MapModelTest extends AbstractModelTest {

	@Test
	public void emptyCollectionShouldGiveEmptyMap() {
		MapModel<String, String> model = new MapModel<String,String>(Model.ofList(new ArrayList<String>()), x->x);
		assertTrue(model.getObject().isEmpty());
	}
	
	@Test
	public void detachMustPropagate() {
		IModel<List<? extends String>> src = Model.ofList(new ArrayList<String>());
		ModelProxy<List<? extends String>> proxy = new ModelProxy<>(src);
		MapModel<String, String> model = new MapModel<String,String>(proxy, x->x);
		
		assertEquals(0,proxy.detachCalled());
		model.getObject();
		model.detach();
		assertEquals(1,proxy.detachCalled());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void keyCollisionMustThrowException() {
		List<String> src=new ArrayList<>();
		src.add("a");
		src.add("b");
		src.add("a");
		MapModel.asMap(src, x->x);
	}
}
