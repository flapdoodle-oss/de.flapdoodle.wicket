package de.flapdoodle.wicket.serialize.java;

import org.apache.wicket.WicketRuntimeException;

public interface ISerializableCheck {

	void inspect(Object obj, IStackTracePrinter printer) throws WicketRuntimeException;

}
