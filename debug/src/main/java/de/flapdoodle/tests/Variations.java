/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Jan Bernitt <unknown@email.de>
 *
 * with contributions from
 * 	nobody yet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
		private S _nextValue;

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
			if (_nextValue==null) _nextValue=_next.get();
			IJoinedSample<A, B, S> ret = Sample.of(_iterator.next(), _nextValue);
			if (!_iterator.hasNext()) {
				if (_next.hasNext()) {
					_iterator=_variation.iterator();
					_nextValue=null;
				}
			}
			return ret;
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
