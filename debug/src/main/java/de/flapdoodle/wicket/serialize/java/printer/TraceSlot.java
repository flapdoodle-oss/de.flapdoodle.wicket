package de.flapdoodle.wicket.serialize.java.printer;

/** Holds information about the field and the resulting object being traced. */
public final class TraceSlot
{
	public final String fieldDescription;

	public final Object object;

	public TraceSlot(Object object, String fieldDescription)
	{
		super();
		this.object = object;
		this.fieldDescription = fieldDescription;
	}

	@Override
	public String toString()
	{
		return object.getClass() + " - " + fieldDescription;
	}
}