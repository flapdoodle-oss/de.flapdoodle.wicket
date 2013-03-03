package de.flapdoodle.tests.sample;


public interface IJoinedSample<A,B,N extends ISample<B>> extends ISample<A> {
	N next();
}
