package de.flapdoodle.wicket.serialize.java.checks;

import org.apache.wicket.WicketRuntimeException;

import de.flapdoodle.wicket.serialize.java.ISerializableCheck;
import de.flapdoodle.wicket.serialize.java.IStackTracePrinter;

public class SerializableChecks extends AbstractSerializableCheck {

	private final ISerializableCheck[] checks;

	public SerializableChecks(ISerializableCheck...checks) {
		this.checks = checks;
	}
	
	@Override
	public void inspect(Object obj, IStackTracePrinter stackTracePrinter)
			throws WicketRuntimeException {
		for (ISerializableCheck check : checks) {
			check.inspect(obj, stackTracePrinter);
		}
	}

}
