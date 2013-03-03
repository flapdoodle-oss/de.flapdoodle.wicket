package de.flapdoodle.tests;

import de.flapdoodle.tests.sample.ISample;


public interface IGenerator<T,S extends ISample<T>> {

	boolean hasNext();

	S get();
}
