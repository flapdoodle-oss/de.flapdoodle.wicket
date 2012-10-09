package de.flapdoodle.wicket.serialize.java.checks;

import org.apache.wicket.WicketRuntimeException;

import de.flapdoodle.wicket.serialize.java.IStackTracePrinter;

public class SerializingNotAllowedForTypesCheck extends AbstractSerializableCheck {

	private final Class<?>[] types;

	public SerializingNotAllowedForTypesCheck(Class<?>...types) {
		this.types = types;
	}
	
	@Override
	public void inspect(Object obj, IStackTracePrinter printer)
			throws WicketRuntimeException {
		for (Class<?> type : types) {
			if (type.isInstance(obj)) {
				failed(printer,obj," is of type "+type+" which should not be serialized");
			}
		}
	}

}
