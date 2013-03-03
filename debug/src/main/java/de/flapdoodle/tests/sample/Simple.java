package de.flapdoodle.tests.sample;

public class Simple<T> implements ISample<T> {

	private final T _value;

	public Simple(T value) {
		_value = value;
	}
	
	public T get() {
		return _value;
	}
	
	public static <T> Simple<T> of(T value) {
		return new Simple<T>(value);
	}
}
