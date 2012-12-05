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
