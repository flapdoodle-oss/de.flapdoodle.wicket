package de.flapdoodle.tests;

import java.util.Iterator;

import de.flapdoodle.tests.sample.IJoinedSample;
import de.flapdoodle.tests.sample.ISample;
import de.flapdoodle.tests.sample.Sample;
import de.flapdoodle.tests.sample.Simple;
import de.flapdoodle.tests.sampler.IVariation;


public class Variations {

	private Variations() {
		// no instance
	}
	
	public static <T> IGenerator<T,ISample<T>> of(IVariation<T> variation) {
		return new First<T>(variation);
	}
	
	public static <A,B,S extends ISample<B>,N extends IGenerator<B,S>> IGenerator<A,IJoinedSample<A, B, S>> of(IVariation<A> variation,N next) {
		return new Join<A, B, S, N>(variation,next);
	}
	
	public static class Join<A,B,S extends ISample<B>,N extends IGenerator<B,S>> implements IGenerator<A,IJoinedSample<A, B, S>> {

		private final IVariation<A> _variation;
		private final N _next;
		private Iterator<A> _iterator;

		public Join(IVariation<A> variation, N next) {
			_variation = variation;
			_next = next;
			
			_iterator = _variation.iterator();
		}
		
		@Override
		public boolean hasNext() {
			return _iterator.hasNext() || _next.hasNext();
		}
		
		public IJoinedSample<A, B, S> get() {
			if (!_iterator.hasNext()) {
				if (_next.hasNext()) _iterator=_variation.iterator();
			}
			return Sample.of(_iterator.next(), _next.get());
		}
	}
	
	static class First<T> implements IGenerator<T,ISample<T>> {

		private Iterator<T> _iterator;

		public First(IVariation<T> variation) {
			_iterator = variation.iterator();
		}
		
		@Override
		public boolean hasNext() {
			return _iterator.hasNext();
		}
		
		@Override
		public ISample<T> get() {
			return Simple.of(_iterator.next());
		}
	}
}
