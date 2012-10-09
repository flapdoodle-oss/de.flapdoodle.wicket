package de.flapdoodle.wicket.serialize.java;

public interface IStackTracePrinter {
	
	String printStack(Class<?> type,String message);
	
}
