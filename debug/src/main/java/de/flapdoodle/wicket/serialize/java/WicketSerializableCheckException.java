package de.flapdoodle.wicket.serialize.java;

import org.apache.wicket.WicketRuntimeException;

/**
 * Exception that is thrown when a non-serializable object was found.
 */
public final class WicketSerializableCheckException extends WicketRuntimeException
{
	private static final long serialVersionUID = 1L;

	public WicketSerializableCheckException(String message)
	{
		super(message);
	}
}