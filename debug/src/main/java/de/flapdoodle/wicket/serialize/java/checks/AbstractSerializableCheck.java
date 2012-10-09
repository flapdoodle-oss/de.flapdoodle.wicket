package de.flapdoodle.wicket.serialize.java.checks;

import de.flapdoodle.wicket.serialize.java.ISerializableCheck;
import de.flapdoodle.wicket.serialize.java.IStackTracePrinter;
import de.flapdoodle.wicket.serialize.java.WicketSerializableCheckException;

public abstract class AbstractSerializableCheck implements ISerializableCheck {

	protected void failed(IStackTracePrinter printer, Object obj, String message) {
		throw new WicketSerializableCheckException(printer.printStack(obj.getClass(), message));
	}

}
