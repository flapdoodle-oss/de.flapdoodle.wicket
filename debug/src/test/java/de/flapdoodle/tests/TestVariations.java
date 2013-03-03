package de.flapdoodle.tests;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import de.flapdoodle.tests.sample.IJoinedSample;
import de.flapdoodle.tests.sample.ISample;
import de.flapdoodle.tests.sample.Sample;
import de.flapdoodle.tests.sampler.BoolVariation;


public class TestVariations {

	@Test
	public void combine() {
		Set<IJoinedSample<Boolean, Boolean, ISample<Boolean>>> values=new HashSet<IJoinedSample<Boolean,Boolean,ISample<Boolean>>>();
		
		IGenerator<Boolean, IJoinedSample<Boolean, Boolean, ISample<Boolean>>> variations = Variations.of(new BoolVariation(),Variations.of(new BoolVariation()));
		Assert.assertTrue(variations.hasNext());
		values.add(variations.get());
		Assert.assertTrue(variations.hasNext());
		values.add(variations.get());
		Assert.assertTrue(variations.hasNext());
		values.add(variations.get());
		Assert.assertTrue(variations.hasNext());
		values.add(variations.get());
		Assert.assertFalse(variations.hasNext());
		
		Assert.assertEquals(4,values.size());
		
		Assert.assertNotNull(values.remove(Sample.of(true, Sample.of(true))));
		Assert.assertNotNull(values.remove(Sample.of(false, Sample.of(true))));
		Assert.assertNotNull(values.remove(Sample.of(true, Sample.of(false))));
		Assert.assertNotNull(values.remove(Sample.of(false, Sample.of(false))));
		
		Assert.assertTrue(values.isEmpty());
	}
}
