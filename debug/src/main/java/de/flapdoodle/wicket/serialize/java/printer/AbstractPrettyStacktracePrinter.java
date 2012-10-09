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
package de.flapdoodle.wicket.serialize.java.printer;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;

import de.flapdoodle.wicket.serialize.java.IStackTracePrinter;

public abstract class AbstractPrettyStacktracePrinter implements IStackTracePrinter {

	protected String toPrettyPrintedStack(List<TraceSlot> localTraceStack, String type, String message) {
		StringBuilder result = new StringBuilder();
		StringBuilder spaces = new StringBuilder();
		result.append("Unable to serialize class: ");
		result.append(type);
		result.append("\nField hierarchy is:");
		for (Iterator<TraceSlot> i = localTraceStack.listIterator(); i.hasNext();)
		{
			spaces.append("  ");
			TraceSlot slot = i.next();
			result.append("\n").append(spaces).append(slot.fieldDescription);
			result.append(" [class=").append(slot.object.getClass().getName());
			if (slot.object.getClass().isAnonymousClass()) {
				result.append(", superclass=").append(slot.object.getClass().getSuperclass().getName());
			}
			if (slot.object instanceof Component)
			{
				Component component = (Component)slot.object;
				result.append(", path=").append(component.getPath());
			}
			result.append("]");
		}
		result.append(" <----- "+message);
		return result.toString();
	}

}
