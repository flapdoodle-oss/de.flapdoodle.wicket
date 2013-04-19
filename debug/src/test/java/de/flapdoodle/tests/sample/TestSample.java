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
