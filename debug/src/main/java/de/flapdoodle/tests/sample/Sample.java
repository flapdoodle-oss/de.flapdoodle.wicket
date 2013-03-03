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
}
