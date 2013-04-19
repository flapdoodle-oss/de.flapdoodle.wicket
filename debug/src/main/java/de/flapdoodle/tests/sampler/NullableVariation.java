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
package de.flapdoodle.tests.sampler;

import java.util.Iterator;

public class NullableVariation<T> implements IVariation<T> {

	private final IVariation<T> _wrapped;

	public NullableVariation(IVariation<T> wrapped) {
		_wrapped = wrapped;
	}

	@Override
	public Iterator<T> iterator() {
		return new NullFirstIterator(_wrapped.iterator());
	}
	
	static class NullFirstIterator<T> implements Iterator<T> {

		private final Iterator<T> _wrapped;
		boolean shouldReturnNull=true;

		public NullFirstIterator(Iterator<T> wrapped) {
			_wrapped = wrapped;
		}

		@Override
		public boolean hasNext() {
			if (shouldReturnNull) return true;
			return _wrapped.hasNext();
		}

		@Override
		public T next() {
			if (shouldReturnNull) {
				shouldReturnNull=false;
				return null;
			}
			return _wrapped.next();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("does not make any sense");
		}
		
	}

}
