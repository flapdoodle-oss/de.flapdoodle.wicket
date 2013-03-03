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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_value == null)
				? 0
				: _value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Simple other = (Simple) obj;
		if (_value == null) {
			if (other._value != null)
				return false;
		} else if (!_value.equals(other._value))
			return false;
		return true;
	}
	
	
}
