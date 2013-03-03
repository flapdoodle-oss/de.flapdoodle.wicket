package de.flapdoodle.tests.sampler;

import java.util.Collection;
import java.util.Iterator;


public class ImmutableListIterator<T> implements Iterator<T> {

	private Iterator<T> _iterator;

	public ImmutableListIterator(Collection<T> source) {
		_iterator = source.iterator();
	}

	@Override
	public boolean hasNext() {
		return _iterator.hasNext();
	}

	@Override
	public T next() {
		return _iterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("remove does not make any sense");
	}
	
	
}
