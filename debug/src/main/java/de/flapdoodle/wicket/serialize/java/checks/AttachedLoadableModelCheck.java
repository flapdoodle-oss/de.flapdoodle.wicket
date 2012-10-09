package de.flapdoodle.wicket.serialize.java.checks;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.io.WicketSerializeableException;

import de.flapdoodle.wicket.serialize.java.ISerializableCheck;
import de.flapdoodle.wicket.serialize.java.IStackTracePrinter;

public class AttachedLoadableModelCheck extends AbstractSerializableCheck {

	@Override
	public void inspect(Object obj, IStackTracePrinter printer)
			throws WicketRuntimeException {
		
		if (obj instanceof LoadableDetachableModel) {
			LoadableDetachableModel ldm=(LoadableDetachableModel) obj;
			if (ldm.isAttached()) {
				failed(printer, obj, "this "+LoadableDetachableModel.class.getSimpleName()+" is NOT detached");
			}
		}
	}

}
