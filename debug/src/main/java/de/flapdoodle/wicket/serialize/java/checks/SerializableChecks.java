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
