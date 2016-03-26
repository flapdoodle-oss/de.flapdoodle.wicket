package de.flapdoodle.wicket.model.transformation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

import de.flapdoodle.wicket.model.AbstractModelTest;
import de.flapdoodle.wicket.model.ModelProxy;

public class ObjectAwareTransformatorTest extends AbstractModelTest {

	@Test
	public void modelMustBeSerializable() {
		Model<Integer> intModel = Model.of(12);
		IModel<String> model = new ObjectAwareTransformator<Integer, String>(intModel, String.class, Object::toString, Integer::valueOf);
		model.setObject("13");
		model.getObject();
		model.detach();
		serialize(model);
	}
	
	@Test
	public void detachMustPropagate() {
		Model<Integer> intModel = Model.of(12);
		ModelProxy<Integer> proxy = new ModelProxy<>(intModel);
		IModel<String> model = new ObjectAwareTransformator<Integer, String>(proxy, String.class, Object::toString, Integer::valueOf);
		
		assertEquals(0,proxy.detachCalled());
		model.detach();
		assertEquals(1,proxy.detachCalled());
	}
	
	@Test
	public void getObjectMustMatchPropertyValue() {
		Model<Integer> intModel = Model.of(12);
		IModel<String> model = new ObjectAwareTransformator<Integer, String>(intModel, String.class, Object::toString, Integer::valueOf);		
		assertEquals("12",model.getObject());
	}
	
	@Test
	public void setObjectMustSetPropertyValue() {
		Model<Integer> intModel = new Model<>();
		IModel<String> model = new ObjectAwareTransformator<Integer, String>(intModel, String.class, Object::toString, Integer::valueOf);		
		
		assertNull(intModel.getObject());
		model.setObject("1231131");
		assertEquals(Integer.valueOf(1231131), intModel.getObject());
	}
}
