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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

public class TestSampler {

	@Test
	public void booleanHasTwo() {
		Iterator<Boolean> iterator = new BoolVariation().iterator();
		Set<Boolean> values = new HashSet<Boolean>();

		Assert.assertTrue(iterator.hasNext());
		values.add(iterator.next());
		Assert.assertTrue(iterator.hasNext());
		values.add(iterator.next());
		Assert.assertFalse(iterator.hasNext());

		Assert.assertTrue(values.remove(Boolean.TRUE));
		Assert.assertTrue(values.remove(Boolean.FALSE));
		Assert.assertEquals(0, values.size());
	}

	@Test
	public void booleanWithNullableHasMore() {
		Iterator<Boolean> iterator = new NullableVariation<Boolean>(new BoolVariation()).iterator();
		Set<Boolean> values = new HashSet<Boolean>();

		Assert.assertTrue(iterator.hasNext());
		values.add(iterator.next());
		Assert.assertTrue(iterator.hasNext());
		values.add(iterator.next());
		Assert.assertTrue(iterator.hasNext());
		values.add(iterator.next());
		Assert.assertFalse(iterator.hasNext());

		Assert.assertTrue(values.remove(Boolean.TRUE));
		Assert.assertTrue(values.remove(Boolean.FALSE));
		Assert.assertTrue(values.remove(null));
		Assert.assertEquals(0, values.size());
	}

	@Test
	public void fixedValues() {
		Iterator<Integer> iterator = new FixedValuesVariation<Integer>(1, 2, 3).iterator();
		Assert.assertTrue(iterator.hasNext());
		Assert.assertEquals(Integer.valueOf(1), iterator.next());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertEquals(Integer.valueOf(2), iterator.next());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertEquals(Integer.valueOf(3), iterator.next());
	}
}
