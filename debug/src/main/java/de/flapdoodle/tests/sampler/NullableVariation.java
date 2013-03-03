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
