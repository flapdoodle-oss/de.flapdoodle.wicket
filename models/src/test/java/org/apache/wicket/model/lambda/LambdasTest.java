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

import org.apache.wicket.core.util.lang.WicketObjects;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LambdaModel;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

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
		IModel<String> personNameModel = LambdaModel.of(person::getName, person::setName);
		check(personNameModel);
	}

	@Test
	public void explicitLambdas()
	{
		Person person = new Person();
		IModel<String> personNameModel = LambdaModel.of( //
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
		assertThat(personNameModel2).isEqualTo(personNameModel1);
	}

	@Test
	public void hashcode()
	{
		Person person = new Person();
		final SerializableSupplier<String> getName = person::getName;
		final SerializableConsumer<String> setName = person::setName;
		IModel<String> personNameModel1 = Lambdas.of(getName, setName);
		IModel<String> personNameModel2 = Lambdas.of(getName, setName);
		Object expected = personNameModel1.hashCode();
		assertThat((Object) personNameModel2.hashCode()).isEqualTo(expected);
	}

	@Test
	public void chainedLambda()
	{
		IModel<NonSerializablePerson> ldm = Lambdas.cachedReadOnly(NonSerializablePerson::new);
		IModel<String> nameModel = Lambdas.of(ldm, NonSerializablePerson::getName);

		ldm.getObject().setName("New name");
		assertThat((Object) nameModel.getObject()).isEqualTo("New name");

		// after a detach, we have a new object, so the name model should return null
		ldm.detach();

		assertThat((Object) nameModel.getObject()).isNull();
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

		assertThat(nameModel1.getObject()).isEqualTo("New name");
		assertThat(nameModel2.getObject()).isEqualTo("New name");
		assertThat(nameModel3.getObject()).isEqualTo("New name");
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

		assertThat(nameModel1.getObject()).isNull();
		assertThat(nameModel2.getObject()).isNull();
		assertThat(nameModel3.getObject()).isNull();
	}

	private void check(IModel<String> personNameModel)
	{
		assertThat(personNameModel.getObject()).isNull();

		final String personName = "new name";
		personNameModel.setObject(personName);
		assertThat(personNameModel.getObject()).isEqualTo(personName);

		serialize(personNameModel, personName);
	}

	private void serialize(IModel<String> personNameModel, String personName)
	{
		final IModel<String> clone = cloneBySerial(personNameModel);
		assertThat(clone).isInstanceOf(LambdaModel.class);
		assertThat(clone.getObject()).isEqualTo(personName);
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
