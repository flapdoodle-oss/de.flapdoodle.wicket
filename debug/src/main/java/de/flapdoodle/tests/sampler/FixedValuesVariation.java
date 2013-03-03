package de.flapdoodle.tests.sampler;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import de.flapdoodle.tests.Lists;

public class FixedValuesVariation<T> implements IVariation<T> {

	private Collection<T> _values;

	public FixedValuesVariation(T... values) {
		_values = Collections.unmodifiableCollection(Lists.newArrayList(values));
	}

	@Override
	public Iterator<T> iterator() {
		return new ImmutableListIterator<T>(_values);
	}

}
