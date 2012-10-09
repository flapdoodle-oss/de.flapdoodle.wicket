package de.flapdoodle.wicket.serialize.java;

import java.io.IOException;
import java.io.Serializable;

import junit.framework.Assert;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.junit.Test;

import de.flapdoodle.wicket.serialize.java.checks.AttachedLoadableModelCheck;
import de.flapdoodle.wicket.serialize.java.checks.SerializableChecks;
import de.flapdoodle.wicket.serialize.java.checks.SerializingNotAllowedForTypesCheck;

public class TestPreSerializeChecker {

	@Test(expected = WicketSerializableCheckException.class)
	public void beanIsNotSerializable() throws IOException {
		checkWith(new SimpleBean(), checks());
	}

	@Test
	public void beanIsSerializable() throws IOException {
		checkWith(new SerialBean(), checks());
	}

	@Test(expected = WicketSerializableCheckException.class)
	public void customTypeIsNotSerializable() throws IOException {
		checkWith(new CouldBeADomainType(), checks());
	}
	
	@Test(expected = WicketSerializableCheckException.class)
	public void notDetachedLDM() throws IOException {
		IModel<String> model = new LoadableDetachableModel<String>() {
			@Override
			protected String load() {
				return "dont care about the value here";
			}
		};
		Assert.assertNotNull(model.getObject());
		checkWith(new CouldBeAPanel("panel", model), checks());
	}

	private ISerializableCheck checks() {
		return new SerializableChecks(new AttachedLoadableModelCheck(),
				new SerializingNotAllowedForTypesCheck(DoNotSerializeMe.class));
	}

	private void checkWith(Object object, ISerializableCheck check)
			throws IOException {
		new PreSerializeChecker(check).writeObject(object);
	}

	static class SimpleBean {

	}

	static class SerialBean implements Serializable {
		String name = "killer";
	}

	static class CouldBeADomainType implements Serializable,DoNotSerializeMe {
		
	}
	
	static class CouldBeAPanel implements Serializable {
		final Object someValue;

		public CouldBeAPanel(String id, Object someValue) {
			this.someValue = someValue;
		}
	}

	static interface DoNotSerializeMe {

	}
}
