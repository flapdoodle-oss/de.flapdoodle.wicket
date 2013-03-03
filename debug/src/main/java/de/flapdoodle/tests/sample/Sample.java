package de.flapdoodle.tests.sample;

public class Sample<A, B, S extends ISample<B>> extends Simple<A> implements IJoinedSample<A, B, S> {

	private final S _next;

	public Sample(A value, S next) {
		super(value);
		_next = next;
	}

	public S next() {
		return _next;
	}

	public static <A,B,N extends ISample<B>> IJoinedSample<A, B, N> of(A a,N next) {
		return new Sample<A, B, N>(a, next);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((_next == null)
				? 0
				: _next.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sample other = (Sample) obj;
		if (_next == null) {
			if (other._next != null)
				return false;
		} else if (!_next.equals(other._next))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return super.toString()+"-"+_next.toString();
	}
	
}
