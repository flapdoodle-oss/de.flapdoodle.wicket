package de.flapdoodle.tests.sample;

import junit.framework.Assert;

import org.junit.Test;


public class TestSample {

	@Test
	public void join() {
		IJoinedSample<Integer, Integer, Simple<Integer>> sample = Sample.of(3, Simple.of(2));
		IJoinedSample<String, Integer, IJoinedSample<Integer, Integer, Simple<Integer>>> twoPlusOne = Sample.of("Hu", sample);
		
		Assert.assertEquals(Integer.valueOf(2), twoPlusOne.next().next().get());
		
	}
}
