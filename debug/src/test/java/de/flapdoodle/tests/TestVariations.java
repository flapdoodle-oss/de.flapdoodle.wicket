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
package de.flapdoodle.tests;

import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.util.lang.Objects;

import de.flapdoodle.tests.sample.IJoinedSample;
import de.flapdoodle.tests.sample.ISample;
import de.flapdoodle.tests.sample.Sample;
import de.flapdoodle.tests.sampler.Variation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestVariations {

	@Test
	public void combine() {
		Set<IJoinedSample<Boolean, Boolean, ISample<Boolean>>> values=new HashSet<IJoinedSample<Boolean,Boolean,ISample<Boolean>>>();
		
		IGenerator<Boolean, IJoinedSample<Boolean, Boolean, ISample<Boolean>>> variations = Variations.of(Variation.bool(),Variations.of(Variation.bool()));
		assertThat(variations.hasNext()).isTrue();
		values.add(variations.get());
		assertThat(variations.hasNext()).isTrue();
		values.add(variations.get());
		assertThat(variations.hasNext()).isTrue();
		values.add(variations.get());
		assertThat(variations.hasNext()).isTrue();
		values.add(variations.get());
		assertThat(variations.hasNext()).isFalse();

		assertThat(values.size()).isEqualTo((Integer) 4);

		assertThat(values.remove(Sample.of(true, Sample.of(true)))).isNotNull();
		assertThat(values.remove(Sample.of(false, Sample.of(true)))).isNotNull();
		assertThat(values.remove(Sample.of(true, Sample.of(false)))).isNotNull();
		assertThat(values.remove(Sample.of(false, Sample.of(false)))).isNotNull();

		assertThat(values.isEmpty()).isTrue();
	}

	@Test
	public void six() {
		Set<String> values=new HashSet<String>();
		
		IGenerator<String, IJoinedSample<String, Integer, ISample<Integer>>> variations = Variations.of(Variation.of("A","B","C"),Variations.of(Variation.of(1,2,3)));
		assertThat(variations.hasNext()).isTrue();
		values.add(asString(variations.get()));
		assertThat(variations.hasNext()).isTrue();
		values.add(asString(variations.get()));
		assertThat(variations.hasNext()).isTrue();
		values.add(asString(variations.get()));
		assertThat(variations.hasNext()).isTrue();
		values.add(asString(variations.get()));
		assertThat(variations.hasNext()).isTrue();
		values.add(asString(variations.get()));
		assertThat(variations.hasNext()).isTrue();
		values.add(asString(variations.get()));
		assertThat(variations.hasNext()).isTrue();
		values.add(asString(variations.get()));
		assertThat(variations.hasNext()).isTrue();
		values.add(asString(variations.get()));
		assertThat(variations.hasNext()).isTrue();
		values.add(asString(variations.get()));
		assertThat(variations.hasNext()).isFalse();

		assertThat(values.size()).isEqualTo((Integer) 9);

		assertThat(values.remove("A1")).isNotNull();
		assertThat(values.remove("A2")).isNotNull();
		assertThat(values.remove("A3")).isNotNull();
		assertThat(values.remove("B1")).isNotNull();
		assertThat(values.remove("B2")).isNotNull();
		assertThat(values.remove("B3")).isNotNull();
		assertThat(values.remove("C1")).isNotNull();
		assertThat(values.remove("C2")).isNotNull();
		assertThat(values.remove("C3")).isNotNull();
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

		assertThat(notEquals.size()).isEqualTo((Integer) 2);
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
