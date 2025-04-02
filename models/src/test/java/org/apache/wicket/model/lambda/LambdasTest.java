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
package org.apache.wicket.model.lambda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.Serializable;

import org.apache.wicket.core.util.lang.WicketObjects;
import org.apache.wicket.model.IModel;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class LambdasTest
{
    static
    {
        System.getProperties().setProperty("sun.io.serialization.extendedDebugInfo", "true");
    }
	@Test
	public void methodReference()
	{
		Person person = new Person();
		IModel<String> personNameModel = Lambdas.of(person::getName, person::setName);
		check(personNameModel);
	}

	@Test
	public void explicitLambdas()
	{
		Person person = new Person();
		IModel<String> personNameModel = Lambdas.of( //
			() -> person.getName(), (name) -> person.setName(name));
		check(personNameModel);
	}

	@Test
	public void equality()
	{
		Person person = new Person();
		final SerializableSupplier<String> getName = person::getName;
		final SerializableConsumer<String> setName = person::setName;
		IModel<String> personNameModel1 = Lambdas.of(getName, setName);
		IModel<String> personNameModel2 = Lambdas.of(getName, setName);
		assertEquals(personNameModel1, personNameModel2);
	}

	@Test
	public void hashcode()
	{
		Person person = new Person();
		final SerializableSupplier<String> getName = person::getName;
		final SerializableConsumer<String> setName = person::setName;
		IModel<String> personNameModel1 = Lambdas.of(getName, setName);
		IModel<String> personNameModel2 = Lambdas.of(getName, setName);
		assertEquals(personNameModel1.hashCode(), personNameModel2.hashCode());
	}

	@Test
	public void chainedLambda()
	{
		IModel<NonSerializablePerson> ldm = Lambdas.cachedReadOnly(NonSerializablePerson::new);
		IModel<String> nameModel = Lambdas.of(ldm, NonSerializablePerson::getName);

		ldm.getObject().setName("New name");
		assertEquals("New name", nameModel.getObject());

		// after a detach, we have a new object, so the name model should return null
		ldm.detach();

		assertNull(nameModel.getObject());
	}

	@Test
	public void chainedLambdas()
	{
		IModel<NonSerializablePerson> person = Lambdas.cachedReadOnly(NonSerializablePerson::new);
		IModel<NonSerializableOrganization> organization = Lambdas.of(person, NonSerializablePerson::getOrganization);

		person.getObject().getOrganization().setName("New name");

		SerializableFunction<NonSerializableOrganization, String> organizationGetName = NonSerializableOrganization::getName;

		IModel<?> nameModel1 = Lambdas.of(person, p -> p.getOrganization(), organizationGetName);
		IModel<?> nameModel2 = Lambdas.of(organization, NonSerializableOrganization::getName);
		IModel<?> nameModel3 = Lambdas.of(organization, organizationGetName);

		assertEquals("New name", nameModel1.getObject());
		assertEquals("New name", nameModel2.getObject());
		assertEquals("New name", nameModel3.getObject());
	}

	@Test
	public void chainedLambdasAreNullSafe()
	{
		IModel<NonSerializablePerson> person = Lambdas.readOnly(() -> null);
		IModel<NonSerializableOrganization> organization = Lambdas.of(person, NonSerializablePerson::getOrganization);

		SerializableFunction<NonSerializableOrganization, String> organizationGetName = NonSerializableOrganization::getName;

		IModel<?> nameModel1 = Lambdas.of(person, p -> p.getOrganization(), organizationGetName);
		IModel<?> nameModel2 = Lambdas.of(organization, NonSerializableOrganization::getName);
		IModel<?> nameModel3 = Lambdas.of(organization, organizationGetName);

		assertNull(nameModel1.getObject());
		assertNull(nameModel2.getObject());
		assertNull(nameModel3.getObject());
}

	private void check(IModel<String> personNameModel)
	{
		Assertions.assertThat(personNameModel.getObject()).isNull();

		final String personName = "new name";
		personNameModel.setObject(personName);
		Assertions.assertThat(personNameModel.getObject()).isEqualTo(personName);

		serialize(personNameModel, personName);
	}

	private void serialize(IModel<String> personNameModel, String personName)
	{
		final IModel<String> clone = cloneBySerial(personNameModel);
		Assertions.assertThat(clone).isInstanceOf(Lambdas.LambdaModel.class);
		Assertions.assertThat(clone.getObject()).isEqualTo(personName);
	}

    private <T> T cloneBySerial(T object) {
        return WicketObjects.cloneObject(object);
//        PlatformSerializer serializer = new PlatformSerializer("foo");
//        byte[] serialized = serializer.serialize(object);
//        assertThat("serialized result", serialized, is(not(nullValue())));
//        Object deserialized = serializer.deserialize(serialized);
//
//        return (T) deserialized;
    }

static class Person implements Serializable
{
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}


static class NonSerializablePerson
{
	private String name;
	private NonSerializableOrganization organization = new NonSerializableOrganization();

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public NonSerializableOrganization getOrganization()
	{
		return organization;
	}

	public void setOrganization(NonSerializableOrganization organization)
	{
		this.organization = organization;
	}
}

static class NonSerializableOrganization
{
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}

}
