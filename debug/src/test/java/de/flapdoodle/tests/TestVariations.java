package de.flapdoodle.tests;

import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.util.lang.Objects;
import org.junit.Assert;
import org.junit.Test;

import de.flapdoodle.tests.sample.IJoinedSample;
import de.flapdoodle.tests.sample.ISample;
import de.flapdoodle.tests.sample.Sample;
import de.flapdoodle.tests.sampler.Variation;


public class TestVariations {

	@Test
	public void combine() {
		Set<IJoinedSample<Boolean, Boolean, ISample<Boolean>>> values=new HashSet<IJoinedSample<Boolean,Boolean,ISample<Boolean>>>();
		
		IGenerator<Boolean, IJoinedSample<Boolean, Boolean, ISample<Boolean>>> variations = Variations.of(Variation.bool(),Variations.of(Variation.bool()));
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
	
	public void six() {
		Set<String> values=new HashSet<String>();
		
		IGenerator<String, IJoinedSample<String, Integer, ISample<Integer>>> variations = Variations.of(Variation.of("A","B","C"),Variations.of(Variation.of(1,2,3)));
		Assert.assertTrue(variations.hasNext());
		values.add(asString(variations.get()));
		Assert.assertTrue(variations.hasNext());
		values.add(asString(variations.get()));
		Assert.assertTrue(variations.hasNext());
		values.add(asString(variations.get()));
		Assert.assertTrue(variations.hasNext());
		values.add(asString(variations.get()));
		Assert.assertTrue(variations.hasNext());
		values.add(asString(variations.get()));
		Assert.assertTrue(variations.hasNext());
		values.add(asString(variations.get()));
		Assert.assertTrue(variations.hasNext());
		values.add(asString(variations.get()));
		Assert.assertTrue(variations.hasNext());
		values.add(asString(variations.get()));
		Assert.assertTrue(variations.hasNext());
		values.add(asString(variations.get()));
		Assert.assertFalse(variations.hasNext());
		
		Assert.assertEquals(9,values.size());
		
		Assert.assertNotNull(values.remove("A1"));
		Assert.assertNotNull(values.remove("A2"));
		Assert.assertNotNull(values.remove("A3"));
		Assert.assertNotNull(values.remove("B1"));
		Assert.assertNotNull(values.remove("B2"));
		Assert.assertNotNull(values.remove("B3"));
		Assert.assertNotNull(values.remove("C1"));
		Assert.assertNotNull(values.remove("C2"));
		Assert.assertNotNull(values.remove("C3"));
	}

	@Test
	public void usage() {
		Set<IJoinedSample<Integer, Boolean, ISample<Boolean>>> notEquals=new HashSet<IJoinedSample<Integer,Boolean,ISample<Boolean>>>();
		
		IGenerator<Integer, IJoinedSample<Integer, Boolean, ISample<Boolean>>> variations = Variations.of(Variation.of(1,2,3,4,5,6,7),Variations.of(Variation.bool()));
		
		while (variations.hasNext()) {
			IJoinedSample<Integer, Boolean, ISample<Boolean>> sample = variations.get();
			int number=sample.get();
			boolean flag=sample.next().get();
			
			if (!Objects.equal(one(number,flag), two(number,flag))) {
				notEquals.add(sample);
			}
		}
		
		Assert.assertEquals(""+notEquals, 2,notEquals.size());
	}
	
	private int one(int number, boolean flag) {
		if (flag) {
			if (number==(2*(number/2))) return number;
		}
		return -1;
	}
	
	private int two(int number, boolean flag) {
		if (number==3) return 5;
		
		if (number==(2*(number/2))) return flag ? number : -1;
		return -1;
	}
	
	private String asString(IJoinedSample<String, Integer, ISample<Integer>> sample) {
		return sample.get()+""+sample.next().get();
	}
}
