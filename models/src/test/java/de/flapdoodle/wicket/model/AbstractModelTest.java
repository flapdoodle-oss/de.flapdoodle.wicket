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
package de.flapdoodle.wicket.model;

import org.apache.wicket.core.util.lang.WicketObjects;
import org.apache.wicket.model.IModel;
import org.assertj.core.api.Assertions;

public abstract class AbstractModelTest {

    static
    {
        System.getProperties().setProperty("sun.io.serialization.extendedDebugInfo", "true");
    }
    
	protected <T> void serialize(IModel<T> model)
	{
		final IModel<T> clone = cloneBySerial(model);
		Assertions.assertThat(clone).isInstanceOf(model.getClass());
		Assertions.assertThat(clone.getObject()).isEqualTo(model.getObject());
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


}
