package de.flapdoodle.wicket.model;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.apache.wicket.core.util.lang.WicketObjects;
import org.apache.wicket.model.IModel;

public abstract class AbstractModelTest {

    static
    {
        System.getProperties().setProperty("sun.io.serialization.extendedDebugInfo", "true");
    }
    
	protected <T> void serialize(IModel<T> model)
	{
		final IModel<T> clone = cloneBySerial(model);
		assertThat(clone, is(instanceOf(model.getClass())));
		assertThat(clone.getObject(), is(model.getObject()));
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
