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
