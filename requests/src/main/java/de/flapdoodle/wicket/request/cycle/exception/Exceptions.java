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
package de.flapdoodle.wicket.request.cycle.exception;

public class Exceptions {

	private Exceptions() {
		throw new IllegalAccessError("no instance");
	}

	public static Throwable rootCause(Throwable ex) {
		Throwable cause = ex.getCause();
		if (cause != null) {
			return rootCause(cause);
		}
		return ex;
	}

	public static <T extends Throwable> T anyCauseOf(Throwable ex, Class<T> type) {
		if (type.isInstance(ex)) {
			return (T) ex;
		}

		Throwable cause = ex.getCause();
		if (cause != null) {
			return anyCauseOf(cause, type);
		}
		return null;
	}

	public static Throwable firstCauseNotOf(Throwable ex, Class<?>... types) {
		boolean typeMatched = false;

		for (Class<?> type : types) {
			if (type.isInstance(ex)) {
				typeMatched = true;
				break;
			}
		}

		if (!typeMatched) {
			return ex;
		}

		Throwable cause = ex.getCause();
		if (cause != null) {
			return firstCauseNotOf(cause, types);
		}
		return null;
	}
}
