/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Jan Bernitt <unknown@email.de>
 *
 * with contributions from
 * 	nobody yet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.wicket.serialize.java;

import de.flapdoodle.wicket.serialize.java.checks.AttachedLoadableModelCheck;
import de.flapdoodle.wicket.serialize.java.checks.SerializableChecks;
import de.flapdoodle.wicket.serialize.java.checks.SerializingNotAllowedForTypesCheck;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled("serialization changed")
public class TestPreSerializeChecker {

	@Test
	public void beanIsNotSerializable() throws IOException {
		assertThatThrownBy(() ->	checkWith(new SimpleBean(), checks()))
			.isInstanceOf(WicketSerializableCheckException.class);
	}

	@Test
	public void beanIsSerializable() throws IOException {
		checkWith(new SerialBean(), checks());
	}

	@Test
	public void customTypeIsNotSerializable() throws IOException {
		assertThatThrownBy(() ->	checkWith(new CouldBeADomainType(), checks()))
			.isInstanceOf(WicketSerializableCheckException.class);
	}
	
	@Test
	public void notDetachedLDM() throws IOException {
		IModel<String> model = new LoadableDetachableModel<String>() {
			@Override
			protected String load() {
				return "dont care about the value here";
			}
		};
		assertThat(model.getObject()).isNotNull();
		assertThatThrownBy(() ->	checkWith(new CouldBeAPanel("panel", model), checks()))
			.isInstanceOf(WicketSerializableCheckException.class);
	}

	private ISerializableCheck checks() {
		return new SerializableChecks(new AttachedLoadableModelCheck(),
				new SerializingNotAllowedForTypesCheck(DoNotSerializeMe.class));
	}

	private void checkWith(Object object, ISerializableCheck check)
			throws IOException {
		try(PreSerializeChecker checker = new PreSerializeChecker(check)) {
			checker.writeObject(object);
		}
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
