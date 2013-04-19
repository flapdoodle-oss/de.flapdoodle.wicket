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
