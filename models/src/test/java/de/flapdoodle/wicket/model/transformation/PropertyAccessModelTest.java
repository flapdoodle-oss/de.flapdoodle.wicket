package de.flapdoodle.wicket.model.transformation;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.util.UUID;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

import de.flapdoodle.wicket.model.AbstractModelTest;
import de.flapdoodle.wicket.model.ModelProxy;

public class PropertyAccessModelTest extends AbstractModelTest {

	@Test
	public void modelMustBeSerializable() {
		Model<Foo> fooModel = Model.of(new Foo());
		IModel<String> propertyModel = new PropertyAccessModel<>(fooModel, String.class, Foo::getText, Foo::setText);
		propertyModel.setObject("Fooo");
		propertyModel.getObject();
		propertyModel.detach();
		serialize(propertyModel);
	}
	
	@Test
	public void detachMustPropagate() {
		Model<Foo> fooModel = Model.of(new Foo());
		ModelProxy<Foo> proxy = new ModelProxy<>(fooModel);
		IModel<String> propertyModel = new PropertyAccessModel<>(proxy, String.class, Foo::getText, Foo::setText);
		
		assertEquals(0,proxy.detachCalled());
		propertyModel.detach();
		assertEquals(1,proxy.detachCalled());
	}
	
	@Test
	public void getObjectMustMatchPropertyValue() {
		Foo foo = new Foo();
		foo.setText(randomString());
		Model<Foo> fooModel = Model.of(foo);
		IModel<String> propertyModel = new PropertyAccessModel<>(fooModel, String.class, Foo::getText, Foo::setText);
		
		assertEquals(foo.getText(),propertyModel.getObject());
	}
	
	@Test
	public void setObjectMustSetPropertyValue() {
		Foo foo = new Foo();
		Model<Foo> fooModel = Model.of(foo);
		IModel<String> propertyModel = new PropertyAccessModel<>(fooModel, String.class, Foo::getText, Foo::setText);
		
		assertEquals(null, foo.getText());
		String randomString = randomString();
		propertyModel.setObject(randomString);
		assertEquals(randomString, foo.getText());
	}

	private static String randomString() {
		return UUID.randomUUID().toString();
	}
	
	static class Foo implements Serializable {
		String text;
		
		public String getText() {
			return text;
		}
		
		public void setText(String text) {
			this.text = text;
		}
	}
}
